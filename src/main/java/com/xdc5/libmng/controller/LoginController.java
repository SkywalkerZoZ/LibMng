package com.xdc5.libmng.controller;

import com.xdc5.libmng.entity.Result;
import com.xdc5.libmng.entity.User;
import com.xdc5.libmng.service.UserService;
import com.xdc5.libmng.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api")
public class LoginController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody HashMap<String, Object> users) {
        //用户名或邮箱登录都可
        User user = userService.auth(users);
        if((user.getUserId() != null) && ((user.getUsername() != null) || (user.getEmail() != null)) && (user.getPassword() != null))
        {
            HashMap<String, Object> claims = new HashMap<>();
            claims.put("userId",user.getUserId());
            claims.put("userRole",user.getUserRole());
            claims.put("username",user.getUsername());
            claims.put("email",user.getEmail());
            String jwt= JwtUtils.generateToken(claims);
            return Result.success(jwt,"Success: post /login");
        }
        return Result.error("Fail: invalid username or password");
    }

}