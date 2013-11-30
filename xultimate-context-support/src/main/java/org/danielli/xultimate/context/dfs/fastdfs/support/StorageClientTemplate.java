package org.danielli.xultimate.context.dfs.fastdfs.support;

import java.io.IOException;

import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.danielli.xultimate.context.dfs.DistributedFileSystemException;
import org.danielli.xultimate.context.dfs.fastdfs.StorageClientCallback;
import org.danielli.xultimate.context.dfs.fastdfs.StorageClientReturnedCallback;

public class StorageClientTemplate {

	private TrackerClient trackerClient;

	public TrackerClient getTrackerClient() {
		return trackerClient;
	}

	public void setTrackerClient(TrackerClient trackerClient) {
		this.trackerClient = trackerClient;
	}
	
	public <T> T execute(StorageClientReturnedCallback<T> returnedCallback) throws DistributedFileSystemException {
		TrackerServer trackerServer = null;
		StorageServer storageServer = null;
		try {
			trackerServer = returnedCallback.getTrackerServer(trackerClient);
			storageServer = returnedCallback.getStorageServer(trackerClient, trackerServer);
			return returnedCallback.doInStorageClient(trackerServer, storageServer);
		} catch (Exception e) {
			throw new DistributedFileSystemException(e.getMessage(), e);
		} finally {
			DistributedFileSystemException exception = null;
			if (trackerServer != null) {
				try {
					trackerServer.close();
				} catch (IOException e) {
					exception = new DistributedFileSystemException(e.getMessage(), e);
				}
			}
			if (storageServer != null) {
				try {
					storageServer.close();
				} catch (IOException e) {
					exception.addSuppressed(e);
				}
			}
			if (exception != null) {
				throw exception;
			}
		}
	}
	
	public void execute(StorageClientCallback callback) throws DistributedFileSystemException {
		TrackerServer trackerServer = null;
		StorageServer storageServer = null;
		try {
			trackerServer = callback.getTrackerServer(trackerClient);
			storageServer = callback.getStorageServer(trackerClient, trackerServer);
			callback.doInStorageClient(trackerServer, storageServer);
		} catch (Exception e) {
			throw new DistributedFileSystemException(e.getMessage(), e);
		} finally {
			DistributedFileSystemException exception = null;
			if (trackerServer != null) {
				try {
					trackerServer.close();
				} catch (IOException e) {
					exception = new DistributedFileSystemException(e.getMessage(), e);
				}
			}
			if (storageServer != null) {
				try {
					storageServer.close();
				} catch (IOException e) {
					exception.addSuppressed(e);
				}
			}
			if (exception != null) {
				throw exception;
			}
		}
	}
}
