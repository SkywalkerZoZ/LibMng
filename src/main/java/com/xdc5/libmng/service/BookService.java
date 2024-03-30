package com.xdc5.libmng.service;


import com.xdc5.libmng.entity.BookInfo;
import com.xdc5.libmng.entity.BookInstance;
import com.xdc5.libmng.entity.Borrowing;
import com.xdc5.libmng.mapper.BookInfoMapper;
import com.xdc5.libmng.mapper.BookInstanceMapper;
import com.xdc5.libmng.mapper.BorrowingMapper;
import com.xdc5.libmng.mapper.UserMapper;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;

import java.util.HashMap;
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

    public boolean updateBookInfo(String isbn) {
        List<BookInfo> book = bookInfoMapper.getBookInfoByISBN(isbn);
        if (book.isEmpty()) {
            return false;
        } else {
            for(BookInfo tBook: book)
                bookInfoMapper.updateBookInfo(tBook);
            return true;
        }
    }

    public boolean addBookInstance(BookInstance bookInstance)
    {
        if(bookInstanceMapper.addBookInstance(bookInstance) > 0)
            return true;
        else
            return false;
    }

    public String getUserName(int userId) {
        return userMapper.getUserNameById(userId);
    }
    //获取全部图书信息
    public List<HashMap<String,Object>> getAllBookInfos(){
        return bookInfoMapper.getAllBookInfo();
    }
    //通过title找到我需要的数目
    public List<HashMap<String,Object>> getBookByTitle(String title){

        String titleWithWildcard = "%" + title + "%";

        List<HashMap<String,Object>> booklist = bookInfoMapper.getBookInfoByTitle(titleWithWildcard);
        return booklist;
    }
    //通过author找到我需要的数目
    public List<HashMap<String,Object>> getBookByAuthor(String author){

        String authorWithWildcard = "%" + author + "%";
        List<HashMap<String,Object>> booklist = bookInfoMapper.getBookInfoByAuthor(authorWithWildcard);
        //我们需要根据bookInfomapper和
        return booklist;
    }

    public List<HashMap<String,Object>> getBookByIsbn(String isbn){

        String isbnWithWildcard = "%" + isbn + "%";
        List<HashMap<String,Object>> booklist = bookInfoMapper.getBookInfoByIsbn(isbnWithWildcard);
        //我们需要根据bookInfomapper和
        return booklist;
    }

    public boolean deleteBookInstance(Integer instanceId) {
        if(bookInstanceMapper.delBookInstanceById(instanceId) > 0)
            return true;
        else
            return false;
    }
}
