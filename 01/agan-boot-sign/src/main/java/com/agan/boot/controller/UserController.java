package com.agan.boot.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
@Slf4j
public class UserController {

    /**
     * 测试需要签名的get请求
     */
    @GetMapping("/sign/testGet")
    public UserVO testGet(String username, String password) {
        UserVO vo=new UserVO();
        vo.setUsername("agan");
        return  vo;
    }
    /**
     * 测试需要签名的post请求
     */
    @PostMapping(value="/sign/testPost")
    public String  testPost( @RequestBody  UserVO userVO ){
        log.info("---------------testPost------------------");
        return "1";
    }

    /**
     * 测试不需要签名的URL
     */
    @GetMapping(value="/getObject")
    public UserVO  getObject(  ){
        UserVO vo=new UserVO();
        vo.setUsername("agan");
        return  vo;
    }
}
