package com.changgou.util;

import com.changgou.file.FastDFSFile;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;

/**
 * 文件的增删改查
 * tracker信息获取
 * storage信息获取
 *
 * @Author DJ
 * @Date 2021-01-10 22:12
 */
public class FastDFSUtil {
    /**
     * 加载tracker链接信息
     */
    static {
        try {
            //ClassPathResource Spring加载resource目录下的文件资源
            String filename = new ClassPathResource("fdfs_client.conf").getPath();
            ClientGlobal.init(filename);
        } catch (IOException | MyException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件上传
     *
     * @param fastDFSFile
     * @return
     * @throws Exception
     */
    public static String[] upload(FastDFSFile fastDFSFile) throws Exception {
        //附加参数
        NameValuePair[] metaList = new NameValuePair[1];
        metaList[0] = new NameValuePair("author", fastDFSFile.getAuthor());
        StorageClient storageClient = getStorageClient();
        /**
         *uploads[0]:文件存储到storage上的组的名字
         * uploads[1]:文件存储到storage上的文件的名字 M00/02/04/XXX.jpg
         */
        String[] uploads = storageClient.upload_file(fastDFSFile.getContent(), fastDFSFile.getExt(), metaList);
        return uploads;
    }

    /**
     * 获取文件信息
     *
     * @param groupName
     * @param remoteFilename
     * @return
     * @throws Exception
     */
    public static FileInfo getFileInfo(String groupName, String remoteFilename) throws Exception {
        StorageClient storageClient = getStorageClient();
        return storageClient.get_file_info(groupName, remoteFilename);
    }

    /**
     * 文件下载
     *
     * @param groupName
     * @param remoteFilename
     * @return
     * @throws Exception
     */
    public static InputStream downloadFile(String groupName, String remoteFilename) throws Exception {
        StorageClient storageClient = getStorageClient();
        byte[] bytes = storageClient.download_file(groupName, remoteFilename);
        return new ByteArrayInputStream(bytes);
    }

    /**
     * 删除文件
     *
     * @param groupName
     * @param remoteFilename
     * @throws Exception
     */
    public static void deleteFile(String groupName, String remoteFilename) throws Exception {
        StorageClient storageClient = getStorageClient();
        storageClient.delete_file(groupName, remoteFilename);
    }

    /**
     * 获取storage客户端
     *
     * @return
     * @throws IOException
     */
    public static StorageClient getStorageClient() throws IOException {
        //创建一个trackerClient，获取trackerServer
        TrackerClient trackerClient = new TrackerClient();
        //根据trackerServer获取storageClient
        TrackerServer trackerServer = trackerClient.getConnection();
        return new StorageClient(trackerServer, null);
    }

    /**
     * 获取storage服务端
     *
     * @return
     * @throws Exception
     */
    public static StorageServer getStorageServer() throws Exception {
        //创建一个trackerClient，获取trackerServer
        TrackerClient trackerClient = new TrackerClient();
        //根据trackerServer获取storageClient
        TrackerServer trackerServer = trackerClient.getConnection();
        return trackerClient.getStoreStorage(trackerServer);
    }

    /**
     * 获取storageServer的IP和端口信息
     *
     * @param groupName
     * @param remoteFilename
     * @return
     * @throws Exception
     */
    public static ServerInfo[] getServerInfo(String groupName, String remoteFilename) throws Exception {
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        return trackerClient.getFetchStorages(trackerServer, groupName, remoteFilename);
    }

    /**
     * 获取trackerServer信息
     *
     * @return
     * @throws Exception
     */
    public static String getTrackerInfo() throws Exception {
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        String host = trackerServer.getInetSocketAddress().getHostString();
        int trackerHttpPort = ClientGlobal.getG_tracker_http_port();
        return "http://" + host + ":" + trackerHttpPort;
    }
}
