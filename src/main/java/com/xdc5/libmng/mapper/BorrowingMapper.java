package com.xdc5.libmng.mapper;

import com.xdc5.libmng.entity.Borrowing;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface BorrowingMapper {
    List<Borrowing> getBorrowing(Borrowing borrowing);
    Borrowing getByInstanceId(@Param("instanceId") Integer instanceId);
    //返回影响的行数
    int delBorrowingById(@Param("borrowingId") Integer borrowingId);
    //返回影响的行数
    int addBorrowing(Borrowing borrowing);
    int updateBorrowing(Borrowing borrowing);
    List<Borrowing> getBorrowAprv(@Param("approved") Integer approved);
    List<Borrowing> getLateRetAprv(@Param("approved") Integer approved);

    List<HashMap<String,Object>> getUnretReader();
}
