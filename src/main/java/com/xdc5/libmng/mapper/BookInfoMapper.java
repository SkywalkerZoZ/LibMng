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
    // TODO List<BookInfo> ?
    BookInfo getBookInfoByIsbn(@Param("isbn") String isbn);
    List<HashMap<String,Object>> getBookInfoByTitle(String title);
    List<HashMap<String,Object>> getBookInfoByAuthor(String author);
    List<HashMap<String,Object>> getBookDetailByIsbn(String isbn);
    List<HashMap<String,Object>> getAllBookInfo();

}
