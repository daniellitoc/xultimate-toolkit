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
public class TestClient1 {
	
	@Resource(name = "storageClientTemplate")
	private StorageClientTemplate storageClientTemplate;

	private static final Logger LOGGER = LoggerFactory.getLogger(TestClient1.class);
	
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
				  	String group_name;
				  	
					String file_id;
					
					StorageClient1 storageClient1 = FastDFSUtils.newStorageClient1(trackerServer, storageServer);
					
			  		byte[] file_buff;
			  		Map<String, String> metaInformation = new HashMap<String, String>();
			  		String master_file_id;
			  		String prefix_name;
			  		String file_ext_name;
			  		String slave_file_id;
			  		String generated_slave_file_id;
			  		int errno;
			  		
			  		group_name = "group1";
					
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
			  		
					metaInformation.put("width", "800");
					metaInformation.put("heigth", "600");
					metaInformation.put("bgcolor", "#FFFFFF");
					metaInformation.put("author", "Mike");
			  		
					file_buff = "this is a test".getBytes(ClientGlobal.g_charset);
					LOGGER.info("file length: " + file_buff.length);
			  		
			  		file_id = storageClient1.upload_file1(file_buff, "txt", FastDFSUtils.newNameValuePairs(metaInformation));
			  		/*
			  		group_name = "group1";
			  		file_id = client.upload_file1(group_name, file_buff, "txt", meta_list);
			  		*/
			  		if (file_id == null) {
			  			LOGGER.error("upload file fail, error code: " + storageClient1.getErrorCode());
			  			return;
			  		} else {
			  			LOGGER.info("file_id: " + file_id);
			  			LOGGER.info(storageClient1.get_file_info1(file_id).toString());
			  			
						ServerInfo[] servers = trackerClient.getFetchStorages1(trackerServer, file_id);
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
							
			  			if ((errno=storageClient1.set_metadata1(file_id, FastDFSUtils.newNameValuePairs(metaInformation), ProtoCommon.STORAGE_SET_METADATA_FLAG_MERGE)) == 0) {
			  				LOGGER.info("set_metadata success");
			  			} else {
			  				LOGGER.error("set_metadata fail, error no: " + errno);
			  			}
						
						metaInformation = FastDFSUtils.newMapFromNameValuePairs(storageClient1.get_metadata1(file_id));
						if (metaInformation != null) {
							for (Map.Entry<String, String> entry : metaInformation.entrySet()) {
								LOGGER.info(entry.getKey() + " " + entry.getValue());
							}
						}
			  			
			  			//Thread.sleep(30000);
			  			
			  			file_buff = storageClient1.download_file1(file_id);
			  			if (file_buff != null) {
			  				LOGGER.info("file length:" + file_buff.length);
			  				LOGGER.info(new String(file_buff));
			  			}
			  			
			  			master_file_id = file_id;
			  			prefix_name = "-part1";
			  			file_ext_name = "txt";
			  			file_buff = "this is a slave buff.".getBytes(ClientGlobal.g_charset);
			  			slave_file_id = storageClient1.upload_file1(master_file_id, prefix_name, file_buff, file_ext_name, FastDFSUtils.newNameValuePairs(metaInformation));
			  			if (slave_file_id != null) {
			  				LOGGER.info("slave file_id: " + slave_file_id);
			  				LOGGER.info(storageClient1.get_file_info1(slave_file_id).toString());
			  				
							generated_slave_file_id = ProtoCommon.genSlaveFilename(master_file_id, prefix_name, file_ext_name);
							if (!generated_slave_file_id.equals(slave_file_id)) {
								LOGGER.error("generated slave file: " + generated_slave_file_id + "\n != returned slave file: " + slave_file_id);
							}
			  			}
			  			
			  			//Thread.sleep(10000);
			  			if ((errno=storageClient1.delete_file1(file_id)) == 0) {
			  				LOGGER.info("Delete file success");
			  			} else {
			  				LOGGER.error("Delete file fail, error no: " + errno);
			  			}
			  		}

