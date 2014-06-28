package org.danielli.xultimate.orm.mybatis.type.biz.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.danielli.xultimate.jdbc.type.StateSet;
import org.danielli.xultimate.orm.mybatis.po.TestPo;
import org.danielli.xultimate.orm.mybatis.po.e.TestEnum;
import org.danielli.xultimate.orm.mybatis.type.biz.TestPoBiz;
import org.danielli.xultimate.orm.mybatis.type.dao.TestPoDAO;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

@Service("testPoBizImpl")
public class TestPoBizImpl implements TestPoBiz {

	@Resource(name = "testPoDAO")
	private TestPoDAO testPoDAO;
	
	@Override
	public int save(TestPo testPo) {
		if (testPo.getCreateTime() == null) {
			testPo.setCreateTime(new DateTime().toDate());
		}
		if (testPo.getUpdateTime() == null) {
			testPo.setUpdateTime(testPo.getCreateTime());
		}
		return testPoDAO.save(testPo);
	}

	@Override
	public List<TestPo> findByParentId(Long parentId) {
		return testPoDAO.findByParentId(parentId);
	}

	@Override
	public List<TestPo> findBetweenMonty(BigDecimal startMoney,
			BigDecimal endMoney) {
		return testPoDAO.findBetweenMonty(startMoney, endMoney);
	}

	@Override
	public List<TestPo> findByMessage(String message) {
		return testPoDAO.findByMessage(message);
	}

	@Override
	public List<TestPo> findByIsLock(Boolean isLock) {
		return testPoDAO.findByIsLock(isLock);
	}

	@Override
	public List<TestPo> findByStateSet(StateSet<TestEnum> stateSet) {
		return testPoDAO.findByStateSet(stateSet);
	}

	@Override
	public List<TestPo> findByStateSets(List<Byte> stateSets) {
		return testPoDAO.findByStateSets(stateSets);
	}

}
