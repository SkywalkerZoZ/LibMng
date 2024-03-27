package com.xdc5.libmng.mapper;

import com.xdc5.libmng.entity.BookInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface BookInfoMapper {
    List<BookInfo> getBookInfo(BookInfo bookInfo);
    //返回影响的行数
    int delBookInfoByISBN(@Param("isbn") String isbn);
    //返回影响的行数
    int addBookInfo(BookInfo bookInfo);
    int updateBookInfo(BookInfo bookInfo);
    List<BookInfo> getBookInfoByISBN(@Param("isbn") String isbn);
//按照api要求返回查找信息
    List<HashMap<String,Object>> getBookInfoByTitle(String title);
    List<HashMap<String,Object>> getBookInfoByAuthor(String author);
    List<HashMap<String,Object>> getBookInfoByIsbn(String isbn);
//按照api要求返回图书信息。
    List<HashMap<String,Object>> getAllBookInfo();
}
