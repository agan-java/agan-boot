package com.agan.boot;



/**
 * @author 阿甘
 * @see 课程地址：https://study.163.com/course/courseMain.htm?courseId=1004348001&share=2&shareId=1016671292
 * @version 1.0
 * 注：如有任何疑问欢迎加入QQ群977438372 进行讨论
 */
public class UserService {

    public UserBean findUser(){
        UserBean userBean= new UserBean();
        userBean.setUsername("agan");
        userBean.setPassword("123456");
        return userBean;
    }

}
