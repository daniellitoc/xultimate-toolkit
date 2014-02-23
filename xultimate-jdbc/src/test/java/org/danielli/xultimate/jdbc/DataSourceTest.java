package org.danielli.xultimate.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.danielli.xultimate.util.performance.PerformanceMonitor;
import org.danielli.xultimate.util.time.stopwatch.support.AdvancedStopWatchSummary;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext-dao-datasource.xml" })
public class DataSourceTest {
	
	@Resource
	private DataSource druidDataSource;
	
	@Resource
	private DataSource c3p0DataSource;
	
	@Test
	public void test() {
		PerformanceMonitor.start("ApplicationContextUtilsTest");
		for (int i = 0; i < 5; i++) {
			try {
				testDataSource(druidDataSource);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			PerformanceMonitor.mark("druidDataSource" + i);
		}
		for (int i = 0; i < 5; i++) {
			try {
				testDataSource(c3p0DataSource);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			PerformanceMonitor.mark("c3p0DataSource" + i);
		}
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
	
	private void testDataSource(DataSource dataSource) throws SQLException {
        for (int i = 0; i < 10000; i++) {
            Connection conn = dataSource.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT 1");
            rs.close();
            stmt.close();
            conn.close();
        }
    }
}
