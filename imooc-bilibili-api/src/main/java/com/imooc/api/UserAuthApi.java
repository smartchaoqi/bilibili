package com.imooc.api;

import com.imooc.domain.JsonResponse;
import com.imooc.domain.auth.UserAuthorities;
import com.imooc.service.UserAuthService;
import com.imooc.support.UserSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAuthApi {
    @Autowired
    private UserSupport userSupport;

    @Autowired
    private UserAuthService userAutoService;

    @GetMapping("/user-authorities")
    public JsonResponse<UserAuthorities> getUserAuthorities(){
        Long userId = userSupport.getCurrentUserId();
        return new JsonResponse<>(userAutoService.getUserAuthorities(userId));
    }
}
