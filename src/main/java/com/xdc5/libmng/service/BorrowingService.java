package com.xdc5.libmng.service;

import com.xdc5.libmng.entity.BookInstance;
import com.xdc5.libmng.entity.Borrowing;
import com.xdc5.libmng.mapper.BookDetailMapper;
import com.xdc5.libmng.mapper.BookInstanceMapper;
import com.xdc5.libmng.mapper.BorrowingMapper;
import com.xdc5.libmng.utils.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class BorrowingService {
    @Autowired
    private BorrowingMapper borrowingMapper;
    @Autowired
    private BookInstanceMapper bookInstanceMapper;
    @Autowired
    private BookDetailMapper bookDetailMapper;

//    public List<Borrowing> getBorrowAprv(Integer approved){
//        return borrowingMapper.getBorrowAprv(approved);
//    }

//    public List<Borrowing> getLateRetAprv(Integer approved){
//        return borrowingMapper.getLateRetAprv(approved);
//    }

//    public void updateBorrowAprvStatus(Integer agree, Integer borrowingId){
//        Borrowing borrowing = new Borrowing();
//        borrowing.setBorrowingId(borrowingId);
//        if (agree == 1){
//            borrowing.setBorrowAprvStatus(1);
//        }else if(agree == 0){
//            borrowing.setBorrowAprvStatus(2);
//        }
//        borrowingMapper.updateBorrowing(borrowing);
//    }

//    public void processLateRetAprv(Integer agree, Integer borrowingId, LocalDate date){
//        Borrowing borrowing = new Borrowing();
//        borrowing.setBorrowingId(borrowingId);
//        if (agree == 1){
//            borrowing.setLateRetAprvStatus(1);
//            borrowing.setDueDate(date);
//        }else if(agree == 0){
//            borrowing.setLateRetAprvStatus(2);
//            borrowingMapper.updateLateRetDateToNULL(borrowingId);
//        }
//        borrowingMapper.updateBorrowing(borrowing);
//    }


    public Borrowing getBorrowingInfo(Integer borrowingId){
        Borrowing borrowId = new Borrowing();
        borrowId.setBorrowingId(borrowingId);
        List<Borrowing> borrowList=borrowingMapper.getBorrowing(borrowId);
        return borrowList.isEmpty() ? null : borrowList.get(0);
    }

    public List<Borrowing> getBorrowingInfo(String isbn) {
        List<Integer> instanceId = bookInstanceMapper.getInstanceId(isbn);
        List<Borrowing> allBorrowingInfo = new ArrayList<>();

        // 检查 instanceId 是否为 null 或者是否为空列表
        if (instanceId != null && !instanceId.isEmpty()) {
            for (Integer integer : instanceId) {
                Borrowing info = borrowingMapper.getByInstanceId(integer);
                if (info != null) { // 检查获取的借阅信息是否为 null
                    allBorrowingInfo.add(info); // 将当前 instanceId 对应的 Borrowing 信息添加到 allBorrowingInfo 列表中
                }
            }
        }

        return allBorrowingInfo; // 返回列表中的所有元素，可能为空
    }

//    public List<Borrowing> getBorrowingInfoByUid(Integer uid) {
//        List<Borrowing> allBorrowingInfo = new ArrayList<>();
//        Borrowing borrowing = new Borrowing();
//        borrowing.setUserId(uid);
//        return borrowingMapper.getBorrowing(borrowing);
//    }

//    public HashMap<String, Object> extractAprvInfo(Borrowing request, String userName, String isbn) {
//        HashMap<String, Object> infoList = new HashMap<>();
//        infoList.put("borrowingId", request.getBorrowingId());
//        infoList.put("userId", request.getUserId());
//        infoList.put("username", userName);
//        infoList.put("instanceId", request.getInstanceId());
//        infoList.put("isbn", isbn);
//
//        String borrowDate = DateTimeUtils.dateToStr(request.getBorrowDate(), "yyyy-MM-dd");
//        infoList.put("borrowDate", borrowDate);
//        String dueDate = DateTimeUtils.dateToStr(request.getDueDate(), "yyyy-MM-dd");
//        infoList.put("dueDate", dueDate);
//        return infoList;
//    }

    //借书申请后BorrowStatus就变为1，如果管理员同意，BorrowStatus不变，如果不同意，BorrowStatus变回0
    public void addBorrowing(Borrowing bInfo) {
        borrowingMapper.addBorrowing(bInfo);
        BookInstance update = new BookInstance();
        update.setBorrowStatus(1);
        update.setInstanceId(bInfo.getInstanceId());
        bookInstanceMapper.updateStatus(update);
    }

//    public Borrowing getUnreturnedBorrowing(Integer userId,Integer instanceId){
//        Borrowing searchBorrowing=new Borrowing();
//        searchBorrowing.setUserId(userId);
//        searchBorrowing.setInstanceId(instanceId);
//        List<Borrowing> borrowinglist = borrowingMapper.getBorrowing(searchBorrowing);
//        Borrowing borrowing=null;
//        for (Borrowing bo :borrowinglist)
//        {
//            //还未归还
//            if (bo.getReturnDate()==null)
//            {
//                borrowing=bo;
//            }
//        }
//        return borrowing;
//    }

    public void updateBorrowing(Borrowing borrowing)
    {
        borrowingMapper.updateBorrowing(borrowing);
    }

    //获取未归还的读者列表
    public List<HashMap<String,Object>> getUNretReader(){
        List<HashMap<String,Object>> data = borrowingMapper.getUnretReader();
        for (HashMap<String,Object> result : data){
            Integer borrowingId = (Integer) result.get("borrowingId");
            result.put("instanceId",borrowingMapper.getInstanceId(borrowingId));
            result.put("isbn",bookInstanceMapper.getIsbnByInstanceId(borrowingId));
            result.remove("borrowingId");
        }
        return data;
    }

//    public void lateRetAprv(LocalDate date,Integer borrowingId){
//        Borrowing borrowing = new Borrowing();
//        borrowing.setLateRetDate(date);
//        borrowing.setBorrowingId(borrowingId);
//        borrowing.setLateRetAprvStatus(0);
//        borrowingMapper.updateBorrowing(borrowing);
//    }

    public List<Borrowing> getBorrowingByStatus(Integer userId,Integer status){
        //已经归还
        if (status == 0){
            return borrowingMapper.getRetBorrowing(1,userId);
        }
        //未归还
        else if (status == 1){
            return borrowingMapper.getRetBorrowing(0,userId);
        }
//        //未审批
//        if (status == 0){
//            Borrowing borrowing = new Borrowing();
//            borrowing.setBorrowAprvStatus(0);
//            borrowing.setUserId(userId);
//            return borrowingMapper.getBorrowing(borrowing);
//        }
//        //未通过
//        else if (status == 1){
//            Borrowing borrowing = new Borrowing();
//            borrowing.setBorrowAprvStatus(2);
//            borrowing.setUserId(userId);
//            return borrowingMapper.getBorrowing(borrowing);
//        }
//        //已经归还
//        else if (status == 2){
//            return borrowingMapper.getRetBorrowing(null, 1,userId);
//        }
//        //未归还
//        else if (status == 3){
//            return borrowingMapper.getRetBorrowing(0,0,userId);
//        }
//        //可迟还
//        else if (status == 4){
//            return borrowingMapper.getRetBorrowing(1,0,userId);
//        }
        else
        {
            return null;
        }
    }
    public Borrowing getUnretByInstanceId(Integer instanceId){
        return borrowingMapper.getUnretByInstanceId(instanceId);
    }
}
