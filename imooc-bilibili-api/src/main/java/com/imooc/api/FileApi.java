package com.imooc.api;

import com.imooc.domain.JsonResponse;
import com.imooc.service.FileService;
import com.imooc.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class FileApi {
    @Autowired
    private FileService fileService;

    @PostMapping("/md5files")
    public JsonResponse<String> getFileMd5(MultipartFile file) throws IOException {
        String fileMd5 = fileService.getFileMd5(file);
        return new JsonResponse<>(fileMd5);
    }

    @PutMapping("/file-slices")
    public JsonResponse<String> uploadFileBySlices(MultipartFile file,String fileMd5,Integer sliceNo,Integer totalSliceNo){

        return new JsonResponse<>(fileService.uploadFileBySlices(file,fileMd5,sliceNo,totalSliceNo));
    }
}
