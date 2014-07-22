package org.danielli.xultimate.testexample.mock.support;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class UserServiceMockTest {

	private UserService userService = Mockito.mock(UserService.class);
	
	private UserServiceImpl userServiceImpl = Mockito.mock(UserServiceImpl.class);
	
	@Mock
	private User user;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testUserService() {
		Mockito.when(userService.getByUsername("daniellitoc")).thenReturn(new User("daniellitoc"));
		Mockito.doReturn(new User("daniellitoc")).when(userServiceImpl).getByUsername("daniellitoc");
		Mockito.doNothing().when(userService).deleteByUsername("danielli");
		
		Assert.assertNotNull(userService.getByUsername("daniellitoc"));
		Assert.assertEquals(userServiceImpl.getByUsername("daniellitoc").getUsername(), "daniellitoc");
		userService.deleteByUsername("daniellitoc");
	}
	
	@Test
	public void testUserServiceImpl1() {
		Mockito.when(userServiceImpl.getByUsername("daniellitoc")).thenReturn(new User("daniellitoc"));
		Mockito.doNothing().when(userServiceImpl).deleteByUsername("danielli");
		
		User testUser = userServiceImpl.getByUsername("daniellitoc");
		Assert.assertNotNull(testUser);
		userServiceImpl.deleteByUsername(testUser.getUsername());
	}
	
	@Test
	public void testUserServiceImpl2() {
		userServiceImpl.getByUsername("danielli");
		
		Mockito.verify(userServiceImpl).getByUsername("danielli");
	}
	
	@Test
	public void testUser() {
		Mockito.when(user.getUsername()).thenReturn("daniellitoc");
		Assert.assertEquals("daniellitoc", user.getUsername());
	}
	
}
