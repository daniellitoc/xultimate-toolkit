package org.danielli.xultimate.jdbc.support.incrementer;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;

/**
 * Base implementation of {@link DataFieldMaxValueIncrementer} that delegates
 * to a single {@link #getNextKey} template method that returns a {@code long}.
 * Uses longs for String values, padding with zeroes if required.
 *
 * @author Daniel Li
 * @since 15 Jun 2013
 */
public abstract class AbstractDataFieldMaxValueIncrementer implements DataFieldMaxValueIncrementer, InitializingBean {

	/** The length to which a string result should be pre-pended with zeroes */
	protected int paddingLength = 0;
	
	/**
	 * Default constructor for bean property style usage.
	 * @see #setDataSource
	 * @see #setIncrementerName
	 */
	public AbstractDataFieldMaxValueIncrementer() {
	}
	
	public void afterPropertiesSet() {
		
	}
	
	/**
	 * Set the padding length, i.e. the length to which a string result
	 * should be pre-pended with zeroes.
	 */
	public void setPaddingLength(int paddingLength) {
		this.paddingLength = paddingLength;
	}

	/**
	 * Return the padding length for String values.
	 */
	public int getPaddingLength() {
		return this.paddingLength;
	}
	
	public int nextIntValue() throws DataAccessException {
		return (int) getNextKey();
	}
	
	public long nextLongValue() throws DataAccessException {
		return getNextKey();
	}

	public String nextStringValue() throws DataAccessException {
		String s = Long.toString(getNextKey());
		int len = s.length();
		if (len < this.paddingLength) {
			StringBuilder sb = new StringBuilder(this.paddingLength);
			for (int i = 0; i < this.paddingLength - len; i++) {
				sb.append('0');
			}
			sb.append(s);
			s = sb.toString();
		}
		return s;
	}


	/**
	 * Determine the next key to use, as a long.
	 * @return the key to use as a long. It will eventually be converted later
	 * in another format by the public concrete methods of this class.
	 */
	protected abstract long getNextKey();
}
