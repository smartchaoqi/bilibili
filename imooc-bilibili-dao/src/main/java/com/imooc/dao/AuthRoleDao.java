package com.imooc.dao;

import com.imooc.domain.auth.AuthRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AuthRoleDao {
    AuthRole getRoleByCode(@Param("code") String code);
}
