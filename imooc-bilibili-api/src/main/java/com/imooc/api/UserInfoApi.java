package com.imooc.api;

import com.alibaba.fastjson.JSONObject;
import com.imooc.domain.JsonResponse;
import com.imooc.domain.PageResult;
import com.imooc.domain.UserInfo;
import com.imooc.service.UserFollowingService;
import com.imooc.service.UserInfoService;
import com.imooc.support.UserSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserInfoApi {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserSupport userSupport;

    @Autowired
    UserFollowingService userFollowingService;

    @PutMapping("/user-infos")
    public JsonResponse<String> updateUserInfos(@RequestBody UserInfo userInfo){
        Long userId = userSupport.getCurrentUserId();
        userInfo.setUserId(userId);
        userInfoService.updateUserInfos(userInfo);
        return JsonResponse.success();
    }

    @GetMapping("/user-infos")
    public JsonResponse<PageResult<UserInfo>> pageListUserInfos(@RequestParam Integer no,@RequestParam Integer size, String nick){
        Long userId = userSupport.getCurrentUserId();
        JSONObject params = new JSONObject();
        params.put("no",no);
        params.put("size",size);
        params.put("nick",nick);
        params.put("userId",userId);
        PageResult<UserInfo> result = userInfoService.pageListUserInfos(params);
        if (result.getTotal()>0){
            List<UserInfo> checkedInfoList = userFollowingService.checkFollowingStatus(result.getList(), userId);
            result.setList(checkedInfoList);
        }
        return new JsonResponse<>(result);
    }
}
