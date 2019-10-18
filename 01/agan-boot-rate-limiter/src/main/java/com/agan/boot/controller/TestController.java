package com.agan.boot.controller;

import com.agan.boot.annotation.Limiter;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
/**
 * @author 阿甘
 * @see 课程地址：https://study.163.com/course/courseMain.htm?courseId=1004348001&share=2&shareId=1016671292
 * @version 1.0
 * 注：如有任何疑问欢迎加入QQ群977438372 进行讨论
 */
@RestController
@Slf4j
public class TestController {
    /**
     * 限流，1秒钟1个
     */
    private RateLimiter limiter = RateLimiter.create(1);

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @GetMapping("/limiter")
    public String testLimiter() {
        //500毫秒内，没拿到令牌，就直接进入服务降级
        boolean tryAcquire = limiter.tryAcquire(500, TimeUnit.MILLISECONDS);
        if (!tryAcquire) {
            log.warn("进入服务降级，时间{}", simpleDateFormat.format(new Date()));
            return "当前排队人数较多，请稍后再试！";
        }
        log.info("获取令牌成功，时间{}", simpleDateFormat.format(new Date()));
        return "success";
    }

    @GetMapping("/limiter2")
    @Limiter(key = "limiter2", permitsPerSecond = 1, timeout = 500, timeunit = TimeUnit.MILLISECONDS,msg = "当前排队人数较多，请稍后再试！")
    public String limiter2() {
        log.debug("令牌桶=limiter2，获取令牌成功");
        return "ok";
    }

    @GetMapping("/limiter3")
    @Limiter(key = "limiter3", permitsPerSecond = 2, timeout = 500, timeunit = TimeUnit.MILLISECONDS,msg = "当前排队人数较多，请稍后再试！")
    public String limiter3() {
        log.debug("令牌桶=limiter3，获取令牌成功");
        return "ok";
    }
}
