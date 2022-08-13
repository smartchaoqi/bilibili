package com.imooc.dao;

import com.imooc.domain.FollowingGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FollowingGroupDao {
    FollowingGroup getByType(@Param("type") String type);

    FollowingGroup getById(@Param("id") Long id);

    List<FollowingGroup> getByUserId(@Param("userId") Long userId);

    Integer addFollowingGroup(FollowingGroup followingGroup);

    List<FollowingGroup> getUserFollowingGroups(@Param("currentUserId") Long currentUserId);
}
