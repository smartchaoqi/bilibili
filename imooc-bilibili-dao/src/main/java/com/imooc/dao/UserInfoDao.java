package com.imooc.dao;

import com.alibaba.fastjson.JSONObject;
import com.imooc.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserInfoDao {
    Integer addUserInfo(UserInfo userInfo);

    UserInfo getUserInfoByUserId(@Param("userId") Long userId);

    Integer updateUserInfos(UserInfo userInfo);

    List<UserInfo> getUserInfoByUserIds(List<Long> ids);

    Integer pageCountUserInfos(Map<String,Object> params);

    List<UserInfo> pageListUserInfos(Map<String,Object> params);
}
