package com.imooc.service;

import com.imooc.domain.auth.AuthRoleMenu;

import java.util.List;
import java.util.Set;

public interface AuthRoleMenuService {
    List<AuthRoleMenu> getRoleMenusByRoleIds(Set<Long> roleIds);
}
