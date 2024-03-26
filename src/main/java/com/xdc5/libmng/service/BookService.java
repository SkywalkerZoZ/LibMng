package com.xdc5.libmng.service;

import com.xdc5.libmng.entity.BookCatalog;
import com.xdc5.libmng.entity.Borrowing;
import com.xdc5.libmng.mapper.BookCatalogMapper;
import com.xdc5.libmng.mapper.BookInstanceMapper;
import com.xdc5.libmng.mapper.BorrowingMapper;
import com.xdc5.libmng.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class BookService {
    @Autowired
    private BorrowingMapper borrowingMapper;
    @Autowired
    private BookInstanceMapper bookInstanceMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BookCatalogMapper bookCatalogMapper;
    public List<Borrowing> getBorrowingInfo(String isbn) {
        List<Integer> instanceId = bookInstanceMapper.getInstanceId(isbn);
        List<Borrowing> allBorrowingInfo = new ArrayList<>();

        for (int i = 0; i < instanceId.size(); i++) {
            //TODO list?
            List<Borrowing> info = borrowingMapper.getByInstanceId(instanceId.get(i));
            allBorrowingInfo.addAll(info); // 将当前 instanceId 对应的 Borrowing 信息添加到 allBorrowingInfo 列表中
        }
            return allBorrowingInfo; // 返回列表中的所有元素

    }

    public void addBookCatalog(BookCatalog bookCatalog){
        //TODO duplicate check
        bookCatalogMapper.addBookCatalog(bookCatalog);
    }

    public void delBookCatalog(String isbn){
        bookCatalogMapper.delBookCatalogByISBN(isbn);
    }

    public List<BookCatalog> getBookCatalogsByISBN(String isbn){
        List<BookCatalog> data = bookCatalogMapper.getBookCatalogsByISBN(isbn);
        return data;
    }
    public String getUserName(int userId){
        return userMapper.getUserNameById(userId);
    }
}
