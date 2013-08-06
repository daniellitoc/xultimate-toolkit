/**
 *  Copyright 2008 ThimbleWare Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.thimbleware.jmemcached.protocol;


import com.thimbleware.jmemcached.Cache;
import com.thimbleware.jmemcached.CacheElement;
import com.thimbleware.jmemcached.Key;
import com.thimbleware.jmemcached.protocol.exceptions.UnknownCommandException;

import org.jboss.netty.channel.*;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

// TODO implement flush_all delay

/**
 * The actual command handler, which is responsible for processing the CommandMessage instances
 * that are inbound from the protocol decoders.
 * <p/>
 * One instance is shared among the entire pipeline, since this handler is stateless, apart from some globals
 * for the entire daemon.
 * <p/>
 * The command handler produces ResponseMessages which are destined for the response encoder.
 * 注：添加主机信息输出，便于测试。
 */
@ChannelHandler.Sharable
public final class MemcachedCommandHandler<CACHE_ELEMENT extends CacheElement> extends SimpleChannelUpstreamHandler {

    final Logger logger = LoggerFactory.getLogger(MemcachedCommandHandler.class);

    public final AtomicInteger curr_conns = new AtomicInteger();
    public final AtomicInteger total_conns = new AtomicInteger();

    /**
     * The following state variables are universal for the entire daemon. These are used for statistics gathering.
     * In order for these values to work properly, the handler _must_ be declared with a ChannelPipelineCoverage
     * of "all".
     */
    public final String version;

    public final int idle_limit;
    public final boolean verbose;



    /**
     * The actual physical data storage.
     */
    private final Cache<CACHE_ELEMENT> cache;

    /**
     * The channel group for the entire daemon, used for handling global cleanup on shutdown.
     */
    private final DefaultChannelGroup channelGroup;

    /**
     * Construct the server session handler
     *
     * @param cache            the cache to use
     * @param memcachedVersion the version string to return to clients
     * @param verbosity        verbosity level for debugging
     * @param idle             how long sessions can be idle for
     * @param channelGroup
     */
    @SuppressWarnings("unchecked")
	public MemcachedCommandHandler(@SuppressWarnings("rawtypes") Cache cache, String memcachedVersion, boolean verbosity, int idle, DefaultChannelGroup channelGroup) {
        this.cache = cache;

        version = memcachedVersion;
        verbose = verbosity;
        idle_limit = idle;
        this.channelGroup = channelGroup;
    }


    /**
     * On open we manage some statistics, and add this connection to the channel group.
     *
     * @param channelHandlerContext
     * @param channelStateEvent
     * @throws Exception
     */
    @Override
    public void channelOpen(ChannelHandlerContext channelHandlerContext, ChannelStateEvent channelStateEvent) throws Exception {
        total_conns.incrementAndGet();
        curr_conns.incrementAndGet();
        channelGroup.add(channelHandlerContext.getChannel());
    }

    /**
     * On close we manage some statistics, and remove this connection from the channel group.
     *
     * @param channelHandlerContext
     * @param channelStateEvent
     * @throws Exception
     */
    @Override
    public void channelClosed(ChannelHandlerContext channelHandlerContext, ChannelStateEvent channelStateEvent) throws Exception {
        curr_conns.decrementAndGet();
        channelGroup.remove(channelHandlerContext.getChannel());
    }


    /**
     * The actual meat of the matter.  Turn CommandMessages into executions against the physical cache, and then
     * pass on the downstream messages.
     *
     * @param channelHandlerContext
     * @param messageEvent
     * @throws Exception
     */

