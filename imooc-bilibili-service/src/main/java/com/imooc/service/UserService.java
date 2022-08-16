package com.imooc.service;

import com.imooc.domain.User;
import com.imooc.domain.UserInfo;

import java.util.List;
import java.util.Map;

public interface UserService {
    void addUser(User user);

    User getUserByPhone(String phone);

    String login(User user) throws Exception;


    User getUserInfo(Long userId);

    User getUserById(Long followingId);

    List<UserInfo> getUserInfoByUserIds(List<Long> collect);

    void updateUsers(User user);

    Map<String, Object> loginForDts(User user) throws Exception;

    void logout(Long userId, String refreshToken);

    String refreshAccessToken(String refreshToken) throws Exception;
}
