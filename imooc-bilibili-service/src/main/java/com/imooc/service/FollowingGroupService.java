package com.imooc.service;

import com.imooc.domain.FollowingGroup;
import com.imooc.domain.JsonResponse;

import java.util.List;

public interface FollowingGroupService {
    FollowingGroup getByType(String type);

    FollowingGroup getById(Long id);

    List<FollowingGroup> getByUserId(Long userId);

    Long addUserFollowingGroups(FollowingGroup followingGroup);

    List<FollowingGroup> getUserFollowingGroups(Long currentUserId);
}
