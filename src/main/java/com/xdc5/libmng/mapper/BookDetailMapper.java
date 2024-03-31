package com.xdc5.libmng.mapper;

import com.xdc5.libmng.entity.BookDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookDetailMapper {
    List<BookDetail> getBookDetailByTitle(String title);
    List<BookDetail> getBookDetailByAuthor(String author);
    List<BookDetail> getBookDetailByIsbn(String isbn);
    List<BookDetail> getAllBookInfo();
}
