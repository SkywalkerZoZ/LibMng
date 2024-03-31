package com.xdc5.libmng.entity;


import lombok.Data;

@Data
public class BookDetail {
    private String isbn;
    private String title;
    private String author;
    private byte[] cover;
    private String description;
    private String location;
    private int available;
    private int borrowed;
}
