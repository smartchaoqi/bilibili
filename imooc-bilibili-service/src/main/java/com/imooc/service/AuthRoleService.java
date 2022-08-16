package com.imooc.service;

import com.imooc.domain.auth.AuthRole;
import com.imooc.domain.auth.AuthRoleElementOperation;
import com.imooc.domain.auth.AuthRoleMenu;

import java.util.List;
import java.util.Set;

public interface AuthRoleService {
    List<AuthRoleElementOperation> getRoleElementOperationsByRoleIds(Set<Long> roleIds);

    List<AuthRoleMenu> getRoleMenusByRoleIds(Set<Long> roleIds);

    AuthRole getRoleByCode(String code);
}
