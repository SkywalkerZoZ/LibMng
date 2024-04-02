package com.xdc5.libmng.controller;

import com.xdc5.libmng.entity.BookInstance;
import com.xdc5.libmng.entity.Borrowing;
import com.xdc5.libmng.entity.Result;
import com.xdc5.libmng.service.BookService;
import com.xdc5.libmng.service.BorrowingService;
import com.xdc5.libmng.service.UserService;
import com.xdc5.libmng.utils.DateTimeUtils;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
public class BorrowingController {
    @Autowired
    BorrowingService borrowingService;
    @Autowired
    BookService bookService;
    @Autowired
    UserService userService;
    @GetMapping("/admin/borrowing/applications")
    public Result showBorrowAprv(@RequestParam Integer approved) {
        if (borrowingService.getBorrowAprv(approved) == null || borrowingService.getBorrowAprv(approved).isEmpty()) {
            return Result.error("Fail: borrowing approval is null or empty");
        }

        List<Borrowing> BorrowingRequest = borrowingService.getBorrowAprv(approved);
        List<HashMap<String, Object>> allInfoLists = new ArrayList<>();
        for (Borrowing request : BorrowingRequest) {
            String userName = bookService.getUserName(request.getUserId());
            String isbn = bookService.getIsbnByInstanceId(request.getInstanceId());
            String location = bookService.getLocationByIsbn(isbn);
            HashMap<String, Object> info = borrowingService.extractAprvInfo(request, userName, isbn);
            info.put("borrowAprvStatus", request.getBorrowAprvStatus());
            info.put("location", location);
            allInfoLists.add(info);
        }
        return Result.success(allInfoLists, "Success: get /admin/borrowing/applications");
    }


    @GetMapping("/admin/borrowing/late-returns")
    public Result showlateRetAprv(@RequestParam Integer approved) {
        if (borrowingService.getLateRetAprv(approved) == null || borrowingService.getLateRetAprv(approved).isEmpty()) {
            return Result.error("Fail: late return approval is null or empty");
        }

        List<Borrowing> LateRetAprv = borrowingService.getLateRetAprv(approved);
        List<HashMap<String, Object>> allInfoLists = new ArrayList<>();
        for (Borrowing request : LateRetAprv) {

            String userName = bookService.getUserName(request.getUserId());
            String isbn = bookService.getIsbnByInstanceId(request.getInstanceId());
            HashMap<String, Object> infoList = borrowingService.extractAprvInfo(request, userName, isbn);
            String lateRetDate = DateTimeUtils.dateToStr(request.getLateRetDate(), "yyyy-MM-dd");
            infoList.put("lateRetDate", lateRetDate);
            infoList.put("lateRetAprvStatus", request.getLateRetAprvStatus());
            allInfoLists.add(infoList);
        }
        return Result.success(allInfoLists, "Success : get /admin/borrowing/late-returns");
    }

    @PutMapping("/admin/borrowing/applications/{borrowingId}")
    public Result processBorrowAprv(@PathVariable Integer borrowingId, @RequestParam Integer agree) {
        if (agree != 0 && agree != 1 ) {
            return Result.error("Fail: input error");
        }
        Borrowing borrowingInfo=borrowingService.getBorrowingInfo(borrowingId);
        if (borrowingInfo == null) {
            return Result.error("Fail: this borrow approval not found");
        }
        if (Objects.equals(borrowingInfo.getBorrowAprvStatus(), agree+1)) {
            return Result.error("Fail: already processed");
        }
        borrowingService.updateBorrowAprvStatus(agree, borrowingId);
        if(agree == 0){
            BookInstance update = new BookInstance();
            update.setBorrowStatus(0);
            update.setInstanceId(borrowingInfo.getInstanceId());
            bookService.updateStatus(update);
        }

        return Result.success("Success: put /admin/borrowing/applications/{borrowingId}");
    }

