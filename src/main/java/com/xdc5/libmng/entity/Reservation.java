package com.xdc5.libmng.entity;

import lombok.Data;

@Data
public class Reservation {
    private Integer rsvId;
    private Integer userId;
    private String isbn;
}
