package com.xdc5.libmng.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Bill {
    String billId;
    Integer userId;
    String billSubject;
    BigDecimal billAmount;
    LocalDateTime billDate;
    Integer billStatus;
}
