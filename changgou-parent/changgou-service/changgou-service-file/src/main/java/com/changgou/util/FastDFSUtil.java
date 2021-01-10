package com.changgou.util;

import com.changgou.file.FastDFSFile;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

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

    public static String[] upload(FastDFSFile fastDFSFile) throws Exception {
        //附加参数
        NameValuePair[] metaList = new NameValuePair[1];
        metaList[0] = new NameValuePair("author", fastDFSFile.getAuthor());
        //创建一个trackerClient，获取trackerServer
        TrackerClient trackerClient = new TrackerClient();
        //根据trackerServer获取storageClient
        TrackerServer trackerServer = trackerClient.getConnection();
        StorageClient storageClient = new StorageClient(trackerServer, null);
        //根据storageClient访问storageServer进行文件上传

        /**
         *uploads[0]:文件存储到storage上的组的名字
         * uploads[1]:文件存储到storage上的文件的名字 M00/02/04/XXX.jpg
         */
        String[] uploads = storageClient.upload_file(fastDFSFile.getContent(), fastDFSFile.getExt(), metaList);
        return uploads;
    }
}
