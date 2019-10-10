package com.agan.boot.service;

import com.agan.boot.controller.TestController;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
/**
 * @author 阿甘
 * @see https://study.163.com/course/courseMain.htm?courseId=1004348001&share=2&shareId=1016671292
 * @version 1.0
 * 注：如有任何疑问欢迎加入QQ群977438372 进行讨论
 */
@Service
@Slf4j
public class ScoreService {


    @Async
    public void addScore(){
        //TODO 模拟睡5秒，用于赠送积分处理
        try {
            Thread.sleep(1000*5);
            log.info("--------S------处理积分--------------------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Async("scorePoolTaskExecutor")
    public void addScore2(){
        //TODO 模拟睡5秒，用于赠送积分处理
        try {
            Thread.sleep(1000*5);
            log.info("--------------处理积分--------------------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
