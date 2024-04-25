package com.xdc5.libmng.entity;

import lombok.Data;
import java.time.LocalDate;

@Data
public class Borrowing {
    public static final int Borrow = 7;
    public static final int Lateret = 7;
    private Integer borrowingId;
    private Integer userId;
    private Integer instanceId;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate lateRetDate;
    private LocalDate returnDate;
    private Integer borrowAprvStatus;
    private Integer lateRetAprvStatus;
}
