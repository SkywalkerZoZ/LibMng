package com.xdc5.libmng.entity;

import lombok.Data;
import java.time.LocalDate;

@Data
public class Borrowing {
    public static final int Borrow = 5;
    public static final int Lateret = 5;
//    public static final int MaxBorrowDate = 30;
    private Integer borrowingId;
    private Integer userId;
    private Integer instanceId;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    //private LocalDate lateRetDate;
    //private Integer borrowAprvStatus;
    //private Integer lateRetAprvStatus;
}
