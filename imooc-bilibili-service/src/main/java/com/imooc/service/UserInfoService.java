package com.imooc.service;

import com.alibaba.fastjson.JSONObject;
import com.imooc.domain.PageResult;
import com.imooc.domain.UserInfo;

public interface UserInfoService {
    void updateUserInfos(UserInfo userInfo);

    PageResult<UserInfo> pageListUserInfos(JSONObject params);
}
