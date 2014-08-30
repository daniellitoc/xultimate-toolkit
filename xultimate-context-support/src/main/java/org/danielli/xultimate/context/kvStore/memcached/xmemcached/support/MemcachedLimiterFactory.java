package org.danielli.xultimate.context.kvStore.memcached.xmemcached.support;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.danielli.xultimate.context.kvStore.memcached.xmemcached.XMemcachedClient;
import org.danielli.xultimate.util.Assert;
import org.danielli.xultimate.util.builder.EqualsBuilderUtils;
import org.danielli.xultimate.util.builder.HashCodeBuilderUtils;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 访问限制器工厂。
 * 
 * @author Daniel Li
 * @since 15 Jun 2013
 */
public class MemcachedLimiterFactory {
	/** XMemcached客户端 */
	protected XMemcachedClient xMemcachedClient;
	
	private ConcurrentMap<AbstractLimiterConfig, MemcachedLimiter> limiterMap = new ConcurrentHashMap<>();
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MemcachedLimiterFactory.class);
	
	/**
	 * 设置XMemcached客户端。
	 * @param xMemcachedClient XMemcached客户端。
	 */
	public void setxMemcachedClient(XMemcachedClient xMemcachedClient) {
		this.xMemcachedClient = xMemcachedClient;
	}
	
	/**
	 * 获取限制器。
	 * @param expireSeconds 失效时间。
	 * @param step 步进。
	 * @param limit 限制个数。
	 * @param initLimit 初始限制个数。 
	 * @return 限制器。
	 */
	public MemcachedLimiter getLimiter(int expireSeconds, long step, long limit, long initLimit) {
		AbstractLimiterConfig limiterConfig = new ExpireSecondsLimiterConfig(expireSeconds, step, limit, initLimit);
		MemcachedLimiter memcachedLimiter = limiterMap.get(limiterConfig);
		if (memcachedLimiter == null) {
			synchronized (this) {
				memcachedLimiter = limiterMap.get(limiterConfig);
				if (memcachedLimiter == null) {
					memcachedLimiter = new DefaultMemcachedLimiter(limiterConfig);
					limiterMap.put(limiterConfig, memcachedLimiter);
				}
			}
		}
		return memcachedLimiter;
	}
	
	/**
	 * 获取限制器。
	 * @param expireSeconds 失效日期。
	 * @param step 步进。
	 * @param limit 限制个数。
	 * @param initLimit 初始限制个数。 
	 * @return 限制器。
	 */
	public MemcachedLimiter getLimiter(Date expireDate, long step, long limit, long initLimit) {
		AbstractLimiterConfig limiterConfig = new ExpireDateLimiterConfig(new DateTime(expireDate), step, limit, initLimit);
		MemcachedLimiter memcachedLimiter = limiterMap.get(limiterConfig);
		if (memcachedLimiter == null) {
			synchronized (this) {
				memcachedLimiter = limiterMap.get(limiterConfig);
				if (memcachedLimiter == null) {
					memcachedLimiter = new DefaultMemcachedLimiter(limiterConfig);
					limiterMap.put(limiterConfig, memcachedLimiter);
				}
			}
		}
		return memcachedLimiter;
	}
	
	/**
	 * 限制器。
	 * 
	 * @author Daniel Li
	 * @since 15 Jun 2013
	 */
	public interface MemcachedLimiter {
		/**
		 * 是否可继续。
		 * @param limitName 限制名称。
		 * @return 限制结果。
		 */
		LimitResult limit(String limitName);
	}
	
	/**
	 * 限制结果。
	 * 
	 * @author Daniel Li
	 * @since 15 Jun 2013
	 */
	public interface LimitResult {
		
		/**
		 * 是否可以继续。
		 * @return 如果为true，表示可以，否则表示不可以。
		 */
		boolean isAllow();
	}
	
	/**
	 * 限制器配置。
	 * 
	 * @author Daniel Li
	 * @since 15 Jun 2013
	 */
	abstract class AbstractLimiterConfig {
		/** 步进 */
		protected long step;
		/** 限制个数 */
		protected long limit;
		/** 初始限制个数 */
		protected long initLimit;
		
		public AbstractLimiterConfig(long step, long limit, long initLimit) {
			this.step = step;
			this.limit = limit;
			this.initLimit = initLimit;
		}
		
		public long getStep() {
			return step;
		}
		
		public long getLimit() {
			return limit;
		}
		
		public long getInitLimit() {
			return initLimit;
		}
		
		public boolean grantThan(long currentLimit) {
			return currentLimit > limit;
		}
		
		/**
		 * 获取失效时间。
		 * @return 失效时间。
		 */
		public abstract int getExpireSeconds();
		
		@Override
		public int hashCode() {
			return HashCodeBuilderUtils.reflectionHashCode(this);
		}
		
		@Override
		public boolean equals(Object obj) {
			return EqualsBuilderUtils.reflectionEqualsForBothClass(this, obj);
		}
	}
	
	/**
	 * 失效时间限制器配置。
	 * 
	 * @author Daniel Li
	 * @since 15 Jun 2013
	 */
	class ExpireSecondsLimiterConfig extends AbstractLimiterConfig {
		/** 失效时间 */
		private int expireSeconds;
		
		public ExpireSecondsLimiterConfig(int expireSeconds, long step, long limit, long initLimit) {
			super(step, limit, initLimit);
			this.expireSeconds = expireSeconds;
		}
		
		@Override
		public int getExpireSeconds() {
			return expireSeconds;
		}
	}

	/**
	 * 失效时间限制器配置。
	 * 
	 * @author Daniel Li
	 * @since 15 Jun 2013
	 */
	class ExpireDateLimiterConfig extends AbstractLimiterConfig {
		/** 失效指定日期 */
		private DateTime expireDate;
		
		public ExpireDateLimiterConfig(DateTime expireDate, long step, long limit, long initLimit) {
			super(step, limit, initLimit);
			this.expireDate = expireDate;
		}
		
		/**
		 * 转换时间。
		 * @return 失效时间。
		 */
		public int getExpireSeconds() {
			Duration duration = new Duration(DateTime.now(), expireDate);
			if (duration.getStandardSeconds() <= 0) {
				LOGGER.warn("expireDate must be great than now, ignore expireDate");
				return 0;
			} else {
				return (int) duration.getStandardSeconds();
			}
		}
	}
	
	/**
	 * 默认实现。
	 * 
	 * @author Daniel Li
	 * @since 15 Jun 2013
	 */
	class DefaultLimitResult implements LimitResult {
		/** 是否允许继续 */
		private boolean allow;
		
		public DefaultLimitResult(boolean allow) {
			this.allow = allow;
		}
		
		@Override
		public boolean isAllow() {
			return allow;
		}
	}
	
	/**
	 * 默认实现。
	 * 
	 * @author Daniel Li
	 * @since 15 Jun 2013
	 */
	class DefaultMemcachedLimiter implements MemcachedLimiter {
		private AbstractLimiterConfig limiterConfig;
		
		public DefaultMemcachedLimiter(AbstractLimiterConfig limiterConfig) {
			this.limiterConfig = limiterConfig;
		}

		public AbstractLimiterConfig getLimiterConfig() {
			return limiterConfig;
		}
		
		@Override
		public DefaultLimitResult limit(String limitName) {
			Assert.notNull(limitName, "this argument `limitName` is required; it must not be null");
			Long currentLimit = xMemcachedClient.incr(limitName, limiterConfig.getStep(), limiterConfig.getInitLimit(), xMemcachedClient.getOpTimeout(), limiterConfig.getExpireSeconds());
			if (currentLimit == null) {
				LOGGER.warn("try to increment a Counter `{}` failure. Please Check Memcached Log", limitName);
				return new DefaultLimitResult(false);
			}
			return new DefaultLimitResult(!limiterConfig.grantThan(currentLimit));
		}
	}
}
