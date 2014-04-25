package org.danielli.xultimate.test.thread.disruptor;

/**
 * 取自{@link https://github.com/LMAX-Exchange/disruptor/wiki}
 */
public class LongEvent {
	
	private long value;

    public void set(long value) {
        this.value = value;
    }

	public long getValue() {
		return value;
	}
}
