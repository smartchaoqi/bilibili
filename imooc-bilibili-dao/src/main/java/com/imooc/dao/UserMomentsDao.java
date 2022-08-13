package com.imooc.dao;

import com.imooc.domain.UserMoment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMomentsDao {
    void addUserMoments(UserMoment userMoment);
}
