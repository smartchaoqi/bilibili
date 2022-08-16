package com.imooc.dao;

import com.imooc.domain.File;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FileDao {
    File getFileByMd5(@Param("fileMd5") String fileMd5);

    void addFile(File dbFile);
}
