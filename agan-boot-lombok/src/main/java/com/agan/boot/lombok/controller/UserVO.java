package com.agan.boot.lombok.controller;

import lombok.Data;

import java.util.Date;

@Data
public class UserVO {
    private Integer id;

    private String username;

    private String password;

    private Byte sex;

    private Byte deleted;

    private Date updateTime;

    private Date createTime;

}