package com.xdc5.libmng;

import com.xdc5.libmng.entity.User;
import com.xdc5.libmng.mapper.UserMapper;
import com.xdc5.libmng.utils.ImageUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class UtilsTests {
    @Autowired
    private UserMapper userMapper;
//    @Test
//    void addUserAvatar() throws IOException {
//        User user = new User();
//        user.setUserId(1);
//        byte[] img= ImageUtils.readImageAsBytes("C:/Users/SkywalkerzzZ/Pictures/Saved Pictures/edward.jpg");
//        user.setAvatar(img);
//        userMapper.updateUser(user);
//    }
//
//    @Test
//    void readUserAvatar() throws IOException {
//        User user=new User();
//        user.setUserId(1);
//        user=userMapper.getUsers(user).get(0);
//        byte[] imageBytes=user.getAvatar();
//        String formatName = "jpg";
//        String filePath = "C:/Users/SkywalkerzzZ/Pictures/Saved Pictures/sky."+formatName;
//        ImageUtils.saveImage(imageBytes, filePath, formatName);
//    }
}
