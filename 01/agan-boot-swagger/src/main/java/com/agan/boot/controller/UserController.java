package com.agan.boot.controller;



import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@Api(description = "用户接口")
@RestController
@RequestMapping("/user")
public class UserController {

    @ApiOperation("修改某条数据")
    @GetMapping(value = "/u/{id}")
    public UserVO findById(@PathVariable  int id){
            Random rand = new Random();
            UserVO user=new UserVO();
            user.setId(id);
            String temp="temp01";
            user.setUsername(temp);
            user.setPassword(temp);
            int n = rand.nextInt(2);
            user.setSex((byte)n);
            return user;
    }

    @ApiOperation("单个用户查询，按userid查用户信息")
    @PostMapping(value = "/user/create", produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
    public UserVO crreteUser(@RequestBody  UserVO userVO) {
        return userVO;
    }

}
