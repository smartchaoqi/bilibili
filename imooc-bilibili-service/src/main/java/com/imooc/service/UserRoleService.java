package com.imooc.service;

import com.imooc.domain.auth.UserRole;

import java.util.List;

public interface UserRoleService {

    List<UserRole> getUserRoleByUserId(Long userId);

    void addUserRole(UserRole userRole);
}
