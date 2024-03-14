package com.xdc5.libmng.mapper;

import com.xdc5.libmng.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> getUsers(User user);
    //返回影响的行数
    int delUserById(@Param("userId") Long userId);
    //返回影响的行数
    int delUserByName(@Param("username") String username);
    //返回影响的行数
    int addUser(User user);
    int updateUser(User user);
}
