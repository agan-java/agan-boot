package com.agan.boot.controller;


import com.agan.boot.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 阿甘
 * @see 课程地址：https://study.163.com/course/courseMain.htm?courseId=1004348001&share=2&shareId=1016671292
 * @version 1.0
 * 注：如有任何疑问欢迎加入QQ群977438372 进行讨论
 */
@RestController
public class PayController {

    @Autowired
    private PayService payService;

    @RequestMapping(value = "/pay", method = RequestMethod.GET)
    public void pay(){
        this.payService.pay(1,2,10);
    }

}
