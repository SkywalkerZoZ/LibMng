package com.xdc5.libmng.controller;


import com.xdc5.libmng.entity.Result;
import com.xdc5.libmng.entity.User;
import com.xdc5.libmng.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.NullLiteral;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.util.logging.Logger;
@Slf4j
@RestController
/*
* 类名：UserinfoController
* 创建者：wwh
* 最近修改时间：2024年3月22日18:35:40
* 内容详情：用日志实例log用来记录操作,public方法Show_User_Info用来
* 响应查询个人信息的请求。
* Change_User_Info用来更改用户信息
* */
public class UserinfoController {
    @Autowired
    private UserService userService;
    /*
    * 方法名：Show_User_Info
    * 创建者：魏文晖
    * 修改时间：2024年3月22日18:35:35
    * 详情：用来响应查询个人信息的请求。在方法中，如果传进来的userId不为空
    * 我们调用userService实例的user_info方法来获取响应参数
    * 如果传进来的userId为空，则返回error信息No userid
    * 获取参数后我们调用user的AvatartoBase64方法将图片信息转换为base64编码格式。
    *
    *
    *
    * 问题：将api.md文档中的请求参数从无，改为Integer userId
    *
    *
    * */
    @RequestMapping(value = "/user/profile",method = RequestMethod.GET)
    public Result  Show_User_Info(Integer userId){
        if(userId!=null){
            log.info("查询个人信息");
            User user=userService.User_Info(userId);
            user.AvatartoBase64();
            return Result.success(user);
        }
        return Result.error("No userid");
    }


    @RequestMapping(value = "/user/profile",method = RequestMethod.PUT)
    /*
    *方法名：Change_User_Info
    * 创建者：魏文晖
    * 修改时间：2024年3月22日18:36:07
    * 详情：根据userid更改数据库数据，如果userid为空则返回error，然后首先判断图片大小是否超出数据库可存储范围，如果超出直接返回error
    * 保证修改操作的原子性，即不会出现邮件和密码已经修改完，但是由于图片大小过大导致修改终止的情况发生。
    *
    * 问题，与原api.md不同增加了Integer userId 的参数。
    * */
    public Result Change_User_Info(Integer userId, String email, String password,byte[] avatar){
        if(userId==null){
            return Result.error("No userId");
        }
        if(avatar.length>65535){
            return Result.error("picture is to big");
        }
        if(email!=null){
            //改变userid对应下的email为新的email
            userService.Change_Email_Byid(userId,email);
        }
        if(password!=null){
            userService.Change_password_Byid(userId,password);
        }
        if(avatar.length!=0){
            userService.Change_avatar_Byid(userId,avatar);
        }
        return Result.success();
    }
}
