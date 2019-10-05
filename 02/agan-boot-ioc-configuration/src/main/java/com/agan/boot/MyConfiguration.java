package com.agan.boot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfiguration {
    public MyConfiguration() {
        System.out.println("MyConfiguration容器启动初始化。。。");
    }

    /**
     * @Bean注解在返回实例的方法上，如果未通过@Bean指定bean的名称，则默认的Bean对象名与标注的方法名相同；
     * 以下创建的对象名，和方法名一样，即userBean
     */
    @Bean
    public UserBean userBean(){
        UserBean userBean= new UserBean();
        userBean.setUsername("agan");
        userBean.setPassword("123456");
        return userBean;
    }
}
