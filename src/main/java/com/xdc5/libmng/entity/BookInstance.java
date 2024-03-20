package com.xdc5.libmng.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookInstance {
    private Long instanceId;
    private String isbn;
    // 借阅状态, 0为未借阅, 1为已借阅
    private Integer borrowStatus;
    private LocalDateTime addDate;

}
