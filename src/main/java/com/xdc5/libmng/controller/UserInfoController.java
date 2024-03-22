package com.xdc5.libmng.controller;


import com.xdc5.libmng.entity.Result;
import com.xdc5.libmng.entity.User;
import com.xdc5.libmng.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserInfoController {
    @Autowired
    private UserService userService;

    //用来响应查询个人信息的请求。在方法中，如果传进来的userId不为空
    //TODO 将api.md文档中的请求参数从无，改为Integer userId

    @RequestMapping(value = "/user/profile",method = RequestMethod.GET)
    public Result showUserInfo(Integer userId){
        if(userId!=null){
            log.info("查询个人信息");
            User user=userService.userInfo(userId);
            user.avatarToBase64();
            return Result.success(user);
        }
        return Result.error("No userid");
    }


    @RequestMapping(value = "/user/profile",method = RequestMethod.PUT)

    //根据userid更改数据库数据，如果userid为空则返回error，然后首先判断图片大小是否超出数据库可存储范围，如果超出直接返回error
    //保证修改操作的原子性，即不会出现邮件和密码已经修改完，但是由于图片大小过大导致修改终止的情况发生。
    //TODO 与原api.md不同增加了Integer userId 的参数。

    public Result changeUserInfo(Integer userId, String email, String password, byte[] avatar){
        if(userId==null){
            return Result.error("No userId");
        }
        if(avatar.length>65535){
            return Result.error("picture is to big");
        }
        if(email!=null){
            //改变userid对应下的email为新的email
            userService.changeEmailById(userId,email);
        }
        if(password!=null){
            userService.changePasswordById(userId,password);
        }
        if(avatar.length!=0){
            userService.changeAvatarById(userId,avatar);
        }
        return Result.success();
    }
}
