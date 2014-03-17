package org.danielli.xultimate.transaction.biz.impl;

import javax.annotation.Resource;

import org.danielli.xultimate.jdbc.datasource.lookup.RoutingDataSourceUtils;
import org.danielli.xultimate.transaction.biz.UserBiz;
import org.danielli.xultimate.transaction.dao.UserDAO;
import org.springframework.stereotype.Service;

@Service("userBizImpl")
public class UserBizImpl implements UserBiz {

	@Resource(name = "userDAOImpl")
	private UserDAO userDAO;
	
	@Override
	public void saveUser() {
		System.out.println("userDAO.saveUser(); Before");
		RoutingDataSourceUtils.setRoutingDataSourceKey("user");
		userDAO.saveUser();
		System.out.println("userDAO.saveUser(); After");
	}
}
