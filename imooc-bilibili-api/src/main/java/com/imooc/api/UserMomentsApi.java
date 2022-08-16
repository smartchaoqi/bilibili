package com.imooc.api;

import com.alibaba.fastjson.JSON;
import com.imooc.annotation.ApiLimitedRole;
import com.imooc.annotation.DataLimited;
import com.imooc.domain.JsonResponse;
import com.imooc.domain.UserMoment;
import com.imooc.domain.constant.AuthRoleConstant;
import com.imooc.service.UserMomentsService;
import com.imooc.support.UserSupport;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserMomentsApi {
    @Autowired
    private UserMomentsService userMomentsService;

    @Autowired
    private UserSupport userSupport;

    @PostMapping("/user-moments")
    @ApiLimitedRole(limitedRoleCodeList = {AuthRoleConstant.ROLE_LV0})
    @DataLimited
    public JsonResponse<String> addUserMoments(@RequestBody UserMoment userMoment) throws MQBrokerException, RemotingException, InterruptedException, MQClientException {
        Long currentUserId = userSupport.getCurrentUserId();
        userMoment.setUserId(currentUserId);
        userMomentsService.addUserMoments(userMoment);
        return JsonResponse.success();
    }

    @GetMapping("/user-subscribed-moments")
    public JsonResponse<List<UserMoment>> getUserSubscribedMoments(){
        Long userId = userSupport.getCurrentUserId();
        List<UserMoment> moments = userMomentsService.getUserSubscribedMoments(userId);
        return new JsonResponse<>(moments);
    }
}
