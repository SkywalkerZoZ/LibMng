package com.xdc5.libmng.service;

import com.xdc5.libmng.entity.Borrowing;
import com.xdc5.libmng.mapper.BookDetailMapper;
import com.xdc5.libmng.mapper.BookInstanceMapper;
import com.xdc5.libmng.mapper.BorrowingMapper;
import com.xdc5.libmng.utils.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<Borrowing> getBorrowAprv(Integer approved){
        return borrowingMapper.getBorrowAprv(approved);
    }
    public List<Borrowing> getLateRetAprv(Integer approved){
        return borrowingMapper.getLateRetAprv(approved);
    }
    public void updateBorrowAprvStatus(Integer agree, Integer borrowingId){
        Borrowing borrowing = new Borrowing();
        borrowing.setBorrowingId(borrowingId);
        if (agree == 1){
            borrowing.setBorrowAprvStatus(1);
        }else if(agree == 0){
            borrowing.setBorrowAprvStatus(2);
        }
        borrowingMapper.updateBorrowing(borrowing);
    }

    public void updateLateRetStatus(Integer agree, Integer borrowingId){
        Borrowing borrowing = new Borrowing();
        borrowing.setBorrowingId(borrowingId);
        if (agree == 1){
            borrowing.setLateRetAprvStatus(1);
        }else if(agree == 0){
            borrowing.setLateRetAprvStatus(2);
        }
        borrowingMapper.updateBorrowing(borrowing);
    }

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

    public List<Borrowing> getBorrowingInfoByUid(Integer uid) {
        List<Borrowing> allBorrowingInfo = new ArrayList<>();
        Borrowing borrowing = new Borrowing();
        borrowing.setUserId(uid);
        return borrowingMapper.getBorrowing(borrowing);
    }
    public HashMap<String, Object> extractAprvInfo(Borrowing request, String userName, String isbn) {
        HashMap<String, Object> infoList = new HashMap<>();
        infoList.put("borrowingId", request.getBorrowingId());
        infoList.put("userId", request.getUserId());
        infoList.put("username", userName);
        infoList.put("instanceId", request.getInstanceId());
        infoList.put("isbn", isbn);

        String borrowDate = DateTimeUtils.dateToStr(request.getBorrowDate(), "yyyy-MM-dd");
        infoList.put("borrowDate", borrowDate);
        String dueDate = DateTimeUtils.dateToStr(request.getDueDate(), "yyyy-MM-dd");
        infoList.put("dueDate", dueDate);
        return infoList;
    }

    public boolean addBorrowing(Borrowing bInfo) {
        //检查书架有该书
        if(bookInstanceMapper.getIsbnByInstanceId(bInfo.getInstanceId()) == null)
            return false;
        //检查该书是否已经被提交过申请
        else if (canBorrow(bInfo)) {
            return false;
        }
        return (borrowingMapper.addBorrowing(bInfo) > 0);
    }

    public boolean canBorrow(Borrowing bInfo) {

        return borrowingMapper.getByInstanceId(bInfo.getInstanceId()) != null;
    }
}
