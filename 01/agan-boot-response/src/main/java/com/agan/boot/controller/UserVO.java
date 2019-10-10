package com.agan.boot.controller;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Date;
/**
 * @author 阿甘
 * @see 课程地址：https://study.163.com/course/courseMain.htm?courseId=1004348001&share=2&shareId=1016671292
 * @version 1.0
 * 注：如有任何疑问欢迎加入QQ群977438372 进行讨论
 */
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