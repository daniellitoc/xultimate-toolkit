package org.danielli.xultimate.context.dfs.fastdfs;

import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

public interface StorageClientCallback {

	void doInStorageClient(TrackerServer trackerServer, StorageServer storageServer) throws Exception;
	
	TrackerServer getTrackerServer(TrackerClient trackerClient) throws Exception;
	
	StorageServer getStorageServer(TrackerClient trackerClient, TrackerServer trackerServer) throws Exception;
}
