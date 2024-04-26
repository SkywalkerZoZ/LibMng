package com.xdc5.libmng.mapper;

import com.xdc5.libmng.entity.Bill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.UUID;

@Mapper
public interface BillMapper {
    int addBill(Bill bill);
    int updateStatusById(@Param("billId") String billId, @Param("billStatus") Integer billStatus);
    int getUserIdByBillId(String billId);
    List<Bill> getBillByUserId(int userId);
}
