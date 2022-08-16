package com.imooc.dao;

import com.imooc.domain.auth.AuthRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface AuthRoleMenuDao {
    List<AuthRoleMenu> getRoleMenusByRoleIds(@Param("roleIds") Set<Long> roleIds);
}
