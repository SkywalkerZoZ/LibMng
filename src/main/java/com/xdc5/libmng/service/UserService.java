package com.xdc5.libmng.service;

import com.xdc5.libmng.entity.User;
import com.xdc5.libmng.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    public List<User> getAllUsers(){
        return  userMapper.getUsers(null);
    }
    public User auth(User user){
        List<User> users = userMapper.getUsers(user);
        int size = users.size();
        if(size==0)
        {
            return new User();
        } else if (size==1) {
            return users.get(0);
        }
        else {
            log.info("duplicated username "+users.get(0).getUsername());
            return new User();
        }
    }
    /*
    *方法名：User_Info
    *创建者：wwh
    *创建时间：2024年3月22日17:28:52
    *用途描述：通过id获取用户信息
    * */
    public User User_Info(Integer userId){
         User user = null;
         user = userMapper.getUserById(userId);
         return user;
    }
    /*
    *方法名：Change_xxx_Byid
    * 创建者：wwh
    * 修改时间：2024年3月22日20:35:28
    * 详情：提供根据id修改对应xxx的方法。
    * */
    public void Change_Email_Byid(Integer userId,String email){
        User user = null;
        user = userMapper.getUserById(userId);
        user.ChangeEmail(email);
        userMapper.updateUser(user);
    }
    public void Change_password_Byid(Integer userId,String password){
        User user = null;
        user = userMapper.getUserById(userId);
        user.ChangePassword(password);
        userMapper.updateUser(user);
    }
    public void Change_avatar_Byid(Integer userId,byte[] avatar){
        User user = null;
        user = userMapper.getUserById(userId);
        user.ChangeAvatar(avatar);
        user.Base64toAvatar();
        userMapper.updateUser(user);
    }

}
