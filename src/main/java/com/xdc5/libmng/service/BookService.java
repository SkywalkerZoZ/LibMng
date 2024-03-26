package com.xdc5.libmng.service;

import com.xdc5.libmng.entity.BookInfo;
import com.xdc5.libmng.entity.Borrowing;
import com.xdc5.libmng.mapper.BookInfoMapper;
import com.xdc5.libmng.mapper.BookInstanceMapper;
import com.xdc5.libmng.mapper.BorrowingMapper;
import com.xdc5.libmng.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
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
    private BookInfoMapper bookInfoMapper;

    public List<Borrowing> getBorrowingInfo(String isbn) {
        List<Integer> instanceId = bookInstanceMapper.getInstanceId(isbn);
        List<Borrowing> allBorrowingInfo = new ArrayList<>();

        // 检查 instanceId 是否为 null 或者是否为空列表
        if (instanceId != null && !instanceId.isEmpty()) {
            for (Integer integer : instanceId) {
                Borrowing info = borrowingMapper.getByInstanceId(integer);
                if (info != null) { // 检查获取的借阅信息是否为 null
                    allBorrowingInfo.add(info); // 将当前 instanceId 对应的 Borrowing 信息添加到 allBorrowingInfo 列表中
                }
            }
        }

        return allBorrowingInfo; // 返回列表中的所有元素，可能为空
    }

    public void addBookInfo(BookInfo bookInfo) {
        bookInfoMapper.addBookInfo(bookInfo);
    }

    public void delBookInfo(String isbn) {
        bookInfoMapper.delBookInfoByISBN(isbn);
    }

    public List<BookInfo> getBookInfoByISBN(String isbn) {
        List<BookInfo> data = bookInfoMapper.getBookInfoByISBN(isbn);
        return data;
    }

    public boolean checkIsbnDuplicate(String isbn) {
        List<BookInfo> bookInfos = bookInfoMapper.getBookInfoByISBN(isbn);
        return !bookInfos.isEmpty();
    }

    public String getUserName(int userId) {
        return userMapper.getUserNameById(userId);
    }
}
