package org.danielli.xultimate.test.thread.linkedhashmap;

import java.util.concurrent.ConcurrentMap;

import org.junit.Test;

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import com.googlecode.concurrentlinkedhashmap.EvictionListener;
import com.googlecode.concurrentlinkedhashmap.Weighers;

public class ConcurrentLinkedHashMapTest {

	@Test
	public void test() {
		EvictionListener<Integer, Integer> listener = new EvictionListener<Integer, Integer>() {
			@Override
			public void onEviction(Integer key, Integer value) {
				System.out.println("Evicted key=" + key + ", value=" + value);
			}
		};

		ConcurrentMap<Integer, Integer> cache = new ConcurrentLinkedHashMap.Builder<Integer, Integer>()
				.maximumWeightedCapacity(2).listener(listener)
				.weigher(Weighers.singleton()).build();
		cache.put(1, 1);
		cache.put(2, 2);
		cache.put(3, 3);
		System.out.println(cache.get(1));// null 已经失效了
		System.out.println(cache.get(2));
	}
}
