package org.danielli.xultimate.context.dfs.fastdfs;

import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

/**
 * 存储服务器客户端回调。
 * 
 * @author Daniel Li
 * @since 19 Jun 2013
 */
public interface StorageClientCallback {

	/**
	 * 回调实现。
	 * 
	 * @param trackerClient 跟踪服务器客户端。
	 * @param trackerServer 跟踪服务器。
	 * @param storageServer 存储服务器。
	 */
	void doInStorageClient(TrackerClient trackerClient, TrackerServer trackerServer, StorageServer storageServer) throws Exception;
	
	/**
	 * 获取跟踪服务器。
	 * 
	 * @param trackerClient 跟踪服务器客户端。
	 * @return 跟踪服务器。
	 * @throws Exception 可能抛出的任何异常。
	 */
	TrackerServer getTrackerServer(TrackerClient trackerClient) throws Exception;
	
	/**
	 * 获取存储服务器。
	 * 
	 * @param trackerClient 跟踪服务器客户端。
	 * @param trackerServer 跟踪服务器。
	 * @return 存储服务器。
	 * @throws Exception 可能抛出的任何异常。
	 */
	StorageServer getStorageServer(TrackerClient trackerClient, TrackerServer trackerServer) throws Exception;
}
