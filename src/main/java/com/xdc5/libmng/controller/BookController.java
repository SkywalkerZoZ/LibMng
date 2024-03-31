package com.xdc5.libmng.controller;

import com.xdc5.libmng.entity.BookInfo;
import com.xdc5.libmng.entity.BookInstance;
import com.xdc5.libmng.entity.Borrowing;
import com.xdc5.libmng.entity.Result;
import com.xdc5.libmng.service.BookService;
import com.xdc5.libmng.utils.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;


import javax.sql.rowset.BaseRowSet;
import java.security.interfaces.RSAKey;
import java.util.*;

@Slf4j
@RestController
public class BookController {
    @Autowired
    private BookService bookService;
    @GetMapping("/user/books/info")
    public Result showBookInfo(){
        //按照api要求返回信息。
        List<HashMap<String,Object>> bookInfoList=bookService.getAllBookInfo();
        return Result.success(bookInfoList,"Success: all books Info");
    }


    @GetMapping("/user/books/search")
    public Result searchBook(@RequestBody Map<String, Object> requestBody){
        String method=(String)requestBody.get("method");
        String keyword=(String)requestBody.get("keyword");
        log.info("method:"+method+"keyword:"+keyword);
        if(Objects.equals(method, "title")){
            //按照title寻找数目并返回success
            List<HashMap<String,Object>> allBooksInfo=bookService.getBookInfoByTitle(keyword);
            return Result.success(allBooksInfo,"Success: books Info");
        }else if(Objects.equals(method, "author")){
            //按照author寻找书目并返回success
            List<HashMap<String,Object>> allBooksInfo=bookService.getBookInfoByAuthor(keyword);
            return Result.success(allBooksInfo,"Success: books Info");
        }else if(Objects.equals(method, "isbn")){
            //按照isbn寻找书目并返回success
            List<HashMap<String,Object>> allBooksInfo=bookService.getBookDetailByIsbn(keyword);
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

        List<BookInfo> data = bookService.getBookInfoByIsbn(isbn);
        if (data == null || data.isEmpty()) {
            return Result.error("Fail: isbn not found");
        }
        if (isbn == null || isbn.isEmpty()) {
            return Result.error("Fail: isbn not null");
        }
        bookService.delBookInfo(isbn);
        return Result.success("Success: delete /admin/books/info/{isbn}");
    }

    @PutMapping("/admin/books/info/{isbn}")
    public Result changeBookInfo(@PathVariable String isbn,@RequestBody BookInfo book) {
        if(bookService.updateBookInfo(isbn,book))
            return Result.success("Success: change /admin/books/info/{isbn}");
        else
            return Result.error("Fail: bad request");
    }

    @Transactional
    @PostMapping("/admin/books/instances")
    public Result addBookInstance(@RequestBody Map<String, Object> requestBody) {
        String isbn = (String)requestBody.get("isbn");
        Integer num=(Integer)requestBody.get("number");
        for (int i =0;i< num;i++) {
            if (!bookService.addBookInstance(isbn)) {
                return Result.error("Fail:can not add book instance");
            }
        }
        return Result.success("Success: post /admin/books/instances");
    }

    @DeleteMapping("/admin/books/instances/{instanceId}")
    public Result deleteBookInstance(@PathVariable Integer instanceId){
        if(bookService.deleteBookInstance(instanceId))
            return Result.success("Success: delete /admin/books/instances/{instanceId}");
        else
            return Result.error("Fail: instanceId not found");

    }

    @GetMapping("/admin/borrowing/applications")
    public Result showBorrowAprv(){
        if (bookService.getBorrowAprv().isEmpty() || bookService.getBorrowAprv() == null){
            return Result.error("Fail: borrowing approval is null or empty");
        }

        List<Borrowing> BorrowingRequest = bookService.getBorrowAprv();
        List<HashMap<String,Object>> allInfoLists = new ArrayList<>();
        for (Borrowing request : BorrowingRequest){
            String userName = bookService.getUserName(request.getUserId());
            String isbn = bookService.getIsbnByInstanceId(request.getInstanceId());
            String location = bookService.getLocationByIsbn(isbn);
            HashMap<String, Object> infoList = new HashMap<>();
            infoList.put("borrowingId", request.getBorrowingId());
            infoList.put("userId", request.getUserId());
            infoList.put("username", userName);
            infoList.put("instanceId", request.getInstanceId());
            infoList.put("isbn", isbn);

            String borrowDate = DateTimeUtils.formatDate(request.getBorrowDate(), "yyyy-MM-dd");
            infoList.put("borrowDate", borrowDate);
            String dueDate = DateTimeUtils.formatDate(request.getDueDate(), "yyyy-MM-dd");
            infoList.put("dueDate", dueDate);

            infoList.put("borrowAprvStatus", request.getBorrowAprvStatus());
            infoList.put("location", location);

            allInfoLists.add(infoList);
        }
        return Result.success(allInfoLists, "Success: get /admin/borrowing/applications");
    }
    @GetMapping("/admin/borrowing/late-returns")
    public Result showlateRetAprv(){
        if (bookService.getLateRetAprv().isEmpty() || bookService.getLateRetAprv() == null){
            return Result.error("Fail: late return approval is null or empty");
        }

        List<Borrowing> LateRetAprv = bookService.getLateRetAprv();
        List<HashMap<String,Object>> allInfoLists = new ArrayList<>();
        for (Borrowing request : LateRetAprv){

            String userName = bookService.getUserName(request.getUserId());
            String isbn = bookService.getIsbnByInstanceId(request.getInstanceId());
            HashMap<String, Object> infoList = new HashMap<>();
            infoList.put("borrowingId", request.getBorrowingId());
            infoList.put("userId", request.getUserId());
            infoList.put("username", userName);
            infoList.put("instanceId", request.getInstanceId());
            infoList.put("isbn", isbn);

            String borrowDate = DateTimeUtils.formatDate(request.getBorrowDate(), "yyyy-MM-dd");
            infoList.put("borrowDate", borrowDate);
            String dueDate = DateTimeUtils.formatDate(request.getDueDate(), "yyyy-MM-dd");
            infoList.put("dueDate", dueDate);
            String lateRetDate = DateTimeUtils.formatDate(request.getLateRetDate(), "yyyy-MM-dd");
            infoList.put("lateRetDate", lateRetDate);

            infoList.put("lateRetAprvStatus", request.getLateRetAprvStatus());

            allInfoLists.add(infoList);
        }
        return Result.success(allInfoLists, "Success : get /admin/borrowing/late-returns");
    }
    @PutMapping("/admin/borrowing/applications/{borrowingId}")
    public Result processBorrowAprv(@PathVariable Integer borrowingId, @RequestParam Integer agree){
        if (agree != 0 && agree != 1 && agree != 2){
            return Result.error("Fail: input error");
        }
        if (bookService.getBorrowingInfo(borrowingId).isEmpty() || bookService.getBorrowingInfo(borrowingId) == null){
            return Result.error("Fail: not found this borrow approval");
        }
        if (Objects.equals(bookService.getBorrowingInfo(borrowingId).get(0).getBorrowAprvStatus(), agree)){
            return Result.error("Fail: already processed");
        }
        Borrowing statusUpdate = new Borrowing();
        statusUpdate.setBorrowAprvStatus(agree);
        statusUpdate.setBorrowingId(borrowingId);
        bookService.updateBorrowStatus(statusUpdate);
        return Result.success("Success: put /admin/borrowing/applications/{borrowingId}");
    }
    @PutMapping("/admin/borrowing/late-returns/{borrowingId}")
    public Result processLateRetAprv(@PathVariable Integer borrowingId, @RequestParam Integer agree){
        if (Objects.equals(bookService.getBorrowingInfo(borrowingId).get(0).getBorrowAprvStatus(), 0)){
            return Result.error("Fail: not approval borrow");
        }
        if (agree != 0 && agree != 1 && agree != 2 && agree != 3){
            return Result.error("Fail: input error");
        }
        if (bookService.getBorrowingInfo(borrowingId).isEmpty() || bookService.getBorrowingInfo(borrowingId) == null){
            return Result.error("Fail: not found this borrow approval");
        }
        if (Objects.equals(bookService.getBorrowingInfo(borrowingId).get(0).getLateRetAprvStatus(), agree)){
            return Result.error("Fail: already processed");
        }
        Borrowing statusUpdate = new Borrowing();
        statusUpdate.setLateRetAprvStatus(agree);
        statusUpdate.setBorrowingId(borrowingId);
        bookService.updateBorrowStatus(statusUpdate);
        return Result.success("Success: put /admin/borrowing/late-returns/{borrowingId}");
    }

}
