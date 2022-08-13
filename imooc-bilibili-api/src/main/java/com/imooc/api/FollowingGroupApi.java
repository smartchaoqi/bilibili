package com.imooc.api;

import com.imooc.domain.FollowingGroup;
import com.imooc.domain.JsonResponse;
import com.imooc.service.FollowingGroupService;
import com.imooc.support.UserSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FollowingGroupApi {

    @Autowired
    FollowingGroupService followingGroupService;

    @Autowired
    UserSupport userSupport;

    /**
     * 添加用户关注分组
     */
    @PostMapping("/user-following-groups")
    public JsonResponse<Long> addUserFollowingGroups(@RequestBody FollowingGroup followingGroup){
        Long currentUserId = userSupport.getCurrentUserId();
        followingGroup.setUserId(currentUserId);
        Long groupId = followingGroupService.addUserFollowingGroups(followingGroup);
        return new JsonResponse<>(groupId);
    }

    /**
     * 获取用户关注分组
     * @return
     */
    @GetMapping("/user-followings-groups")
    public JsonResponse<List<FollowingGroup>> getUserFollowingGroups(){
        Long currentUserId = userSupport.getCurrentUserId();
        List<FollowingGroup> userFollowingGroups = followingGroupService.getUserFollowingGroups(currentUserId);
        return new JsonResponse<>(userFollowingGroups);
    }
}
