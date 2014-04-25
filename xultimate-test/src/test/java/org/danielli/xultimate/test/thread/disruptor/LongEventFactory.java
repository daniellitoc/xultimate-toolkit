package org.danielli.xultimate.test.thread.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * 取自{@link https://github.com/LMAX-Exchange/disruptor/wiki}
 */
public class LongEventFactory implements EventFactory<LongEvent> {
	
	public LongEvent newInstance() {
        return new LongEvent();
    }
}
