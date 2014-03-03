package org.danielli.xultimate.orm.mybatis.type.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.danielli.xultimate.jdbc.type.StateSet;
import org.danielli.xultimate.orm.mybatis.po.TestPo;
import org.danielli.xultimate.orm.mybatis.type.biz.TestPoBiz;
import org.danielli.xultimate.orm.mybatis.type.service.TestPoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("testPoServiceImpl")
public class TestPoServiceImpl implements TestPoService {

	@Resource(name = "testPoBizImpl")
	private TestPoBiz testPoBiz;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int save(TestPo testPo) {
		return testPoBiz.save(testPo);
	}

	@Override
	public List<TestPo> findByParentId(Long parentId) {
		return testPoBiz.findByParentId(parentId);
	}

	@Override
	public List<TestPo> findBetweenMonty(BigDecimal startMoney, BigDecimal endMoney) {
		return testPoBiz.findBetweenMonty(startMoney, endMoney);
	}

	@Override
	public List<TestPo> findByMessage(String message) {
		return testPoBiz.findByMessage(message);
	}

	@Override
	public List<TestPo> findByIsLock(Boolean isLock) {
		return testPoBiz.findByIsLock(isLock);
	}

	@Override
	public List<TestPo> findByStateSet(StateSet stateSet) {
		return testPoBiz.findByStateSet(stateSet);
	}

	@Override
	public List<TestPo> findByStateSets(List<Byte> stateSets) {
		return testPoBiz.findByStateSets(stateSets);
	}
	
	

}
