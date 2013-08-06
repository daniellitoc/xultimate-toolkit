package org.danielli.xultimate.context.kvStore.memcached;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/kvStore/memcached/applicationContext-service-memcached-daemon.xml"})
public class MemcachedDaemonTest {

	@Test
	public void test() {
		MemcachedDaemon daemon = new MemcachedDaemon();
		daemon.setServerUrl("127.0.0.1:11212");
		daemon.setBinary(true);
		try {
			daemon.afterPropertiesSet();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			Thread.sleep(1000 * 60);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			daemon.destroy();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			Thread.sleep(1000 * 60 * 60);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
