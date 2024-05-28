package com.xdc5.libmng.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Penalty {
    private Integer penaltyId;
    private Integer adminId;
    private Integer userId;
    private String reason;
    private BigDecimal money;
//    private LocalDate penaltyDate;
//    private LocalDate endDate;
}
