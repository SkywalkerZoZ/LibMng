package com.xdc5.libmng.service;


import com.xdc5.libmng.entity.*;
import com.xdc5.libmng.mapper.*;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.awt.print.Book;
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
        String isbn=getIsbnByInstanceId(instId);
        BookInfo bookInfo=getBookInfoByIsbn(isbn);
        log.info(bookInfo.getLocation());
        return  bookInfo.getLocation();
    }

    public void addBookInfo(BookInfo bookInfo) {
        bookInfoMapper.addBookInfo(bookInfo);
    }

    public boolean delBookInfo(String isbn) {
        try {
            bookInfoMapper.delBookInfoByISBN(isbn);
            return true;
        } catch (Exception e) {
            return false;
        }
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

    public boolean updateBookInfo(BookInfo bookInfo) {
        try {
            return bookInfoMapper.updateBookInfo(bookInfo) > 0;
        } catch (Exception e) {
            return false;
        }
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
    public BookDetail getBookDetailByExactIsbn(String isbn){

        List<BookDetail> booklist = bookDetailMapper.getBookDetailByIsbn(isbn);
        if(booklist==null || booklist.isEmpty())
        {
            return null;
        }
        return booklist.get(0);
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
    public void updateStatus(BookInstance bInstance){
        bookInstanceMapper.updateStatus(bInstance);
    }
    public boolean canBorrow(Integer bookInstanceId) {
        return bookInstanceMapper.getStatusByInstanceId(bookInstanceId)==0;
    }
    public Integer getAvailableInstance(String isbn){
        List <Integer> availableBooks = bookInstanceMapper.getInstanceId(isbn);
        for (Integer availableBook : availableBooks){
            if (canBorrow(availableBook)){
                return availableBook;
            }
        }
        return null;
    }
    public BookInstance getInstanceById(Integer instanceId){
        return bookInstanceMapper.getInstanceById(instanceId);
    }

    public List<BookInstance> getBookInstanceList(BookInstance bookInstance){
        return bookInstanceMapper.getBookInstances(bookInstance);
    }
}

