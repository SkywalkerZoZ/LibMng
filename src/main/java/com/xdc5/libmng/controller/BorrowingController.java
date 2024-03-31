package com.xdc5.libmng.controller;

import com.xdc5.libmng.entity.Borrowing;
import com.xdc5.libmng.entity.Result;
import com.xdc5.libmng.service.BookService;
import com.xdc5.libmng.service.BorrowingService;
import com.xdc5.libmng.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestController
public class BorrowingController {
    @Autowired
    BorrowingService borrowingService;

    @Autowired
    BookService bookService;
    @GetMapping("/admin/borrowing/applications")
    public Result showBorrowAprv() {
        if (borrowingService.getBorrowAprv() == null || borrowingService.getBorrowAprv().isEmpty()) {
            return Result.error("Fail: borrowing approval is null or empty");
        }

        List<Borrowing> BorrowingRequest = borrowingService.getBorrowAprv();
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
    public Result showlateRetAprv() {
        if (borrowingService.getLateRetAprv() == null || borrowingService.getLateRetAprv().isEmpty()) {
            return Result.error("Fail: late return approval is null or empty");
        }

        List<Borrowing> LateRetAprv = borrowingService.getLateRetAprv();
        List<HashMap<String, Object>> allInfoLists = new ArrayList<>();
        for (Borrowing request : LateRetAprv) {

            String userName = bookService.getUserName(request.getUserId());
            String isbn = bookService.getIsbnByInstanceId(request.getInstanceId());
            HashMap<String, Object> infoList = borrowingService.extractAprvInfo(request, userName, isbn);
            String lateRetDate = DateTimeUtils.formatDate(request.getLateRetDate(), "yyyy-MM-dd");
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
        Borrowing statusUpdate = new Borrowing();
        statusUpdate.setBorrowAprvStatus(agree);
        statusUpdate.setBorrowingId(borrowingId);
        borrowingService.updateBorrowStatus(statusUpdate);
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
        Borrowing statusUpdate = new Borrowing();
        statusUpdate.setLateRetAprvStatus(agree);
        statusUpdate.setBorrowingId(borrowingId);
        borrowingService.updateBorrowStatus(statusUpdate);
        return Result.success("Success: put /admin/borrowing/late-returns/{borrowingId}");
    }
}
