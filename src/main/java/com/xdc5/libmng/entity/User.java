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

    /*
    * 方法名：AvatartoBase64
    * 创建者：wwh
    * 修改时间：2024年3月22日18:15:42
    * 详情：为了便于图片数据avatar的传输，我们将avatar改为base64的编码格式。
    * */
    public void AvatartoBase64() {
        avatar= Base64.getEncoder().encode(avatar);
    }
    /*
    *方法名：Base64toAvatar
    * 创建者：魏文晖
    * 修改时间：2024年3月22日20:32:22
    * 详情：将收到的base64格式的图片文件解码
    * */
    public void Base64toAvatar(){
        avatar=Base64.getDecoder().decode(avatar);
    }
    /*
    *方法名：Changexxx
    * 创建者：魏文晖
    * 修改时间：2024年3月22日20:33:27
    * 详情：用于提供直接修改临时user变量的方法。
    *
    * */
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
