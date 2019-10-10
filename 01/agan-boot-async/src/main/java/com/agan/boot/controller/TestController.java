package com.agan.boot.controller;

import com.agan.boot.service.ScoreService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author 阿甘
 * @see https://study.163.com/course/courseMain.htm?courseId=1004348001&share=2&shareId=1016671292
 * @version 1.0
 * 注：如有任何疑问欢迎加入QQ群977438372 进行讨论
 */
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
