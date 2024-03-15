package com.xdc5.libmng;

import com.xdc5.libmng.entity.User;
import com.xdc5.libmng.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

@SpringBootTest
class LibMngApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testAddUser() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");
        user.setUserRole("user");
//        user.setEmail("test@gmail.com");

        int rowsAffected = userMapper.addUser(user);
        System.out.println("新增用户成功，受影响的行数：" + rowsAffected);
        System.out.println("新插入的用户ID：" + user.getUserId());
    }

    @Test
    public void testUpdateUser() {
        User user=new User();
        user.setUsername("testUser");
        user = userMapper.getUsers(user).get(0);
        user.setPassword("newPassword666");
        int rowsAffected = userMapper.updateUser(user);
        System.out.println("更新用户成功，受影响的行数：" + rowsAffected);
    }

    @Test
    public void testDeleteUserById() {
        int rowsAffected = userMapper.delUserById(1L);
        System.out.println("删除用户成功，受影响的行数：" + rowsAffected);
    }

    @Test
    public void testDeleteUserByName() {
        int rowsAffected = userMapper.delUserByName("testUser2");
        System.out.println("删除用户成功，受影响的行数：" + rowsAffected);
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = userMapper.getUsers(new User());
        for (User user : users) {
            System.out.println(user);
        }
    }

}
