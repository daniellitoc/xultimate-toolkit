package org.danielli.xultimate.orm.jpa.area;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.danielli.xultimate.orm.jpa.area.domain.Area;
import org.danielli.xultimate.orm.jpa.area.service.AreaService;
import org.danielli.xultimate.orm.jpa.ds.ComparsionOperator;
import org.danielli.xultimate.orm.jpa.ds.Item;
import org.danielli.xultimate.orm.jpa.ds.LogicalOperator;
import org.danielli.xultimate.orm.jpa.ds.ValueUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext-service-util.xml", "classpath:/applicationContext-service-crypto.xml", "classpath:/applicationContext-service-config.xml", "classpath:/applicationContext-service-generic.xml", "classpath:/applicationContext-dao-base.xml", "classpath:/applicationContext-dao-generic.xml" })
public class AreaServiceImplTest {

	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	
	private static final Logger logger = LoggerFactory.getLogger(AreaServiceImplTest.class);
	
	@Test
	public void test() {
		Area area = new Area();
		area.setName("北京");
		logger.info("Add Area : {}", (area = areaService.save(area)));
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		List<Area> areas =  areaService.findAll();
		logger.info("Show Areas :");
		for (Area a : areas) {
			logger.info("\t" + a);
			a.setName("上海");
			logger.info("\tUpdate Area : {}", areaService.save(a));
		}
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		areas = areaService.findAll();
		logger.info("Show Areas :");
		for (Area a : areas) {
			logger.info("\t" + a.getName());
			areaService.delete(a);
			logger.info("\tDelete Area : {}", a);
		}

		try {
			area = new Area();
			area.setName("北京");
			areaService.testRollback(area);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Item<String> item = new Item<>();
		item.setKey("name");
		item.setComparsionOperator(ComparsionOperator.LIKE);
		item.setLogicalOperator(LogicalOperator.AND);
		item.setValue(ValueUtils.newValue(true, "上海", true));
		
		List<Item<? extends Object>> items = new ArrayList<>();
		items.add(item);
		
		areas = areaService.findByItems(items);
		System.out.println(areas.size());
		for (Area a : areas) {
			logger.info("\t" + a.getName());
		}
		
		
		areas = areaService.findAll();
		logger.info("Show Areas :");
		for (Area a : areas) {
			logger.info("\t" + a.getName());
			areaService.delete(a);
			logger.info("\tDelete Area : {}", a.getId());
		}
	}
}
