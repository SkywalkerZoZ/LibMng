package com.xdc5.libmng.entity;

import lombok.Data;

@Data
public class User {
    private Integer userId;
    private String username;
    private String password;
    private String email;
    private byte[] avatar;
    private Integer borrowPerms;
    private String userRole;
}
