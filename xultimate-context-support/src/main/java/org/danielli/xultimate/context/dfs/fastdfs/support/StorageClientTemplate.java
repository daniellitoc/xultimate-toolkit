package org.danielli.xultimate.context.dfs.fastdfs.support;

import java.io.IOException;

import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.danielli.xultimate.context.dfs.DistributedFileSystemException;
import org.danielli.xultimate.context.dfs.fastdfs.StorageClientCallback;
import org.danielli.xultimate.context.dfs.fastdfs.StorageClientReturnedCallback;

/**
 * 存储服务器客户端模板类。
 * 
 * @author Daniel Li
 * @since 19 Jun 2013
 */
public class StorageClientTemplate {

	/** 跟踪服务器客户端 */
	private TrackerClient trackerClient;

	/**
	 * 设置跟踪服务器客户端。
	 * 
	 * @param trackerClient 跟踪服务器客户端。
	 */
	public void setTrackerClient(TrackerClient trackerClient) {
		this.trackerClient = trackerClient;
	}
	
	/**
	 * 此方法可以通过跟踪服务器客户端、跟踪服务器、存储服务器执行相应功能。
	 * 
	 * @param returnedCallback 回调。
	 * @return 回调返回值。
	 * @throws DistributedFileSystemException 分布式文件系统异常。
	 */
	public <T> T execute(StorageClientReturnedCallback<T> returnedCallback) throws DistributedFileSystemException {
		TrackerServer trackerServer = null;
		StorageServer storageServer = null;
		try {
			trackerServer = returnedCallback.getTrackerServer(trackerClient);
			storageServer = returnedCallback.getStorageServer(trackerClient, trackerServer);
			return returnedCallback.doInStorageClient(trackerClient, trackerServer, storageServer);
		}  catch (DistributedFileSystemException e) {
			throw e;
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
					exception = new DistributedFileSystemException(e.getMessage(), e);
				}
			}
			if (exception != null) {
				throw exception;
			}
		}
	}
	
	/**
	 * 此方法可以通过跟踪服务器客户端、跟踪服务器、存储服务器执行相应功能。
	 * 
	 * @param callback 回调。
	 * @throws DistributedFileSystemException 分布式文件系统异常。
	 */
	public void execute(StorageClientCallback callback) throws DistributedFileSystemException {
		TrackerServer trackerServer = null;
		StorageServer storageServer = null;
		try {
			trackerServer = callback.getTrackerServer(trackerClient);
			storageServer = callback.getStorageServer(trackerClient, trackerServer);
			callback.doInStorageClient(trackerClient, trackerServer, storageServer);
		} catch (DistributedFileSystemException e) {
			throw e;
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
					exception = new DistributedFileSystemException(e.getMessage(), e);
				}
			}
			if (exception != null) {
				throw exception;
			}
		}
	}
}
