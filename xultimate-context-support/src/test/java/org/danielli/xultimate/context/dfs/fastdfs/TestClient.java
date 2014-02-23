package org.danielli.xultimate.context.dfs.fastdfs;

import java.io.File;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.ProtoCommon;
import org.csource.fastdfs.ServerInfo;
import org.csource.fastdfs.StorageClient;
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
public class TestClient {
	
	@Resource(name = "storageClientTemplate")
	private StorageClientTemplate storageClientTemplate;

	private static final Logger LOGGER = LoggerFactory.getLogger(TestClient.class);
	
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
			  		String group_name;
			  		String remote_filename;
			  		ServerInfo[] servers;
			  		
					StorageClient client = FastDFSUtils.newStorageClient(trackerServer, storageServer);

					byte[] file_buff;
					Map<String, String> metaInformation = new HashMap<String, String>();
			  		String[] results;
			  		String master_filename;
			  		String prefix_name;
			  		String file_ext_name;
			  		String generated_slave_filename;
			  		int errno;

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
					results = client.upload_file(file_buff, "txt", FastDFSUtils.newNameValuePairs(metaInformation));
					LOGGER.info("upload_file time used: " + (System.currentTimeMillis() - startTime) + " ms");
					/*
					 * group_name = ""; results = client.upload_file(group_name,
					 * file_buff, "txt", meta_list);
					 */
					if (results == null) {
						LOGGER.error("upload file fail, error code: " + client.getErrorCode());
						return;
					} else {
						group_name = results[0];
						remote_filename = results[1];
						LOGGER.info("group_name: " + group_name + ", remote_filename: " + remote_filename);
						LOGGER.info(client.get_file_info(group_name, remote_filename).toString());

						servers = trackerClient.getFetchStorages(trackerServer, group_name, remote_filename);
						if (servers == null) {
							LOGGER.error("get storage servers fail, error code: " + trackerClient.getErrorCode());
						} else {
							LOGGER.info("storage servers count: " + servers.length);
							for (int k = 0; k < servers.length; k++) {
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
						errno = client.set_metadata(group_name, remote_filename, FastDFSUtils.newNameValuePairs(metaInformation), ProtoCommon.STORAGE_SET_METADATA_FLAG_MERGE);
						LOGGER.info("set_metadata time used: " + (System.currentTimeMillis() - startTime) + " ms");
						if (errno == 0) {
							LOGGER.info("set_metadata success");
						} else {
							LOGGER.error("set_metadata fail, error no: " + errno);
						}

						metaInformation = FastDFSUtils.newMapFromNameValuePairs(client.get_metadata(group_name, remote_filename));
						if (metaInformation != null) {
							for (Map.Entry<String, String> entry : metaInformation.entrySet()) {
								LOGGER.info(entry.getKey() + " " + entry.getValue());
							}
						}

						// Thread.sleep(30000);

						startTime = System.currentTimeMillis();
						file_buff = client.download_file(group_name, remote_filename);
						LOGGER.info("download_file time used: " + (System.currentTimeMillis() - startTime) + " ms");

						if (file_buff != null) {
			  				LOGGER.info("file length:" + file_buff.length);
			  				LOGGER.info(new String(file_buff));
						}

						file_buff = "this is a slave buff".getBytes(ClientGlobal.g_charset);
						master_filename = remote_filename;
						prefix_name = "-part1";
						file_ext_name = "txt";
						startTime = System.currentTimeMillis();
						results = client.upload_file(group_name, master_filename, prefix_name, file_buff, file_ext_name, FastDFSUtils.newNameValuePairs(metaInformation));
						LOGGER.info("upload_file time used: " + (System.currentTimeMillis() - startTime) + " ms");
						if (results != null) {
							LOGGER.info("slave file group_name: " + results[0] + ", remote_filename: " + results[1]);

							generated_slave_filename = ProtoCommon.genSlaveFilename(master_filename, prefix_name, file_ext_name);
							if (!generated_slave_filename.equals(results[1])) {
								LOGGER.error("generated slave file: " + generated_slave_filename + "\n != returned slave file: " + results[1]);
							}
							LOGGER.info(client.get_file_info(results[0], results[1]).toString());
						}

						startTime = System.currentTimeMillis();
						errno = client.delete_file(group_name, remote_filename);
						LOGGER.info("delete_file time used: " + (System.currentTimeMillis() - startTime) + " ms");
						if (errno == 0) {
			  				LOGGER.info("Delete file success");
			  			} else {
			  				LOGGER.error("Delete file fail, error no: " + errno);
			  			}
					}

					results = client.upload_file(local_filename, null, FastDFSUtils.newNameValuePairs(metaInformation));
					if (results != null) {
						String file_id;
						int ts;
						String token;
						String file_url;
						InetSocketAddress inetSockAddr;

						group_name = results[0];
						remote_filename = results[1];
						file_id = group_name + StorageClient1.SPLIT_GROUP_NAME_AND_FILENAME_SEPERATOR + remote_filename;

						inetSockAddr = trackerServer.getInetSocketAddress();
						file_url = "http://" + inetSockAddr.getAddress().getHostAddress();
						if (ClientGlobal.g_tracker_http_port != 80) {
							file_url += ":" + ClientGlobal.g_tracker_http_port;
						}
						file_url += "/" + file_id;
						if (ClientGlobal.g_anti_steal_token) {
							ts = (int) (System.currentTimeMillis() / 1000);
							token = ProtoCommon.getToken(file_id, ts, ClientGlobal.g_secret_key);
							file_url += "?token=" + token + "&ts=" + ts;
						}

						LOGGER.info("group_name: " + group_name + ", remote_filename: " + remote_filename);
			  			LOGGER.info(client.get_file_info(group_name, remote_filename).toString());
			  			LOGGER.info("file url: " + file_url);

						errno = client.download_file(group_name, remote_filename, 0, 0, "/tmp/" + remote_filename.replaceAll("/", "_"));
						if (errno == 0) {
							LOGGER.info("Download file success");
			  			} else {
			  				LOGGER.error("Download file fail, error no: " + errno);
			  			}

						errno = client.download_file(group_name, remote_filename, 0, 0, new DownloadFileWriter("/tmp/" + remote_filename.replaceAll("/", "-")));
						if (errno == 0) {
							LOGGER.info("Download file success");
			  			} else {
			  				LOGGER.error("Download file fail, error no: " + errno);
			  			}
						
						master_filename = remote_filename;
						prefix_name = "-part2";
						file_ext_name = null;
						startTime = System.currentTimeMillis();
						results = client.upload_file(group_name, master_filename, prefix_name, local_filename, null, FastDFSUtils.newNameValuePairs(metaInformation));
						LOGGER.info("upload_file time used: " + (System.currentTimeMillis() - startTime) + " ms");
						if (results != null) {
							LOGGER.info("slave file group_name: " + results[0] + ", remote_filename: " + results[1]);

							generated_slave_filename = ProtoCommon.genSlaveFilename(master_filename, prefix_name, file_ext_name);
							if (!generated_slave_filename.equals(results[1])) {
								LOGGER.error("generated slave file: " + generated_slave_filename + "\n != returned slave file: " + results[1]);
							}
							LOGGER.info(client.get_file_info(results[0], results[1]).toString());
						}

						File f;
						f = new File(local_filename);
						int nPos = local_filename.lastIndexOf('.');
						if (nPos > 0 && local_filename.length() - nPos <= ProtoCommon.FDFS_FILE_EXT_NAME_MAX_LEN + 1) {
							file_ext_name = local_filename.substring(nPos + 1);
						} else {
							file_ext_name = null;
						}

						results = client.upload_file(null, f.length(), new UploadLocalFileSender(local_filename), file_ext_name, FastDFSUtils.newNameValuePairs(metaInformation));
						if (results != null) {
							group_name = results[0];
							remote_filename = results[1];

							LOGGER.info("group name: " + group_name + ", remote filename: " + remote_filename);
							LOGGER.info(client.get_file_info(group_name, remote_filename).toString());

							master_filename = remote_filename;
							prefix_name = "-part3";
							startTime = System.currentTimeMillis();
							results = client.upload_file(group_name, master_filename, prefix_name, f.length(), new UploadLocalFileSender(local_filename), file_ext_name, FastDFSUtils.newNameValuePairs(metaInformation));
							LOGGER.info("upload_file time used: " + (System.currentTimeMillis() - startTime) + " ms");
							if (results != null) {
								LOGGER.info("slave file group_name: " + results[0] + ", remote_filename: " + results[1]);

								generated_slave_filename = ProtoCommon.genSlaveFilename(master_filename, prefix_name, file_ext_name);
								if (!generated_slave_filename.equals(results[1])) {
									LOGGER.error("generated slave file: " + generated_slave_filename + "\n != returned slave file: " + results[1]);
								}
								LOGGER.info(client.get_file_info(results[0], results[1]).toString());
							}
						} else {
							LOGGER.error("Upload file fail, error no: " + errno);
						}

						storageServer = trackerClient.getFetchStorage(trackerServer, group_name, remote_filename);
						if (storageServer == null) {
							LOGGER.error("getFetchStorage fail, errno code: " + trackerClient.getErrorCode());
							return;
						}
						/* for test only */
						LOGGER.info("active test to storage server: " + ProtoCommon.activeTest(storageServer.getSocket()));
						
						/* for test only */
						LOGGER.info("active test to tracker server: " + ProtoCommon.activeTest(trackerServer.getSocket()));
					}
				}
			});
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
