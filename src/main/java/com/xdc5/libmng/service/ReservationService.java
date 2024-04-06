package com.xdc5.libmng.service;

import com.xdc5.libmng.mapper.ReservationMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xdc5.libmng.entity.Reservation;

import java.util.List;

@Slf4j
@Service
public class ReservationService {
    @Autowired
    private ReservationMapper reservationMapper;

    public boolean reserveBook(Reservation reservation) {
        return (reservationMapper.addReservation(reservation) > 0);
    }
    public boolean checkIfReserved(Reservation reservation)
    {
        return !reservationMapper.getReservation(reservation).isEmpty();
    }

    public List<String> getReservation(Integer userId) {
        return reservationMapper.getRsvIsbnByUserId(userId);
    }

    public boolean cancelReservation(Reservation reservation){

        Integer rsvId = reservationMapper.getReservation(reservation).get(0).getRsvId();
        reservationMapper.delReservationById(rsvId);
        return !reservationMapper.getReservation(reservation).isEmpty();
    }
}
