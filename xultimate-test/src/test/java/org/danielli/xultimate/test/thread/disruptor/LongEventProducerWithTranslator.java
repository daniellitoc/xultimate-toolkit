package org.danielli.xultimate.test.thread.disruptor;

import java.nio.ByteBuffer;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

/**
 * 取自{@link https://github.com/LMAX-Exchange/disruptor/wiki}
 */
public class LongEventProducerWithTranslator {
	private final RingBuffer<LongEvent> ringBuffer;

	public LongEventProducerWithTranslator(RingBuffer<LongEvent> ringBuffer) {
		this.ringBuffer = ringBuffer;
	}

	private static final EventTranslatorOneArg<LongEvent, ByteBuffer> TRANSLATOR = new EventTranslatorOneArg<LongEvent, ByteBuffer>() {
		public void translateTo(LongEvent event, long sequence, ByteBuffer bb) {
			event.set(bb.getLong(0));
		}
	};

	public void onData(ByteBuffer bb) {
		ringBuffer.publishEvent(TRANSLATOR, bb);
	}
}
