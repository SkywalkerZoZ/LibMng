package com.xdc5.libmng.controller;

import com.xdc5.libmng.entity.Reservation;
import com.xdc5.libmng.entity.Result;
import com.xdc5.libmng.service.BookService;
import com.xdc5.libmng.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ReservationController {
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private BookService bookService;

    @PutMapping("/user/reservation/{isbn}")
    public Result reservateBook(HttpServletRequest request,@PathVariable String isbn) {
        System.out.println(isbn);
        Integer userId = (Integer) request.getAttribute("userId");
        Reservation reservation = new Reservation();
        reservation.setIsbn(isbn);
        reservation.setUserId(userId);
        //不存在该书籍
        if(bookService.getBookInfoByIsbn(reservation.getIsbn()) == null)
            return Result.error("Fail: the book not exists");
        else if(reservationService.reserveBook(reservation))
            return Result.success("Success: put /user/reservation/{userId}");
        return Result.error("Fail: bad request");
    }
}
