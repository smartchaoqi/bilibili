package com.imooc.service.impl;

import com.imooc.dao.AuthRoleElementOperationDao;
import com.imooc.domain.auth.AuthRoleElementOperation;
import com.imooc.service.AuthRoleElementOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class AuthRoleElementOperationServiceImpl implements AuthRoleElementOperationService {

    @Autowired
    private AuthRoleElementOperationDao authRoleElementOperationDao;

    @Override
    public List<AuthRoleElementOperation> getRoleElementOperationsByRoleIds(Set<Long> roleIds) {
        return authRoleElementOperationDao.getRoleElementOperationsByRoleIds(roleIds);
    }
}