    @Override
    @SuppressWarnings("unchecked")
    public void messageReceived(ChannelHandlerContext channelHandlerContext, MessageEvent messageEvent) throws Exception {
        if (!(messageEvent.getMessage() instanceof CommandMessage)) {
            // Ignore what this encoder can't encode.
            channelHandlerContext.sendUpstream(messageEvent);
            return;
        }

        CommandMessage<CACHE_ELEMENT> command = (CommandMessage<CACHE_ELEMENT>) messageEvent.getMessage();
        Op cmd = command.op;
        int cmdKeysSize = command.keys == null ? 0 : command.keys.size();

        // first process any messages in the delete queue
        cache.asyncEventPing();

        // now do the real work
        if (this.verbose) {
            StringBuilder log = new StringBuilder();
            // TODO 追加主机名称
            log.append(messageEvent.getChannel().getLocalAddress());
            log.append(" ");
            log.append(cmd);
            if (command.element != null) {
                log.append(" ").append(command.element.getKey());
            }
            for (int i = 0; i < cmdKeysSize; i++) {
                log.append(" ").append(command.keys.get(i));
            }
            logger.info(log.toString());
        }

        Channel channel = messageEvent.getChannel();
        if (cmd == null) handleNoOp(channelHandlerContext, command);
        else
        switch (cmd) {
            case GET:
            case GETS:
                handleGets(channelHandlerContext, command, channel);
                break;
            case APPEND:
                handleAppend(channelHandlerContext, command, channel);
                break;
            case PREPEND:
                handlePrepend(channelHandlerContext, command, channel);
                break;
            case DELETE:
                handleDelete(channelHandlerContext, command, channel);
                break;
            case DECR:
                handleDecr(channelHandlerContext, command, channel);
                break;
            case INCR:
                handleIncr(channelHandlerContext, command, channel);
                break;
            case REPLACE:
                handleReplace(channelHandlerContext, command, channel);
                break;
            case ADD:
                handleAdd(channelHandlerContext, command, channel);
                break;
            case SET:
                handleSet(channelHandlerContext, command, channel);
                break;
            case CAS:
                handleCas(channelHandlerContext, command, channel);
                break;
            case STATS:
                handleStats(channelHandlerContext, command, cmdKeysSize, channel);
                break;
            case VERSION:
                handleVersion(channelHandlerContext, command, channel);
                break;
            case QUIT:
                handleQuit(channel);
                break;
            case FLUSH_ALL:
                handleFlush(channelHandlerContext, command, channel);
                break;
            case VERBOSITY:
                handleVerbosity(channelHandlerContext, command, channel);
                break;
            default:
                 throw new UnknownCommandException("unknown command");
        }
    }

    @SuppressWarnings("rawtypes")
    protected void handleNoOp(ChannelHandlerContext channelHandlerContext, CommandMessage<CACHE_ELEMENT> command) {
        Channels.fireMessageReceived(channelHandlerContext, new ResponseMessage(command));
    }

    @SuppressWarnings("rawtypes")
    protected void handleFlush(ChannelHandlerContext channelHandlerContext, CommandMessage<CACHE_ELEMENT> command, Channel channel) {
        Channels.fireMessageReceived(channelHandlerContext, new ResponseMessage(command).withFlushResponse(cache.flush_all(command.time)), channel.getRemoteAddress());
    }
    
    @SuppressWarnings("rawtypes")
	protected void handleVerbosity(ChannelHandlerContext channelHandlerContext, CommandMessage command, Channel channel) {
    	//TODO set verbosity mode
    	Channels.fireMessageReceived(channelHandlerContext, new ResponseMessage(command), channel.getRemoteAddress());
 	}

    protected void handleQuit(Channel channel) {
        channel.disconnect();
    }