			  		if ((file_id=storageClient1.upload_file1(local_filename, null, FastDFSUtils.newNameValuePairs(metaInformation))) != null) {
			  			int ts;
			  			String token;
			  			String file_url;
			  			InetSocketAddress inetSockAddr;
			  			
			  			LOGGER.info("file_id: " + file_id);
			  			LOGGER.info(storageClient1.get_file_info1(file_id).toString());
			  			
			  			inetSockAddr = trackerServer.getInetSocketAddress();
			  			file_url = "http://" + inetSockAddr.getAddress().getHostAddress();
			  			if (ClientGlobal.g_tracker_http_port != 80) {
			  				 file_url += ":" + ClientGlobal.g_tracker_http_port;
			  			}
			  			file_url += "/" + file_id;
			  			if (ClientGlobal.g_anti_steal_token) {
				  			ts = (int)(System.currentTimeMillis() / 1000);
				  			token = ProtoCommon.getToken(file_id, ts, ClientGlobal.g_secret_key);
				  			file_url += "?token=" + token + "&ts=" + ts;
			  			}
			  			LOGGER.info("file url: " + file_url);
			  			
			  			errno = storageClient1.download_file1(file_id, 0, 100, "/tmp/" + file_id.replaceAll("/", "_"));
			  			if (errno == 0) {
			  				LOGGER.info("Download file success");
			  			} else {
			  				LOGGER.error("Download file fail, error no: " + errno);
			  			}
			  			
			  			errno = storageClient1.download_file1(file_id, new DownloadFileWriter("/tmp/" + file_id.replaceAll("/", "-")));
			  			if (errno == 0) {
			  				LOGGER.info("Download file success");
			  			} else {
			  				LOGGER.error("Download file fail, error no: " + errno);
			  			}
			  			
			  			master_file_id = file_id;
			  			prefix_name = "-part2";
			  			file_ext_name = null;
			  			slave_file_id = storageClient1.upload_file1(master_file_id, prefix_name, local_filename, file_ext_name, FastDFSUtils.newNameValuePairs(metaInformation));
			  			if (slave_file_id != null) {
			  				LOGGER.info("slave file_id: " + slave_file_id);
				  			LOGGER.info(storageClient1.get_file_info1(slave_file_id).toString());

							generated_slave_file_id = ProtoCommon.genSlaveFilename(master_file_id, prefix_name, file_ext_name);
							if (!generated_slave_file_id.equals(slave_file_id)) {
								LOGGER.error("generated slave file: " + generated_slave_file_id + "\n != returned slave file: " + slave_file_id);
							}
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
						
			  		file_id = storageClient1.upload_file1(null, f.length(), new UploadLocalFileSender(local_filename), file_ext_name, FastDFSUtils.newNameValuePairs(metaInformation));
				    if (file_id != null) {
				    	LOGGER.info("file id: " + file_id);
			  			LOGGER.info(storageClient1.get_file_info1(file_id).toString());
			  			master_file_id = file_id;
			  			prefix_name = "-part3";
			  			slave_file_id = storageClient1.upload_file1(master_file_id, prefix_name, f.length(), new UploadLocalFileSender(local_filename), file_ext_name, FastDFSUtils.newNameValuePairs(metaInformation));
			  			if (slave_file_id != null) {
			  				LOGGER.info("slave file_id: " + slave_file_id);
			  				
							generated_slave_file_id = ProtoCommon.genSlaveFilename(master_file_id, prefix_name, file_ext_name);
							if (!generated_slave_file_id.equals(slave_file_id)) {
								LOGGER.error("generated slave file: " + generated_slave_file_id + "\n != returned slave file: " + slave_file_id);
							}
			  			}
				    } else {
				    	LOGGER.error("Upload file fail, error no: " + errno);
				    }
				    
			  		storageServer = trackerClient.getFetchStorage1(trackerServer, file_id);
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
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
