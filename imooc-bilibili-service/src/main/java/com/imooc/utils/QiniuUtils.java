package com.imooc.utils;

import com.alibaba.fastjson.JSON;
import com.imooc.exception.ConditionException;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.persistent.FileRecorder;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;

public class QiniuUtils {
    @Value("${qiniu.accessKey}")
    static String accessKey;
    @Value("${qiniu.secretKey}")
    static String secretKey;
    @Value("${qiniu.bucket}")
    static String bucket;

    public static final String url = "http://rglk11lfv.hn-bkt.clouddn.com/";

    public static   String getFileType(MultipartFile file) {
        if (file == null) {
            throw new ConditionException("非法文件");
        }
        String fileName = file.getOriginalFilename();
        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }

    public static String uploadCommonFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.huanan());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
//        String bucket = "qcqblog-image";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        try {
            byte[] uploadBytes = file.getBytes();
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            Response response = uploadManager.put(uploadBytes, fileName, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
            return url + putRet.key;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public static void deleteFile(String fileName) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.huanan());
        //...其他参数参考类注释
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, fileName);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            throw new ConditionException("删除失败");
        }
    }

    public static String uploadSince(MultipartFile multipartFile) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.huanan());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        cfg.resumableUploadMaxConcurrentTaskCount = 2;  // 设置分片上传并发，1：采用同步上传；大于1：采用并发上传
        String localFilePath = "/home/qiniu/test.mp4";
//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        String localTempDir = Paths.get(System.getenv("java.io.tmpdir"), bucket).toString();
        try {
            byte[] bytes = multipartFile.getBytes();
            //设置断点续传文件进度保存目录
            FileRecorder fileRecorder = new FileRecorder(localTempDir);
            UploadManager uploadManager = new UploadManager(cfg, fileRecorder);
            Response response = uploadManager.put(bytes, key, upToken);
            DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
            return url+putRet.key;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "";
    }
}
