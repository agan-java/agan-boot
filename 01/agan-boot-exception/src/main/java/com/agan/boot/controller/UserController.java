package com.agan.boot.controller;


import com.agan.boot.enums.ResultCode;
import com.agan.boot.exceptions.BusinessException;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@Api(description = "用户接口")
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

//    @PostMapping(value="/user/create",produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
//    public void  validatorError( @RequestBody @Validated UserVO userVO ){
//        log.info("---------------crreteUser------------------");
//    }

    @PostMapping(value="/error1")
    public void  error1(  ){
        int i=9/0;
    }

    @PostMapping(value="/error11")
    public String  error11(  ){
        try{
            int i=9/0;
        }catch (Exception ex){
            log.error("异常：{}",ex);
            return "no";
        }
        return "ok";
    }

    @PostMapping(value="/error2")
    public void  error2(  ){
        Integer.valueOf("a");
    }


    @PostMapping(value="/error3")
    public void  error3(  ){
        throw new BusinessException(ResultCode.USER_HAS_EXISTED);
    }

    @PostMapping(value="/error4")
    public void  error4(  ){
        throw new RuntimeException("用户已存在！！");
    }


//    @PostMapping(value="/error5")
//    public void  error5(  ){
//        Object obj=null;
//        Assert.notNull(obj, "用户不存在！！");
//
//    }


}
