package com.changgou.controller;

import com.changgou.file.FastDFSFile;
import com.changgou.util.FastDFSUtil;
import entity.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author DJ
 * @Date 2021-01-10 22:42
 */
@RestController
@RequestMapping("fastdfs")
public class FileCenterController {

    @PostMapping("upload")
    public Result upload(@RequestParam("file") MultipartFile file) throws Exception {
        //封装文件信息
        FastDFSFile fastDFSFile = new FastDFSFile(file.getOriginalFilename(), file.getBytes(), StringUtils.getFilenameExtension(file.getOriginalFilename()));
        String[] upload = FastDFSUtil.upload(fastDFSFile);
        //拼接访问地址
        String url = FastDFSUtil.getTrackerInfo() + "/" + upload[0] + "/" + upload[1];
        return Result.success(url);
    }
}
