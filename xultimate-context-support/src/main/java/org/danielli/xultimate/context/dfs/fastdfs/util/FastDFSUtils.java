package org.danielli.xultimate.context.dfs.fastdfs.util;

import java.util.HashMap;
import java.util.Map;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerServer;
import org.danielli.xultimate.util.ArrayUtils;
import org.danielli.xultimate.util.collections.MapUtils;

/**
 * FastDFS工具类。
 * 
 * @author Daniel Li
 * @since 19 Jun 2013
 */
public class FastDFSUtils {

	/**
	 * 创建存储服务器客户端。
	 * 
	 * @param trackerServer 跟踪服务器。
	 * @param storageServer 存储服务器。
	 * @return 存储服务器客户端。
	 */
	public static StorageClient1 newStorageClient1(TrackerServer trackerServer, StorageServer storageServer) {
		return new StorageClient1(trackerServer, storageServer);
	}
	
	/**
	 * 创建存储服务器客户端。
	 * 
	 * @param trackerServer 跟踪服务器。
	 * @param storageServer 存储服务器。
	 * @return 存储服务器客户端。
	 */
	public static StorageClient newStorageClient(TrackerServer trackerServer, StorageServer storageServer) {
		return new StorageClient(trackerServer, storageServer);
	}
	
	/**
	 * 转换Map类型的元数据信息为NameValuePair数组形式。
	 * 
	 * @param metaInformation Map类型的元数据信息。
	 * @return NameValuePair 数组类型的元数据信息。
	 */
	public static NameValuePair[] newNameValuePairs(Map<String, String> metaInformation) {
		if (MapUtils.isEmpty(metaInformation)) {
			return null;
		}
		NameValuePair[] nameValuePairs = new NameValuePair[metaInformation.size()];
		{
			int i = 0;
			for (Map.Entry<String, String> entry : metaInformation.entrySet()) {
				nameValuePairs[i++] = new NameValuePair(entry.getKey(), entry.getValue());
			}
		}
		return nameValuePairs;
	}
	
	/**
	 * 转换NameValuePair数组类型的元数据信息为Map形式。
	 * 
	 * @param nameValuePairs 数组类型的元数据信息。
	 * @return Map类型的元数据信息。
	 */
	public static Map<String, String> newMapFromNameValuePairs(NameValuePair[] nameValuePairs) {
		Map<String, String> metaInformation = new HashMap<String, String>();
		if (ArrayUtils.isNotEmpty(nameValuePairs)) {
			for (NameValuePair entry : nameValuePairs) {
				metaInformation.put(entry.getName(), entry.getValue());
			}
		}
		return metaInformation;
	}
}
