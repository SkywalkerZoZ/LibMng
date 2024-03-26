package com.xdc5.libmng.controller;

import com.xdc5.libmng.entity.BookInfo;
import com.xdc5.libmng.entity.BookInstance;
import com.xdc5.libmng.entity.Borrowing;
import com.xdc5.libmng.entity.Result;
import com.xdc5.libmng.service.BookService;
import com.xdc5.libmng.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@RestController
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/admin/books/borrowing-info")
    public Result borrowingInfo(@RequestBody BookInstance bookInstance) {
        if (bookInstance.getIsbn() == null) {
            return Result.error("Fail: isbn not found");
        }
        List<Borrowing> borrowingInfoList = bookService.getBorrowingInfo(bookInstance.getIsbn());
        if ((borrowingInfoList == null) || (borrowingInfoList.isEmpty())) {
            return Result.error("Fail: borrowing info is null or empty");
        }

        List<HashMap<String, Object>> infoList = new ArrayList<>();
        for (Borrowing borrowingInfo : borrowingInfoList) {
            if (bookService.getUserName(borrowingInfo.getUserId()) != null) {
                String username = bookService.getUserName(borrowingInfo.getUserId());

                HashMap<String, Object> info = new HashMap<>();
                info.put("username", username);
                info.put("instanceId", borrowingInfo.getInstanceId());
                //格式化时间成字符串
                String borrowDate = DateTimeUtils.formatDate(borrowingInfo.getBorrowDate(), "yyyy-MM-dd");
                info.put("borrowDate", borrowDate);
                String dueDate = DateTimeUtils.formatDate(borrowingInfo.getDueDate(), "yyyy-MM-dd");
                info.put("dueDate", dueDate);
                infoList.add(info);
            }


        }
        return Result.success(infoList, "Success: get /admin/books/borrowing-info");

    }

    @PostMapping("/admin/books/add")
    public Result addBookInfo(@RequestBody BookInfo bookinfo) {
        if ((bookinfo.getIsbn() == null) || (bookinfo.getIsbn().isEmpty())) {
            return Result.error("Fail: isbn not null");
        }
        if ((bookinfo.getTitle() == null) || (bookinfo.getTitle().isEmpty())) {
            return Result.error("Fail: title not null");
        }
        if (bookService.checkIsbnDuplicate(bookinfo.getIsbn())) {
            return Result.error("Fail: info already exist");
        }
        bookService.addBookInfo(bookinfo);
        return Result.success(null, "Success: post /admin/books/add");

    }

    @DeleteMapping("/admin/books/{isbn}")
    public Result delBookInfo(@PathVariable String isbn) {

        List<BookInfo> data = bookService.getBookInfoByISBN(isbn);
        if (data == null || data.isEmpty()) {
            return Result.error("Fail: isbn not found");
        }
        if (isbn == null || isbn.isEmpty()) {
            return Result.error("Fail: isbn not null");
        }
        bookService.delBookInfo(isbn);
        return Result.success("Success: books delete");
    }


}