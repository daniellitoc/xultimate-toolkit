package org.danielli.xultimate.jdbc.support.incrementer;

import java.util.concurrent.locks.ReentrantLock;

import org.danielli.xultimate.util.Assert;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;

/**
 * Abstract base class for {@link DataFieldMaxValueIncrementer} implementations that use
 * a key. Subclasses need to provide the specific handling
 * of that table in their {@link #getNextKey()} implementation..
 *
 * @author Daniel Li
 * @since 15 Jun 2013
 */
public abstract class AbstractKeyMaxValueIncrementer extends AbstractDataFieldMaxValueIncrementer {

	/** The name of the key */
	private String keyName;

	/** The number of keys buffered in a cache */
	private int cacheSize = 1;
	
	/** 此次请求的下一个值 */
	private long nextId = 0;

	/** 此次请求的最大值 */
	private long maxId = 0;
	
	/** 此次请求的步进 */
	private long step = 1;
	
	private ReentrantLock reentrantLock = new ReentrantLock();
	
	/**
	 * Default constructor for bean property style usage.
	 * @see #setKeyName
	 */
	public AbstractKeyMaxValueIncrementer() {
	}

	/**
	 * Convenience constructor.
	 * @param keyName the name of the column in the sequence table to use
	 */
	public AbstractKeyMaxValueIncrementer(String keyName) {
		Assert.notNull(keyName, "Key name must not be null");
		this.keyName = keyName;
	}
	
	@Override
	protected long getNextKey() throws DataAccessException {
		reentrantLock.lock();
		try {
			this.nextId += this.step;
			if (this.maxId < this.nextId) {
				maxId = getNextMaxId();
				this.nextId = this.maxId - this.cacheSize * this.step + 1;
			}
			return this.nextId;
		} finally {
			reentrantLock.unlock();
		}
	}
	
	protected abstract long getNextMaxId() throws DataAccessException;
	
	/**
	 * Set the name of the Key.
	 */
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	/**
	 * Return the name of the Key.
	 */
	public String getKeyName() {
		return this.keyName;
	}

	/**
	 * Set the number of buffered keys.
	 */
	public void setCacheSize(int cacheSize) {
		this.cacheSize = cacheSize;
	}

	/**
	 * Return the number of buffered keys.
	 */
	public int getCacheSize() {
		return this.cacheSize;
	}

	@Override
	public void afterPropertiesSet() {
		Assert.isTrue(this.step > 0, "Property 'step' must greater than 0");
		Assert.isTrue(this.cacheSize > 0, "Property 'cacheSize' must greater than 0");
		super.afterPropertiesSet();
		if (this.keyName == null) {
			throw new IllegalArgumentException("Property 'keyName' is required");
		}
	}

	/**
	 * 获取步进。
	 */
	public long getStep() {
		return step;
	}

	/**
	 * 设置步进。
	 */
	public void setStep(long step) {
		this.step = step;
	}
}
