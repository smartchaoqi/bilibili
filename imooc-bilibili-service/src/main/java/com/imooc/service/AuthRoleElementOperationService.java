package com.imooc.service;

import com.imooc.domain.auth.AuthRoleElementOperation;

import java.util.List;
import java.util.Set;

public interface AuthRoleElementOperationService {
    List<AuthRoleElementOperation> getRoleElementOperationsByRoleIds(Set<Long> roleIds);
}
