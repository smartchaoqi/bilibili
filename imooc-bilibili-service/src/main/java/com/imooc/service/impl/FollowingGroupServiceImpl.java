package com.imooc.service.impl;

import com.imooc.dao.FollowingGroupDao;
import com.imooc.domain.FollowingGroup;
import com.imooc.domain.JsonResponse;
import com.imooc.domain.constant.UserConstant;
import com.imooc.service.FollowingGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FollowingGroupServiceImpl implements FollowingGroupService {
    @Autowired
    FollowingGroupDao followingGroupDao;

    @Override
    public FollowingGroup getByType(String type){
        return followingGroupDao.getByType(type);
    }

    @Override
    public FollowingGroup getById(Long id){
        return followingGroupDao.getById(id);
    }

    @Override
    public List<FollowingGroup> getByUserId(Long userId) {
        return followingGroupDao.getByUserId(userId);
    }

    @Override
    public Long addUserFollowingGroups(FollowingGroup followingGroup) {
        followingGroup.setCreateTime(new Date());
        followingGroup.setUpdateTime(new Date());
        followingGroup.setType(UserConstant.USER_FOLLOWING_GROUP_TYPE_USER);
        followingGroupDao.addFollowingGroup(followingGroup);
        return followingGroup.getId();
    }

    @Override
    public List<FollowingGroup> getUserFollowingGroups(Long currentUserId) {
        return followingGroupDao.getUserFollowingGroups(currentUserId);
    }
}
