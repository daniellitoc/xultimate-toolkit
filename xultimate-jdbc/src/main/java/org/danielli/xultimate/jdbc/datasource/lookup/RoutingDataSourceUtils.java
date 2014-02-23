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
	 * 设置数据源Key。
	 * 
	 * @param dataSourceKey 数据源Key。
	 */
	public static void setDataSourceKey(String dataSourceKey) {  
        holder.set(dataSourceKey);  
    }  
    
	/**
	 * 获取数据源Key。
	 * 
	 * @return 数据源Key。
	 */
    public static String getDataSourceKey() {  
        return holder.get();  
    }  
    
    /**
     * 清理数据源Key。
     */
    public static void removeDataSourceKey () {
    	holder.remove();
    }
}
