package com.imooc.dao;

import com.imooc.domain.auth.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserRoleDao {

    List<UserRole> getUserRoleByUserId(@Param("userId") Long userId);

    void addUserRole(UserRole userRole);
}
