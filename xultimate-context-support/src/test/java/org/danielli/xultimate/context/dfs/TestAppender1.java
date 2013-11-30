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
* @version Version 1.20
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/dfs/fastdfs/applicationContext-service-fastdfs.xml" })
public class TestAppender1 {

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
					String local_filename = ResourceUtils.getClassPathResource("/dfs/fastdfs/README").getPath();;
					
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
					System.out.println("file length: " + file_buff.length);
					
					group_name = null;
					StorageServer[] storageServers = storageClientTemplate.getTrackerClient().getStoreStorages(trackerServer, group_name);
					if (storageServers == null) {
						System.err.println("get store storage servers fail, error code: " + storageClientTemplate.getTrackerClient().getErrorCode());
					} else {
						System.err.println("store storage servers count: " + storageServers.length);
						for (int k=0; k<storageServers.length; k++)
						{
							System.err.println((k+1) + ". " + storageServers[k].getInetSocketAddress().getAddress().getHostAddress() + ":" + storageServers[k].getInetSocketAddress().getPort());
						}
						System.err.println("");
					}
					
					startTime = System.currentTimeMillis();
					appender_file_id = storageClient.upload_appender_file1(file_buff, "txt", FastDFSUtils.newNameValuePairs(metaInformation));
					System.out.println("upload_appender_file1 time used: " + (System.currentTimeMillis() - startTime) + " ms");
					
					/*
					group_name = "";
					appender_file_id = client.upload_appender_file1(group_name, file_buff, "txt", meta_list);
					*/
					if (appender_file_id == null) {
						System.err.println("upload file fail, error code: " + storageClient.getErrorCode());
						return;
					} else {
						System.err.println(storageClient.get_file_info1(appender_file_id));
				
						servers = storageClientTemplate.getTrackerClient().getFetchStorages1(trackerServer, appender_file_id);
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
						
						startTime = System.currentTimeMillis();
						errno=storageClient.set_metadata1(appender_file_id, FastDFSUtils.newNameValuePairs(metaInformation), ProtoCommon.STORAGE_SET_METADATA_FLAG_MERGE);
						System.out.println("set_metadata time used: " + (System.currentTimeMillis() - startTime) + " ms");
						if (errno == 0)
						{
							System.err.println("set_metadata success");
						}
						else
						{
							System.err.println("set_metadata fail, error no: " + errno);
						}
						
						metaInformation = FastDFSUtils.newMapFromNameValuePairs(storageClient.get_metadata1(appender_file_id));
						if (metaInformation != null) {
							for (Map.Entry<String, String> entry : metaInformation.entrySet()) {
								System.out.println(entry.getKey() + " " + entry.getValue());
							}
						}
						  			
						startTime = System.currentTimeMillis();
						file_buff = storageClient.download_file1(appender_file_id);
						System.out.println("download_file time used: " + (System.currentTimeMillis() - startTime) + " ms");
						
						if (file_buff != null)
						{
							System.out.println("file length:" + file_buff.length);
							System.out.println((new String(file_buff)));
						}
						
						file_buff = "this is a slave buff".getBytes(ClientGlobal.g_charset);
						file_ext_name = "txt";
						startTime = System.currentTimeMillis();
						errno = storageClient.append_file1(appender_file_id, file_buff);
						System.out.println("append_file time used: " + (System.currentTimeMillis() - startTime) + " ms");
						if (errno == 0) {					
								System.err.println(storageClient.get_file_info1(appender_file_id));
						} else {
							System.err.println("append file fail, error no: " + errno);
						}
						
						startTime = System.currentTimeMillis();
					  	errno = storageClient.delete_file1(appender_file_id);
						System.out.println("delete_file time used: " + (System.currentTimeMillis() - startTime) + " ms");
						if (errno == 0) {
							System.err.println("Delete file success");
						} else {
							System.err.println("Delete file fail, error no: " + errno);
						}
					}
					
