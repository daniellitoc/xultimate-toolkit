package org.danielli.xultimate.context.dfs;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/dfs/fastdfs/applicationContext-service-fastdfs.xml" })
public class Test1 {
	
	@Resource(name = "storageClientTemplate")
	private StorageClientTemplate storageClientTemplate;

	@org.junit.Test
	public void test() {
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
					return trackerClient.getStoreStorage(trackerServer);
				}
				
				@Override
				public void doInStorageClient(TrackerServer trackerServer, StorageServer storageServer) throws Exception {
					String filePath = ResourceUtils.getClassPathResource("/dfs/fastdfs/README").getPath();
					
					StorageClient1 storageClient = FastDFSUtils.newStorageClient1(trackerServer, storageServer);
					NameValuePair[] meta_list = null; // new NameValuePair[0];
					String fileid = storageClient.upload_file1(filePath, "exe", meta_list); 
					System.out.println("Upload local file " + filePath + " ok, fileid=" + fileid);
				}
			});
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
