package com.xdc5.libmng.controller;

import com.xdc5.libmng.entity.Reservation;
import com.xdc5.libmng.entity.Result;
import com.xdc5.libmng.service.BookService;
import com.xdc5.libmng.service.ReservationService;
import com.xdc5.libmng.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class ReservationController {
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private BookService bookService;

    @PutMapping("/user/reservation/{userId}")
    public Result reservateBook(@PathVariable Integer userId, @RequestBody Map<String,Object> request) {
        Reservation reservation = new Reservation();
        reservation.setIsbn((String) request.get("isbn"));
        reservation.setUserId(userId);
        //不存在该书籍
        if(bookService.getBookInfoByIsbn(reservation.getIsbn()) == null)
            return Result.error("Fail: the book not exists");
        else if(reservationService.reservateBook(reservation))
            return Result.success("Success: put /user/reservation/{userId}");
        return Result.error("Fail: bad request");
    }
}
