package com.xdc5.libmng.controller;

import com.xdc5.libmng.entity.Result;
import com.xdc5.libmng.entity.User;
import com.xdc5.libmng.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


// TODO 接受上传二进制图片demo，能用，但是先别按照这个写
@RestController
public class UploadController {
    @Autowired
    private UserService userService;
    @PutMapping("/upload")
    public Result uploadAvatar(@RequestParam("avatar") MultipartFile avatarFile) {
        try {
            // Read the bytes from the uploaded file
            byte[] avatarData = avatarFile.getBytes();
            User user = new User();
            user.setAvatar(avatarData);
            user.setUserId(1);
            userService.updateUserInfo(user);
            return Result.success("Avatar uploaded successfully");
        } catch (IOException e) {
            return Result.error("Fail: " + e.getMessage());
        }
    }
}
