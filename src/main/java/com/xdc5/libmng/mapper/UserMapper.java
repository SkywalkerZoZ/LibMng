package com.xdc5.libmng.mapper;

import com.xdc5.libmng.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> getUsers(User user);
    //返回影响的行数
    int delUserById(@Param("userId") Integer userId);
    //返回影响的行数
    int delUserByName(@Param("username") String username);
    //返回影响的行数
    int addUser(User user);
    int updateUser(User user);
    /*
    * 方法名：getUserById
    * 创建者：wwh
    * 创建时间：2024年3月22日17:37:48
    * 方法简介：通过id获取用户信息
    * */
    User getUserById(@Param("userId") Integer userId);
}
