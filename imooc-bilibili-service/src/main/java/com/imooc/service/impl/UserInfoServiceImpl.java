package com.imooc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.imooc.dao.UserInfoDao;
import com.imooc.domain.PageResult;
import com.imooc.domain.UserInfo;
import com.imooc.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    UserInfoDao userInfoDao;
    @Override
    public void updateUserInfos(UserInfo userInfo) {
        userInfo.setUpdateTime(new Date());
        userInfoDao.updateUserInfos(userInfo);
    }

    @Override
    public PageResult<UserInfo> pageListUserInfos(JSONObject params) {
        Integer no = params.getInteger("no");
        Integer size = params.getInteger("size");
        params.put("start",(no-1)*size);
        params.put("limit",size);
        Integer total = userInfoDao.pageCountUserInfos(params);
        List<UserInfo> list=new ArrayList<>();
        if (total>0){
            list=userInfoDao.pageListUserInfos(params);
        }
        return new PageResult<>(total,list);
    }
}
