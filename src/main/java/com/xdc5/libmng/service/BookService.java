package com.xdc5.libmng.service;


import com.xdc5.libmng.entity.BookDetail;
import com.xdc5.libmng.entity.BookInfo;
import com.xdc5.libmng.entity.BookInstance;
import com.xdc5.libmng.entity.Borrowing;
import com.xdc5.libmng.mapper.*;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
public class BookService {

    @Autowired
    private BookInstanceMapper bookInstanceMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BookInfoMapper bookInfoMapper;
    @Autowired
    private BookDetailMapper bookDetailMapper;


    public String getLocation(Integer instId) {
        return  getBookInfoByIsbn(getIsbnByInstanceId(instId)).getLocation();
    }

    public void addBookInfo(BookInfo bookInfo) {
        bookInfoMapper.addBookInfo(bookInfo);
    }

    public void delBookInfo(String isbn) {
        bookInfoMapper.delBookInfoByISBN(isbn);
    }

    public BookInfo getBookInfoByIsbn(String isbn) {
        return bookInfoMapper.getBookInfoByIsbn(isbn);
    }

    public boolean existsIsbn(String isbn) {
        BookInfo bookInfo = bookInfoMapper.getBookInfoByIsbn(isbn);
        return bookInfo!=null;
    }

    public boolean checkBookInfoIsEmpty(String isbn){
        List<Integer> bookInstances = bookInstanceMapper.getInstanceId(isbn);
        return !bookInstances.isEmpty();
    }

    public boolean updateBookInfo(BookInfo bookInfo)
    {
        return bookInfoMapper.updateBookInfo(bookInfo)>0;
    }

    public boolean addBookInstance(String isbn)
    {
        BookInstance bookInstance = new BookInstance();
        bookInstance.setIsbn(isbn);
        bookInstance.setBorrowStatus(0);
        return bookInstanceMapper.addBookInstance(bookInstance) > 0;
    }

    public String getUserName(int userId) {
        return userMapper.getUserNameById(userId);
    }
    //获取全部图书信息
    public List<BookDetail> getAllBookInfo(){
        return bookDetailMapper.getAllBookInfo();
    }
    //通过title找到我需要的数目
    public List<BookDetail> getBookInfoByTitle(String title){

        String titleWithWildcard = "%" + title + "%";

        List<BookDetail> booklist = bookDetailMapper.getBookDetailByTitle(titleWithWildcard);
        return booklist;
    }
    //通过author找到我需要的数目
    public List<BookDetail> getBookInfoByAuthor(String author){

        String authorWithWildcard = "%" + author + "%";
        List<BookDetail> booklist = bookDetailMapper.getBookDetailByAuthor(authorWithWildcard);

        return booklist;
    }

    public List<BookDetail> getBookDetailByIsbn(String isbn){

        String isbnWithWildcard = "%" + isbn + "%";
        List<BookDetail> booklist = bookDetailMapper.getBookDetailByIsbn(isbnWithWildcard);

        return booklist;
    }

    public boolean deleteBookInstance(Integer instanceId) {
        return bookInstanceMapper.delBookInstanceById(instanceId) > 0;
    }
    public String getIsbnByInstanceId(Integer instanceId){
        return bookInstanceMapper.getIsbnByInstanceId(instanceId);
    }
    public String getLocationByIsbn(String isbn){
        BookInfo BookInfo = bookInfoMapper.getBookInfoByIsbn(isbn);
        return BookInfo.getLocation();
    }
}
