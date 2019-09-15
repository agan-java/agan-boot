package com.agan.boot.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Value("${agan.msg}")
    private  String msg;

    @GetMapping("msg")
    public String getMsg() {
        return msg;
    }

    @GetMapping("/home")
    public String home(){
        return "hello agan!";
    }
}
