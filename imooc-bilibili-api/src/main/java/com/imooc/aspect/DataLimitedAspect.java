package com.imooc.aspect;

import com.imooc.annotation.ApiLimitedRole;
import com.imooc.domain.UserMoment;
import com.imooc.domain.auth.UserRole;
import com.imooc.domain.constant.AuthRoleConstant;
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
public class DataLimitedAspect {
    @Autowired
    UserSupport userSupport;

    @Autowired
    UserRoleService userRoleService;

    @Pointcut("@annotation(com.imooc.annotation.DataLimited)")
    public void check(){
    }

    @Before("check()")
    public void doBefore(JoinPoint joinPoint){
        Long userId = userSupport.getCurrentUserId();
        //用户所有角色 role
        List<UserRole> userRoleByUserId = userRoleService.getUserRoleByUserId(userId);
        Set<String> userRoleSet = userRoleByUserId.stream().map(UserRole::getRoleCode).collect(Collectors.toSet());
        Object[] args = joinPoint.getArgs();
        for (Object arg:args){
            if (arg instanceof UserMoment){
                UserMoment moment = (UserMoment) arg;
                if (userRoleSet.contains(AuthRoleConstant.ROLE_LV0)&&(!("0".equals(moment.getType())))){
                    throw new ConditionException("参数异常");
                }
            }
        }
    }
}
