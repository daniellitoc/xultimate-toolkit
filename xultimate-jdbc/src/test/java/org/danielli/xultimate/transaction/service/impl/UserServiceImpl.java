package org.danielli.xultimate.transaction.service.impl;

import javax.annotation.Resource;

import org.danielli.xultimate.transaction.biz.OtherBiz;
import org.danielli.xultimate.transaction.biz.UserBiz;
import org.danielli.xultimate.transaction.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

	@Resource(name = "userBizImpl")
	private UserBiz userBiz;
	
	@Resource(name = "otherBizImpl")
	private OtherBiz otherBiz;
	
	@Transactional(value = "chainTransactionManager", propagation = Propagation.REQUIRED)
	@Override
	public void testErrorRequired() {
		userBiz.saveUser();
		otherBiz.saveErrorOther();
	}
	
	@Transactional(value = "chainTransactionManager", propagation = Propagation.REQUIRED)
	@Override
	public void testRequired() {
		userBiz.saveUser();
		otherBiz.saveOther();
	};
	
}
