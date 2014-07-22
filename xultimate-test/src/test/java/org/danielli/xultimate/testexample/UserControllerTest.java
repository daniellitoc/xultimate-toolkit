package org.danielli.xultimate.testexample;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration(value = "src/main/webapp")
@ContextHierarchy({  
    @ContextConfiguration(name = "parent", locations = { "classpath*:/applicationContext-service*.xml", "classpath*:/applicationContext-dao*.xml" }),  
    @ContextConfiguration(name = "child", locations = "classpath:applicationContext-servlet.xml")  
})  
public class UserControllerTest {

	@Autowired  
    private WebApplicationContext webApplicationContext;  
	
    private MockMvc mockMvc;  
  
    @Before  
    public void init() {  
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();  
    }
    
    @Test
    public void testParam11() throws Exception {
    	 MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/users/param1_1").param("message", "Test Message"))
    	            .andExpect(MockMvcResultMatchers.view().name("param1"))  
    	            .andExpect(MockMvcResultMatchers.model().attributeExists("message"))  
    	            .andDo(MockMvcResultHandlers.print())  
    	            .andReturn();  
    	      
    	 Assert.assertEquals("Test Message", result.getModelAndView().getModelMap().get("message"));
    }
}