    @SuppressWarnings("unchecked")
	protected void handleVersion(ChannelHandlerContext channelHandlerContext, CommandMessage<CACHE_ELEMENT> command, Channel channel) {
        @SuppressWarnings("rawtypes")
		ResponseMessage responseMessage = new ResponseMessage(command);
        responseMessage.version = version;
        Channels.fireMessageReceived(channelHandlerContext, responseMessage, channel.getRemoteAddress());
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	protected void handleStats(ChannelHandlerContext channelHandlerContext, CommandMessage<CACHE_ELEMENT> command, int cmdKeysSize, Channel channel) {
        String option = "";
        if (cmdKeysSize > 0) {
            option = command.keys.get(0).bytes.toString();
        }
        Channels.fireMessageReceived(channelHandlerContext, new ResponseMessage(command).withStatResponse(cache.stat(option)), channel.getRemoteAddress());
    }

    @SuppressWarnings("rawtypes")
    protected void handleDelete(ChannelHandlerContext channelHandlerContext, CommandMessage<CACHE_ELEMENT> command, Channel channel) {
        Cache.DeleteResponse dr = cache.delete(command.keys.get(0), command.time);
        Channels.fireMessageReceived(channelHandlerContext, new ResponseMessage(command).withDeleteResponse(dr), channel.getRemoteAddress());
    }
    
    @SuppressWarnings("rawtypes")
    protected void handleDecr(ChannelHandlerContext channelHandlerContext, CommandMessage<CACHE_ELEMENT> command, Channel channel) {
        Integer incrDecrResp = cache.get_add(command.keys.get(0), -1 * command.incrAmount);
        Channels.fireMessageReceived(channelHandlerContext, new ResponseMessage(command).withIncrDecrResponse(incrDecrResp), channel.getRemoteAddress());
    }

    @SuppressWarnings("rawtypes")
    protected void handleIncr(ChannelHandlerContext channelHandlerContext, CommandMessage<CACHE_ELEMENT> command, Channel channel) {
        Integer incrDecrResp = cache.get_add(command.keys.get(0), command.incrAmount); // TODO support default value and expiry!!
        Channels.fireMessageReceived(channelHandlerContext, new ResponseMessage(command).withIncrDecrResponse(incrDecrResp), channel.getRemoteAddress());
    }

    @SuppressWarnings("rawtypes")
    protected void handlePrepend(ChannelHandlerContext channelHandlerContext, CommandMessage<CACHE_ELEMENT> command, Channel channel) {
        Cache.StoreResponse ret;
        ret = cache.prepend(command.element);
        Channels.fireMessageReceived(channelHandlerContext, new ResponseMessage(command).withResponse(ret), channel.getRemoteAddress());
    }

    @SuppressWarnings("rawtypes")
    protected void handleAppend(ChannelHandlerContext channelHandlerContext, CommandMessage<CACHE_ELEMENT> command, Channel channel) {
        Cache.StoreResponse ret;
        ret = cache.append(command.element);
        Channels.fireMessageReceived(channelHandlerContext, new ResponseMessage(command).withResponse(ret), channel.getRemoteAddress());
    }

    @SuppressWarnings("rawtypes")
    protected void handleReplace(ChannelHandlerContext channelHandlerContext, CommandMessage<CACHE_ELEMENT> command, Channel channel) {
        Cache.StoreResponse ret;
        ret = cache.replace(command.element);
        Channels.fireMessageReceived(channelHandlerContext, new ResponseMessage(command).withResponse(ret), channel.getRemoteAddress());
    }

    @SuppressWarnings("rawtypes")
    protected void handleAdd(ChannelHandlerContext channelHandlerContext, CommandMessage<CACHE_ELEMENT> command, Channel channel) {
        Cache.StoreResponse ret;
        ret = cache.add(command.element);
        Channels.fireMessageReceived(channelHandlerContext, new ResponseMessage(command).withResponse(ret), channel.getRemoteAddress());
    }

    @SuppressWarnings("rawtypes")
    protected void handleCas(ChannelHandlerContext channelHandlerContext, CommandMessage<CACHE_ELEMENT> command, Channel channel) {
        Cache.StoreResponse ret;
        ret = cache.cas(command.cas_key, command.element);
        Channels.fireMessageReceived(channelHandlerContext, new ResponseMessage(command).withResponse(ret), channel.getRemoteAddress());
    }

    @SuppressWarnings("rawtypes")
    protected void handleSet(ChannelHandlerContext channelHandlerContext, CommandMessage<CACHE_ELEMENT> command, Channel channel) {
        Cache.StoreResponse ret;
        ret = cache.set(command.element);
        Channels.fireMessageReceived(channelHandlerContext, new ResponseMessage(command).withResponse(ret), channel.getRemoteAddress());
    }

    protected void handleGets(ChannelHandlerContext channelHandlerContext, CommandMessage<CACHE_ELEMENT> command, Channel channel) {
        Key[] keys = new Key[command.keys.size()];
        keys = command.keys.toArray(keys);
        CACHE_ELEMENT[] results = get(keys);
        ResponseMessage<CACHE_ELEMENT> resp = new ResponseMessage<CACHE_ELEMENT>(command).withElements(results);
        Channels.fireMessageReceived(channelHandlerContext, resp, channel.getRemoteAddress());
    }

    /**
     * Get an element from the cache
     *
     * @param keys the key for the element to lookup
     * @return the element, or 'null' in case of cache miss.
     */
    private CACHE_ELEMENT[] get(Key... keys) {
        return cache.get(keys);
    }


    /**
     * @return the current time in seconds (from epoch), used for expiries, etc.
     */
    @SuppressWarnings("unused")
	private static int Now() {
        return (int) (System.currentTimeMillis() / 1000);
    }
}