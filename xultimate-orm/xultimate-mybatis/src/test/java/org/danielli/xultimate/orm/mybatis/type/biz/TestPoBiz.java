package org.danielli.xultimate.orm.mybatis.type.biz;

import java.math.BigDecimal;
import java.util.List;

import org.danielli.xultimate.jdbc.type.StateSet;
import org.danielli.xultimate.orm.mybatis.po.TestPo;

public interface TestPoBiz {

	int save(TestPo testPo);
	
	List<TestPo> findByParentId(Long parentId);
	
	List<TestPo> findBetweenMonty(BigDecimal startMoney, BigDecimal endMoney);
	
	List<TestPo> findByMessage(String message);
	
	List<TestPo> findByIsLock(Boolean isLock);
	
	List<TestPo> findByStateSet(StateSet stateSet);
	
	List<TestPo> findByStateSets(List<Byte> stateSets);
}
