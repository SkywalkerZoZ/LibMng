package com.xdc5.libmng.controller;

import com.xdc5.libmng.entity.*;
import com.xdc5.libmng.service.BookService;
import com.xdc5.libmng.service.BorrowingService;
import com.xdc5.libmng.service.ReservationService;
import com.xdc5.libmng.service.UserService;
import com.xdc5.libmng.utils.DateTimeUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;



@RestController
@RequestMapping("/api")
public class BorrowingController {
    @Autowired
    BorrowingService borrowingService;
    @Autowired
    BookService bookService;
    @Autowired
    UserService userService;
    @Autowired
    ReservationService reservationService;
    @GetMapping("/admin/borrowing/applications")
    public Result showBorrowAprv(@RequestParam Integer approved) {
        if (borrowingService.getBorrowAprv(approved) == null) {
            return Result.error("Fail: borrowing approval is null");
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


//    @GetMapping("/admin/borrowing/late-returns")
//    public Result showlateRetAprv(@RequestParam Integer approved) {
////        if (borrowingService.getLateRetAprv(approved) == null || borrowingService.getLateRetAprv(approved).isEmpty()) {
////            return Result.error("Fail: late return approval is null or empty");
////        }
//
//        List<Borrowing> LateRetAprv = borrowingService.getLateRetAprv(approved);
//        List<HashMap<String, Object>> allInfoLists = new ArrayList<>();
//        for (Borrowing request : LateRetAprv) {
//
//            String userName = bookService.getUserName(request.getUserId());
//            String isbn = bookService.getIsbnByInstanceId(request.getInstanceId());
//            HashMap<String, Object> infoList = borrowingService.extractAprvInfo(request, userName, isbn);
//            String lateRetDate = DateTimeUtils.dateToStr(request.getLateRetDate(), "yyyy-MM-dd");
//            infoList.put("lateRetDate", lateRetDate);
//            infoList.put("lateRetAprvStatus", request.getLateRetAprvStatus());
//            allInfoLists.add(infoList);
//        }
//        return Result.success(allInfoLists, "Success : get /admin/borrowing/late-returns");
//    }

    @PutMapping("/admin/borrowing/applications/{borrowingId}")
    public Result processBorrowAprv(@PathVariable Integer borrowingId, @RequestBody Map<String, Object> requestBody) {
        int agree= (int) requestBody.get("agree");
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
            User user = userService.getUserByBorrowingId(borrowingId);
            user.setBorrowPerms(user.getBorrowPerms() + 1);
            userService.updateUserInfo(user);

            BookInstance update = new BookInstance();
            update.setBorrowStatus(0);
            update.setInstanceId(borrowingInfo.getInstanceId());
            bookService.updateStatus(update);
        }

        return Result.success("Success: put /admin/borrowing/applications/{borrowingId}");
    }

//    @PutMapping("/admin/borrowing/late-returns/{borrowingId}")
//    public Result processLateRetAprv(@PathVariable Integer borrowingId, @RequestBody Map<String, Object> requestBody) {
//        int agree= (int) requestBody.get("agree");
//        Borrowing borrowingInfo=borrowingService.getBorrowingInfo(borrowingId);
//        if (agree != 0 && agree != 1) {
//            return Result.error("Fail: input error");
//        }
//        if (borrowingInfo==null) {
//            return Result.error("Fail: borrow approval not found");
//        }
//        int borrowAprvStatus=borrowingInfo.getBorrowAprvStatus();
//        if (borrowAprvStatus==0) {
//            return Result.error("Fail: approval has not processed");
//        }
//        if (Objects.equals(borrowingInfo.getLateRetAprvStatus(), agree+1)) {
//            return Result.error("Fail: already processed");
//        }
//        borrowingService.processLateRetAprv(agree, borrowingId, borrowingInfo.getLateRetDate());
//        return Result.success("Success: put /admin/borrowing/late-returns/{borrowingId}");
//    }

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



    @PostMapping("/user/borrowing")
    public Result borrowBook(HttpServletRequest request,@RequestBody Map<String, Object> requestBody) {
        Integer userId = (Integer) request.getAttribute("userId");
        LocalDate dueDate=LocalDate.now().plusDays(Borrowing.Borrow);
        String isbn= (String) requestBody.get("isbn");
        //检查用户权限
        if(!userService.checkPermsByID(userId)) {
            return Result.error("Fail: do not have borrowing privileges");
        }
        Integer availableBook = bookService.getAvailableInstance(isbn);
        if (availableBook==null){
            return Result.error("Fail: no available instance exists");
        }

        //借阅数量暂时减1，若未通过，加回来
        User user = userService.getUserInfo(userId);
        user.setBorrowPerms(user.getBorrowPerms() - 1);
        userService.updateUserInfo(user);

        Borrowing bInfo = new Borrowing();
        bInfo.setUserId(userId);
        bInfo.setInstanceId(availableBook);
        bInfo.setDueDate(dueDate);

        //添加借阅信息 更改 BookInstance borrowStatus
        borrowingService.addBorrowing(bInfo);

        //同时删除对应的reservation
        Reservation reservation = new Reservation();
        reservation.setIsbn(isbn);
        reservation.setUserId(userId);

        //返回数据信息
        HashMap<String,Object> data = new HashMap<>();

        //加入是否删除了预约
        if(reservationService.checkIfReserved(reservation))
        {
//          data.put("ReservationInfo","cancel Reserved");
            reservationService.cancelReservation(reservation);
        }

        data.put("instanceId",availableBook);
        data.put("location",bookService.getLocation(availableBook));
        data.put("dueDate",dueDate);
        return Result.success(data,"Success: post /user/borrowing");
    }

    @GetMapping("/user/borrowing/records")
    public Result selectBorrowingInfo(HttpServletRequest request,@RequestParam Integer status) {
        Integer userId = (Integer) request.getAttribute("userId");

        List<Borrowing> borrowingList = borrowingService.getBorrowingByStatus(userId,status);
        if (borrowingList == null){
            return  Result.error("Fail: no such borrowing");
        }
        return Result.success(borrowingList,"Success: get /user/borrowing/records");

        //return Result.success(borrowingService.getBorrowingInfoByUid(userId),"Success: get /user/borrowing/records");

    }

//    @PutMapping("/user/borrowing/return/{instanceId}")
//    public Result returnBook(HttpServletRequest request,@PathVariable Integer instanceId)
//    {
//        Integer userId = (Integer) request.getAttribute("userId");
//        if(instanceId==null)
//        {
//            return Result.error("Fail: instanceId not found");
//        }
//        BookInstance instance = bookService.getInstanceById(instanceId);
//        if(instance==null)
//        {
//            return Result.error("Fail: instanceId not found");
//        }
//        instance.setBorrowStatus(0);
//        bookService.updateStatus(instance);
//        Borrowing borrowing=borrowingService.getUnreturnedBorrowing(userId,instanceId);
//        if(borrowing==null)
//        {
//            return Result.error("Fail: borrowing not found");
//        }
//        borrowing.setReturnDate(LocalDate.now());
//        borrowingService.updateBorrowing(borrowing);
//        return Result.success("Success: put /user/borrowing/return/{instanceId}");
//    }


//    @PostMapping("/user/borrowing/lateret-request")
//    public Result lateRetRequest(@RequestBody Map<String, Object> requestBody){
//        if (requestBody == null || requestBody.isEmpty()){
//            return Result.error("Fail: invalid input");
//        }
//        Integer borrowId= (Integer) requestBody.get("borrowId");
//        Borrowing borrowingInfo = borrowingService.getBorrowingInfo(borrowId);
//        if (borrowingInfo == null){
//            return Result.error("Fail: borrowing info is null");
//        }
//        LocalDate lateRetDate=borrowingInfo.getDueDate().plusDays(Borrowing.Lateret);
//
//        if (borrowingInfo.getReturnDate()!=null){
//            return Result.error("Fail: already return");
//        }
//        Integer aprvStatus = borrowingInfo.getBorrowAprvStatus();
//        if (aprvStatus==0 || aprvStatus==2){
//            return Result.error("Fail: not agreed borrow");
//        }
//        Integer status = borrowingInfo.getLateRetAprvStatus();
//        if(status != null){
//            return Result.error("Fail: already request");
//        }
//        borrowingService.lateRetAprv(lateRetDate,borrowId);
//        return Result.success("Success: post /admin/borrowing/lateret-request");
//    }

    @PutMapping("/admin/borrowing/return/{instanceId}")
    public Result returnConfirm(@PathVariable Integer instanceId){
        if(instanceId==null)
        {
            return Result.error("Fail: instanceId not found");
        }
        Borrowing borrowing = borrowingService.getUnretByInstanceId(instanceId);
        if (borrowing == null){
            return Result.error("Fail: borrowing info is null");
        }
        User user = userService.getUserByBorrowingId(borrowing.getBorrowingId());
        if (user == null){
            return Result.error("Fail: user info is null");
        }
        BookInstance instance = bookService.getInstanceById(instanceId);
        if(instance==null)
        {
            return Result.error("Fail: instanceId not found");
        }
        user.setBorrowPerms(user.getBorrowPerms() + 1);
        userService.updateUserInfo(user);
        instance.setBorrowStatus(0);
        bookService.updateStatus(instance);
        borrowing.setReturnDate(LocalDate.now());
        borrowingService.updateBorrowing(borrowing);
        return Result.success("Success: put /admin/borrowing/return/{instanceId}");
    }

    @PostMapping("/user/borrowing/lateretBorrow")
    public Result lateretBorrow(@RequestBody Map<String, Object> requestBody){
        if (requestBody == null || requestBody.isEmpty()){
            return Result.error("Fail: invalid input");
        }
        Integer borrowId= (Integer) requestBody.get("borrowId");
        Borrowing borrowingInfo = borrowingService.getBorrowingInfo(borrowId);
        if (borrowingInfo == null){
            return Result.error("Fail: borrowing info is null");
        }
        User user = userService.getUserByBorrowingId(borrowId);
        BigDecimal money = new BigDecimal(String.valueOf(user.getMoney()));

        if (money.compareTo(BigDecimal.ONE) < 0){
            return Result.error("Fail: not enough money");
        }
        LocalDate DueDate = borrowingInfo.getDueDate();
//        //借阅最长时间限制
//        LocalDate borrowDate = borrowingInfo.getBorrowDate();
//        if (borrowDate.plusDays(Borrowing.MaxBorrowDate).isBefore(DueDate)){
//            return Result.error("Fail: can't overdue return");
//        }
        //每次延期减1元
        money = money.subtract(BigDecimal.ONE);
        user.setMoney(money);
        userService.updateUserInfo(user);
        DueDate = DueDate.plusDays(Borrowing.Lateret);
        Borrowing bInfo = new Borrowing();
        bInfo.setDueDate(DueDate);
        bInfo.setBorrowingId(borrowId);
        borrowingService.updateBorrowing(bInfo);
        HashMap<String,Object> data = new HashMap<>();
        data.put("money",money);
        return Result.success(data,"Success: post /admin/borrowing/lateretBorrow");
    }

}
