package com.imooc.service.impl;

import com.imooc.dao.UserAuthDao;
import com.imooc.domain.auth.*;
import com.imooc.domain.constant.AuthRoleConstant;
import com.imooc.service.AuthRoleService;
import com.imooc.service.UserAuthService;
import com.imooc.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserAuthServiceImpl implements UserAuthService {
    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private AuthRoleService authRoleService;

    @Override
    public UserAuthorities getUserAuthorities(Long userId) {
        UserAuthorities authorities = new UserAuthorities();

        List<UserRole> userRoleList=userRoleService.getUserRoleByUserId(userId);
        Set<Long> roleIds = userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toSet());

        List<AuthRoleElementOperation> authRoleElementOperations = authRoleService.getRoleElementOperationsByRoleIds(roleIds);
        authorities.setRoleElementOperationList(authRoleElementOperations);

        List<AuthRoleMenu> authRoleMenus=authRoleService.getRoleMenusByRoleIds(roleIds);
        authorities.setRoleMenuList(authRoleMenus);

        return authorities;
    }

    @Override
    public void addUserDefaultRole(Long id) {
        UserRole userRole = new UserRole();
        AuthRole authRole = authRoleService.getRoleByCode(AuthRoleConstant.ROLE_LV0);
        userRole.setUserId(id);
        userRole.setRoleId(authRole.getId());
        userRole.setCreateTime(new Date());
        userRoleService.addUserRole(userRole);
    }
}
