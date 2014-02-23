package org.danielli.xultimate.context.dfs.fastdfs;

import java.io.File;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.ProtoCommon;
import org.csource.fastdfs.ServerInfo;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.danielli.xultimate.context.dfs.fastdfs.support.StorageClientTemplate;
import org.danielli.xultimate.context.dfs.fastdfs.util.FastDFSUtils;
import org.danielli.xultimate.util.io.ResourceUtils;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/dfs/fastdfs/applicationContext-service-fastdfs.xml" })
public class TestAppender1 {

	@Resource(name = "storageClientTemplate")
	private StorageClientTemplate storageClientTemplate;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TestAppender1.class);

	@Test
	public void test() {
		try {
			LOGGER.info("network_timeout={}ms", ClientGlobal.g_network_timeout);
			LOGGER.info("charset={}", ClientGlobal.g_charset);

			storageClientTemplate.execute(new AbstractStorageClientCallback() {
				
				@Override
				public void doInStorageClient(TrackerClient trackerClient, TrackerServer trackerServer, StorageServer storageServer)
						throws Exception {
					String local_filename = ResourceUtils.getClassPathResource("/dfs/fastdfs/README").getFile().getPath();
					
					long startTime;
					ServerInfo[] servers;
					
					byte[] file_buff;
					Map<String, String> metaInformation = new HashMap<String, String>();
			  		String group_name;
			  		String appender_file_id;
			  		String file_ext_name;
			  		int errno;
					
					StorageClient1 storageClient = FastDFSUtils.newStorageClient1(trackerServer, storageServer);
				
					metaInformation.put("width", "800");
					metaInformation.put("heigth", "600");
					metaInformation.put("bgcolor", "#FFFFFF");
					metaInformation.put("author", "Mike");
					
					file_buff = "this is a test".getBytes(ClientGlobal.g_charset);
					LOGGER.info("file length: " + file_buff.length);
					
					group_name = null;
					StorageServer[] storageServers = trackerClient.getStoreStorages(trackerServer, group_name);
					if (storageServers == null) {
						LOGGER.error("get store storage servers fail, error code: " + trackerClient.getErrorCode());
					} else {
						LOGGER.info("store storage servers count: " + storageServers.length);
						
						for (int k=0; k<storageServers.length; k++) {
							LOGGER.info((k+1) + ". " + storageServers[k].getInetSocketAddress().getAddress().getHostAddress() + ":" + storageServers[k].getInetSocketAddress().getPort());
						}
						LOGGER.info("");
					}
					
					startTime = System.currentTimeMillis();
					appender_file_id = storageClient.upload_appender_file1(file_buff, "txt", FastDFSUtils.newNameValuePairs(metaInformation));
					LOGGER.info("upload_appender_file1 time used: " + (System.currentTimeMillis() - startTime) + " ms");
					
					/*
					group_name = "";
					appender_file_id = client.upload_appender_file1(group_name, file_buff, "txt", meta_list);
					*/
					if (appender_file_id == null) {
						LOGGER.error("upload file fail, error code: " + storageClient.getErrorCode());
						return;
					} else {
						LOGGER.info(storageClient.get_file_info1(appender_file_id).toString());
				
						servers = trackerClient.getFetchStorages1(trackerServer, appender_file_id);
						if (servers == null) {
							LOGGER.error("get storage servers fail, error code: " + trackerClient.getErrorCode());
						} else {
							LOGGER.info("storage servers count: " + servers.length);
							for (int k=0; k<servers.length; k++) {
								LOGGER.info((k+1) + ". " + servers[k].getIpAddr() + ":" + servers[k].getPort());
							}
							LOGGER.info("");
						}
							
						metaInformation = new HashMap<String, String>();
						metaInformation.put("width", "1024");
						metaInformation.put("heigth", "768");
						metaInformation.put("bgcolor", "#000000");
						metaInformation.put("author", "Untitle");
						
						startTime = System.currentTimeMillis();
						errno=storageClient.set_metadata1(appender_file_id, FastDFSUtils.newNameValuePairs(metaInformation), ProtoCommon.STORAGE_SET_METADATA_FLAG_MERGE);
						LOGGER.info("set_metadata time used: " + (System.currentTimeMillis() - startTime) + " ms");
						if (errno == 0) {
							LOGGER.info("set_metadata success");
						} else {
							LOGGER.error("set_metadata fail, error no: " + errno);
						}
						
						metaInformation = FastDFSUtils.newMapFromNameValuePairs(storageClient.get_metadata1(appender_file_id));
						if (metaInformation != null) {
							for (Map.Entry<String, String> entry : metaInformation.entrySet()) {
								LOGGER.info(entry.getKey() + " " + entry.getValue());
							}
						}
						  			
						startTime = System.currentTimeMillis();
						file_buff = storageClient.download_file1(appender_file_id);
						LOGGER.info("download_file time used: " + (System.currentTimeMillis() - startTime) + " ms");
						
						if (file_buff != null) {
							LOGGER.info("file length:" + file_buff.length);
			  				LOGGER.info(new String(file_buff));
						}
						
						file_buff = "this is a slave buff".getBytes(ClientGlobal.g_charset);
						file_ext_name = "txt";
						startTime = System.currentTimeMillis();
						errno = storageClient.append_file1(appender_file_id, file_buff);
						LOGGER.info("append_file time used: " + (System.currentTimeMillis() - startTime) + " ms");
						if (errno == 0) {
							LOGGER.info(storageClient.get_file_info1(appender_file_id).toString());
						} else {
							LOGGER.error("append file fail, error no: " + errno);
						}
						
						startTime = System.currentTimeMillis();
					  	errno = storageClient.delete_file1(appender_file_id);
						LOGGER.info("delete_file time used: " + (System.currentTimeMillis() - startTime) + " ms");
						if (errno == 0) {
			  				LOGGER.info("Delete file success");
			  			} else {
			  				LOGGER.error("Delete file fail, error no: " + errno);
			  			}
					}
					
					appender_file_id = storageClient.upload_appender_file1(local_filename, null, FastDFSUtils.newNameValuePairs(metaInformation));
			  		if (appender_file_id != null) {
			  			int ts;
			  			String token;
			  			String file_url;
			  			InetSocketAddress inetSockAddr;
			  			  			
			  			inetSockAddr = trackerServer.getInetSocketAddress();
			  			file_url = "http://" + inetSockAddr.getAddress().getHostAddress();
			  			if (ClientGlobal.g_tracker_http_port != 80) {
			  				 file_url += ":" + ClientGlobal.g_tracker_http_port;
			  			}
			  			file_url += "/" + appender_file_id;
			  			if (ClientGlobal.g_anti_steal_token) {
				  			ts = (int)(System.currentTimeMillis() / 1000);
				  			token = ProtoCommon.getToken(appender_file_id, ts, ClientGlobal.g_secret_key);
				  			file_url += "?token=" + token + "&ts=" + ts;
			  			}
			  		
			  			LOGGER.info(storageClient.get_file_info1(appender_file_id).toString());
			  			LOGGER.info("file url: " + file_url);
			  			
			  			errno = storageClient.download_file1(appender_file_id, 0, 0, "/tmp/" + appender_file_id.replaceAll("/", "_"));
			  			if (errno == 0) {
							LOGGER.info("Download file success");
			  			} else {
			  				LOGGER.error("Download file fail, error no: " + errno);
			  			}
			  			
			  			errno = storageClient.download_file1(appender_file_id, 0, 0, new DownloadFileWriter("/tmp/" + appender_file_id.replaceAll("/", "-")));
			  			if (errno == 0) {
							LOGGER.info("Download file success");
			  			} else {
			  				LOGGER.error("Download file fail, error no: " + errno);
			  			}
			  			
			  			file_ext_name = null;
						startTime = System.currentTimeMillis();
			  			errno = storageClient.append_file1(appender_file_id, local_filename);
			  			LOGGER.info("append_file time used: " + (System.currentTimeMillis() - startTime) + " ms");
						if (errno == 0) {
							LOGGER.info(storageClient.get_file_info1(appender_file_id).toString());
			  			} else {
			  				LOGGER.error("append file fail, error no: " + errno);
			  			}
			  		}
			  		
					File f;
					f = new File(local_filename);
					int nPos = local_filename.lastIndexOf('.');
					if (nPos > 0 && local_filename.length() - nPos <= ProtoCommon.FDFS_FILE_EXT_NAME_MAX_LEN + 1) {
						file_ext_name = local_filename.substring(nPos+1);
					} else {
						file_ext_name = null;
					}
						
			  		appender_file_id = storageClient.upload_appender_file1(null, f.length(), new UploadLocalFileSender(local_filename), file_ext_name, FastDFSUtils.newNameValuePairs(metaInformation));
				    if (appender_file_id != null) {
				    	LOGGER.info(storageClient.get_file_info1(appender_file_id).toString());
				    	
						startTime = System.currentTimeMillis();
			  			errno = storageClient.append_file1(appender_file_id, f.length(), new UploadLocalFileSender(local_filename));
						LOGGER.info("append_file time used: " + (System.currentTimeMillis() - startTime) + " ms");
						if (errno == 0) {
							LOGGER.info(storageClient.get_file_info1(appender_file_id).toString());
						} else {
							LOGGER.error("append file fail, error no: " + errno);
						}
			  			
						startTime = System.currentTimeMillis();
			  			errno = storageClient.modify_file1(appender_file_id, 0, f.length(), new UploadLocalFileSender(local_filename));
						LOGGER.info("modify_file time used: " + (System.currentTimeMillis() - startTime) + " ms");
						if (errno == 0) {
							LOGGER.info(storageClient.get_file_info1(appender_file_id).toString());
						} else {
							LOGGER.error("modify file fail, error no: " + errno);
						}
			  			
						startTime = System.currentTimeMillis();
			  			errno = storageClient.truncate_file1(appender_file_id, 0);
			  			LOGGER.info("truncate_file time used: " + (System.currentTimeMillis() - startTime) + " ms");
						if (errno == 0) {
							LOGGER.info(storageClient.get_file_info1(appender_file_id).toString());
						} else {
							LOGGER.error("truncate file fail, error no: " + errno);
						}
				    } else {
				    	LOGGER.error("Upload file fail, error no: " + errno);
				    }
				    
				    storageServer = trackerClient.getFetchStorage1(trackerServer, appender_file_id);
			  		if (storageServer == null) {
			  			LOGGER.error("getFetchStorage fail, errno code: " + trackerClient.getErrorCode());
			  			return;
			  		}
					/* for test only */
					LOGGER.info("active test to storage server: " + ProtoCommon.activeTest(storageServer.getSocket()));
					
					/* for test only */
					LOGGER.info("active test to tracker server: " + ProtoCommon.activeTest(trackerServer.getSocket()));
				}
			});
		} catch(Exception ex) {
	  		ex.printStackTrace();
	  	}
	}
}
