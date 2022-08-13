package com.imooc.api;

import com.imooc.domain.FollowingGroup;
import com.imooc.domain.JsonResponse;
import com.imooc.domain.UserFollowing;
import com.imooc.service.UserFollowingService;
import com.imooc.support.UserSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class UserFollowingApi {
    @Autowired
    private UserFollowingService userFollowingService;

    @Autowired
    private UserSupport userSupport;

    /**
     * 点关注
     * @param userFollowing
     * @return
     */
    @PostMapping("/user-followings")
    public JsonResponse<String> addUserFollowing(@RequestBody UserFollowing userFollowing){
        Long currentUserId = userSupport.getCurrentUserId();
        userFollowing.setUserId(currentUserId);
        userFollowing.setCreateTime(new Date());
        userFollowingService.addUserFollowing(userFollowing);
        return JsonResponse.success();
    }

    /**
     * 分组形式获取关注列表
     * @return
     */
    @GetMapping("/user-followings")
    public JsonResponse<List<FollowingGroup>> getUserFollowings(){
        Long currentUserId = userSupport.getCurrentUserId();
        List<FollowingGroup> userFollowing = userFollowingService.getUserFollowing(currentUserId);
        return new JsonResponse<>(userFollowing);
    }

    /**
     * 获取粉丝
     * @return
     */
    @GetMapping("/user-fans")
    public JsonResponse<List<UserFollowing>> getUserFans(){
        Long currentUserId = userSupport.getCurrentUserId();
        List<UserFollowing> userFans = userFollowingService.getUserFans(currentUserId);
        return new JsonResponse<>(userFans);
    }


}
