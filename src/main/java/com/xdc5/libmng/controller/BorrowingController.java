package com.xdc5.libmng.controller;

import com.xdc5.libmng.entity.Borrowing;
import com.xdc5.libmng.entity.Result;
import com.xdc5.libmng.service.BookService;
import com.xdc5.libmng.service.BorrowingService;
import com.xdc5.libmng.service.UserService;
import com.xdc5.libmng.utils.DateTimeUtils;
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
        // TODO agree逻辑处理 borrowingService.updateBorrowStatus
        if (agree != 0 && agree != 1 ) {
            return Result.error("Fail: input error");
        }
        Borrowing borrowingInfo=borrowingService.getBorrowingInfo(borrowingId);
        if (borrowingInfo == null) {
            return Result.error("Fail: this borrow approval not found");
        }
        if (Objects.equals(borrowingInfo.getBorrowAprvStatus(), agree)) {
            return Result.error("Fail: already processed");
        }
        borrowingService.updateBorrowAprvStatus(agree, borrowingId);
        return Result.success("Success: put /admin/borrowing/applications/{borrowingId}");
    }

    @PutMapping("/admin/borrowing/late-returns/{borrowingId}")
    public Result processLateRetAprv(@PathVariable Integer borrowingId, @RequestParam Integer agree) {
        Borrowing borrowingInfo=borrowingService.getBorrowingInfo(borrowingId);
        if (borrowingInfo==null) {
            return Result.error("Fail: borrow approval not found");
        }
        int borrowAprvStatus=borrowingInfo.getBorrowAprvStatus();
        if (borrowAprvStatus==0) {
            return Result.error("Fail: approval has not processed");
        }
        if (agree != 0 && agree != 1) {
            return Result.error("Fail: input error");
        }
        borrowingService.updateLateRetStatus(agree, borrowingId);
        return Result.success("Success: put /admin/borrowing/late-returns/{borrowingId}");
    }

    /* ****User Part**** */
    // TODO 修改方法
    @PostMapping("/user/borrowing")
    public Result borrowBook(HttpServletRequest request,@RequestBody Map<String, Object> requestBody) {
        Integer userId = (Integer) request.getAttribute("userId");
        String dueDate=(String)requestBody.get("dueDate");
        Integer instanceId= (Integer) requestBody.get("instanceId");
        Borrowing bInfo = new Borrowing();
        bInfo.setUserId(userId);
        bInfo.setInstanceId((Integer) requestBody.get("instanceId"));

        //转换data格式
        LocalDate date=DateTimeUtils.strToDate(dueDate,"yyyy-MM-dd");
        bInfo.setDueDate(date);
        //检查用户权限
        if(!userService.checkPermsByID(bInfo.getUserId())) {
            return Result.error("Fail: do not have borrowing privileges");
        }
        //添加借阅信息
        if (!borrowingService.addBorrowing(bInfo))
        {
            return Result.error("the book has been borrowed");
        }
        //返回数据信息
        HashMap<String,Object> data = new HashMap<>();
        data.put("instanceId",instanceId);
        data.put("location",bookService.getLocation(instanceId));
        return Result.success(data,"Success: post /user/borrowing");
        //更新实体状态（审批处处理）
    }

    @GetMapping("/user/borrowing/records")
    public Result selectBorrowingInfo(HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("userId");
        return Result.success(borrowingService.getBorrowingInfo(userId),"Success: post /user/borrowing");

    }
}
