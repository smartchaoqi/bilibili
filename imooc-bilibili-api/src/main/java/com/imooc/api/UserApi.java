package com.imooc.api;

import com.imooc.domain.JsonResponse;
import com.imooc.domain.PageResult;
import com.imooc.domain.User;
import com.imooc.domain.UserInfo;
import com.imooc.service.UserService;
import com.imooc.service.impl.UserServiceImpl;
import com.imooc.utils.RSAUtil;
import com.imooc.support.UserSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserApi {
    @Autowired
    private UserService userService;

    @Autowired
    private UserSupport userSupport;

    @GetMapping("/rsa-pks")
    public JsonResponse<String> getRsaPublicKey() {
        String pk = RSAUtil.getPublicKeyStr();
        return new JsonResponse<>(pk);
    }

    @PostMapping("/users")
    public JsonResponse<String> addUser(@RequestBody User user){
        userService.addUser(user);
        return JsonResponse.success();
    }

    @PostMapping("/users-tokens")
    public JsonResponse<String> login(@RequestBody User user) throws Exception {
        String token=userService.login(user);
        return new JsonResponse<>(token);
    }

    @GetMapping("/users")
    public JsonResponse<User> getUserInfo(){
        Long currentUserId = userSupport.getCurrentUserId();
        User user = userService.getUserInfo(currentUserId);
        return new JsonResponse<>(user);
    }

    @PutMapping("/users")
    public JsonResponse<String> updateUsers(@RequestBody User user) throws Exception{
        Long currentUserId = userSupport.getCurrentUserId();
        user.setId(currentUserId);
        userService.updateUsers(user);
        return JsonResponse.success("账户修改成功");
    }

}
