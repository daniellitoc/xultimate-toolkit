package org.danielli.xultimate.context.dfs.fastdfs;

import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.danielli.xultimate.context.dfs.DistributedFileSystemException;

/**
 * 抽象存储服务器客户端回调。
 * 
 * @author Daniel Li
 * @since 19 Jun 2013
 */
public abstract class AbstractStorageClientCallback implements StorageClientCallback {

	@Override
	public TrackerServer getTrackerServer(TrackerClient trackerClient) throws Exception {
		TrackerServer trackerServer = trackerClient.getConnection();
		if (trackerServer == null) {
			throw new DistributedFileSystemException("trackerClient.getConnection() return null, error code:" + trackerClient.getErrorCode());
		}
		return trackerServer;
	}
	
	@Override
	public StorageServer getStorageServer(TrackerClient trackerClient, TrackerServer trackerServer) throws Exception {
		StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);
		if (storageServer == null) {
			throw new DistributedFileSystemException("trackerClient.getStoreStorage(trackerServer) fail, error code: " + trackerClient.getErrorCode());
		}
		return storageServer;
	}
}
