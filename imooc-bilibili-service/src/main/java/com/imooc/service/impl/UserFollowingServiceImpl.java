package com.imooc.service.impl;

import com.imooc.dao.FollowingGroupDao;
import com.imooc.dao.UserFollowingDao;
import com.imooc.domain.FollowingGroup;
import com.imooc.domain.User;
import com.imooc.domain.UserFollowing;
import com.imooc.domain.UserInfo;
import com.imooc.domain.constant.UserConstant;
import com.imooc.exception.ConditionException;
import com.imooc.service.FollowingGroupService;
import com.imooc.service.UserFollowingService;
import com.imooc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserFollowingServiceImpl implements UserFollowingService {
    @Autowired
    UserFollowingDao userFollowingDao;
    @Autowired
    FollowingGroupService followingGroupService;
    @Autowired
    UserService userService;

    @Override
    @Transactional
    public void addUserFollowing(UserFollowing userFollowing) {
        Long groupId = userFollowing.getGroupId();
        if (groupId==null){
            FollowingGroup byType = followingGroupService.getByType(UserConstant.USER_FOLLOWING_GROUP_TYPE_DEFAULT);
            userFollowing.setGroupId(byType.getId());
        }else{
            FollowingGroup byId = followingGroupService.getById(groupId);
            if (byId==null){
                throw new ConditionException("分组不存在");
            }
        }
        User user = userService.getUserById(userFollowing.getFollowingId());
        if (user==null){
            throw new ConditionException("关注用户不存在");
        }

        userFollowingDao.deleteUserFollowing(userFollowing.getUserId(),userFollowing.getFollowingId());
        userFollowing.setCreateTime(new Date());
        userFollowingDao.addUserFollowing(userFollowing);
    }

    @Override
    public List<FollowingGroup> getUserFollowing(Long userId) {
        //获取所有关注
        List<UserFollowing> list=userFollowingDao.getUserFollowings(userId);
        List<Long> collect = list.stream().map(UserFollowing::getFollowingId).collect(Collectors.toList());
        List<UserInfo> userInfos=new ArrayList<>();
        if (collect.size()>0){
            //获取关注人的信息
            userInfos = userService.getUserInfoByUserIds(collect);
        }

        //注入userinfo
        for (UserFollowing following:list){
            for (UserInfo info:userInfos){
                if (following.getUserId().equals(info.getUserId())){
                    following.setUserInfo(info);
                }
            }
        }
        //所有关注
        List<FollowingGroup> groups = followingGroupService.getByUserId(userId);
        FollowingGroup allGroup = new FollowingGroup();
        allGroup.setName(UserConstant.USER_FOLLOWING_GROUP_ALL_NAME);
        allGroup.setFollowingUserInfoList(userInfos);

        List<FollowingGroup> result=new ArrayList<>();
        result.add(allGroup);

        //按分类给分组设置 userInfo
        for (FollowingGroup group:groups){
            group.setFollowingUserInfoList(new ArrayList<>());
            for (UserFollowing info:list){
                if (group.getId().equals(info.getGroupId())){
                    group.getFollowingUserInfoList().add(info.getUserInfo());
                }
            }
            result.add(group);
        }
        return result;
    }

    @Override
    public List<UserFollowing> getUserFans(Long userId) {
        List<UserFollowing> fanList = userFollowingDao.getUserFans(userId);

        List<Long> collect = fanList.stream().map(UserFollowing::getUserId).collect(Collectors.toList());
        List<UserInfo> userInfos=new ArrayList<>();
        if (collect.size()>0){
            //获取关注人的信息
            userInfos = userService.getUserInfoByUserIds(collect);
        }

        List<UserFollowing> followingList=userFollowingDao.getUserFollowings(userId);
        for (UserFollowing fan:fanList){
            for (UserInfo userInfo:userInfos){
                if (fan.getUserId().equals(userInfo.getUserId())){
                    userInfo.setFollowed(false);
                    fan.setUserInfo(userInfo);
                }
            }
            for (UserFollowing following:followingList){
                if (following.getFollowingId().equals(fan.getUserId())){
                    fan.getUserInfo().setFollowed(true);
                    break;
                }
            }
        }
        return fanList;
    }

    @Override
    public List<UserInfo> checkFollowingStatus(List<UserInfo> list, Long userId) {
        //关注的信息
        List<UserFollowing> userFollowings = userFollowingDao.getUserFollowings(userId);
        for (UserInfo info:list){
            info.setFollowed(false);
            for (UserFollowing userFollowing:userFollowings){
                if (info.getUserId().equals(userFollowing.getFollowingId())){
                    info.setFollowed(true);
                    break;
                }
            }
        }
        return list;
    }
}
