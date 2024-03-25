package com.xdc5.libmng.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Integer code;
    private String message;
    private Object data;


    public static Result success(String message){
        return new Result(200,message,null);
    }
    public static Result success(Object data, String msg){
        return new Result(200,msg,data);
    }
    public static Result error(String msg){
        return new Result(400,msg,null);
    }
    public static Result error(int code,String msg){
        return new Result(code,msg,null);
    }


}
