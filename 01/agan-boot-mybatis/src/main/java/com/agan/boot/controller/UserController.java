package com.agan.boot.controller;



import com.agan.boot.service.UserService;
import com.agan.boot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

/**
 * @author 阿甘
 * @see 课程地址：https://study.163.com/course/courseMain.htm?courseId=1004348001&share=2&shareId=1016671292
 * @version 1.0
 * 注：如有任何疑问欢迎加入QQ群977438372 进行讨论
 */
@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    /**
     * 初始化1000条数据
     */
    @RequestMapping(value = "/c", method = RequestMethod.GET)
    public void create(){
        for(int i=0;i<1000;i++){
            User user=new User();
            String temp="user"+i;
            user.setUsername(temp);
            user.setPassword(temp);
            Random random=new Random();
            int sex=random.nextInt(2);
            user.setSex((byte)sex);
            userService.createUser(user);
        }
    }

    /**
     * 修改某条数据
     * @param id
     */
    @RequestMapping(value = "/u/{id}", method = RequestMethod.GET)
    public void create(@PathVariable  int id){
        User user=new User();
        user.setId(id);
        String temp="update"+id;
        user.setUsername(temp);
        user.setPassword(temp);
        this.userService.updateUser(user);
    }

    /**
     * 查询例子
     */
    @RequestMapping(value = "/f", method = RequestMethod.GET)
    public void find(){
        this.userService.findExample();
    }

}
