package com.agan.boot.ioc.imports;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(UserService.class);
        UserService userService = context.getBean(UserService.class);
        UserBean userBean=context.getBean(UserBean.class);

        System.out.println(userService.toString());
        System.out.println(userBean.toString());
    }

}
