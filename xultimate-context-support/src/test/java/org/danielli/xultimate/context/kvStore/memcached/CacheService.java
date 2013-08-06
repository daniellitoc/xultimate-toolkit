package org.danielli.xultimate.context.kvStore.memcached;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

public class CacheService {

	@Cacheable(value="user", key="#userId")  
	public String addUser(Integer userId, String userName) {
		System.out.println(userId + ":" + userName);
		return userName;
	}
	
	@Cacheable(value="user", key="#userId")  
	public String getUserNameById(Integer userId) {
		System.out.println(userId);
		return "Name";
	}
	
	@CacheEvict(value="user",key="#userId")  
	public void deleteUser(Integer userId) {  

	}  
}
