package com.imooc.dao;

import com.imooc.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {
    User getUserByPhone(@Param("phone") String phone);

    Integer addUser(User user);

    User getUserById(@Param("userId") Long userId);

    Integer updateUser(User user);

    User getUserByPhoneOrEmail(@Param("phone") String phone, @Param("email") String email);
}
