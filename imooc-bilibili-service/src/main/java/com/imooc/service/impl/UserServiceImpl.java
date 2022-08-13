package com.imooc.service.impl;

import com.imooc.dao.UserDao;
import com.imooc.dao.UserInfoDao;
import com.imooc.domain.User;
import com.imooc.domain.UserInfo;
import com.imooc.domain.constant.UserConstant;
import com.imooc.exception.ConditionException;
import com.imooc.service.UserService;
import com.imooc.utils.MD5Util;
import com.imooc.utils.RSAUtil;
import com.imooc.utils.TokenUtil;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public void addUser(User user) {
        String phone = user.getPhone();
        if (StringUtils.isNullOrEmpty(phone)){
            throw new ConditionException("手机号不能为空");
        }
        User dbUser = getUserByPhone(phone);
        if (dbUser!=null){
            throw new ConditionException("手机号已被注册");
        }
        Date now = new Date();
        String rawPassword="";
        String salt= String.valueOf(now.getTime());
        try{
            rawPassword=RSAUtil.decrypt(user.getPassword());
        }catch (Exception e){
            throw new ConditionException("密码解密失败");
        }
        String sign = MD5Util.sign(rawPassword, salt, "utf-8");
        user.setSalt(salt);
        user.setPassword(sign);
        user.setCreateTime(now);
        user.setUpdateTime(now);
        userDao.addUser(user);

        //添加用户信息
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId());
        userInfo.setNick(UserConstant.DEFAULT_NICK);
        userInfo.setBirth(UserConstant.DEFAULT_BIRTH);
        userInfo.setGender(UserConstant.GENDER_MALE);
        userInfo.setCreateTime(now);
        userInfo.setUpdateTime(now);
        userInfoDao.addUserInfo(userInfo);
    }

    @Override
    public User getUserByPhone(String phone){
        return userDao.getUserByPhone(phone);
    }

    @Override
    public String login(User user) throws Exception {
        String phone = user.getPhone();
        String email = user.getEmail();
//        if (StringUtils.isNullOrEmpty(phone)){
//            throw new ConditionException("手机号不能为空");
//        }
        if (StringUtils.isNullOrEmpty(phone)&&StringUtils.isNullOrEmpty(email)){
            throw new ConditionException("请输入邮箱或手机号");
        }
//        User dbUser = userDao.getUserByPhone(phone);
        User dbUser = userDao.getUserByPhoneOrEmail(phone,email);
        if (dbUser==null){
            throw new ConditionException("用户不存在");
        }
        String rawPassword="";
        try {
            rawPassword = RSAUtil.decrypt(user.getPassword());
        } catch (Exception e) {
            throw new ConditionException("密码解密失败");
        }
        String md5Password = MD5Util.sign(rawPassword, dbUser.getSalt(), "utf-8");
        if (!md5Password.equals(dbUser.getPassword())){
            throw new ConditionException("密码错误");
        }
        return TokenUtil.generateToken(dbUser.getId());
    }

    @Override
    public User getUserInfo(Long userId) {
        User user = userDao.getUserById(userId);
        UserInfo info = userInfoDao.getUserInfoByUserId(userId);
        user.setUserInfo(info);
        return user;
    }

    @Override
    public User getUserById(Long followingId) {
        return userDao.getUserById(followingId);
    }

    @Override
    public List<UserInfo> getUserInfoByUserIds(List<Long> ids) {
        return userInfoDao.getUserInfoByUserIds(ids);
    }

    @Override
    public void updateUsers(User user) {
        User dbUser = getUserById(user.getId());
        if (!dbUser.getPhone().equals(user.getPhone())){
            User userByPhone = getUserByPhone(user.getPhone());
            if (userByPhone!=null){
                throw new ConditionException("手机号已被注册");
            }
        }

        String rawPassword="";
        try{
            rawPassword = RSAUtil.decrypt(user.getPassword());
        }catch (Exception e){
            throw new ConditionException("密码解密失败");
        }
        user.setUpdateTime(new Date());
        String md5Password = MD5Util.sign(rawPassword, dbUser.getSalt(), "utf-8");
        user.setPhone(md5Password);
        userDao.updateUser(user);
    }
}
