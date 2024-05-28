package com.xdc5.libmng.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.net.Inet4Address;
import java.time.LocalDate;

@Data
public class PenaltyDetail {
    Integer penaltyId;
    Integer adminId;
    String adminName;
    String adminEmail;
    Integer userId;
    String username;
    String reason;
    BigDecimal money;
//    LocalDate penaltyDate;
//    LocalDate endDate;
}
