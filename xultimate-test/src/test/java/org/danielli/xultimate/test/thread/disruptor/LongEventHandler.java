package org.danielli.xultimate.test.thread.disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * 取自{@link https://github.com/LMAX-Exchange/disruptor/wiki}
 */
public class LongEventHandler implements EventHandler<LongEvent> {
	
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) {
        System.out.println("Event: " + event.getValue());
    }
}
