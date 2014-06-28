package org.danielli.xultimate.orm.mybatis.type.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.danielli.xultimate.jdbc.type.StateSet;
import org.danielli.xultimate.orm.mybatis.MyBatisRepository;
import org.danielli.xultimate.orm.mybatis.po.TestPo;
import org.danielli.xultimate.orm.mybatis.po.e.TestEnum;

@MyBatisRepository
public interface TestPoDAO {
	
	int save(TestPo testPo);
	
	List<TestPo> findByParentId(Long parentId);
	
	List<TestPo> findBetweenMonty(BigDecimal startMoney, BigDecimal endMoney);
	
	List<TestPo> findByMessage(String message);
	
	List<TestPo> findByIsLock(@Param("isLock") Boolean isLock);
	
	List<TestPo> findByStateSet(@Param("stateSet") StateSet<TestEnum> stateSet);
	
	List<TestPo> findByStateSets(List<Byte> stateSets);
}
