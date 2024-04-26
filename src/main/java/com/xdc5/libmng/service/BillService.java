package com.xdc5.libmng.service;


import com.xdc5.libmng.entity.Bill;
import com.xdc5.libmng.mapper.BillMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class BillService {
    @Autowired
    BillMapper billMapper;

    public int addBill(Bill bill)
    {
        return billMapper.addBill(bill);
    }
    public int updateStatusById(String billId, Integer billStatus)
    {
        return billMapper.updateStatusById(billId,billStatus);
    }
    public int getUserIdByBillId(String billId)
    {
        return billMapper.getUserIdByBillId(billId);
    }

    public List<Bill> getBillByUserId(int userId){
        return billMapper.getBillByUserId(userId);
    }


}
