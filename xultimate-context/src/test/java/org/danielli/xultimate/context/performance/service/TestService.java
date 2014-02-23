package org.danielli.xultimate.context.performance.service;

import javax.annotation.Resource;

import org.danielli.xultimate.context.performance.PerformanceMonitor;
import org.danielli.xultimate.context.performance.biz.TestBiz;
import org.springframework.stereotype.Service;

@Service("testService")
public class TestService {

	@Resource(name = "testBiz")
	private TestBiz testBiz;
	
	@PerformanceMonitor
	public void doSomething() {
		try {
			Thread.sleep(4 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		testBiz.doSomething1();
		try {
			Thread.sleep(4 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		testBiz.doSomething2();
		try {
			Thread.sleep(4 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
