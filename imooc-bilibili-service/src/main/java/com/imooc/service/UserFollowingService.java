package com.imooc.service;

import com.imooc.domain.FollowingGroup;
import com.imooc.domain.UserFollowing;
import com.imooc.domain.UserInfo;

import java.util.List;

public interface UserFollowingService {
    void addUserFollowing(UserFollowing userFollowing);

    List<FollowingGroup> getUserFollowing(Long userId);

    List<UserFollowing> getUserFans(Long userId);

    List<UserInfo> checkFollowingStatus(List<UserInfo> list, Long userId);
}