					appender_file_id = storageClient.upload_appender_file1(local_filename, null, FastDFSUtils.newNameValuePairs(metaInformation));
			  		if (appender_file_id != null)
			  		{
			  			int ts;
			  			String token;
			  			String file_url;
			  			InetSocketAddress inetSockAddr;
			  			  			
			  			inetSockAddr = trackerServer.getInetSocketAddress();
			  			file_url = "http://" + inetSockAddr.getAddress().getHostAddress();
			  			if (ClientGlobal.g_tracker_http_port != 80)
			  			{
			  				 file_url += ":" + ClientGlobal.g_tracker_http_port;
			  			}
			  			file_url += "/" + appender_file_id;
			  			if (ClientGlobal.g_anti_steal_token)
			  			{
				  			ts = (int)(System.currentTimeMillis() / 1000);
				  			token = ProtoCommon.getToken(appender_file_id, ts, ClientGlobal.g_secret_key);
				  			file_url += "?token=" + token + "&ts=" + ts;
			  			}
			  		
			  			System.err.println(storageClient.get_file_info1(appender_file_id));
			  			System.err.println("file url: " + file_url);
			  			
			  			errno = storageClient.download_file1(appender_file_id, 0, 0, "c:\\" + appender_file_id.replaceAll("/", "_"));
			  			if (errno == 0)
			  			{
			  				System.err.println("Download file success");
			  			}
			  			else
			  			{
			  				System.err.println("Download file fail, error no: " + errno);
			  			}
			  			
			  			errno = storageClient.download_file1(appender_file_id, 0, 0, new DownloadFileWriter("c:\\" + appender_file_id.replaceAll("/", "-")));
			  			if (errno == 0)
			  			{
			  				System.err.println("Download file success");
			  			}
			  			else
			  			{
			  				System.err.println("Download file fail, error no: " + errno);
			  			}
			  			
			  			file_ext_name = null;
							startTime = System.currentTimeMillis();
			  			errno = storageClient.append_file1(appender_file_id, local_filename);
			  			System.out.println("append_file time used: " + (System.currentTimeMillis() - startTime) + " ms");
			  			if (errno == 0)
			  			{
								System.err.println(storageClient.get_file_info1(appender_file_id));
							}
			  			else
			  			{
			  				System.err.println("append file fail, error no: " + errno);
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
						
			  		appender_file_id = storageClient.upload_appender_file1(null, f.length(), 
				       new UploadLocalFileSender(local_filename), file_ext_name, FastDFSUtils.newNameValuePairs(metaInformation));
				    if (appender_file_id != null)
				    {
				    	System.out.println(storageClient.get_file_info1(appender_file_id));
				    	
							startTime = System.currentTimeMillis();
			  			errno = storageClient.append_file1(appender_file_id, f.length(), new UploadLocalFileSender(local_filename));
			  			System.out.println("append_file time used: " + (System.currentTimeMillis() - startTime) + " ms");
			  			if (errno == 0)
			  			{
								System.err.println(storageClient.get_file_info1(appender_file_id));
							}
			  			else
			  			{
			  				System.err.println("append file fail, error no: " + errno);
			  			}
			  			
							startTime = System.currentTimeMillis();
			  			errno = storageClient.modify_file1(appender_file_id, 0, f.length(), new UploadLocalFileSender(local_filename));
			  			System.out.println("modify_file time used: " + (System.currentTimeMillis() - startTime) + " ms");
			  			if (errno == 0)
			  			{
								System.err.println(storageClient.get_file_info1(appender_file_id));
							}
			  			else
			  			{
			  				System.err.println("modify file fail, error no: " + errno);
			  			}
			  			
							startTime = System.currentTimeMillis();
			  			errno = storageClient.truncate_file1(appender_file_id, 0);
			  			System.out.println("truncate_file time used: " + (System.currentTimeMillis() - startTime) + " ms");
			  			if (errno == 0)
			  			{
								System.err.println(storageClient.get_file_info1(appender_file_id));
							}
			  			else
			  			{
			  				System.err.println("truncate file fail, error no: " + errno);
			  			}
				    }
				    else
				    {
				    	System.err.println("Upload file fail, error no: " + errno);
				    }
				    
			  		storageServer = storageClientTemplate.getTrackerClient().getFetchStorage1(trackerServer, appender_file_id);
			  		if (storageServer == null)
			  		{
			  			System.out.println("getFetchStorage fail, errno code: " + storageClientTemplate.getTrackerClient().getErrorCode());
			  			return;
			  		}
			  		/* for test only */
			  		System.out.println("active test to storage server: " + ProtoCommon.activeTest(storageServer.getSocket()));
			  		
			  		storageServer.close();
			  		
			  		/* for test only */
			  		System.out.println("active test to tracker server: " + ProtoCommon.activeTest(trackerServer.getSocket()));
			  		
			  		trackerServer.close();
				}
			});
		}
	  	catch(Exception ex)
	  	{
	  		ex.printStackTrace();
	  	}
	}
}
