package org.danielli.xultimate.context.net.netty.support;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;

import java.util.Map;

import org.danielli.xultimate.util.collections.MapUtils;
import org.springframework.beans.factory.FactoryBean;

/**
 * {@code Bootstrap }的Spring {@code FactoryBean}创建类。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public class BootstrapFactoryBean implements FactoryBean<Bootstrap> {
	
	private EventLoopGroup group;
	
	private Map<ChannelOption<Object>, Object> options;
	
	private ChannelHandler channelHandler;
	
	private Class<? extends Channel> channelClass;
	
	@Override
	public Bootstrap getObject() throws Exception {
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(group); 
		bootstrap.channel(channelClass); 
		if (MapUtils.isNotEmpty(options)) {
			for (Map.Entry<ChannelOption<Object>, Object> entry : options.entrySet()) {
				bootstrap.option(entry.getKey(), entry.getValue());
			}
		}
		
		bootstrap.handler(channelHandler);
		return bootstrap;
	}

	@Override
	public Class<?> getObjectType() {
		return Bootstrap.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public void setGroup(EventLoopGroup group) {
		this.group = group;
	}

	public void setOptions(Map<ChannelOption<Object>, Object> options) {
		this.options = options;
	}

	public void setChannelHandler(ChannelHandler channelHandler) {
		this.channelHandler = channelHandler;
	}

	public void setChannelClass(Class<? extends Channel> channelClass) {
		this.channelClass = channelClass;
	}

}
