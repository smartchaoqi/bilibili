package com.imooc.service;

public interface UserCoinService {
    Integer getUserCoinsAmounts(Long userId);

    void updateUserCoinAmount(Long userId, Integer amount);
}
