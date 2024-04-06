package com.xdc5.libmng.mapper;

import com.xdc5.libmng.entity.Reservation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReservationMapper {
    List<Reservation> getReservation(Reservation reservation);
    //返回影响的行数
    int delReservationById(@Param("rsvId") Integer rsvId);
    //返回影响的行数
    int addReservation(Reservation reservation);
    int updateReservation(Reservation reservation);
    List<String> getRsvIsbnByUserId(@Param("userId")Integer userId);
}
