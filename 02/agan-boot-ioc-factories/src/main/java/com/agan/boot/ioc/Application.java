package com.agan.boot.ioc;

import agan.core.Agan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {

		ConfigurableApplicationContext run = SpringApplication.run(Application.class, args);
		Agan bean = run.getBean(Agan.class);
		System.out.println(bean.info());
	}

}
