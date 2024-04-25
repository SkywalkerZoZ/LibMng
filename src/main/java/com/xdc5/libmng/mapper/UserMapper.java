package com.xdc5.libmng.mapper;

import com.xdc5.libmng.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.HashMap;
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
    User getUserById(@Param("userId") Integer userId);

    String getUserNameById(@Param("userId") Integer userId);
    //根据读者名返回读者信息
    List<HashMap<String ,Object>> getReaderByName(String userName);
    List<HashMap<String,Object>> getReaderById(Integer userId);

    //更改用户perms
    void changeReaderPerms(Integer userId);
    List<User> login(@Param("username") String username, @Param("password") String password);
    int increaseUserMoney(@Param("userId") int userId, @Param("billAmount") BigDecimal billAmount);

}
