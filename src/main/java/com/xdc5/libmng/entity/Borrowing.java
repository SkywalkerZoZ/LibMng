package com.xdc5.libmng.entity;

import lombok.Data;
import java.time.LocalDate;

@Data
public class Borrowing {
    private Integer borrowingId;
    private Integer userId;
    private Integer instanceId;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate lateRetDate;
    private Integer borrowAprvStatus;
    private Integer lateRetAprvStatus;
}
