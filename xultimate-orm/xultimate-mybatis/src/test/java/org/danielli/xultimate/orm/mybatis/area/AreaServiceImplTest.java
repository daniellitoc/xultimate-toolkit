package org.danielli.xultimate.orm.mybatis.area;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.danielli.xultimate.orm.mybatis.area.service.AreaService;
import org.danielli.xultimate.orm.mybatis.ds.ComparsionOperator;
import org.danielli.xultimate.orm.mybatis.ds.Item;
import org.danielli.xultimate.orm.mybatis.ds.LogicalOperator;
import org.danielli.xultimate.orm.mybatis.ds.ValueUtils;
import org.danielli.xultimate.orm.mybatis.po.Area;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-service-config.xml", "classpath:applicationContext-service-crypto.xml", "classpath*:applicationContext-dao-*.xml", "classpath:applicationContext-service-generic.xml" })
public class AreaServiceImplTest {

	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	
	private static final Logger logger = LoggerFactory.getLogger(AreaServiceImplTest.class);

	@Test
	public void test() {
		Area parent = new Area();
		parent.setName("北京");
		parent.setDisplayName("北京");
		areaService.save(parent);
		
		Area area = new Area();
		area.setName("朝阳");
		area.setDisplayName("北京朝阳");
		area.setParentId(parent.getId());
		areaService.save(area);
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		List<Area> areas =  areaService.findAll();
		logger.info("Show Areas :");
		for (Area a : areas) {
			logger.info("\t" + a.getName());
			a.setName("上海");
			areaService.update(a);
		}
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		try {
			area = new Area();
			area.setId(3L);
			area.setName("大兴");
			area.setDisplayName("北京大兴");
			area.setParentId(parent.getId());
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
		try {
			areas = areaService.findByItems(items);
			for (Area a : areas) {
				logger.info("\t" + a.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		areas = areaService.findAll();
		logger.info("Show Areas :");
		for (Area a : areas) {
			logger.info("\t" + a.getName());
			areaService.delete(a);
		}
		
		logger.info("\t" + areaService.findAll().size());
	}
}
