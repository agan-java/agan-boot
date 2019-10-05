package com.agan.boot;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(MyConfiguration.class);
        UserBean userBean=(UserBean)context.getBean("userBean");

        System.out.println(userBean.toString());
    }

}
