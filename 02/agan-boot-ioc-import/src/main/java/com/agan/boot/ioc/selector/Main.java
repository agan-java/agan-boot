package com.agan.boot.ioc.selector;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
/**
 * @author 阿甘
 * @see 课程地址：https://study.163.com/course/courseMain.htm?courseId=1004348001&share=2&shareId=1016671292
 * @version 1.0
 * 注：如有任何疑问欢迎加入QQ群977438372 进行讨论
 */
public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(UserConfig.class);
        RoleBean roleBean = context.getBean(RoleBean.class);
        UserBean userBean = context.getBean(UserBean.class);

        System.out.println(userBean.toString());
        System.out.println(roleBean.toString());
    }

}
