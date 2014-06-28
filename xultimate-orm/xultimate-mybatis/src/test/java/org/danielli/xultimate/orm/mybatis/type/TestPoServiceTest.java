package org.danielli.xultimate.orm.mybatis.type;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.danielli.xultimate.jdbc.type.StateSet;
import org.danielli.xultimate.orm.mybatis.po.TestPo;
import org.danielli.xultimate.orm.mybatis.po.e.Sex;
import org.danielli.xultimate.orm.mybatis.po.e.TestEnum;
import org.danielli.xultimate.orm.mybatis.type.service.TestPoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-service-config.xml", "classpath:applicationContext-service-crypto.xml", "classpath*:applicationContext-dao-*.xml", "classpath:applicationContext-service-generic.xml" })
public class TestPoServiceTest {
	
	@Resource(name = "testPoServiceImpl")
	private TestPoService testPoService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TestPoServiceTest.class);
	
	@Test
	public void test() {
		TestPo testPo1 = new TestPo();
		testPo1.setParentId(10L);
		testPo1.setOtherId(15);
		testPo1.setLoginCount(5);
		testPo1.setBoost(0.1f);
		testPo1.setMoney(BigDecimal.valueOf(1000, 2));
		testPo1.setMessage("Daniel Li's Home");
		testPo1.setIntroduction("I'm Daniel Li");
		testPo1.setSex(Sex.MALE);
		testPo1.setIsLock(false);
		testPo1.setHasLogin(false);
		StateSet<TestEnum> stateSet1 = StateSet.of(TestEnum.class);
		stateSet1.add(TestEnum.CAN_EXECUTE);
		stateSet1.add(TestEnum.CAN_WRITE);
		testPo1.setStateSet(stateSet1);
		testPo1.setLoginIp("192.168.1.3");
		
		testPoService.save(testPo1);
		LOGGER.info("Add TestPo: {}", testPo1);
		
		TestPo testPo2 = new TestPo();
		testPo2.setParentId(null);
		testPo2.setOtherId(null);
		testPo2.setLoginCount(5);
		testPo2.setBoost(0.1f);
		testPo2.setMoney(null);
		testPo2.setMessage(null);
		testPo2.setIntroduction(null);
		testPo2.setSex(Sex.MALE);
		testPo2.setIsLock(true);
		testPo2.setHasLogin(true);
		StateSet<TestEnum> stateSet2 = StateSet.of(TestEnum.class);
		stateSet2.add(TestEnum.CAN_EXECUTE);
		stateSet2.add(TestEnum.CAN_READ);
		testPo2.setStateSet(stateSet2);
		testPo2.setLoginIp(null);
		
		testPoService.save(testPo2);
		LOGGER.info("Add TestPo: {}", testPo2);
		
		
		try {
			TestPo testPo3 = new TestPo();
			testPo3.setParentId(null);
			testPo3.setOtherId(null);
			testPo3.setLoginCount(5);
			testPo3.setBoost(0.1f);
			testPo3.setMoney(null);
			testPo3.setMessage(null);
			testPo3.setIntroduction(null);
			testPo3.setSex(Sex.MALE);
			testPo3.setIsLock(null);
			testPo3.setHasLogin(null);
			StateSet<TestEnum> stateSet3 = StateSet.of(TestEnum.class);
			stateSet3.add(TestEnum.CAN_EXECUTE);
			stateSet3.add(TestEnum.CAN_READ);
			testPo3.setStateSet(stateSet3);
			testPo3.setLoginIp(null);
			
			testPoService.save(testPo3);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		showTestPoList(testPoService.findByParentId(null), "findByParentId(null)");
		showTestPoList(testPoService.findByParentId(10L), "findByParentId(10L)");
		
		showTestPoList(testPoService.findBetweenMonty(BigDecimal.valueOf(1000, 2), BigDecimal.valueOf(1001, 2)), "testPoService.findBetweenMonty(BigDecimal.valueOf(1000, 2), BigDecimal.valueOf(1001, 2))");
		
		showTestPoList(testPoService.findByMessage("Daniel Li's Home"), "testPoService.findByMessage(\"Daniel Li's Home\")");
		showTestPoList(testPoService.findByMessage(null), "testPoService.findByMessage(null)");
		
		showTestPoList(testPoService.findByIsLock(true), "testPoService.findByIsLock(true)");
		showTestPoList(testPoService.findByIsLock(false), "testPoService.findByIsLock(false)");
		
		StateSet<TestEnum> stateSet = StateSet.of(TestEnum.class);
		stateSet.add(TestEnum.CAN_EXECUTE);
		stateSet.add(TestEnum.CAN_READ);
		showTestPoList(testPoService.findByStateSet(stateSet), "testPoService.findByStateSet(stateSet)");
		stateSet.remove(TestEnum.CAN_READ);
		showTestPoList(testPoService.findByStateSets(StateSet.getContainStates(stateSet)), "testPoService.findByStateSets(StateSetUtils.getContainStates(stateSet))");
	}
	
	public void showTestPoList(List<TestPo> testPosList, String message) {
		LOGGER.info(message);
		for (TestPo testPo : testPosList) {
			LOGGER.info("\t{}", testPo);
		}
	}

}
