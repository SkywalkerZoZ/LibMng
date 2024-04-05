package com.xdc5.libmng.controller;

import com.xdc5.libmng.entity.BookDetail;
import com.xdc5.libmng.entity.BookInfo;
import com.xdc5.libmng.entity.Reservation;
import com.xdc5.libmng.entity.Result;
import com.xdc5.libmng.service.BookService;
import com.xdc5.libmng.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ReservationController {
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private BookService bookService;

    @PutMapping("/user/reservation/{isbn}")
    public Result reservateBook(HttpServletRequest request,@PathVariable String isbn) {
        //不存在该书籍
        if(bookService.getBookInfoByIsbn(isbn) == null)
            return Result.error("Fail: the book not exists");
        Integer userId = (Integer) request.getAttribute("userId");
        Reservation reservation = new Reservation();
        reservation.setIsbn(isbn);
        reservation.setUserId(userId);
        if(reservationService.checkIfReserved(reservation))
        {
            return Result.error("Fail: has been reserved");
        }
        if(!reservationService.reserveBook(reservation)){
            return Result.error("Fail: bad request");
        }
        return Result.success("Success: put /user/reservation/{userId}");

    }

    @GetMapping("/user/reservation")
    public Result getReservation(HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("userId");
        List<String> reservations = reservationService.getReservation(userId);
        List<BookDetail> data=new ArrayList<>();
        for(String rsvIsbn : reservations)
        {
            data.add(bookService.getBookDetailByExactIsbn(rsvIsbn));
        }
        return Result.success(data,"Success: get /user/reservation");
    }
}
