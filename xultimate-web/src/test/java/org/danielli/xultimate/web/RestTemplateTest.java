package org.danielli.xultimate.web;

import java.util.Collections;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/applicationContext-web-rest.xml"})
public class RestTemplateTest {

	@Resource(name = "restTemplate")
	private RestTemplate restTemplate;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplateTest.class);
	
	@Test
	public void test() {
		HttpHeaders entityHeaders = new HttpHeaders();
		entityHeaders.setContentType(MediaType.valueOf("text/html;charset=UTF-8"));
		entityHeaders.setAccept(Collections.singletonList(MediaType.TEXT_HTML));
		HttpEntity<Object> httpEntity = new HttpEntity<>(entityHeaders);
		
		ResponseEntity<String> entity  = restTemplate.exchange("http://me.1688.com/fans/aliserviceyou.htm?page={page}", HttpMethod.GET, httpEntity, String.class, 7);
		LOGGER.info(entity.getBody());
		
		entity = restTemplate.getForEntity("http://me.1688.com/fans/aliserviceyou.htm?page={page}", String.class, 7);
		LOGGER.info(entity.getBody());
	}
}
