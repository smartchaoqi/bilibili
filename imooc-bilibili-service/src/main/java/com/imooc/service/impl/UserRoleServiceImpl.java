package com.imooc.service.impl;

import com.imooc.dao.UserRoleDao;
import com.imooc.domain.auth.UserRole;
import com.imooc.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public List<UserRole> getUserRoleByUserId(Long userId) {
        return userRoleDao.getUserRoleByUserId(userId);
    }

    @Override
    public void addUserRole(UserRole userRole) {
        userRoleDao.addUserRole(userRole);
    }
}
