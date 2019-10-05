package com.agan.boot.app;

import com.agan.boot.scan.TestComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.agan.boot.scan")
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Application.class, args);
        TestComponentScan componentScan = run.getBean(TestComponentScan.class);
        System.out.println(componentScan.toString());
    }
}