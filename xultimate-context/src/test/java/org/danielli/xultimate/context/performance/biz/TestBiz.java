package org.danielli.xultimate.context.performance.biz;

import org.danielli.xultimate.context.performance.PerformanceMonitor;
import org.springframework.stereotype.Service;

@Service("testBiz")
public class TestBiz {

	@PerformanceMonitor
	public void doSomething1() {
		try {
			Thread.sleep(5 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		};
	}
	
	@PerformanceMonitor
	public void doSomething2() {
		try {
			Thread.sleep(3 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		};
	}
}
