package com.xdc5.libmng;


import com.xdc5.libmng.controller.BookController;
import com.xdc5.libmng.entity.BookInstance;
import com.xdc5.libmng.entity.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ControllerTests {
    @Test
    public void test(){
        System.out.println("controller test here");
    }

    @Autowired
    private BookController bookController;

    @Test
    public void testBorrowingInfo() {
        // 创建一个 BookInstance 对象
        BookInstance bookInstance = new BookInstance();
        bookInstance.setIsbn("978-3-16-148410-0");

        // 调用 borrowingInfo 方法
        Result result = bookController.borrowingInfo(bookInstance);

        // 输出结果
        System.out.println("Code: " + result.getCode());
        System.out.println("Message: " + result.getMessage());
        System.out.println("Data: " + result.getData());
    }
}
