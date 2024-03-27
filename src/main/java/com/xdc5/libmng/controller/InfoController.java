package com.xdc5.libmng.controller;

import com.xdc5.libmng.entity.BookInfo;
import com.xdc5.libmng.entity.Result;
import com.xdc5.libmng.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Slf4j
@RestController
public class InfoController {
    @Autowired
    private BookService bookService;
    //
    @GetMapping("/user/books/info")
    public Result showBookInfo(){
        //按照api要求返回信息。
        List<HashMap<String,Object>> bookInfoList=bookService.getAllBookInfos();
        return Result.success(bookInfoList,"Success: all books Info");
    }

    //
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

}

