package org.danielli.xultimate.context.dfs.fastdfs;

import java.util.HashMap;
import java.util.Map;

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
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/dfs/fastdfs/applicationContext-service-fastdfs.xml"})
public class Test {

	@Resource(name = "storageClientTemplate")
	private StorageClientTemplate storageClientTemplate;

	private static final Logger LOGGER = LoggerFactory.getLogger(Test.class);
	
	@org.junit.Test
	public void test() {
		LOGGER.info("network_timeout={}ms", ClientGlobal.g_network_timeout);
		LOGGER.info("charset={}", ClientGlobal.g_charset);
		
		try {
			storageClientTemplate.execute(new AbstractStorageClientCallback() {
				
				@Override
				public StorageServer getStorageServer(TrackerClient trackerClient, TrackerServer trackerServer)
						throws Exception {
					return null;
				}
				
				@Override
				public void doInStorageClient(TrackerClient trackerClient, TrackerServer trackerServer, StorageServer storageServer)
						throws Exception {
					StorageClient1 client = FastDFSUtils.newStorageClient1(trackerServer, storageServer);
					
					String filePath = ResourceUtils.getClassPathResource("/dfs/fastdfs/README").getFile().getPath();
					
					Map<String, String> metaInformation = new HashMap<String, String>();
					metaInformation.put("fileName", filePath);
					NameValuePair[] metaList = FastDFSUtils.newNameValuePairs(metaInformation);
					
					String fileId = client.upload_file1(filePath, null, metaList);
					LOGGER.info("upload success. file id is: {}", fileId);
					
					for (int i = 0; i < 10; i++) {
						byte[] result = client.download_file1(fileId);
						LOGGER.info("{}, download result is:  {}", i, result.length);
					}
				}
			});
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
