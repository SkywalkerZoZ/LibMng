package com.xdc5.libmng.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Payment {
    private BigDecimal totalAmount;
    private String subject;
}