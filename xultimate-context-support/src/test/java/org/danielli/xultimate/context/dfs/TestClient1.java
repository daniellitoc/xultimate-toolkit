/**
* Copyright (C) 2008 Happy Fish / YuQing
*
* FastDFS Java Client may be copied only under the terms of the GNU Lesser
* General Public License (LGPL).
* Please visit the FastDFS Home Page http://www.csource.org/ for more detail.
**/

package org.danielli.xultimate.context.dfs;

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
import org.danielli.xultimate.context.dfs.fastdfs.StorageClientCallback;
import org.danielli.xultimate.context.dfs.fastdfs.support.StorageClientTemplate;
import org.danielli.xultimate.context.dfs.fastdfs.util.FastDFSUtils;
import org.danielli.xultimate.util.io.ResourceUtils;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
* client test
* @author Happy Fish / YuQing
* @version Version 1.16
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/dfs/fastdfs/applicationContext-service-fastdfs.xml" })
public class TestClient1 {
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
			  		/*
			  		storageServer = tracker.getStoreStorage(trackerServer);
			  		if (storageServer == null)
			  		{
			  			System.out.println("getStoreStorage fail, error code: " + tracker.getErrorCode());
			  			return;
			  		}
			  		*/
				}

				@Override
				public void doInStorageClient(TrackerServer trackerServer, StorageServer storageServer) throws Exception {
					String local_filename = ResourceUtils.getClassPathResource("/dfs/fastdfs/README").getPath();
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
					
			  		StorageServer[] storageServers = storageClientTemplate.getTrackerClient().getStoreStorages(trackerServer, group_name);
					if (storageServers == null) {
						System.err.println("get store storage servers fail, error code: " + storageClientTemplate.getTrackerClient().getErrorCode());
					} else {
						System.err.println("store storage servers count: " + storageServers.length);
						for (int k=0; k<storageServers.length; k++) {
							System.err.println((k+1) + ". " + storageServers[k].getInetSocketAddress().getAddress().getHostAddress() + ":" + storageServers[k].getInetSocketAddress().getPort());
						}
						System.err.println("");
					}
			  		
					metaInformation.put("width", "800");
					metaInformation.put("heigth", "600");
					metaInformation.put("bgcolor", "#FFFFFF");
					metaInformation.put("author", "Mike");
			  		
					file_buff = "this is a test".getBytes(ClientGlobal.g_charset);
			  		System.out.println("file length: " + file_buff.length);
			  		
			  		file_id = storageClient1.upload_file1(file_buff, "txt", FastDFSUtils.newNameValuePairs(metaInformation));
			  		/*
			  		group_name = "group1";
			  		file_id = client.upload_file1(group_name, file_buff, "txt", meta_list);
			  		*/
			  		if (file_id == null) {
			  			System.err.println("upload file fail, error code: " + storageClient1.getErrorCode());
			  			return;
			  		}
			  		else
			  		{
			  			System.err.println("file_id: " + file_id);
			  			System.err.println(storageClient1.get_file_info1(file_id));
							
							ServerInfo[] servers = storageClientTemplate.getTrackerClient().getFetchStorages1(trackerServer, file_id);
							if (servers == null)
							{
								System.err.println("get storage servers fail, error code: " + storageClientTemplate.getTrackerClient().getErrorCode());
							}
							else
							{
								System.err.println("storage servers count: " + servers.length);
								for (int k=0; k<servers.length; k++)
								{
									System.err.println((k+1) + ". " + servers[k].getIpAddr() + ":" + servers[k].getPort());
								}
								System.err.println("");
							}
							
							metaInformation = new HashMap<String, String>();
							metaInformation.put("width", "1024");
							metaInformation.put("heigth", "768");
							metaInformation.put("bgcolor", "#000000");
							metaInformation.put("author", "Untitle");
							
			  			if ((errno=storageClient1.set_metadata1(file_id, FastDFSUtils.newNameValuePairs(metaInformation), ProtoCommon.STORAGE_SET_METADATA_FLAG_MERGE)) == 0)
			  			{
			  				System.err.println("set_metadata success");
			  			}
			  			else
			  			{
			  				System.err.println("set_metadata fail, error no: " + errno);
			  			}
						
						metaInformation = FastDFSUtils.newMapFromNameValuePairs(storageClient1.get_metadata1(file_id));
						if (metaInformation != null) {
							for (Map.Entry<String, String> entry : metaInformation.entrySet()) {
								System.out.println(entry.getKey() + " " + entry.getValue());
							}
						}
			  			
			  			//Thread.sleep(30000);
			  			
			  			file_buff = storageClient1.download_file1(file_id);
			  			if (file_buff != null)
			  			{
			  				System.out.println("file length:" + file_buff.length);
			  				System.out.println((new String(file_buff)));
			  			}
			  			
			  			master_file_id = file_id;
			  			prefix_name = "-part1";
			  			file_ext_name = "txt";
			  			file_buff = "this is a slave buff.".getBytes(ClientGlobal.g_charset);
			  			slave_file_id = storageClient1.upload_file1(master_file_id, prefix_name, file_buff, file_ext_name, FastDFSUtils.newNameValuePairs(metaInformation));
			  			if (slave_file_id != null)
			  			{
			  				System.err.println("slave file_id: " + slave_file_id);
			  				System.err.println(storageClient1.get_file_info1(slave_file_id));
			  				
								generated_slave_file_id = ProtoCommon.genSlaveFilename(master_file_id, prefix_name, file_ext_name);
								if (!generated_slave_file_id.equals(slave_file_id))
								{
									System.err.println("generated slave file: " + generated_slave_file_id + "\n != returned slave file: " + slave_file_id);
								}
			  			}
			  			
			  			//Thread.sleep(10000);
			  			if ((errno=storageClient1.delete_file1(file_id)) == 0) {
			  				System.err.println("Delete file success");
			  			} else {
			  				System.err.println("Delete file fail, error no: " + errno);
			  			}
			  		}

			  		if ((file_id=storageClient1.upload_file1(local_filename, null, FastDFSUtils.newNameValuePairs(metaInformation))) != null)
			  		{
			  			int ts;
			  			String token;
			  			String file_url;
			  			InetSocketAddress inetSockAddr;
			  			
			  			System.err.println("file_id: " + file_id);
			  			System.err.println(storageClient1.get_file_info1(file_id));
			  			
			  			inetSockAddr = trackerServer.getInetSocketAddress();
			  			file_url = "http://" + inetSockAddr.getAddress().getHostAddress();
			  			if (ClientGlobal.g_tracker_http_port != 80)
			  			{
			  				 file_url += ":" + ClientGlobal.g_tracker_http_port;
			  			}
			  			file_url += "/" + file_id;
			  			if (ClientGlobal.g_anti_steal_token)
			  			{
				  			ts = (int)(System.currentTimeMillis() / 1000);
				  			token = ProtoCommon.getToken(file_id, ts, ClientGlobal.g_secret_key);
				  			file_url += "?token=" + token + "&ts=" + ts;
			  			}
			  			System.err.println("file url: " + file_url);
			  			
			  			errno = storageClient1.download_file1(file_id, 0, 100, "c:\\" + file_id.replaceAll("/", "_"));
			  			if (errno == 0)
			  			{
			  				System.err.println("Download file success");
			  			}
			  			else
			  			{
			  				System.err.println("Download file fail, error no: " + errno);
			  			}
			  			
			  			errno = storageClient1.download_file1(file_id, new DownloadFileWriter("c:\\" + file_id.replaceAll("/", "-")));
			  			if (errno == 0)
			  			{
			  				System.err.println("Download file success");
			  			}
			  			else
			  			{
			  				System.err.println("Download file fail, error no: " + errno);
			  			}
			  			
			  			master_file_id = file_id;
			  			prefix_name = "-part2";
			  			file_ext_name = null;
			  			slave_file_id = storageClient1.upload_file1(master_file_id, prefix_name, local_filename, file_ext_name, FastDFSUtils.newNameValuePairs(metaInformation));
			  			if (slave_file_id != null)
			  			{
			  				System.err.println("slave file_id: " + slave_file_id);
			  				System.err.println(storageClient1.get_file_info1(slave_file_id));
			  				
								generated_slave_file_id = ProtoCommon.genSlaveFilename(master_file_id, prefix_name, file_ext_name);
								if (!generated_slave_file_id.equals(slave_file_id))
								{
									System.err.println("generated slave file: " + generated_slave_file_id + "\n != returned slave file: " + slave_file_id);
								}
			  			}
			  		}

						File f;
						f = new File(local_filename);
						int nPos = local_filename.lastIndexOf('.');
						if (nPos > 0 && local_filename.length() - nPos <= ProtoCommon.FDFS_FILE_EXT_NAME_MAX_LEN + 1)
						{
							file_ext_name = local_filename.substring(nPos+1);
						}
						else
						{
							file_ext_name = null;
						}
						
			  		file_id = storageClient1.upload_file1(null, f.length(), new UploadLocalFileSender(local_filename), file_ext_name, FastDFSUtils.newNameValuePairs(metaInformation));
				    if (file_id != null)
				    {
				    	System.out.println("file id: " + file_id);
				    	System.out.println(storageClient1.get_file_info1(file_id));
			  			master_file_id = file_id;
			  			prefix_name = "-part3";
			  			slave_file_id = storageClient1.upload_file1(master_file_id, prefix_name, f.length(), new UploadLocalFileSender(local_filename), file_ext_name, FastDFSUtils.newNameValuePairs(metaInformation));
			  			if (slave_file_id != null)
			  			{
			  				System.err.println("slave file_id: " + slave_file_id);
								generated_slave_file_id = ProtoCommon.genSlaveFilename(master_file_id, prefix_name, file_ext_name);
								if (!generated_slave_file_id.equals(slave_file_id))
								{
									System.err.println("generated slave file: " + generated_slave_file_id + "\n != returned slave file: " + slave_file_id);
								}
			  			}
				    }
				    else
				    {
				    	System.err.println("Upload file fail, error no: " + errno);
				    }
				    
			  		storageServer = storageClientTemplate.getTrackerClient().getFetchStorage1(trackerServer, file_id);
			  		if (storageServer == null)
			  		{
			  			System.out.println("getFetchStorage fail, errno code: " + storageClientTemplate.getTrackerClient().getErrorCode());
			  			return;
			  		}
			  		
			  		/* for test only */
			  		System.out.println("active test to storage server: " + ProtoCommon.activeTest(storageServer.getSocket()));
			  		
			  		/* for test only */
			  		System.out.println("active test to tracker server: " + ProtoCommon.activeTest(trackerServer.getSocket()));
				}
			});
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
