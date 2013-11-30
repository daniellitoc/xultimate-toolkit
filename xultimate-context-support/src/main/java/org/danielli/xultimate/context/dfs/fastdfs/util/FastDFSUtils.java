package org.danielli.xultimate.context.dfs.fastdfs.util;

import java.util.HashMap;
import java.util.Map;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerServer;

public class FastDFSUtils {

	public static StorageClient1 newStorageClient1(TrackerServer trackerServer, StorageServer storageServer) {
		return new StorageClient1(trackerServer, storageServer);
	}
	
	public static StorageClient newStorageClient(TrackerServer trackerServer, StorageServer storageServer) {
		return new StorageClient(trackerServer, storageServer);
	}
	
	public static NameValuePair[] newNameValuePairs(Map<String, String> metaInformation) {
		NameValuePair[] nameValuePairs = new NameValuePair[metaInformation.size()];
		int i = 0;
		for (Map.Entry<String, String> entry : metaInformation.entrySet()) {
			nameValuePairs[i++] = new NameValuePair(entry.getKey(), entry.getValue());
		}
		return nameValuePairs;
	}
	
	public static Map<String, String> newMapFromNameValuePairs(NameValuePair[] nameValuePairs) {
		Map<String, String> metaInformation = new HashMap<String, String>();
		for (NameValuePair entry : nameValuePairs) {
			metaInformation.put(entry.getName(), entry.getValue());
		}
		return metaInformation;
	}
}
