package com.xdc5.libmng.entity;

import lombok.Data;

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
    LocalDate penaltyDate;
    LocalDate endDate;
}
