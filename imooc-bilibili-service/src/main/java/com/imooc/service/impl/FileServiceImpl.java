package com.imooc.service.impl;

import com.imooc.dao.FileDao;
import com.imooc.domain.File;
import com.imooc.service.FileService;
import com.imooc.utils.MD5Util;
import com.imooc.utils.QiniuUtils;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileDao fileDao;

    @Override
    public String getFileMd5(MultipartFile file) throws IOException {
        return MD5Util.getFileMd5(file);
    }

    @Override
    public String uploadFileBySlices(MultipartFile file, String fileMd5, Integer sliceNo, Integer totalSliceNo) {
        File dbFile = fileDao.getFileByMd5(fileMd5);
        if (dbFile!=null){
            return dbFile.getUrl();
        }
        String url = QiniuUtils.uploadSince(file);
        if (!StringUtils.isNullOrEmpty(url)){
            dbFile=new File();
            dbFile.setCreateTime(new Date());
            dbFile.setType(QiniuUtils.getFileType(file));
            dbFile.setMd5(fileMd5);
            dbFile.setUrl(url);
            fileDao.addFile(dbFile);
        }
        return url;
    }
}
