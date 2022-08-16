package com.imooc.aspect;

import com.imooc.annotation.ApiLimitedRole;
import com.imooc.domain.auth.UserRole;
import com.imooc.exception.ConditionException;
import com.imooc.service.UserRoleService;
import com.imooc.support.UserSupport;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Aspect
@Order(1)
@Component
public class ApiLimitedRoleAspect {
    @Autowired
    UserSupport userSupport;

    @Autowired
    UserRoleService userRoleService;

    @Pointcut("@annotation(com.imooc.annotation.ApiLimitedRole)")
    public void check(){
    }

    @Before("check() && @annotation(apiLimitedRole)")
    public void doBefore(JoinPoint joinPoint, ApiLimitedRole apiLimitedRole){
        Long userId = userSupport.getCurrentUserId();
        //用户所有角色 role
        List<UserRole> userRoleByUserId = userRoleService.getUserRoleByUserId(userId);
        //被限制的角色
        String[] roleCodeList = apiLimitedRole.limitedRoleCodeList();

        Set<String> roleCodeLimitSet = new HashSet<>(Arrays.asList(roleCodeList));

        for (String userCode:roleCodeList){
            if (roleCodeLimitSet.contains(userCode)){
                throw new ConditionException("权限不足");
            }
        }
    }
}
