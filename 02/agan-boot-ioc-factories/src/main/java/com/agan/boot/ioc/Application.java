package com.agan.boot.ioc;

import agan.core.Agan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
/**
 * @author 阿甘
 * @see 课程地址：https://study.163.com/course/courseMain.htm?courseId=1004348001&share=2&shareId=1016671292
 * @version 1.0
 * 注：如有任何疑问欢迎加入QQ群977438372 进行讨论
 */
@SpringBootApplication
public class Application {

	public static void main(String[] args) {

		ConfigurableApplicationContext run = SpringApplication.run(Application.class, args);
		Agan bean = run.getBean(Agan.class);
		System.out.println(bean.info());
	}

}
