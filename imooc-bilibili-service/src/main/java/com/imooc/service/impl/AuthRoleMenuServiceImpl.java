package com.imooc.service.impl;

import com.imooc.dao.AuthRoleMenuDao;
import com.imooc.domain.auth.AuthRoleMenu;
import com.imooc.service.AuthRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class AuthRoleMenuServiceImpl implements AuthRoleMenuService {

    @Autowired
    AuthRoleMenuDao authRoleMenuDao;
    @Override
    public List<AuthRoleMenu> getRoleMenusByRoleIds(Set<Long> roleIds) {
        return authRoleMenuDao.getRoleMenusByRoleIds(roleIds);
    }
}
