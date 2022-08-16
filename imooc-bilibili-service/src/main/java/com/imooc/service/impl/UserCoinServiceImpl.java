package com.imooc.service.impl;

import com.imooc.dao.UserCoinDao;
import com.imooc.service.UserCoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.Date;

@Service
public class UserCoinServiceImpl implements UserCoinService {
    @Autowired
    private UserCoinDao userCoinDao;

    @Override
    public Integer getUserCoinsAmounts(Long userId) {
        return userCoinDao.getUserCoinsAmounts(userId);
    }

    @Override
    public void updateUserCoinAmount(Long userId, Integer amount) {
        userCoinDao.updateUserCoinAmount(userId,amount,new Date());
    }
}
