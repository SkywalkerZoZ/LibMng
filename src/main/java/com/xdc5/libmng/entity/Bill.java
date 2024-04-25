package com.xdc5.libmng.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Bill {
    Integer billId;
    Integer userId;
    String billSubject;
    BigDecimal billAmount;
    LocalDateTime billDate;
    Integer billStatus;
}
