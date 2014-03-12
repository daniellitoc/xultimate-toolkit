package org.danielli.xultimate.context.net.netty.support;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

import java.util.Map;

import org.danielli.xultimate.util.collections.MapUtils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * {@code ChannelInitializ<SocketChannel> }的Spring {@code FactoryBean}创建类。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public class SocketChannelInitializerFactoryBean implements FactoryBean<ChannelInitializer<SocketChannel>>, ApplicationContextAware {

	private Map<String, String> channelHandlerAdapterBeanNameMap;
	
	private ApplicationContext applicationContext;
	
	@Override
	public ChannelInitializer<SocketChannel> getObject() throws Exception {		
		ChannelInitializer<SocketChannel> initializer = new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline channelPipeline = ch.pipeline();
				if (MapUtils.isNotEmpty(channelHandlerAdapterBeanNameMap)) {
					for (Map.Entry<String, String> entry : channelHandlerAdapterBeanNameMap.entrySet()) {
						channelPipeline.addLast(entry.getKey(), applicationContext.getBean(entry.getValue(), ChannelHandler.class));
					}
				}
			}
			
		};
		return initializer;
	}

	@Override
	public Class<?> getObjectType() {
		return ChannelInitializer.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public void setChannelHandlerAdapterBeanNameMap(Map<String, String> channelHandlerAdapterBeanNameMap) {
		this.channelHandlerAdapterBeanNameMap = channelHandlerAdapterBeanNameMap;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	
}
