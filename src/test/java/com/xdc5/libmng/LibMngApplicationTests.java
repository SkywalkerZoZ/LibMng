package com.xdc5.libmng;

import com.xdc5.libmng.entity.BookCatalog;
import com.xdc5.libmng.entity.BookInstance;
import com.xdc5.libmng.entity.User;
import com.xdc5.libmng.mapper.BookCatalogMapper;
import com.xdc5.libmng.mapper.BookInstanceMapper;
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


    @Autowired
    private BookCatalogMapper bookCatalogMapper;

    @Test
    public void testAddBookCatalog() {
        BookCatalog book = new BookCatalog();
        book.setIsbn("123456789");
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setDescription("Test Description");
        int rowsAffected = bookCatalogMapper.addBookCatalog(book);
        System.out.println("添加书籍目录成功，受影响的行数：" + rowsAffected);
    }

    @Test
    public void testDelBookCatalogByISBN() {
        String isbn = "123456789"; // Assuming the book with this ISBN exists
        int rowsAffected = bookCatalogMapper.delBookCatalogByISBN(isbn);
        System.out.println("删除书籍目录成功，受影响的行数：" + rowsAffected);
    }

    @Test
    public void testGetBooks() {
        // Assuming there are some books in the database
        List<BookCatalog> books = bookCatalogMapper.getBookCatalogs(new BookCatalog());
        System.out.println("获取到的书籍目录信息：");
        for (BookCatalog book : books) {
            System.out.println(book);
        }
    }

    @Test
    public void testUpdateBookCatalog() {
        BookCatalog book = new BookCatalog();
        book.setIsbn("123456789"); // Assuming the book with this ISBN exists
        book.setTitle("Updated Test Book");
        book.setAuthor("Updated Test Author");
        book.setDescription("Updated Test Description");
        int rowsAffected = bookCatalogMapper.updateBookCatalog(book);
        System.out.println("更新书籍目录成功，受影响的行数：" + rowsAffected);
    }


    @Autowired
    private BookInstanceMapper bookInstanceMapper;

    @Test
    public void testAddBookInstance() {
        BookInstance bookInstance = new BookInstance();
        bookInstance.setIsbn("123456789");
        bookInstance.setBorrowStatus(0);
        int rowsAffected = bookInstanceMapper.addBookInstance(bookInstance);
    }

    @Test
    public void testDelBookInstanceByInstanceId() {
        long instanceId = 1; // Assuming the book instance with this ID exists
        int rowsAffected = bookInstanceMapper.delBookInstanceByInstanceId(instanceId);
    }

    @Test
    public void testGetBookInstances() {
        // Assuming there are some book instances in the database
        List<BookInstance> bookInstances = bookInstanceMapper.getBookInstances(new BookInstance());
        for (BookInstance bookInstance : bookInstances) {
            System.out.println(bookInstance);
        }
    }

}
