package com.xdc5.libmng.controller;

import com.xdc5.libmng.entity.Result;
import com.xdc5.libmng.entity.User;
import com.xdc5.libmng.service.BillService;
import com.xdc5.libmng.service.UserService;
import com.xdc5.libmng.utils.ImageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Base64;

@Slf4j
@RestController
@RequestMapping("/api")
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

    //TODO 是否要自动判断并加上前缀 性能极差1s+
//    @GetMapping("/user/profile")
//    public Result showUserInfo(HttpServletRequest request) {
//        Integer userId = (Integer) request.getAttribute("userId");
//        log.info("request userId: " + userId);
//        User user = userService.getUserInfo(userId);
//        if (user == null) {
//            return Result.error("Fail: no such user");
//        }
//        String base64Image= ImageUtils.blobToBase64(user.getAvatar());
//        HashMap<String, Object> userData=new HashMap<>();
//        userData.put("userId",user.getUserId());
//        userData.put("username",user.getUsername());
//        userData.put("email",user.getEmail());
//        userData.put("userRole",user.getUserRole());
//        userData.put("borrowPerms",user.getBorrowPerms());
//        userData.put("avatar",base64Image);
//        return Result.success(userData, "Success: get /user/profile");
//    }


    @PutMapping("/user/profile")
    public Result updateUserInfo(HttpServletRequest request, @RequestBody HashMap<String, Object> requestBody) {
        Integer userId = (Integer) request.getAttribute("userId");
        if (userId == null) {
            return Result.error("Fail: no such userId");
        }
        log.info(String.valueOf(requestBody));
        String email = (String) requestBody.get("email");
        String password = (String) requestBody.get("password");
        String base64Avatar = (String) requestBody.get("avatar");
        byte[] avatar;
        if (base64Avatar != null) {
            try {
                avatar = Base64.getDecoder().decode(base64Avatar);
            } catch (IllegalArgumentException e) {
                return Result.error("Fail: invalid avatar data");
            }
            if (avatar.length > MAX_SIZE) {
                return Result.error("Fail: picture is too big");
            }
        }
        else
        {
            avatar=null;
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