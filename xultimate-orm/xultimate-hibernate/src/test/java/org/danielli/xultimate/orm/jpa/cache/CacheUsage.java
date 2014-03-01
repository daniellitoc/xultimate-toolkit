package org.danielli.xultimate.orm.jpa.cache;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.danielli.xultimate.orm.jpa.cache.domain.Address;
import org.danielli.xultimate.orm.jpa.cache.domain.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * 理解Hibernate3一级和二级(也包括查询)缓存专用。
 * 
 * @deprecated Hibernate4以后，hibernate-memcached没有更新，也觉得二级缓存不好控制，所以选择在Biz层手动或通过Spring缓存支持memcached。
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext-service-util.xml", "classpath:/applicationContext-service-crypto.xml", "classpath:/applicationContext-service-config.xml", "classpath:/applicationContext-service-generic.xml", "classpath:/applicationContext-dao-base.xml", "classpath:/applicationContext-dao-generic.xml" })
@TransactionConfiguration(defaultRollback = false)
public class CacheUsage {

	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;

	private static final Logger LOGGER = LoggerFactory.getLogger(CacheUsage.class);

//	 @Test
	public void t0() {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		Person danielli = new Person();
		danielli.setName("Daniel Li");
		danielli.setAge(22);

		Address libeijing = new Address();
		libeijing.setLocation("Li Beijing");
		libeijing.setPhone("187010609204");
		libeijing.setPerson(danielli);

		Address lishanghai = new Address();
		lishanghai.setLocation("Li ShangHai");
		lishanghai.setPhone("187010609204");
		lishanghai.setPerson(danielli);

		Set<Address> danielliAddresses = new HashSet<Address>();
		danielliAddresses.add(libeijing);
		danielliAddresses.add(lishanghai);

		danielli.setAddresses(danielliAddresses);
		session.save(danielli);

		Person danielxiao = new Person();
		danielxiao.setName("Daniel Xiao");
		danielxiao.setAge(22);

		Address xiaobeijing = new Address();
		xiaobeijing.setLocation("Xiao Beijing");
		xiaobeijing.setPhone("187010609204");
		xiaobeijing.setPerson(danielxiao);

		Address xiaoshanghai = new Address();
		xiaoshanghai.setLocation("Xiao ShangHai");
		xiaoshanghai.setPhone("187010609204");
		xiaoshanghai.setPerson(danielxiao);

		Set<Address> danielxiaoAddresses = new HashSet<Address>();
		danielxiaoAddresses.add(xiaobeijing);
		danielxiaoAddresses.add(xiaoshanghai);

		danielxiao.setAddresses(danielxiaoAddresses);
		session.save(danielxiao);

		Person danielhuo = new Person();
		danielhuo.setName("Daniel Huo");
		danielhuo.setAge(22);

		Address huobeijing = new Address();
		huobeijing.setLocation("Huo Beijing");
		huobeijing.setPhone("187010609204");
		huobeijing.setPerson(danielhuo);

		Address huoshanghai = new Address();
		huoshanghai.setLocation("Huo ShangHai");
		huoshanghai.setPhone("187010609204");
		huoshanghai.setPerson(danielhuo);

		Set<Address> danielhuoAddresses = new HashSet<Address>();
		danielhuoAddresses.add(huobeijing);
		danielhuoAddresses.add(huoshanghai);

		danielhuo.setAddresses(danielhuoAddresses);
		session.save(danielhuo);

		transaction.commit();
		session.close();
	}
	
