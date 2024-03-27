package com.xdc5.libmng.controller;

import com.xdc5.libmng.entity.BookInfo;
import com.xdc5.libmng.entity.Result;
import com.xdc5.libmng.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@Slf4j
@RestController
public class InfoController {
    @Autowired
    private BookService bookService;
    //
    @GetMapping("/user/books/Info")
    public Result showBookCatasInfo(){
        List<BookInfo> bookInfoList=bookService.getAllBookInfos();
        return Result.success(bookInfoList,"Success: books Info");
    }

    //
    @GetMapping("/user/books/search")
    public Result searchBook(@RequestBody Map<String, Object> requestBody){
        String method=(String)requestBody.get("method");
        String keyword=(String)requestBody.get("keyword");
        if(method=="title"){
            //按照title寻找数目并返回success
            return Result.success("hh");
        }else if(method=="author"){
            //按照author寻找书目并返回success
            return Result.success("hh");
        }else if(method=="isbn"){
            //按照isbn寻找书目并返回success
            return Result.success("hh");
        }else{
            return Result.error("Fail: invalid method");
        }
    }

}

