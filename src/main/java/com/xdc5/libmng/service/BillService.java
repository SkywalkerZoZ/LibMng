package com.xdc5.libmng.service;


import com.xdc5.libmng.entity.Bill;
import com.xdc5.libmng.mapper.BillMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BillService {
    @Autowired
    BillMapper billMapper;

    public int addBill(Bill bill)
    {
        return billMapper.addBill(bill);
    }
    public int updateStatusById(Integer billId,Integer billStatus)
    {
        return billMapper.updateStatusById(billId,billStatus);
    }
    public int getUserIdByBillId(int billId)
    {
        return billMapper.getUserIdByBillId(billId);
    }


}
