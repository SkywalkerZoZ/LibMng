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
}