	@Test
	public void t1() {
		Session session = null;
		Transaction transaction = null;

		// 保存
		LOGGER.info("Save");
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		Person danielli = new Person();
		danielli.setName("Daniel Li");
		danielli.setAge(22);
		Address libeijing = new Address();
		libeijing.setLocation("Li Beijing");
		libeijing.setPhone("187010609204");
		libeijing.setPerson(danielli);
		Address lishanghai = new Address();
		lishanghai.setLocation("Li ShangHai");
		lishanghai.setPhone("187010609204");
		lishanghai.setPerson(danielli);
		Set<Address> danielliAddresses = new HashSet<Address>();
		danielliAddresses.add(libeijing);
		danielliAddresses.add(lishanghai);
		danielli.setAddresses(danielliAddresses);
		session.save(danielli);
		transaction.commit();
		session.close();
		// 查询
		LOGGER.info("Searching");
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		danielli = (Person) session.get(Person.class, danielli.getId());
		danielli.getName();
		danielli.getAddresses().size();
		transaction.commit();
		session.close();
		// 更新
		LOGGER.info("Update");
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		danielli = (Person) session.get(Person.class, danielli.getId());
//		danielli.setName("Daniel Li Changed");
//		session.update(danielli);
		session.createQuery("update Person set name = :name where id = :id").setParameter("name", "Daniel Li Changed").setParameter("id", danielli.getId()).executeUpdate();
		transaction.commit();
		session.close();
		// 查询
		LOGGER.info("Searching");
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		danielli = (Person) session.get(Person.class, danielli.getId());
		danielli.getName();
		danielli.getAddresses().size();
		transaction.commit();
		session.close();
		// 删除
		LOGGER.info("Delete");
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		danielli = (Person) session.get(Person.class, danielli.getId());
//		session.delete(danielli);
		 session.createQuery("delete from Address where person = :person").setParameter("person", danielli).executeUpdate();
		 session.createQuery("delete from Person where id = :id").setParameter("id", danielli.getId()).executeUpdate();
		transaction.commit();
		session.close();
		// 查询
		LOGGER.info("Searching");
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		session.get(Person.class, danielli.getId());
		transaction.commit();
		session.close();
	}
	
//	@Test
	public void t2() {
		Session session = null;
		Transaction transaction = null;
		// 保存
		LOGGER.info("Save");
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		Person danielli = new Person();
		danielli.setName("Daniel Li");
		danielli.setAge(22);
		Address libeijing = new Address();
		libeijing.setLocation("Li Beijing");
		libeijing.setPhone("187010609204");
		libeijing.setPerson(danielli);
		Address lishanghai = new Address();
		lishanghai.setLocation("Li ShangHai");
		lishanghai.setPhone("187010609204");
		lishanghai.setPerson(danielli);
		Set<Address> danielliAddresses = new HashSet<Address>();
		danielliAddresses.add(libeijing);
		danielliAddresses.add(lishanghai);
		danielli.setAddresses(danielliAddresses);
		session.save(danielli);
		transaction.commit();
		session.close();
		// 查询
		LOGGER.info("Searching");
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		danielli = (Person) session.get(Person.class, danielli.getId());
		danielli.getName();
		LOGGER.info("" + danielli.getAddresses().size());
		transaction.commit();
		session.close();
		// 删除
		LOGGER.info("Delete");
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		lishanghai = (Address) session.get(Address.class, lishanghai.getId());
//		lishanghai.getPerson().getAddresses().remove(lishanghai);
		session.delete(lishanghai);
//		session.createQuery("delete from Address where id = :id").setParameter("id", lishanghai.getId()).executeUpdate();
		transaction.commit();
		session.close();
		// 查询
		LOGGER.info("Search");
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		danielli = (Person) session.get(Person.class, danielli.getId());
		danielli.getName();
		LOGGER.info("" + danielli.getAddresses().size());
		transaction.commit();
		session.close();
	}
	
//	@Test
	public void t3() {
		Session session = null;
		Transaction transaction = null;
		// 保存
		LOGGER.info("Save");
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		Person danielli = new Person();
		danielli.setName("Daniel Li");
		danielli.setAge(22);
		Address libeijing = new Address();
		libeijing.setLocation("Li Beijing");
		libeijing.setPhone("187010609204");
		libeijing.setPerson(danielli);
		Address lishanghai = new Address();
		lishanghai.setLocation("Li ShangHai");
		lishanghai.setPhone("187010609204");
		lishanghai.setPerson(danielli);
		Set<Address> danielliAddresses = new HashSet<Address>();
		danielliAddresses.add(libeijing);
		danielliAddresses.add(lishanghai);
		danielli.setAddresses(danielliAddresses);
		session.save(danielli);
		transaction.commit();
		session.close();
		// 查询
		LOGGER.info("Searching");
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		danielli = (Person) session.get(Person.class, danielli.getId());
		danielli.getName();
		LOGGER.info("" + danielli.getAddresses().size());
		transaction.commit();
		session.close();	
		// 更新
		LOGGER.info("Update");
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		lishanghai = (Address) session.get(Address.class, lishanghai.getId());
		lishanghai.setLocation("Li Hangzhou");
		session.update(lishanghai);
//		session.createQuery("update Address set location = :location where id = :id").setParameter("location", "Li Hangzhou").setParameter("id", lishanghai.getId()).executeUpdate();
		transaction.commit();
		session.close();
		// 查询
		LOGGER.info("Search");
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		danielli = (Person) session.get(Person.class, danielli.getId());
		danielli.getName();
		for (Address address : danielli.getAddresses()) {
			LOGGER.info(address.getLocation());
		}
		transaction.commit();
		session.close();
	} 
	
//	@Test
	@SuppressWarnings("unchecked")
	public void t4() {
		Session session = null;
		Transaction transaction = null;
		
		// 保存
		LOGGER.info("Save");
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		Person danielli = new Person();
		danielli.setName("Daniel Li");
		danielli.setAge(22);
		Address libeijing = new Address();
		libeijing.setLocation("Li Beijing");
		libeijing.setPhone("187010609204");
		libeijing.setPerson(danielli);
		Address lishanghai = new Address();
		lishanghai.setLocation("Li ShangHai");
		lishanghai.setPhone("187010609204");
		lishanghai.setPerson(danielli);
		Set<Address> danielliAddresses = new HashSet<Address>();
		danielliAddresses.add(libeijing);
		danielliAddresses.add(lishanghai);
		danielli.setAddresses(danielliAddresses);
		session.save(danielli);
		transaction.commit();
		session.close();
		
		// 查询
		LOGGER.info("Search");
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		List<Address> danielliAddressList = session.createQuery("from Address where person = :person").setParameter("person", danielli).setCacheable(true).list();
		LOGGER.info("" + danielliAddressList.size());
		transaction.commit();
		session.close();
		
		// 删除
		LOGGER.info("Delete");
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		lishanghai = (Address) session.get(Address.class, lishanghai.getId());
//		session.delete(lishanghai);
		session.createQuery("delete from Address where id = :id").setParameter("id", lishanghai.getId()).executeUpdate();
		transaction.commit();
		session.close();
		
		// 查询
		LOGGER.info("Search");
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		danielliAddressList = session.createQuery("from Address where person = :person").setParameter("person", danielli).setCacheable(true).list();
		LOGGER.info("" + danielliAddressList.size());
		transaction.commit();
		session.close();
	}
	
//	@Test
	@SuppressWarnings("unchecked")
	public void t5() {
		Session session = null;
		Transaction transaction = null;
		
		// 保存
		LOGGER.info("Save");
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		Person danielli = new Person();
		danielli.setName("Daniel Li");
		danielli.setAge(22);
		Address libeijing = new Address();
		libeijing.setLocation("Li Beijing");
		libeijing.setPhone("187010609204");
		libeijing.setPerson(danielli);
		Address lishanghai = new Address();
		lishanghai.setLocation("Li ShangHai");
		lishanghai.setPhone("187010609204");
		lishanghai.setPerson(danielli);
		Set<Address> danielliAddresses = new HashSet<Address>();
		danielliAddresses.add(libeijing);
		danielliAddresses.add(lishanghai);
		danielli.setAddresses(danielliAddresses);
		session.save(danielli);
		transaction.commit();
		session.close();
		
		// 查询
		LOGGER.info("Search");
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		List<Address> danielliAddressList = session.createQuery("from Address where person = :person").setParameter("person", danielli).setCacheable(true).list();
		LOGGER.info("" + danielliAddressList.size());
		transaction.commit();
		session.close();
		
		// 删除
		LOGGER.info("Update");
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		lishanghai = (Address) session.get(Address.class, lishanghai.getId());
		lishanghai.setLocation("Li Hangzhou");
		session.update(lishanghai);
//		session.createQuery("update Address set location = :location where id = :id").setParameter("location", "Li Hangzhou").setParameter("id", lishanghai.getId()).executeUpdate();
		transaction.commit();
		session.close();
		
		// 查询
		LOGGER.info("Search");
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		danielliAddressList = session.createQuery("from Address where person = :person").setParameter("person", danielli).setCacheable(true).list();
		LOGGER.info("" + danielliAddressList.size());
		transaction.commit();
		session.close();
	}
	
	
	 
