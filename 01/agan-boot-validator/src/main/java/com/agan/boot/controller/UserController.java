package com.agan.boot.controller;


import io.swagger.annotations.Api;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
/**
 * @author 阿甘
 * @see 课程地址：https://study.163.com/course/courseMain.htm?courseId=1004348001&share=2&shareId=1016671292
 * @version 1.0
 * 注：如有任何疑问欢迎加入QQ群977438372 进行讨论
 */
@Api(description = "用户接口")
@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping(value = "/user/create", produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
    public void crreteUser(@RequestBody @Validated UserVO userVO) {

    }

    @PostMapping(value = "/user/update", produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
    public void updateUser(@RequestBody @Validated UserVO userVO) {
        UserVO user = null;
        //user = userDao.selectById(userId);
        Assert.notNull(user, "用户不存在！");
    }

}
