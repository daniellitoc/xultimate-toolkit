package org.danielli.xultimate.context.dfs.fastdfs;

import javax.annotation.Resource;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.danielli.xultimate.context.dfs.fastdfs.support.StorageClientTemplate;
import org.danielli.xultimate.context.dfs.fastdfs.util.FastDFSUtils;
import org.danielli.xultimate.util.io.ResourceUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/dfs/fastdfs/applicationContext-service-fastdfs.xml" })
public class Test1 {
	
	@Resource(name = "storageClientTemplateWithTrackerGroup")
	private StorageClientTemplate storageClientTemplate;

	private static final Logger LOGGER = LoggerFactory.getLogger(Test1.class);
	
	@Test
	public void test() {
		LOGGER.info("network_timeout={}ms", ClientGlobal.g_network_timeout);
		LOGGER.info("charset={}", ClientGlobal.g_charset);
		
		try {
			storageClientTemplate.execute(new AbstractStorageClientCallback() {
				
				@Override
				public void doInStorageClient(TrackerClient trackerClient, TrackerServer trackerServer, StorageServer storageServer)
						throws Exception {
					StorageClient1 storageClient = FastDFSUtils.newStorageClient1(trackerServer, storageServer);
					
					String filePath = ResourceUtils.getClassPathResource("/dfs/fastdfs/README").getFile().getPath();
					
					NameValuePair[] meta_list = null;
					String fileId = storageClient.upload_file1(filePath, "txt", meta_list); 
					LOGGER.info("Upload local file {} ok, fileid={}", filePath, fileId);
				}
			});
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
