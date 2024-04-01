package com.xdc5.libmng.service;

import com.xdc5.libmng.entity.Result;
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

    public List<User> getAllUsers() {
        return userMapper.getUsers(null);
    }

    public User auth(User user) {
        // 通过用户名密码找
        List<User> users = userMapper.getUsers(user);
        int size = users.size();
        if (size == 0) {
            return new User();
        } else if (size == 1) {
            return users.get(0);
        } else {
            log.info("duplicated username " + users.get(0).getUsername());
            return new User();
        }
    }

    //重复返回true
    public boolean checkNameDuplicate(String username)
    {
        User user=new User();
        user.setUsername(username);
        List<User> users = userMapper.getUsers(user);
        return !users.isEmpty();
    }

    //重复返回true
    public boolean checkEmailDuplicate(String email){

        User user=new User();
        user.setEmail(email);
        List<User> users=userMapper.getUsers(user);
        return !users.isEmpty();
    }
    // 通过id获取用户信息
    public User getUserInfo(Integer userId) {
        return userMapper.getUserById(userId);
    }

    public void updateUserInfo(User user) {
        userMapper.updateUser(user);
    }
    public void addUser(User user){
        userMapper.addUser(user);
    }
    public boolean checkPermsByID(Integer uid) {
        User user = userMapper.getUserById(uid);
        //检查用户存在
        if(user == null)
            return false;
        return user.getBorrowPerms() == 1;
    }
}
