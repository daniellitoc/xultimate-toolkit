/**
 * Copyright (C) 2008 Happy Fish / YuQing
 *
 * FastDFS Java Client may be copied only under the terms of the GNU Lesser
 * General Public License (LGPL).
 * Please visit the FastDFS Home Page http://www.csource.org/ for more detail.
 **/

package org.danielli.xultimate.context.dfs;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.danielli.xultimate.context.dfs.fastdfs.StorageClientCallback;
import org.danielli.xultimate.context.dfs.fastdfs.support.StorageClientTemplate;
import org.danielli.xultimate.context.dfs.fastdfs.util.FastDFSUtils;
import org.danielli.xultimate.util.io.ResourceUtils;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * client test
 * 
 * @author Happy Fish / YuQing
 * @version Version 1.18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/dfs/fastdfs/applicationContext-service-fastdfs.xml"})
public class Test {

	@Resource(name = "storageClientTemplate")
	private StorageClientTemplate storageClientTemplate;

	@org.junit.Test
	public void test() {
		System.out.println("java.version=" + System.getProperty("java.version"));

		try {
			System.out.println("network_timeout=" + ClientGlobal.g_network_timeout + "ms");
			System.out.println("charset=" + ClientGlobal.g_charset);

			storageClientTemplate.execute(new StorageClientCallback() {
				
				@Override
				public TrackerServer getTrackerServer(TrackerClient trackerClient) throws Exception {
					return trackerClient.getConnection();
				}
				
				@Override
				public StorageServer getStorageServer(TrackerClient trackerClient, TrackerServer trackerServer) throws Exception {
					return null;
				}
				
				@Override
				public void doInStorageClient(TrackerServer trackerServer, StorageServer storageServer) throws Exception {
					
					String filePath = ResourceUtils.getClassPathResource("/dfs/fastdfs/README").getPath();
					
					StorageClient1 client = FastDFSUtils.newStorageClient1(trackerServer, storageServer);
					
					Map<String, String> metaInformation = new HashMap<String, String>();
					metaInformation.put("fileName", filePath);
					NameValuePair[] metaList = FastDFSUtils.newNameValuePairs(metaInformation);
					
					String fileId = client.upload_file1(filePath, null, metaList);
					System.out.println("upload success. file id is: " + fileId);

					int i = 0;
					while (i++ < 10) {
						byte[] result = client.download_file1(fileId);
						System.out.println(i + ", download result is: " + result.length);
					}
				}
			});
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
