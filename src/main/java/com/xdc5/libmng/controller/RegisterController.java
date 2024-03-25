package com.xdc5.libmng.controller;

import com.xdc5.libmng.entity.Result;
import com.xdc5.libmng.entity.User;
import com.xdc5.libmng.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result registerUser(@RequestBody User user){
        if(userService.checkNameDuplicate(user.getUsername()))
        {
            return Result.error("Fail: duplicate username");
        }
        if(userService.checkEmailDuplicate(user.getEmail()))
        {
            return Result.error("Fail: duplicate email");
        }
        userService.addUser(user);

        return Result.success("Success: post /register");
    }
}
