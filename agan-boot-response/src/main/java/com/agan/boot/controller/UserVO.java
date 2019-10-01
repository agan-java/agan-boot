package com.agan.boot.controller;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
public class UserVO {

    private Integer id;

    private String username;

    private String password;

    private String email;

    private String phone;

    private String idCard;

    private Byte sex;
    private Byte deleted;
    private Date updateTime;
    private Date createTime;
}