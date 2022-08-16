package com.imooc.service.impl;

import com.imooc.dao.AuthRoleDao;
import com.imooc.domain.auth.AuthRole;
import com.imooc.domain.auth.AuthRoleElementOperation;
import com.imooc.domain.auth.AuthRoleMenu;
import com.imooc.service.AuthRoleElementOperationService;
import com.imooc.service.AuthRoleMenuService;
import com.imooc.service.AuthRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class AuthRoleServiceImpl implements AuthRoleService {

    @Autowired
    private AuthRoleElementOperationService authRoleElementOperationService;

    @Autowired
    private AuthRoleMenuService authRoleMenuService;

    @Autowired
    private AuthRoleDao authRoleDao;

    @Override
    public List<AuthRoleElementOperation> getRoleElementOperationsByRoleIds(Set<Long> roleIds) {
        return authRoleElementOperationService.getRoleElementOperationsByRoleIds(roleIds);
    }

    @Override
    public List<AuthRoleMenu> getRoleMenusByRoleIds(Set<Long> roleIds) {
        return authRoleMenuService.getRoleMenusByRoleIds(roleIds);
    }

    @Override
    public AuthRole getRoleByCode(String code) {
        return authRoleDao.getRoleByCode(code);
    }
}
