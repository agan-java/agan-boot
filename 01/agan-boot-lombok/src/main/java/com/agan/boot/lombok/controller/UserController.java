package com.agan.boot.lombok.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author 阿甘
 * @see 课程地址：https://study.163.com/course/courseMain.htm?courseId=1004348001&share=2&shareId=1016671292
 * @version 1.0
 * 注：如有任何疑问欢迎加入QQ群977438372 进行讨论
 */
@RestController
@Slf4j
public class UserController {

    //private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping("/user")
    public  UserVO  user() {
        UserVO userVO=new UserVO();
        userVO.setId(100);
        userVO.setUsername("agan");
        return userVO;
    }

    @RequestMapping("/log")
    public  void  log() {
        log.trace("-----------trace-------------");
        log.debug("-----------debug-------------");
        log.info("-----------info-------------");
        log.warn("-----------warn-------------");
        log.error("-----------error-------------");
    }
}
