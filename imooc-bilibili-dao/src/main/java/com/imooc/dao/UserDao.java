package com.imooc.dao;

import com.imooc.domain.RefreshTokenDetail;
import com.imooc.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Mapper
public interface UserDao {
    User getUserByPhone(@Param("phone") String phone);

    Integer addUser(User user);

    User getUserById(@Param("userId") Long userId);

    Integer updateUser(User user);

    User getUserByPhoneOrEmail(@Param("phone") String phone, @Param("email") String email);

    Integer deleteRefreshToken(@Param("refreshToken") String refreshToken, @Param("userId") Long userId);

    Integer addRefreshToken(@Param("refreshToken") String refreshToken, @Param("userId") Long userId, @Param("date") Date date);

    RefreshTokenDetail getRefreshTokenDetail(@Param("refreshToken") String refreshToken);
}
