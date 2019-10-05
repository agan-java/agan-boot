package com.agan.boot.ioc.selector;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(UserConfig.class);
        RoleBean roleBean = context.getBean(RoleBean.class);
        UserBean userBean = context.getBean(UserBean.class);

        System.out.println(userBean.toString());
        System.out.println(roleBean.toString());
    }

}