	// @Test
	public void testSaveUpdateGetAndSaveOnEntity() {
		Session session = null;
		Transaction transaction = null;

		session = sessionFactory.openSession();
		transaction = session.beginTransaction();

		Person danielli = new Person();
		danielli.setName("Daniel Li");
		danielli.setAge(22);
		Address libeijing = new Address();
		libeijing.setLocation("Li Beijing");
		libeijing.setPhone("187010609204");
		libeijing.setPerson(danielli);
		Address lishanghai = new Address();
		lishanghai.setLocation("Li ShangHai");
		lishanghai.setPhone("187010609204");
		lishanghai.setPerson(danielli);
		Set<Address> danielliAddresses = new HashSet<Address>();
		danielliAddresses.add(libeijing);
		danielliAddresses.add(lishanghai);
		danielli.setAddresses(danielliAddresses);

		session.save(danielli);
		LOGGER.info(danielli.getName());
		transaction.commit();
		session.close();

		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		danielli = (Person) session.get(Person.class, danielli.getId());
		LOGGER.info(danielli.getName());
		LOGGER.info("" + danielli.getAddresses());
		transaction.commit();
		session.close();

		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		libeijing = (Address) session.get(Address.class, libeijing.getId());
		LOGGER.info(libeijing.getLocation());
		transaction.commit();
		session.close();

		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		danielli = (Person) session.get(Person.class, danielli.getId());
		LOGGER.info(danielli.getName());
		transaction.commit();
		session.close();

		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		danielli = (Person) session.get(Person.class, danielli.getId());
		danielli.setName("Daniel Li Changed");
		session.update(danielli);
		LOGGER.info(danielli.getName());
		transaction.commit();
		session.close();

		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		libeijing = (Address) session.get(Address.class, libeijing.getId());
		LOGGER.info(libeijing.getLocation());
		transaction.commit();
		session.close();

		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		danielli = (Person) session.get(Person.class, danielli.getId());
		LOGGER.info(danielli.getName());
		transaction.commit();
		session.close();

		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		danielli = (Person) session.get(Person.class, danielli.getId());
		session.delete(danielli);
		transaction.commit();
		session.close();

		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		session.get(Address.class, libeijing.getId());
		transaction.commit();
		session.close();

		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		session.get(Address.class, libeijing.getId());
		transaction.commit();
		session.close();

		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		session.get(Person.class, danielli.getId());
		transaction.commit();
		session.close();

		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		session.get(Person.class, danielli.getId());
		transaction.commit();
		session.close();
	}
}
