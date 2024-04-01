package com.xdc5.libmng.service;

import com.xdc5.libmng.mapper.ReservationMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xdc5.libmng.entity.Reservation;
@Slf4j
@Service
public class ReservationService {
    @Autowired
    private ReservationMapper reservationMapper;

    public boolean reservateBook(Reservation reservation) {
        return (reservationMapper.addReservation(reservation) > 0);
    }

}
