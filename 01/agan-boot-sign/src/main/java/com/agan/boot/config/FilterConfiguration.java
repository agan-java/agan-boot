package com.agan.boot.config;

import com.agan.boot.filter.SignFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * @author 阿甘
 * @see 课程地址：https://study.163.com/course/courseMain.htm?courseId=1004348001&share=2&shareId=1016671292
 * @version 1.0
 * 注：如有任何疑问欢迎加入QQ群977438372 进行讨论
 */
@Configuration
public class FilterConfiguration {

    @Bean
    public FilterRegistrationBean signFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new SignFilter());
        registration.addUrlPatterns("/user/sign/*");
        registration.setName("SignFilter");
        // 设置过滤器被调用的顺序
        registration.setOrder(1);
        return registration;
    }
}
