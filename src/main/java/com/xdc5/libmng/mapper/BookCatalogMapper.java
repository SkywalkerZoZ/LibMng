package com.xdc5.libmng.mapper;

import com.xdc5.libmng.entity.BookCatalog;
import com.xdc5.libmng.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookCatalogMapper {
    List<BookCatalog> getBookCatalogs(BookCatalog bookCatalog);
    //返回影响的行数
    int delBookCatalogByISBN(@Param("isbn") String isbn);
    //返回影响的行数
    int addBookCatalog(BookCatalog bookCatalog);
    int updateBookCatalog(BookCatalog bookCatalog);
}
