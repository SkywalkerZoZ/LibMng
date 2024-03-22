package com.xdc5.libmng.entity;

import lombok.Data;

import java.util.Base64;

@Data
public class User {
    private Integer userId;
    private String username;
    private String password;
    private String email;
    private byte[] avatar;
    private Integer borrowPerms;
    private String userRole;


    //为了便于图片数据avatar的传输，将avatar改为base64的编码格式。
    public void avatarToBase64() {
        avatar= Base64.getEncoder().encode(avatar);
    }
    //将收到的base64格式的图片文件解码
    public void Base64toAvatar(){
        avatar=Base64.getDecoder().decode(avatar);
    }
    //用于提供直接修改临时user变量的方法。
    public void ChangeEmail(String newemail){
        email=newemail;
    }
    public void ChangePassword(String newpassword){
        password=newpassword;
    }
    public void ChangeAvatar(byte[] newavatar){
        avatar=newavatar;
    }
}
