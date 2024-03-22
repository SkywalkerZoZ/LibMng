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

    //通过id获取用户信息
    public User userInfo(Integer userId){
        return userMapper.getUserById(userId);
    }

    //提供根据id修改对应xxx的方法。
    public void changeEmailById(Integer userId, String email){
        User user = userMapper.getUserById(userId);
        user.ChangeEmail(email);
        userMapper.updateUser(user);
    }
    public void changePasswordById(Integer userId, String password){
        User user = userMapper.getUserById(userId);
        user.ChangePassword(password);
        userMapper.updateUser(user);
    }
    public void changeAvatarById(Integer userId, byte[] avatar){
        User user = userMapper.getUserById(userId);
        user.ChangeAvatar(avatar);
        user.Base64toAvatar();
        userMapper.updateUser(user);
    }

}
