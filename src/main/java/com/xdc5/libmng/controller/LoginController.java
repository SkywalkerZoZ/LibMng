package com.xdc5.libmng.controller;

import com.xdc5.libmng.entity.Result;
import com.xdc5.libmng.entity.User;
import com.xdc5.libmng.service.UserService;
import com.xdc5.libmng.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class LoginController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        user=userService.auth(user);
        if((user.getUserId() != null) && (user.getUsername() != null) && (user.getPassword() != null))
        {
            HashMap<String, Object> claims = new HashMap<>();
            claims.put("username",user.getUsername());
            claims.put("userId",user.getUserId());
            String jwt= JwtUtils.generateToken(claims);
            return Result.success(jwt);
        }
        return Result.error("invalid username or password");
    }

}