    @PutMapping("/admin/borrowing/late-returns/{borrowingId}")
    public Result processLateRetAprv(@PathVariable Integer borrowingId, @RequestParam Integer agree) {
        Borrowing borrowingInfo=borrowingService.getBorrowingInfo(borrowingId);
        if (agree != 0 && agree != 1) {
            return Result.error("Fail: input error");
        }
        if (borrowingInfo==null) {
            return Result.error("Fail: borrow approval not found");
        }
        int borrowAprvStatus=borrowingInfo.getBorrowAprvStatus();
        if (borrowAprvStatus==0) {
            return Result.error("Fail: approval has not processed");
        }
        if (Objects.equals(borrowingInfo.getLateRetAprvStatus(), agree+1)) {
            return Result.error("Fail: already processed");
        }
        borrowingService.updateLateRetStatus(agree, borrowingId);
        return Result.success("Success: put /admin/borrowing/late-returns/{borrowingId}");
    }

    //查看未归还的读者列表
    @GetMapping("/admin/borrowing/overdue-readers")
    public Result showUnretReader(){
        List<HashMap<String,Object>> resultData=borrowingService.getUNretReader();
        return Result.success(resultData,"Success: get /admin/borrowing/overdue-readers");
    }
    //检索读者
    @GetMapping("/admin/readers/search")
    public Result searchReader(String username,Integer userId){
        if(username!=null&&userId==null){
        //按照姓名模糊查找
            return Result.success(userService.getReaderByName(username),"Success: get /admin/readers/search");
        }else if(username==null&&userId!=null){
            return Result.success(userService.getReaderById(userId),"Success: get /admin/readers/search");
        }else{
            return Result.error("Fail: bad request");
        }
    }



    /* ****User Part**** */
    @PostMapping("/user/borrowing")
    public Result borrowBook(HttpServletRequest request,@RequestBody Map<String, Object> requestBody) {
        Integer userId = (Integer) request.getAttribute("userId");
        String dueDate=(String)requestBody.get("dueDate");
        String isbn= (String) requestBody.get("isbn");
        //检查用户权限
        if(!userService.checkPermsByID(userId)) {
            return Result.error("Fail: do not have borrowing privileges");
        }
        Integer availableBook = bookService.getAvailableInstance(isbn);
        if (availableBook==null){
            return Result.error("Fail: no available instance exists");
        }

        Borrowing bInfo = new Borrowing();
        bInfo.setUserId(userId);
        bInfo.setInstanceId(availableBook);
        //转换data格式
        LocalDate date=DateTimeUtils.strToDate(dueDate,"yyyy-MM-dd");
        bInfo.setDueDate(date);

        //添加借阅信息 更改 BookInstance borrowStatus
        borrowingService.addBorrowing(bInfo);

        //返回数据信息
        HashMap<String,Object> data = new HashMap<>();
        data.put("instanceId",availableBook);
        data.put("location",bookService.getLocation(availableBook));
        return Result.success(data,"Success: post /user/borrowing");
    }

    @GetMapping("/user/borrowing/records")
    public Result selectBorrowingInfo(HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("userId");
        return Result.success(borrowingService.getBorrowingInfoByUid(userId),"Success: get /user/borrowing/records");

    }

    @PutMapping("/user/borrowing/return/{instanceId}")
    public Result returnBook(HttpServletRequest request,@PathVariable Integer instanceId)
    {
        Integer userId = (Integer) request.getAttribute("userId");
        if(instanceId==null)
        {
            return Result.error("Fail: instanceId not found");
        }
        BookInstance instance = bookService.getInstanceById(instanceId);
        if(instance==null)
        {
            return Result.error("Fail: instanceId not found");
        }
        instance.setBorrowStatus(0);
        bookService.updateStatus(instance);
        Borrowing borrowing=borrowingService.getUnreturnedBorrowing(userId,instanceId);
        if(borrowing==null)
        {
            return Result.error("Fail: borrowing not found");
        }
        borrowing.setReturnDate(LocalDate.now());
        borrowingService.updateBorrowing(borrowing);
        return Result.success("Success: put /user/borrowing/return/{instanceId}");
    }

}
