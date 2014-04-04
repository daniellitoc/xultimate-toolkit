package org.danielli.xultimate.jdbc.datasource.lookup;

/**
 * DataSource上下文。
 * 
 * @author Daniel Li
 * @since 15 Jun 2013
 */
public class DataSourceContext {
	
	private static final ThreadLocal<String> currentLookupKey = new ThreadLocal<String>();
	
	/**
	 * 设置数据源Key。
	 * 
	 * @param lookupKey 数据源Key。
	 * @return 原数据源Key。
	 */
	public static String setCurrentLookupKey(String lookupKey) {  
		String oldLookupKey = currentLookupKey.get();
		if (lookupKey != null) {
			currentLookupKey.set(lookupKey);
		}
		else {
			currentLookupKey.remove();
		}
		return oldLookupKey;
    }  
    
	/**
	 * 获取当前数据源Key。
	 * 
	 * @return 当前数据源Key。
	 */
    public static String currentLookupKey() {  
    	return currentLookupKey.get();
    }  
}
