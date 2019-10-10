package com.agan.boot.controller;


import com.agan.boot.response.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value="/user/create",produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
    public void  crreteUser( @RequestBody @Validated UserVO userVO ){
        log.info("---------------crreteUser------------------");
    }

    @GetMapping(value="/getStr")
    public String  getStr(  ){
        return  "test";
    }

    @GetMapping(value="/getObject")
    public UserVO  getObject(  ){
        UserVO vo=new UserVO();
        vo.setUsername("agan");
        return  vo;
    }

    @GetMapping(value="/empty")
    public void  empty(  ){

    }

    @GetMapping(value="/error")
    public void  error(  ){
        int i=9/0;
    }


    @GetMapping(value="/getResult")
    public Result getResult(  ){
        return Result.suc("test");
    }
}
