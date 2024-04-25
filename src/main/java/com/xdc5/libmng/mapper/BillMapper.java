package com.xdc5.libmng.mapper;

import com.xdc5.libmng.entity.Bill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BillMapper {
    int addBill(Bill bill);
    int updateStatusById(@Param("billId") Integer billId,@Param("billStatus") Integer billStatus);
    int getUserIdByBillId(int billId);
}
