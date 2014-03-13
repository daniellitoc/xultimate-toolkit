package org.danielli.xultimate.jdbc.datasource.lookup;

/**
 * RoutingDataSource工具类。
 * 
 * @author Daniel Li
 * @since 15 Jun 2013
 */
public class RoutingDataSourceUtils {
	
	private static final ThreadLocal<String> holder = new ThreadLocal<String>();
	
	/**
	 * 设置路由数据源Key。
	 * 
	 * @param dataSourceKey 路由数据源Key。
	 */
	public static void setRoutingDataSourceKey(String routingDataSourceKey) {  
        holder.set(routingDataSourceKey);  
    }  
    
	/**
	 * 获取路由数据源Key。
	 * 
	 * @return 路由数据源Key。
	 */
    public static String getRoutingDataSourceKey() {  
        return holder.get();  
    }  
    
    /**
     * 清理路由数据源Key。
     */
    public static void removeRoutingDataSourceKey () {
    	holder.remove();
    }
}
