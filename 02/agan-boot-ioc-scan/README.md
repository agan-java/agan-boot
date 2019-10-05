#剖析springboot的@ComponentScan注解
##一：本课程目标：
1. 理解springboot的@ComponentScan注解作用
2. 学会用@ComponentScan

##二、剖析springboot的@ComponentScan注解
```
@ComponentScan(excludeFilters = { @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
		@Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
```
excludeFilters：过滤不需要扫描的类型。
@Filter 过滤注解
FilterType.CUSTOM 过滤类型为自定义规则，即指定特定的class
classes :过滤指定的class，即剔除了TypeExcludeFilter.class、AutoConfigurationExcludeFilter.class

从以上源码，我们可以得出结论：
1. @SpringBootApplication的源码包含了@ComponentScan，
故，只要@SpringBootApplication注解的所在的包及其下级包，都会讲class扫描到并装入spring ioc容器
2. 如果你自定义的定义一个Spring bean，不在@SpringBootApplication注解的所在的包及其下级包，
都必须手动加上@ComponentScan注解并指定那个bean所在的包。

## 三、为什么要用@ComponentScan？它解決什么问题？
1. 为什么要用@ComponentScan ？
定义一个Spring bean 一般是在类上加上注解 @Service 或@Controller 或 @Component就可以，
但是，spring怎么知道有你这个bean的存在呢？
故，我们必须告诉spring去哪里找这个bean类。
@ComponentScan就是用来告诉spring去哪里找bean类。
2. @componentscan的作用
作用：告诉Spring去扫描@componentscan指定包下所有的注解类，然后将扫描到的类装入spring bean容器。
例如：@ComponentScan("com.agan.boot.scan")，就只能扫描com.agan.boot.scan包下的注解类。
如果不写？就像@SpringBootApplication的@ComponentScan没有指定路径名？它去哪里找？
@SpringBootApplication注解的所在的包及其下级包，都会讲class扫描到并装入spring ioc容器

## 四、 案例实战：体验@ComponentScan的作用

### 步骤1：在包名为com.agan.boot.scan，新建一个ComponentScan测试类
``` 
package com.agan.boot.scan;

import org.springframework.stereotype.Service;

@Service
public class TestComponentScan {
}
```
### 步骤2：在com.agan.boot.app下面建个启动类
``` 
package com.agan.boot.app;

import com.agan.boot.scan.TestComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Application.class, args);
        TestComponentScan componentScan = run.getBean(TestComponentScan.class);
        System.out.println(componentScan.toString());
    }
}
```
启动报错：
``` 
seconds (JVM running for 3.941)
Exception in thread "main" org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'com.agan.boot.scan.TestComponentScan' available
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.getBean(DefaultListableBeanFactory.java:346)
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.getBean(DefaultListableBeanFactory.java:337)
	at org.springframework.context.support.AbstractApplicationContext.getBean(AbstractApplicationContext.java:1123)
	at com.agan.boot.app.Application.main(Application.java:14)
```
以上报错的意思是：找不到com.agan.boot.scan.TestComponentScan这个bean，那怎么办呢？
这要加这行代码重新运行即可
- 手工指定包路径
``` 
@ComponentScan("com.agan.boot.scan")
```
整体如下：
``` 
@SpringBootApplication
@ComponentScan("com.agan.boot.scan")
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Application.class, args);
        TestComponentScan componentScan = run.getBean(TestComponentScan.class);
        System.out.println(componentScan.toString());
    }
}
```
以上启动正常

## 如果不想手工指定包路径的如何做？


##五：课后练习题
在本项目的基础上，在com.agan.boot.practise包下增加以下代码
```
package com.agan.boot.practise;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @RequestMapping("/hello")
    public  String  hello() {
        return "Hello agan!";
    }
}
```
启动项目后，在浏览器中访问路径:http://127.0.0.1:9090/hello ,能正常显示内容

