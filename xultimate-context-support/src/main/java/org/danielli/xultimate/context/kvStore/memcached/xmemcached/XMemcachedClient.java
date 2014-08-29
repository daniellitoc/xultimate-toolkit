package org.danielli.xultimate.context.kvStore.memcached.xmemcached;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.CASOperation;
import net.rubyeye.xmemcached.CommandFactory;
import net.rubyeye.xmemcached.Counter;
import net.rubyeye.xmemcached.GetsResponse;
import net.rubyeye.xmemcached.KeyProvider;
import net.rubyeye.xmemcached.MemcachedClientCallable;
import net.rubyeye.xmemcached.MemcachedClientStateListener;
import net.rubyeye.xmemcached.MemcachedSessionLocator;
import net.rubyeye.xmemcached.XMemcachedClientMBean;
import net.rubyeye.xmemcached.auth.AuthInfo;
import net.rubyeye.xmemcached.buffer.BufferAllocator;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.impl.ReconnectRequest;
import net.rubyeye.xmemcached.networking.Connector;
import net.rubyeye.xmemcached.transcoders.Transcoder;
import net.rubyeye.xmemcached.utils.Protocol;

import org.danielli.xultimate.util.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XMemcachedClient implements XMemcachedClientMBean {
	
	protected static Logger logger = LoggerFactory.getLogger(XMemcachedClient.class);
	
	protected static final Map<InetSocketAddress, String> EMPTY_VERSION_MAP = Collections.unmodifiableMap(new HashMap<InetSocketAddress, String>(0));
	
	protected static final Map<InetSocketAddress, Map<String, String>> EMPTY_ALL_STAT_MAP = Collections.unmodifiableMap(new HashMap<InetSocketAddress, Map<String, String>>(0));

	protected static final Map<String, String> EMPTY_STAT_MAP = Collections.unmodifiableMap(new HashMap<String, String>(0));
	
	protected static final Map<String, ? extends Object> EMPTY_MULTI_MAP = Collections.unmodifiableMap(new HashMap<String, Object>(0));
	
	private net.rubyeye.xmemcached.XMemcachedClient xMemcachedClient;
	
	public XMemcachedClient(net.rubyeye.xmemcached.XMemcachedClient xMemcachedClient) {
		this.xMemcachedClient = xMemcachedClient;
	}
	
	public net.rubyeye.xmemcached.XMemcachedClient getxMemcachedClient() {
		return xMemcachedClient;
	}
	
	/**
	 * Set the merge factor,this factor determins how many 'get' commands would
	 * be merge to one multi-get command.default is 150
	 * 
	 * @param mergeFactor
	 */
	public final void setMergeFactor(final int mergeFactor) {
		xMemcachedClient.setMergeFactor(mergeFactor);
	}
	
	/**
	 * Returns maximum number of timeout exception for closing connection.
	 * 
	 * @return
	 */
	public int getTimeoutExceptionThreshold() {
		return xMemcachedClient.getTimeoutExceptionThreshold();
	}
	
	/**
	 * Set maximum number of timeout exception for closing connection.You can
	 * set it to be a large value to disable this feature.
	 * 
	 * @see #DEFAULT_MAX_TIMEOUTEXCEPTION_THRESHOLD
	 * @param timeoutExceptionThreshold
	 */
	public void setTimeoutExceptionThreshold(int timeoutExceptionThreshold) {
		xMemcachedClient.setTimeoutExceptionThreshold(timeoutExceptionThreshold);
	}
	
	/**
	 * Remove current namespace set for this memcached client.It must begin with
	 * {@link #beginWithNamespace(String)} method.
	 * @see #beginWithNamespace(String)
	 */
	public void endWithNamespace() {
		xMemcachedClient.endWithNamespace();
	}
	
	/**
	 * set current namespace for following operations with memcached client.It
	 * must be ended with {@link #endWithNamespace()} method.For example:
	 * <pre>
	 *    memcachedClient.beginWithNamespace(userId);
	 *    try{
	 *      memcachedClient.set("username",0,username);
	 *      memcachedClient.set("email",0,email);
	 *    }finally{
	 *      memcachedClient.endWithNamespace();
	 *    }
	 * </pre>
	 * @see #endWithNamespace()
	 * @see #withNamespace(String, MemcachedClientCallable)
	 * @param ns
	 */
	public void beginWithNamespace(String ns) {
		xMemcachedClient.beginWithNamespace(ns);
	}
	
	public KeyProvider getKeyProvider() {
		return xMemcachedClient.getKeyProvider();
	}
	
	/**
	 * Set a key provider for pre-processing keys before sending them to
	 * memcached.
	 * 
	 * @since 1.3.8
	 * @param keyProvider
	 */
	public void setKeyProvider(KeyProvider keyProvider) {
		xMemcachedClient.setKeyProvider(keyProvider);
	}
	
	public final MemcachedSessionLocator getSessionLocator() {
		return xMemcachedClient.getSessionLocator();
	}
	
	public final CommandFactory getCommandFactory() {
		return xMemcachedClient.getCommandFactory();
	}
	
	/**
	 * Return the cache instance name
	 * 
	 * @return
	 */
	public String getName() {
		return xMemcachedClient.getName();
	}

	/**
	 * Set cache instance name
	 * 
	 * @param name
	 */
	public void setName(String name) {
		xMemcachedClient.setName(name);
	}
	
	/**
	 * Get the connect timeout
	 * 
	 */
	public long getConnectTimeout() {
		return xMemcachedClient.getConnectTimeout();
	}
	
	/**
	 * Set the connect timeout,default is 1 minutes
	 * 
	 * @param connectTimeout
	 */
	public void setConnectTimeout(long connectTimeout) {
		xMemcachedClient.setConnectTimeout(connectTimeout);
	}
	
	/**
	 * Whether to enable heart beat
	 * 
	 * @param enableHeartBeat
	 *            if true,then enable heartbeat,true by default
	 */
	public void setEnableHeartBeat(boolean enableHeartBeat) {
		xMemcachedClient.setEnableHeartBeat(enableHeartBeat);
	}
	
	/**
	 * get operation timeout setting
	 * 
	 * @return
	 */
	public final long getOpTimeout() {
		return xMemcachedClient.getOpTimeout();
	}
	
	/**
	 * set operation timeout,default is one second.
	 * 
	 * @param opTimeout
	 */
	public final void setOpTimeout(long opTimeout) {
		xMemcachedClient.setOpTimeout(opTimeout);
	}
	
	/**
	 * If the memcached dump or network error cause connection closed,xmemcached
	 * would try to heal the connection.The interval between reconnections is 2
	 * seconds by default. You can change that value by this method.
	 * 
	 * @param healConnectionInterval
	 *            MILLISECONDS
	 */
	public void setHealSessionInterval(long healConnectionInterval) {
		xMemcachedClient.setHealSessionInterval(healConnectionInterval);
	}
	
	/**
	 * Return the default heal session interval in milliseconds
	 * 
	 * @return
	 */
	public long getHealSessionInterval() {
		return xMemcachedClient.getHealSessionInterval();
	}
	
	/**
	 * return current all auth info
	 * 
	 * @return Auth info map,key is memcached server address,and value is the
	 *         auth info for the key.
	 */
	public Map<InetSocketAddress, AuthInfo> getAuthInfoMap() {
		return xMemcachedClient.getAuthInfoMap();
	}
	
	/**
	 * Configure auth info
	 * 
	 * @param map
	 *            Auth info map,key is memcached server address,and value is the
	 *            auth info for the key.
	 */
	public void setAuthInfoMap(Map<InetSocketAddress, AuthInfo> map) {
		xMemcachedClient.setAuthInfoMap(map);
	}
	
	/**
	 * return the session manager
	 * 
	 * @return
	 */
	public final Connector getConnector() {
		return xMemcachedClient.getConnector();
	}
	
	/**
	 * Enable/Disable merge many command's buffers to one big buffer fit
	 * socket's send buffer size.Default is true.Recommend true.
	 * 
	 * @param optimizeMergeBuffer
	 */
	public final void setOptimizeMergeBuffer(final boolean optimizeMergeBuffer) {
		xMemcachedClient.setOptimizeMergeBuffer(optimizeMergeBuffer);
	}
	
	/**
	 * @return
	 */
	public boolean isShutdown() {
		return xMemcachedClient.isShutdown();
	}
	
	/**
	 * Aadd a memcached server,the thread call this method will be blocked until
	 * the connecting operations completed(success or fail)
	 * 
	 * @param server
	 *            host string
	 * @param port
	 *            port number
	 */
	public final void addServer(final String server, final int port) throws IOException {
		xMemcachedClient.addServer(server, port);
	}
	
	/**
	 * add a memcached server to MemcachedClient
	 * 
	 * @param server
	 * @param port
	 * @param weight
	 * @throws IOException
	 */
	public final void addServer(final String server, final int port, int weight) throws IOException {
		xMemcachedClient.addServer(server, port, weight);
	}
	
	/**
	 * Add a memcached server,the thread call this method will be blocked until
	 * the connecting operations completed(success or fail)
	 * 
	 * @param inetSocketAddress
	 *            memcached server's socket address
	 */
	public final void addServer(final InetSocketAddress inetSocketAddress) throws IOException {
		xMemcachedClient.addServer(inetSocketAddress);
	}
	
	public void addServer(final InetSocketAddress inetSocketAddress, int weight) throws IOException {
		xMemcachedClient.addServer(inetSocketAddress, weight);
	}
	
	/**
	 * Add many memcached servers.You can call this method through JMX or
	 * program
	 * 
	 * @param host
	 *            String like [host1]:[port1] [host2]:[port2] ...
	 */
	public void addServer(String hostList) throws IOException {
		xMemcachedClient.addServer(hostList);
	}
	
	public void addOneServerWithWeight(String server, int weight) throws IOException {
		xMemcachedClient.addOneServerWithWeight(server, weight);
	}
	
	/**
	 * Get current server list.You can call this method through JMX or program
	 */
	public final List<String> getServersDescription() {
		return xMemcachedClient.getServersDescription();
	}
	
	public final void setServerWeight(String server, int weight) {
		xMemcachedClient.setServerWeight(server, weight);
	}
	
	/**
	 * Remove many memcached server
	 * 
	 * @param host
	 *            String like [host1]:[port1] [host2]:[port2] ...
	 */
	public void removeServer(String hostList) {
		xMemcachedClient.removeServer(hostList);
	}
	
	/**
	 * Enable/Disable merge many get commands to one multi-get command.true is
	 * to enable it,false is to disable it.Default is true.Recommend users to
	 * enable it.
	 * 
	 * @param optimizeGet
	 */
	public void setOptimizeGet(boolean optimizeGet) {
		xMemcachedClient.setOptimizeGet(optimizeGet);
	}
	
	/**
	 * Set the nio's ByteBuffer Allocator,use SimpleBufferAllocator by default.
	 * 
	 * 
	 * @param bufferAllocator
	 * @return
	 */
	@Deprecated
	public final void setBufferAllocator(final BufferAllocator bufferAllocator) {
		xMemcachedClient.setBufferAllocator(bufferAllocator);
	}
	
	protected void handleException(Exception e) {
		logger.error(e.getMessage(), e); 
	}
	
	/**
	 * Get value by key
	 * 
	 * @param <T>
	 * @param key
	 *            Key
	 * @param timeout
	 *            Operation timeout,if the method is not returned in this
	 *            time,throw TimeoutException
	 * @param transcoder
	 *            The value's transcoder
	 * @return
	 */
	public final <T> T get(final String key, final long timeout, final Transcoder<T> transcoder) {
		try {
			return xMemcachedClient.get(key, timeout, transcoder);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return null;
		}
	}
	
	public final <T> T get(final String key, final long timeout) {
		try {
			return xMemcachedClient.get(key, timeout);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return null;
		}
	}
	
	public final <T> T get(final String key, final Transcoder<T> transcoder) {
		try {
			return xMemcachedClient.get(key, transcoder);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return null;
		}
	}
	
	public final <T> T get(final String key) {
		try {
			return xMemcachedClient.get(key);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return null;
		}
	}
	
	/**
	 * Just like get,But it return a GetsResponse,include cas value for cas
	 * update.
	 * 
	 * @param <T>
	 * @param key
	 *            key
	 * @param timeout
	 *            operation timeout
	 * @param transcoder
	 * 
	 * @return GetsResponse
	 */
	public final <T> GetsResponse<T> gets(final String key, final long timeout, final Transcoder<T> transcoder) {
		try {
			return xMemcachedClient.gets(key, timeout, transcoder);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return null;
		}
	}
	
	/**
	 * @see #gets(String, long, Transcoder)
	 * @param <T>
	 * @param key
	 * @return
	 */
	public final <T> GetsResponse<T> gets(final String key) {
		try {
			return xMemcachedClient.gets(key);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return null;
		}
	}
	
	/**
	 * @see #gets(String, long, Transcoder)
	 * @param <T>
	 * @param key
	 * @param timeout
	 * @return
	 */
	public final <T> GetsResponse<T> gets(final String key, final long timeout) {
		try {
			return xMemcachedClient.gets(key, timeout);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return null;
		}
	}
	
	/**
	 * @see #gets(String, long, Transcoder)
	 * @param <T>
	 * @param key
	 * @param transcoder
	 * @return
	 */
	public final <T> GetsResponse<T> gets(final String key, @SuppressWarnings("rawtypes") final Transcoder transcoder) {
		try {
			return xMemcachedClient.gets(key, transcoder);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return null;
		}
	}
	
	/**
	 * Bulk get items
	 * 
	 * @param <T>
	 * @param keyCollections
	 *            key collection
	 * @param opTimeout
	 *            opTimeout
	 * @param transcoder
	 *            Value transcoder
	 * @return Exists items map
	 */
	@SuppressWarnings("unchecked")
	public final <T> Map<String, T> get(final Collection<String> keyCollections, final long opTimeout, final Transcoder<T> transcoder) {
		try {
			if (!CollectionUtils.isEmpty(keyCollections)) return xMemcachedClient.get(keyCollections, opTimeout, transcoder);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
		}
		return (Map<String, T>) EMPTY_MULTI_MAP;
	}
	
	/**
	 * @see #get(Collection, long, Transcoder)
	 * @param <T>
	 * @param keyCollections
	 * @param transcoder
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public final <T> Map<String, T> get(final Collection<String> keyCollections, final Transcoder<T> transcoder) {
		try {
			if (!CollectionUtils.isEmpty(keyCollections)) return xMemcachedClient.get(keyCollections, transcoder);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
		}
		return (Map<String, T>) EMPTY_MULTI_MAP;
	}
	
	/**
	 * @see #get(Collection, long, Transcoder)
	 * @param <T>
	 * @param keyCollections
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public final <T> Map<String, T> get(final Collection<String> keyCollections) {
		try {
			if (!CollectionUtils.isEmpty(keyCollections)) return xMemcachedClient.get(keyCollections);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
		}
		return (Map<String, T>) EMPTY_MULTI_MAP;
	}
	
	/**
	 * @see #get(Collection, long, Transcoder)
	 * @param <T>
	 * @param keyCollections
	 * @param timeout
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public final <T> Map<String, T> get(final Collection<String> keyCollections, final long timeout) {
		try {
			if (!CollectionUtils.isEmpty(keyCollections)) return xMemcachedClient.get(keyCollections, timeout);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
		}
		return (Map<String, T>) EMPTY_MULTI_MAP;
	}
	
	/**
	 * Bulk gets items
	 * 
	 * @param <T>
	 * @param keyCollections
	 *            key collection
	 * @param opTime
	 *            Operation timeout
	 * @param transcoder
	 *            Value transcoder
	 * @return Exists GetsResponse map
	 * @see net.rubyeye.xmemcached.GetsResponse
	 */
	@SuppressWarnings("unchecked")
	public final <T> Map<String, GetsResponse<T>> gets(final Collection<String> keyCollections, final long opTime, final Transcoder<T> transcoder) {
		try {
			if (!CollectionUtils.isEmpty(keyCollections)) return xMemcachedClient.gets(keyCollections, opTime, transcoder);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
		}
		return (Map<String, GetsResponse<T>>) EMPTY_MULTI_MAP;
	}
	
	/**
	 * @see #gets(Collection, long, Transcoder)
	 * @param <T>
	 * @param keyCollections
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public final <T> Map<String, GetsResponse<T>> gets(final Collection<String> keyCollections) {
		try {
			if (!CollectionUtils.isEmpty(keyCollections)) return xMemcachedClient.gets(keyCollections);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
		}
		return (Map<String, GetsResponse<T>>) EMPTY_MULTI_MAP;
	}
	
	/**
	 * @see #gets(Collection, long, Transcoder)
	 * @param <T>
	 * @param keyCollections
	 * @param timeout
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public final <T> Map<String, GetsResponse<T>> gets(final Collection<String> keyCollections, final long timeout) {
		try {
			if (!CollectionUtils.isEmpty(keyCollections)) return xMemcachedClient.gets(keyCollections, timeout);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
		}
		return (Map<String, GetsResponse<T>>) EMPTY_MULTI_MAP;
	}
	
	/**
	 * @see #gets(Collection, long, Transcoder)
	 * @param <T>
	 * @param keyCollections
	 * @param transcoder
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public final <T> Map<String, GetsResponse<T>> gets(final Collection<String> keyCollections, final Transcoder<T> transcoder) {
		try {
			if (!CollectionUtils.isEmpty(keyCollections)) return xMemcachedClient.gets(keyCollections, transcoder);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
		}
		return (Map<String, GetsResponse<T>>) EMPTY_MULTI_MAP;
	}
	
	/**
	 * Store key-value item to memcached
	 * 
	 * @param <T>
	 * @param key
	 *            stored key
	 * @param exp
	 *            An expiration time, in seconds. Can be up to 30 days. After 30
	 *            days, is treated as a unix timestamp of an exact date.
	 * @param value
	 *            stored data
	 * @param transcoder
	 *            transocder
	 * @param timeout
	 *            operation timeout,in milliseconds
	 * @return boolean result
	 */
	public final <T> boolean set(final String key, final int exp, final T value, final Transcoder<T> transcoder, final long timeout) {
		try {
			return xMemcachedClient.set(key, exp, value, transcoder, timeout);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return false;
		}
	}
	
	/**
	 * Store key-value item to memcached,doesn't wait for reply
	 * 
	 * @param <T>
	 * @param key
	 *            stored key
	 * @param exp
	 *            An expiration time, in seconds. Can be up to 30 days. After 30
	 *            days, is treated as a unix timestamp of an exact date.
	 * @param value
	 *            stored data
	 * @param transcoder
	 *            transocder
	 */
	public void setWithNoReply(final String key, final int exp, final Object value) {
		try {
			xMemcachedClient.setWithNoReply(key, exp, value);
		} catch (InterruptedException | MemcachedException e) {
			handleException(e);
		}
	}
	
	/**
	 * @see #setWithNoReply(String, int, Object, Transcoder)
	 * @param <T>
	 * @param key
	 * @param exp
	 * @param value
	 * @param transcoder
	 */
	public <T> void setWithNoReply(final String key, final int exp, final T value, final Transcoder<T> transcoder) {
		try {
			xMemcachedClient.setWithNoReply(key, exp, value, transcoder);
		} catch (InterruptedException | MemcachedException e) {
			handleException(e);
		}
	}
	
	/**
	 * @see #set(String, int, Object, Transcoder, long)
	 */
	public final boolean set(final String key, final int exp, final Object value) {
		try {
			return xMemcachedClient.set(key, exp, value);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return false;
		}
	}
	
	/**
	 * @see #set(String, int, Object, Transcoder, long)
	 */
	public final boolean set(final String key, final int exp, final Object value, final long timeout) {
		try {
			return xMemcachedClient.set(key, exp, value, timeout);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return false;
		}
	}
	
	/**
	 * @see #set(String, int, Object, Transcoder, long)
	 */
	public final <T> boolean set(final String key, final int exp, final T value, final Transcoder<T> transcoder) {
		try {
			return xMemcachedClient.set(key, exp, value, transcoder);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return false;
		}
	}
	
	/**
	 * Add key-value item to memcached, success only when the key is not exists
	 * in memcached.
	 * 
	 * @param <T>
	 * @param key
	 * @param exp
	 *            An expiration time, in seconds. Can be up to 30 days. After 30
	 *            days, is treated as a unix timestamp of an exact date.
	 * @param value
	 * @param transcoder
	 * @param timeout
	 * @return boolean result
	 */
	public final <T> boolean add(final String key, final int exp, final T value, final Transcoder<T> transcoder, final long timeout) {
		try {
			return xMemcachedClient.add(key, exp, value, transcoder, timeout);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return false;
		}
	}
	
	/**
	 * @see #add(String, int, Object, Transcoder, long)
	 * @param key
	 * @param exp
	 * @param value
	 * @return
	 */
	public final boolean add(final String key, final int exp, final Object value) {
		try {
			return xMemcachedClient.add(key, exp, value);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return false;
		}
	}
	
	/**
	 * @see #add(String, int, Object, Transcoder, long)
	 * @param key
	 * @param exp
	 * @param value
	 * @param timeout
	 * @return
	 */
	public final boolean add(final String key, final int exp, final Object value, final long timeout) {
		try {
			return xMemcachedClient.add(key, exp, value, timeout);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return false;
		}
	}
	
	/**
	 * @see #add(String, int, Object, Transcoder, long)
	 * 
	 * @param <T>
	 * @param key
	 * @param exp
	 * @param value
	 * @param transcoder
	 * @return
	 */
	public final <T> boolean add(final String key, final int exp, final T value, final Transcoder<T> transcoder) {
		try {
			return xMemcachedClient.add(key, exp, value, transcoder);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return false;
		}
	}
	
	/**
	 * Add key-value item to memcached, success only when the key is not exists
	 * in memcached.This method doesn't wait for reply.
	 * 
	 * @param <T>
	 * @param key
	 * @param exp
	 *            An expiration time, in seconds. Can be up to 30 days. After 30
	 *            days, is treated as a unix timestamp of an exact date.
	 * @param value
	 * @param transcoder
	 * @return
	 */

	public void addWithNoReply(final String key, final int exp, final Object value) {
		try {
			xMemcachedClient.addWithNoReply(key, exp, value);
		} catch (InterruptedException | MemcachedException e) {
			handleException(e);
		}
	}
	
	/**
	 * @see #addWithNoReply(String, int, Object, Transcoder)
	 * @param <T>
	 * @param key
	 * @param exp
	 * @param value
	 * @param transcoder
	 */
	public <T> void addWithNoReply(final String key, final int exp, final T value, final Transcoder<T> transcoder) {
		try {
			xMemcachedClient.addWithNoReply(key, exp, value, transcoder);
		} catch (InterruptedException | MemcachedException e) {
			handleException(e);
		}
	}
	
	/**
	 * Replace the key's data item in memcached,success only when the key's data
	 * item is exists in memcached.This method doesn't wait for reply from
	 * server.
	 * 
	 * @param <T>
	 * @param key
	 * @param exp
	 *            An expiration time, in seconds. Can be up to 30 days. After 30
	 *            days, is treated as a unix timestamp of an exact date.
	 * @param value
	 * @param transcoder
	 */
	public void replaceWithNoReply(final String key, final int exp, final Object value) {
		try {
			xMemcachedClient.replaceWithNoReply(key, exp, value);
		} catch (InterruptedException | MemcachedException e) {
			handleException(e);
		}
	}
	
	/**
	 * @see #replaceWithNoReply(String, int, Object, Transcoder)
	 * @param <T>
	 * @param key
	 * @param exp
	 * @param value
	 * @param transcoder
	 */
	public <T> void replaceWithNoReply(final String key, final int exp, final T value, final Transcoder<T> transcoder) {
		try {
			xMemcachedClient.replaceWithNoReply(key, exp, value, transcoder);
		} catch (InterruptedException | MemcachedException e) {
			handleException(e);
		}
	}
	
	/**
	 * Replace the key's data item in memcached,success only when the key's data
	 * item is exists in memcached.This method will wait for reply from server.
	 * 
	 * @param <T>
	 * @param key
	 * @param exp
	 *            An expiration time, in seconds. Can be up to 30 days. After 30
	 *            days, is treated as a unix timestamp of an exact date.
	 * @param value
	 * @param transcoder
	 * @param timeout
	 * @return boolean result
	 */
	public final <T> boolean replace(final String key, final int exp, final T value, final Transcoder<T> transcoder, final long timeout) {
		try {
			return xMemcachedClient.replace(key, exp, value, transcoder, timeout);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return false;
		}
	}
	
	/**
	 * @see #replace(String, int, Object, Transcoder, long)
	 * @param key
	 * @param exp
	 * @param value
	 * @return
	 */
	public final boolean replace(final String key, final int exp, final Object value) {
		try {
			return xMemcachedClient.replace(key, exp, value);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return false;
		}
	}
	
	/**
	 * @see #replace(String, int, Object, Transcoder, long)
	 * @param key
	 * @param exp
	 * @param value
	 * @param timeout
	 * @return
	 */
	public final boolean replace(final String key, final int exp, final Object value, final long timeout) {
		try {
			return xMemcachedClient.replace(key, exp, value, timeout);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return false;
		}
	}
	
	/**
	 * @see #replace(String, int, Object, Transcoder, long)
	 * @param <T>
	 * @param key
	 * @param exp
	 * @param value
	 * @param transcoder
	 * @return
	 */
	public final <T> boolean replace(final String key, final int exp, final T value, final Transcoder<T> transcoder) {
		try {
			return xMemcachedClient.replace(key, exp, value, transcoder);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return false;
		}
	}
	
	/**
	 * @see #append(String, Object, long)
	 * @param key
	 * @param value
	 * @return
	 */
	public final boolean append(final String key, final Object value) {
		try {
			return xMemcachedClient.append(key, value);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return false;
		}
	}
	
	/**
	 * Append value to key's data item,this method will wait for reply
	 * 
	 * @param key
	 * @param value
	 * @param timeout
	 * @return boolean result
	 */
	public boolean append(final String key, final Object value, final long timeout) {
		try {
			return xMemcachedClient.append(key, value, timeout);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return false;
		}
	}
	
	/**
	 * Append value to key's data item,this method doesn't wait for reply.
	 * 
	 * @param key
	 * @param value
	 */
	public void appendWithNoReply(final String key, final Object value) {
		try {
			xMemcachedClient.appendWithNoReply(key, value);
		} catch (InterruptedException | MemcachedException e) {
			handleException(e);
		}
	}
	
	/**
	 * @see #prepend(String, Object, long)
	 * @param key
	 * @param value
	 * @return
	 */
	public final boolean prepend(final String key, final Object value) {
		try {
			return xMemcachedClient.prepend(key, value);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return false;
		}
	}
	
	/**
	 * Prepend value to key's data item in memcached.This method doesn't wait
	 * for reply.
	 * 
	 * @param key
	 * @param value
	 * @return boolean result
	 */
	public final boolean prepend(final String key, final Object value, final long timeout) {
		try {
			return xMemcachedClient.prepend(key, value, timeout);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return false;
		}
	}
	
	/**
	 * Prepend value to key's data item in memcached.This method doesn't wait
	 * for reply.
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public void prependWithNoReply(final String key, final Object value) {
		try {
			xMemcachedClient.prependWithNoReply(key, value);
		} catch (InterruptedException | MemcachedException e) {
			handleException(e);
		}
	}
	
	/**
	 * @see #cas(String, int, Object, Transcoder, long, long)
	 * @param key
	 * @param exp
	 * @param value
	 * @param cas
	 * @return
	 */
	public final boolean cas(final String key, final int exp, final Object value, final long cas) {
		try {
			return xMemcachedClient.cas(key, exp, value, cas);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return false;
		}
	}
	
	/**
	 * Cas is a check and set operation which means "store this data but only if
	 * no one else has updated since I last fetched it."
	 * 
	 * @param <T>
	 * @param key
	 * @param exp
	 *            An expiration time, in seconds. Can be up to 30 days. After 30
	 *            days, is treated as a unix timestamp of an exact date.
	 * @param value
	 * @param transcoder
	 * @param timeout
	 * @param cas
	 * @return
	 */
	public <T> boolean cas(final String key, final int exp, final T value, final Transcoder<T> transcoder, final long timeout, final long cas) {
		try {
			return xMemcachedClient.cas(key, exp, value, timeout, cas);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return false;
		}
	}
	
	/**
	 * @see #cas(String, int, Object, Transcoder, long, long)
	 * @param key
	 * @param exp
	 * @param value
	 * @param timeout
	 * @param cas
	 * @return
	 */
	public boolean cas(final String key, final int exp, final Object value, final long timeout, final long cas) {
		try {
			return xMemcachedClient.cas(key, exp, value, timeout, cas);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return false;
		}
	}
	
	/**
	 * @see #cas(String, int, Object, Transcoder, long, long)
	 * @param <T>
	 * @param key
	 * @param exp
	 * @param value
	 * @param transcoder
	 * @param cas
	 * @return
	 */
	public <T> boolean cas(final String key, final int exp, final T value, final Transcoder<T> transcoder, final long cas) {
		try {
			return xMemcachedClient.cas(key, exp, value, transcoder, cas);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return false;
		}
	}
	
	/**
	 * Cas is a check and set operation which means "store this data but only if
	 * no one else has updated since I last fetched it."
	 * 
	 * @param <T>
	 * @param key
	 * @param exp
	 *            An expiration time, in seconds. Can be up to 30 days. After 30
	 *            days, is treated as a unix timestamp of an exact date.
	 * @param operation
	 *            CASOperation
	 * @param transcoder
	 *            object transcoder
	 * @return
	 */
	public final <T> boolean cas(final String key, final int exp, final CASOperation<T> operation, final Transcoder<T> transcoder) {
		try {
			return xMemcachedClient.cas(key, exp, operation, transcoder);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return false;
		}
	}

	/**
	 * cas is a check and set operation which means "store this data but only if
	 * no one else has updated since I last fetched it."
	 * 
	 * @param <T>
	 * @param key
	 * @param exp
	 *            An expiration time, in seconds. Can be up to 30 days. After 30
	 *            days, is treated as a unix timestamp of an exact date.
	 * @param getsReponse
	 *            gets method's result
	 * @param operation
	 *            CASOperation
	 * @param transcoder
	 * @return
	 */
	public final <T> boolean cas(final String key, final int exp, GetsResponse<T> getsReponse, final CASOperation<T> operation, final Transcoder<T> transcoder) {
		try {
			return xMemcachedClient.cas(key, exp, getsReponse, operation, transcoder);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return false;
		}
	}
	
	/**
	 * @see #cas(String, int, GetsResponse, CASOperation, Transcoder)
	 * @param <T>
	 * @param key
	 * @param exp
	 * @param getsReponse
	 * @param operation
	 * @return
	 */
	public final <T> boolean cas(final String key, final int exp, GetsResponse<T> getsReponse, final CASOperation<T> operation) {
		try {
			return xMemcachedClient.cas(key, exp, getsReponse, operation);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return false;
		}
	}
	
	/**
	 * @see #casWithNoReply(String, int, GetsResponse, CASOperation)
	 * @param <T>
	 * @param key
	 * @param operation
	 */
	public <T> void casWithNoReply(final String key, final CASOperation<T> operation) {
		try {
			xMemcachedClient.casWithNoReply(key, operation);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
		}
	}
	
	/**
	 * 
	 * @param <T>
	 * @param key
	 * @param getsResponse
	 * @param operation
	 * @return
	 */
	public <T> void casWithNoReply(final String key, GetsResponse<T> getsResponse, final CASOperation<T> operation) {
		try {
			xMemcachedClient.casWithNoReply(key, getsResponse, operation);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
		}
	}
	
	/**
	 * @see #casWithNoReply(String, int, GetsResponse, CASOperation)
	 * @param <T>
	 * @param key
	 * @param exp
	 * @param operation
	 */
	public <T> void casWithNoReply(final String key, final int exp, final CASOperation<T> operation) {
		try {
			xMemcachedClient.casWithNoReply(key, exp, operation);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
		}
	}
	
	/**
	 * cas noreply
	 * 
	 * @param <T>
	 * @param key
	 * @param exp
	 * @param getsReponse
	 * @param operation
	 */
	public <T> void casWithNoReply(final String key, final int exp, GetsResponse<T> getsReponse, final CASOperation<T> operation) {
		try {
			xMemcachedClient.casWithNoReply(key, exp, getsReponse, operation);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
		}
	}
	
	/**
	 * @see #cas(String, int, GetsResponse, CASOperation, Transcoder)
	 * @param <T>
	 * @param key
	 * @param getsResponse
	 * @param operation
	 * @return
	 */
	public final <T> boolean cas(final String key, GetsResponse<T> getsResponse, final CASOperation<T> operation) {
		try {
			return xMemcachedClient.cas(key, getsResponse, operation);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return false;
		}
	}
	
	/**
	 * @see #cas(String, int, GetsResponse, CASOperation, Transcoder)
	 * @param <T>
	 * @param key
	 * @param exp
	 * @param operation
	 * @return
	 */
	public final <T> boolean cas(final String key, final int exp, final CASOperation<T> operation) {
		try {
			return xMemcachedClient.cas(key, exp, operation);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return false;
		}
	}

	/**
	 * @see #cas(String, int, GetsResponse, CASOperation, Transcoder)
	 * @param <T>
	 * @param key
	 * @param operation
	 * @return
	 */
	public final <T> boolean cas(final String key, final CASOperation<T> operation) {
		try {
			return xMemcachedClient.cas(key, operation);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return false;
		}
	}
	
	/**
	 * Delete key's date item from memcached
	 * 
	 * @param key
	 * @param opTimeout
	 *            Operation timeout
	 * @return
	 * @since 1.3.2
	 */
	public final boolean delete(final String key, long opTimeout) {
		try {
			return xMemcachedClient.delete(key, opTimeout);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return false;
		}
	}
	
	/**
	 * Delete key's date item from memcached only if its cas value is the same
	 * as what was read.
	 * 
	 * @param key
	 * @cas cas on delete to make sure the key is deleted only if its value is
	 *      same as what was read.
	 * @param opTimeout
	 *            Operation timeout
	 * @return
	 * @since 1.3.2
	 */
	public final boolean delete(final String key, long cas, long opTimeout) {
		try {
			return xMemcachedClient.delete(key, cas, opTimeout);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return false;
		}
	}

	public final void deleteWithNoReply(final String key) {
		try {
			xMemcachedClient.deleteWithNoReply(key);
		} catch (InterruptedException | MemcachedException e) {
			handleException(e);
		}
	}
	
	/**
	 * Set a new expiration time for an existing item
	 * 
	 * @param key
	 *            item's key
	 * @param exp
	 *            New expiration time, in seconds. Can be up to 30 days. After
	 *            30 days, is treated as a unix timestamp of an exact date.
	 * @param opTimeout
	 *            operation timeout
	 * @return
	 */
	public boolean touch(final String key, int exp, long opTimeout) {
		try {
			return xMemcachedClient.touch(key, exp, opTimeout);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return false;
		}
	}
	
	/**
	 * Set a new expiration time for an existing item,using default opTimeout
	 * second.
	 * 
	 * @param key
	 *            item's key
	 * @param exp
	 *            New expiration time, in seconds. Can be up to 30 days. After
	 *            30 days, is treated as a unix timestamp of an exact date.
	 * @return
	 */
	public boolean touch(final String key, int exp) {
		try {
			return xMemcachedClient.touch(key, exp);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return false;
		}
	}
	
	/**
	 * Get item and set a new expiration time for it
	 * 
	 * @param <T>
	 * @param key
	 *            item's key
	 * @param newExp
	 *            New expiration time, in seconds. Can be up to 30 days. After
	 *            30 days, is treated as a unix timestamp of an exact date.
	 * @param opTimeout
	 *            operation timeout
	 * @return
	 */
	public <T> T getAndTouch(final String key, int newExp, long opTimeout) {
		try {
			return xMemcachedClient.getAndTouch(key, newExp, opTimeout);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return null;
		}
	}
	
	/**
	 * Get item and set a new expiration time for it,using default opTimeout
	 * 
	 * @param <T>
	 * @param key
	 * @param newExp
	 * @return
	 */
	public <T> T getAndTouch(final String key, int newExp) {
		try {
			return xMemcachedClient.getAndTouch(key, newExp);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return null;
		}
	}
	
	/**
	 * "incr" are used to change data for some item in-place, incrementing it.
	 * The data for the item is treated as decimal representation of a 64-bit
	 * unsigned integer. If the current data value does not conform to such a
	 * representation, the commands behave as if the value were 0. Also, the
	 * item must already exist for incr to work; these commands won't pretend
	 * that a non-existent key exists with value 0; instead, it will fail.This
	 * method doesn't wait for reply.
	 * 
	 * @return the new value of the item's data, after the increment operation
	 *         was carried out.
	 * @param key
	 * @param num
	 */
	public final Long incr(final String key, final long delta) {
		try {
			return xMemcachedClient.incr(key, delta);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return null;
		}
	}
	
	public final Long incr(final String key, final long delta, final long initValue) {
		try {
			return xMemcachedClient.incr(key, delta, initValue);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return null;
		}
	}
	
	/**
	 * "incr" are used to change data for some item in-place, incrementing it.
	 * The data for the item is treated as decimal representation of a 64-bit
	 * unsigned integer. If the current data value does not conform to such a
	 * representation, the commands behave as if the value were 0. Also, the
	 * item must already exist for incr to work; these commands won't pretend
	 * that a non-existent key exists with value 0; instead, it will fail.This
	 * method doesn't wait for reply.
	 * 
	 * @param key
	 *            key
	 * @param num
	 *            increment
	 * @param initValue
	 *            initValue if the data is not exists.
	 * @param timeout
	 *            operation timeout
	 * @return
	 */
	public final Long incr(final String key, final long delta, final long initValue, long timeout) {
		try {
			return xMemcachedClient.incr(key, delta, initValue, timeout);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return null;
		}
	}
	
	/**
	 * "incr" are used to change data for some item in-place, incrementing it.
	 * The data for the item is treated as decimal representation of a 64-bit
	 * unsigned integer. If the current data value does not conform to such a
	 * representation, the commands behave as if the value were 0. Also, the
	 * item must already exist for incr to work; these commands won't pretend
	 * that a non-existent key exists with value 0; instead, it will fail.This
	 * method doesn't wait for reply.
	 * 
	 * @param key
	 *            key
	 * @param delta
	 *            increment delta
	 * @param initValue
	 *            the initial value to be added when value is not found
	 * @param timeout
	 *            operation timeout
	 * @param exp
	 *            the initial vlaue expire time, in seconds. Can be up to 30
	 *            days. After 30 days, is treated as a unix timestamp of an
	 *            exact date.
	 * @return
	 */
	public final Long incr(String key, long delta, long initValue, long timeout, int exp) {
		try {
			return xMemcachedClient.incr(key, delta, initValue, timeout, exp);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return null;
		}
	}
	
	/**
	 * "incr" are used to change data for some item in-place, incrementing it.
	 * The data for the item is treated as decimal representation of a 64-bit
	 * unsigned integer. If the current data value does not conform to such a
	 * representation, the commands behave as if the value were 0. Also, the
	 * item must already exist for incr to work; these commands won't pretend
	 * that a non-existent key exists with value 0; instead, it will fail.This
	 * method doesn't wait for reply.
	 * 
	 * @param key
	 * @param num
	 */
	public final void incrWithNoReply(final String key, final long delta) {
		try {
			xMemcachedClient.incrWithNoReply(key, delta);
		} catch (InterruptedException | MemcachedException e) {
			handleException(e);
		}
	}
	
	/**
	 * "decr" are used to change data for some item in-place, decrementing it.
	 * The data for the item is treated as decimal representation of a 64-bit
	 * unsigned integer. If the current data value does not conform to such a
	 * representation, the commands behave as if the value were 0. Also, the
	 * item must already exist for decr to work; these commands won't pretend
	 * that a non-existent key exists with value 0; instead, it will fail.This
	 * method doesn't wait for reply.
	 * 
	 * @param key
	 * @param num
	 */
	public final void decrWithNoReply(final String key, final long delta) {
		try {
			xMemcachedClient.decrWithNoReply(key, delta);
		} catch (InterruptedException | MemcachedException e) {
			handleException(e);
		}
	}
	
	/**
	 * "decr" are used to change data for some item in-place, decrementing it.
	 * The data for the item is treated as decimal representation of a 64-bit
	 * unsigned integer. If the current data value does not conform to such a
	 * representation, the commands behave as if the value were 0. Also, the
	 * item must already exist for decr to work; these commands won't pretend
	 * that a non-existent key exists with value 0; instead, it will fail.This
	 * method doesn't wait for reply.
	 * 
	 * @return the new value of the item's data, after the decrement operation
	 *         was carried out.
	 * @param key
	 * @param num
	 */
	public final Long decr(final String key, final long delta) {
		try {
			return xMemcachedClient.decr(key, delta);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return null;
		}
	}
	
	/**
	 * @see decr
	 * @param key
	 * @param num
	 * @param initValue
	 * @return
	 */
	public final Long decr(final String key, final long delta, long initValue) {
		try {
			return xMemcachedClient.decr(key, delta, initValue);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return null;
		}
	}
	
	/**
	 * "decr" are used to change data for some item in-place, decrementing it.
	 * The data for the item is treated as decimal representation of a 64-bit
	 * unsigned integer. If the current data value does not conform to such a
	 * representation, the commands behave as if the value were 0. Also, the
	 * item must already exist for decr to work; these commands won't pretend
	 * that a non-existent key exists with value 0; instead, it will fail.This
	 * method doesn't wait for reply.
	 * 
	 * @param key
	 *            The key
	 * @param num
	 *            The increment
	 * @param initValue
	 *            The initial value if the data is not exists.
	 * @param timeout
	 *            Operation timeout
	 * @return
	 */
	public final Long decr(final String key, final long delta, long initValue, long timeout) {
		try {
			return xMemcachedClient.decr(key, delta, initValue, timeout);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return null;
		}
	}
	
	/**
	 * "incr" are used to change data for some item in-place, incrementing it.
	 * The data for the item is treated as decimal representation of a 64-bit
	 * unsigned integer. If the current data value does not conform to such a
	 * representation, the commands behave as if the value were 0. Also, the
	 * item must already exist for incr to work; these commands won't pretend
	 * that a non-existent key exists with value 0; instead, it will fail.This
	 * method doesn't wait for reply.
	 * 
	 * @param key
	 * @param delta
	 * @param initValue
	 *            the initial value to be added when value is not found
	 * @param timeout
	 * @param exp
	 *            the initial vlaue expire time, in seconds. Can be up to 30
	 *            days. After 30 days, is treated as a unix timestamp of an
	 *            exact date.
	 * @return
	 */
	public final Long decr(final String key, final long delta, final long initValue, final long timeout, final int exp) {
		try {
			return xMemcachedClient.decr(key, delta, initValue, timeout, exp);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return null;
		}
	}

	/**
	 * Make All connected memcached's data item invalid
	 */
	public final void flushAll() {
		try {
			xMemcachedClient.flushAll();
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
		}
	}
	
	public void flushAllWithNoReply() {
		try {
			xMemcachedClient.flushAllWithNoReply();
		} catch (InterruptedException | MemcachedException e) {
			handleException(e);
		}
	}
	
	public void flushAllWithNoReply(int exptime) {
		try {
			xMemcachedClient.flushAllWithNoReply(exptime); 
		} catch (InterruptedException | MemcachedException e) {
			handleException(e);
		}
	}
	
	public void flushAllWithNoReply(InetSocketAddress address) {
		try {
			xMemcachedClient.flushAllWithNoReply(address);
		} catch (InterruptedException | MemcachedException e) {
			handleException(e);
		}
	}

	public void flushAllWithNoReply(InetSocketAddress address, int exptime) {
		try {
			xMemcachedClient.flushAllWithNoReply(address, exptime);
		} catch (InterruptedException | MemcachedException e) {
			handleException(e);
		}
	}
	
	public final void flushAll(int exptime, long timeout) {
		try {
			xMemcachedClient.flushAll(exptime, timeout);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
		}
	}
	
	/**
	 * Make All connected memcached's data item invalid
	 * 
	 * @param timeout
	 *            operation timeout
	 */
	public final void flushAll(long timeout) {
		try {
			xMemcachedClient.flushAll(timeout);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
		}
	}
	
	/**
	 * Set the verbosity level of the memcached's logging output.This method
	 * will wait for reply.
	 * 
	 * @param address
	 * @param level
	 *            logging level
	 */
	public void setLoggingLevelVerbosity(InetSocketAddress address, int level) {
		try {
			xMemcachedClient.setLoggingLevelVerbosity(address, level);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
		}
	}
	
	/**
	 * Set the verbosity level of the memcached's logging output.This method
	 * doesn't wait for reply from server
	 * 
	 * @param address
	 *            memcached server address
	 * @param level
	 *            logging level
	 */
	public void setLoggingLevelVerbosityWithNoReply(InetSocketAddress address, int level) {
		try {
			xMemcachedClient.setLoggingLevelVerbosityWithNoReply(address, level);
		} catch (InterruptedException | MemcachedException e) {
			handleException(e);
		}
	}
	
	/**
	 * Invalidate all existing items immediately
	 * 
	 * @param address
	 *            Target memcached server
	 * @param timeout
	 *            operation timeout
	 */
	public final void flushAll(InetSocketAddress address) {
		try {
			xMemcachedClient.flushAll(address);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
		}
	}
	
	public final void flushAll(InetSocketAddress address, long timeout) {
		try {
			xMemcachedClient.flushAll(address, timeout);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
		}
	}
	
	public final void flushAll(InetSocketAddress address, long timeout, int exptime) {
		try {
			xMemcachedClient.flushAll(address, timeout, exptime);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
		}
	}
	
	public final Map<String, String> stats(InetSocketAddress address) {
		try {
			return xMemcachedClient.stats(address);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return EMPTY_STAT_MAP;
		}
	}
	
	/**
	 * �ョ��瑰������emcached server缁��淇℃�
	 * 
	 * @param address
	 *            ����板�
	 * @param timeout
	 *            ���瓒��
	 * @return
	 */
	public final Map<String, String> stats(InetSocketAddress address, long timeout) {
		try {
			return xMemcachedClient.stats(address, timeout);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return EMPTY_STAT_MAP;
		}
	}
	
	public final Map<InetSocketAddress, Map<String, String>> getStats() {
		try {
			return xMemcachedClient.getStats();
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return EMPTY_ALL_STAT_MAP;
		}
	}
	
	/**
	 * Get special item stats. "stats items" for example
	 * 
	 * @param item
	 * @return
	 */
	public final Map<InetSocketAddress, Map<String, String>> getStatsByItem(String itemName) {
		try {
			return xMemcachedClient.getStatsByItem(itemName);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return EMPTY_ALL_STAT_MAP;
		}
	}
	
	public final Map<InetSocketAddress, Map<String, String>> getStatsByItem(String itemName, long timeout) {
		try {
			return xMemcachedClient.getStatsByItem(itemName, timeout);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return EMPTY_ALL_STAT_MAP;
		}
	}

	/**
	 * Get all connected memcached servers's version.
	 * 
	 * @return
	 */
	public final Map<InetSocketAddress, String> getVersions() {
		try {
			return xMemcachedClient.getVersions();
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return EMPTY_VERSION_MAP;
		}
	}
	
	public final Map<InetSocketAddress, String> getVersions(long timeout) {
		try {
			return xMemcachedClient.getVersions(timeout);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return EMPTY_VERSION_MAP;
		}
	}
	
	/**
	 * Get stats from all memcached servers
	 * 
	 * @param timeout
	 * @return server->item->value map
	 */
	public Map<InetSocketAddress, Map<String, String>> getStats(long timeout) {
		try {
			return xMemcachedClient.getStats(timeout);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return EMPTY_ALL_STAT_MAP;
		}
	}
	
	public final void shutdown() throws IOException {
		xMemcachedClient.shutdown();
	}
	
	/**
	 * In a high concurrent enviroment,you may want to pool memcached
	 * clients.But a xmemcached client has to start a reactor thread and some
	 * thread pools,if you create too many clients,the cost is very large.
	 * Xmemcached supports connection pool instreadof client pool.you can create
	 * more connections to one or more memcached servers,and these connections
	 * share the same reactor and thread pools,it will reduce the cost of
	 * system.
	 * 
	 * @param poolSize
	 *            pool size,default is one,every memcached has only one
	 *            connection.
	 */
	public void setConnectionPoolSize(int poolSize) {
		xMemcachedClient.setConnectionPoolSize(poolSize);
	}
	
	public final boolean delete(final String key) {
		try {
			return xMemcachedClient.delete(key);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return false;
		}
	}
	
	/**
	 * return default transcoder,default is SerializingTranscoder
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public final Transcoder getTranscoder() {
		return xMemcachedClient.getTranscoder();
	}
	
	/**
	 * set transcoder
	 * 
	 * @param transcoder
	 */
	@SuppressWarnings("rawtypes")
	public final void setTranscoder(final Transcoder transcoder) {
		xMemcachedClient.setTranscoder(transcoder);
	}
	
	/**
	 * Returns available memcached servers list.
	 * 
	 * @return A available server collection
	 */
	public Collection<InetSocketAddress> getAvailableServers() {
		return xMemcachedClient.getAvailableServers();
	}
	
	public final int getConnectionSizeBySocketAddress(InetSocketAddress address) {
		return xMemcachedClient.getConnectionSizeBySocketAddress(address);
	}
	
	/**
	 * Add a memcached client listener
	 * 
	 * @param listener
	 */
	public void addStateListener(MemcachedClientStateListener listener) {
		xMemcachedClient.addStateListener(listener);
	}
	
	/**
	 * Get all current state listeners
	 * 
	 * @return
	 */
	public Collection<MemcachedClientStateListener> getStateListeners() {
		return xMemcachedClient.getStateListeners();
	}
	
	/**
	 * Store all primitive type as string,defualt is false.
	 */
	public void setPrimitiveAsString(boolean primitiveAsString) {
		xMemcachedClient.setPrimitiveAsString(primitiveAsString);
	}
	
	/**
	 * Remove a memcached client listener
	 * 
	 * @param listener
	 */
	public void removeStateListener(MemcachedClientStateListener listener) {
		xMemcachedClient.addStateListener(listener);
	}
	
	public Protocol getProtocol() {
		return xMemcachedClient.getProtocol();
	}
	
	public boolean isSanitizeKeys() {
		return xMemcachedClient.isSanitizeKeys();
	}
	
	/**
	 * Enables/disables sanitizing keys by URLEncoding.
	 * 
	 * @param sanitizeKey
	 *            if true, then URLEncode all keys
	 */
	public void setSanitizeKeys(boolean sanitizeKey) {
		xMemcachedClient.setSanitizeKeys(sanitizeKey);
	}
	
	/**
	 * Invalidate all items under the namespace.
	 * 
	 * @since 1.4.2
	 * @param ns
	 *            the namespace
	 * @param opTimeout
	 *            operation timeout in milliseconds.
	 */
	public void invalidateNamespace(String ns, long opTimeout) {
		try {
			xMemcachedClient.invalidateNamespace(ns, opTimeout);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
		}
	}
	
	/**
	 * Invalidate all namespace under the namespace using the default operation
	 * timeout.
	 * 
	 * @since 1.4.2
	 * @param ns
	 *            the namespace
	 */
	public void invalidateNamespace(String ns) {
		try {
			xMemcachedClient.invalidateNamespace(ns);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
		}
	}
	
	/**
	 * Returns the real namespace of ns.
	 * 
	 * @param ns
	 * @return
	 */
	public String getNamespace(String ns) {
		try {
			return xMemcachedClient.getNamespace(ns);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			handleException(e);
			return null;
		}
	}
	
	/**
	 * Get counter for key,and if the key's value is not set,then set it with
	 * initial value.
	 * 
	 * @param key
	 * @param initialValue
	 * @return
	 */
	public Counter getCounter(String key, long initialValue) {
		return xMemcachedClient.getCounter(key, initialValue);
	}
	
	/**
	 * Get counter for key,and if the key's value is not set,then set it with 0.
	 * 
	 * @param key
	 * @return
	 */
	public Counter getCounter(String key) {
		return xMemcachedClient.getCounter(key);
	}
	
	/**
	 * If the memcached dump or network error cause connection closed,xmemcached
	 * would try to heal the connection.You can disable this behaviour by using
	 * this method:<br/>
	 * <code> client.setEnableHealSession(false); </code><br/>
	 * The default value is true.
	 * 
	 * @param enableHealSession
	 * @since 1.3.9
	 */
	public void setEnableHealSession(boolean enableHealSession) {
		xMemcachedClient.setEnableHealSession(enableHealSession);
	}
	
	/**
	 * Configure wheather to set client in failure mode.If set it to true,that
	 * means you want to configure client in failure mode. Failure mode is that
	 * when a memcached server is down,it would not taken from the server list
	 * but marked as unavailable,and then further requests to this server will
	 * be transformed to standby node if configured or throw an exception until
	 * it comes back up.
	 * 
	 * @param failureMode
	 *            true is to configure client in failure mode.
	 */
	public void setFailureMode(boolean failureMode) {
		xMemcachedClient.setFailureMode(failureMode);
	}
	
	/**
	 * Returns if client is in failure mode.
	 * 
	 * @return
	 */
	public boolean isFailureMode() {
		return xMemcachedClient.isFailureMode();
	}
	
	/**
	 * Returns reconnecting task queue,the queue is thread-safe and 'weakly
	 * consistent',but maybe you <strong>should not modify it</strong> at all.
	 * 
	 * @return The reconnecting task queue,if the client has not been
	 *         started,returns null.
	 */
	public Queue<ReconnectRequest> getReconnectRequestQueue() {
		return xMemcachedClient.getReconnectRequestQueue();
	}
}
