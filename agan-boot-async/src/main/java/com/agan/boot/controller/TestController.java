package com.agan.boot.controller;

import com.agan.boot.service.ScoreService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {


    @Autowired
    private ScoreService scoreService;

    @RequestMapping("/sync")
    public  String  createUser() {
        log.info("--------------注册用户--------------------");
        this.scoreService.addScore();
        return "OK";
    }

    @RequestMapping("/sync2")
    public  String  createUser2() {
        log.info("--------------注册用户2--------------------");
        this.scoreService.addScore2();
        return "OK";
    }
}
