package org.danielli.xultimate.transaction;

import javax.annotation.Resource;

import org.danielli.xultimate.transaction.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"classpath:/applicationContext-dao-base.xml",
		"classpath:/transaction/applicationContext-service-transaction.xml"
	})
public class ChainedTransactionManagerTest {

	@Resource(name = "userServiceImpl")
	private UserService userService; 
	
	@Test
	public void test() {
		System.out.println("----------------------------------------------------------");
//		userService.testRequired();	
		try {
			userService.testErrorRequired();
		} catch (Exception e) {}
		
		System.out.println("----------------------------------------------------------");
	}
	
}
