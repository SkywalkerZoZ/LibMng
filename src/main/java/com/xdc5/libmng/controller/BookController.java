package com.xdc5.libmng.controller;

import com.xdc5.libmng.entity.BookInfo;
import com.xdc5.libmng.entity.BookInstance;
import com.xdc5.libmng.entity.Borrowing;
import com.xdc5.libmng.entity.Result;
import com.xdc5.libmng.service.BookService;
import com.xdc5.libmng.utils.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@RestController
public class BookController {
    @Autowired
    private BookService bookService;
    @GetMapping("/user/books/info")
    public Result showBookInfo(){
        //按照api要求返回信息。
        List<HashMap<String,Object>> bookInfoList=bookService.getAllBookInfos();
        return Result.success(bookInfoList,"Success: all books Info");
    }


    @GetMapping("/user/books/search")
    public Result searchBook(@RequestBody Map<String, Object> requestBody){
        String method=(String)requestBody.get("method");
        String keyword=(String)requestBody.get("keyword");
        log.info("method:"+method+"keyword:"+keyword);
        if(Objects.equals(method, "title")){
            //按照title寻找数目并返回success
            List<HashMap<String,Object>> allBooksInfo=bookService.getBookByTitle(keyword);
            return Result.success(allBooksInfo,"Success: books Info");
        }else if(Objects.equals(method, "author")){
            //按照author寻找书目并返回success
            List<HashMap<String,Object>> allBooksInfo=bookService.getBookByAuthor(keyword);
            return Result.success(allBooksInfo,"Success: books Info");
        }else if(Objects.equals(method, "isbn")){
            //按照isbn寻找书目并返回success
            List<HashMap<String,Object>> allBooksInfo=bookService.getBookByIsbn(keyword);
            return Result.success(allBooksInfo,"Success: books Info");
        }else{
            return Result.error("Fail: invalid method");
        }
    }

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

    @PostMapping("/admin/books/info")
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
        try {
            bookService.addBookInfo(bookinfo);
        } catch (Exception e) {
            return Result.error("Fail: data too long");
        }
        return Result.success(null, "Success: post /admin/books/info");

    }
    @DeleteMapping("/admin/books/info/{isbn}")
    public Result delBookInfo(@PathVariable String isbn) {

        List<BookInfo> data = bookService.getBookInfoByISBN(isbn);
        if (data == null || data.isEmpty()) {
            return Result.error("Fail: isbn not found");
        }
        if (isbn == null || isbn.isEmpty()) {
            return Result.error("Fail: isbn not null");
        }
        if (bookService.checkBookInfoIsEmpty(isbn)){
            return Result.error("Fail: can't delete bookinfo(exist instances)");
        }
        bookService.delBookInfo(isbn);
        return Result.success("Success: delete /admin/books/info/{isbn}");
    }

    @PutMapping("/admin/books/info/{isbn}")
    public Result changeBookInfo(@PathVariable String isbn) {

        if(bookService.updateBookInfo(isbn))
            return Result.success("Success: change /admin/books/info/{isbn}");
        else
            return Result.error("Fail: Fail: bad request");
    }

    @PostMapping("/admin/books/instances")
    public Result addBookInstance(@RequestBody BookInstance bookInstance) {
        if(bookService.addBookInstance(bookInstance))
            return Result.success("Success: post /admin/books/instances");
        else
            return Result.error("Fail: Fail: bad request");

    }

}