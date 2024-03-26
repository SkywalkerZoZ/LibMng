package com.xdc5.libmng.mapper;

import com.xdc5.libmng.entity.Borrowing;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BorrowingMapper {
    List<Borrowing> getBorrowing(Borrowing borrowing);
    List<Borrowing> getByInstanceId(@Param("instanceId") Integer instanceId);
    //返回影响的行数
    int delBorrowingById(@Param("borrowingId") Integer borrowingId);
    //返回影响的行数
    int addBorrowing(Borrowing borrowing);
    int updateBorrowing(Borrowing borrowing);

}
