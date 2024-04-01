package com.xdc5.libmng.controller;

import com.xdc5.libmng.entity.Result;
import com.xdc5.libmng.entity.User;
import com.xdc5.libmng.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Base64;

@Slf4j
@RestController
public class UserInfoController {
    @Autowired
    private UserService userService;
    private final long MAX_SIZE = 5 * 1024 * 1024;

    @GetMapping("/user/profile")
    public Result showUserInfo(HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("userId");
        log.info("request userId: " + userId);
        User user = userService.getUserInfo(userId);
        if (user == null) {
            return Result.error("Fail: no such user");
        }
        // avatar会自动转为base64编码格式
        return Result.success(user, "Success: get /user/profile");
    }


    @PutMapping("/user/profile")
    public Result updateUserInfo(HttpServletRequest request, @RequestBody Map<String, Object> requestBody) {
        Integer userId = (Integer) request.getAttribute("userId");
        if (userId == null) {
            return Result.error("Fail: no such userId");
        }
        String email = (String) requestBody.get("email");
        String password = (String) requestBody.get("password");
        String base64Avatar = (String) requestBody.get("avatar");
        log.info("request userId: " + userId);
        log.info("request email: " + email);

        byte[] avatar;
        try {
            avatar = Base64.getDecoder().decode(base64Avatar);
        } catch (IllegalArgumentException e) {
            return Result.error("Fail: invalid avatar data");
        }

        if (avatar.length > MAX_SIZE) {
            return Result.error("Fail: picture is too big");
        }
        User user = new User();
        user.setUserId(userId);
        user.setEmail(email);
        user.setPassword(password);
        user.setAvatar(avatar);
        userService.updateUserInfo(user);
        return Result.success("Success: put /user/profile");
    }

}