package com.xdc5.libmng.controller;

import com.xdc5.libmng.entity.BookCatalog;
import com.xdc5.libmng.entity.BookInstance;
import com.xdc5.libmng.entity.Borrowing;
import com.xdc5.libmng.entity.Result;
import com.xdc5.libmng.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@RestController
public class BookController {
    // TODO ????
    private final long MAX_SIZE = 5 * 1024 * 1024;
    @Autowired
    private BookService bookService;

    //TODO get not post
    @PostMapping("/admin/books/borrowing-info")
    public Result borrowingInfo(@RequestBody BookInstance bookInstance) {
        List<Borrowing> borrowingInfoList = bookService.getBorrowingInfo(bookInstance.getIsbn());

        if ((borrowingInfoList != null) && (!borrowingInfoList.isEmpty())) {
            List<HashMap<String, Object>> infoList = new ArrayList<>();

            for (Borrowing borrowingInfo : borrowingInfoList) {
                String username = bookService.getUserName(borrowingInfo.getUserId());
                if (username != null) {
                    HashMap<String, Object> info = new HashMap<>();
                    info.put("username", username);
                    //格式化时间成字符串
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String borrowDate = borrowingInfo.getBorrowDate().format(formatter);
                    info.put("borrowDate", borrowDate);

                    String dueDate = borrowingInfo.getDueDate().format(formatter);
                    info.put("dueDate", dueDate);

                    info.put("instanceId", borrowingInfo.getInstanceId());

                    infoList.add(info);
                }
            }
            return Result.success(infoList, "Success: post /admin/books/borrowing-info");

        } else {
            return Result.error("Fail: Borrowing info is null or empty");
        }
    }

    @PostMapping("/admin/books/add")
    public Result addBookCatalog(@RequestBody BookCatalog bookCatalog) {

        if ((bookCatalog.getIsbn() != null) && (bookCatalog.getTitle() != null)) {
            bookService.addBookCatalog(bookCatalog);
            return Result.success(null, "Success: books add");
        }else{
            return Result.error("Fail: bad request");
        }
    }

    // TODO delete not post
    @PostMapping("/admin/books/{isbn}")
    public Result delBookCatalog(@PathVariable String isbn){
        List<BookCatalog> data = bookService.getBookCatalogsByISBN(isbn);
        if ((isbn != null) && (data != null)){
            bookService.delBookCatalog(isbn);
            return Result.success("Success: books delete");
        }else {
            return Result.error("Fail: isbn not found");
        }
    }


}