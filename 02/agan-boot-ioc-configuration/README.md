
#剖析@SpringBootConfiguration源码

##一：本课程目标：
3个目标：
1. 剖析@SpringBootConfiguration源码，掌握@SpringBootConfiguration的原理
2. 分析@Configuration的原理，并学会如何用好@Configuration ？
3. 分析@Bean的原理，并学会如何用好@Bean ?

##二：剖析@SpringBootConfiguration源码
上节课，我们已经知道了 @SpringBootApplication就是一个复合注解，
包括@ComponentScan，和@SpringBootConfiguration，@EnableAutoConfiguration。
那这节课，我们就来学习@SpringBootConfiguration
先看@SpringBootConfiguration的源码。
``` 
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration
public @interface SpringBootConfiguration {
}

```
从以上源码可以看出@SpringBootConfiguration其实就是一个@Configuration，说明了标注当前类是配置类。

##三：什么是@Configuration注解，它有什么作用？
从Spring3.0，@Configuration用于定义配置类，可替换xml配置文件，被注解的类内部包含有一个或多个被@Bean注解的方法，
这些方法将会被AnnotationConfigApplicationContext或AnnotationConfigWebApplicationContext类进行扫描，
并用于构建bean定义，初始化Spring容器。

##三：用@Configuration配置spring并加载spring容器
@Configuration标注在类上，@Configuation等价于spring的xml配置文件中的<Beans></Beans>
### 步骤1：先加入spring的依赖包
``` 
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>5.1.9.RELEASE</version>
</dependency>
```
### 步骤2：创建一个Configuration类
``` 

@Configuration
public class MyConfiguration {
    public MyConfiguration() {
        System.out.println("MyConfiguration容器启动初始化。。。");
    }

}
```
以上代码，等价于以下xml配置文件中的<Beans></Beans>
``` 
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:context="http://www.springframework.org/schema/context" xmlns:jdbc="http://www.springframework.org/schema/jdbc"  
    xmlns:jee="http://www.springframework.org/schema/jee" xmlns:tx="http://www.springframework.org/schema/tx"
    xmlns:util="http://www.springframework.org/schema/util" xmlns:task="http://www.springframework.org/schema/task" xsi:schemaLocation="
        http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.0.xsd
        http://www.springframework.org/schema/jdbc http://www.springframework.org/schema/jdbc/spring-jdbc-4.0.xsd
        http://www.springframework.org/schema/jee http://www.springframework.org/schema/jee/spring-jee-4.0.xsd
        http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-4.0.xsd
        http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util-4.0.xsd
        http://www.springframework.org/schema/task http://www.springframework.org/schema/task/spring-task-4.0.xsd" default-lazy-init="false">


</beans>
```
### 步骤3：加一个测试类
``` 
public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(MyConfiguration.class);
    }

}

```
结果：
``` 
MyConfiguration容器启动初始化。。。

Process finished with exit code 0
```



##四：如何把一个对象，注册到Spring IoC 容器中
要把一个对象注册到Spring IoC 容器中，一般是用@Bean 注解来实现

@Bean的作用：带有 @Bean 的注解方法将返回一个对象，该对象应该被注册为在Spring IoC 容器中。

### 步骤1：创建一个bean
``` 

public class UserBean {

    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
```
### 步骤2：把bean注解在ioc容器里面
``` 
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
```
上面的代码将等同于下面的 XML 配置：
``` 
<beans>
   <bean id="userBean" class="com.agan.boot.annotation.bean.UserBean" />
</beans>
```
### 步骤3：加一个体验类
创建的bean对象，可以通过AnnotationConfigApplicationContext加载进spring ioc 容器中。
``` 
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(MyConfiguration.class);
        UserBean userBean=(UserBean)context.getBean("userBean");

        System.out.println(userBean.toString());
    }
```
结果：
``` 
MyConfiguration容器启动初始化。。。
UserBean{username='agan', password='123456'}

Process finished with exit code 0
```

##五：课后练习题

把以下的UserService对象，注册到Spring IoC 容器中；然后从IoC容器中读取UserBean对象
``` 
public class UserService {
    
    public UserBean findUser(){
        UserBean userBean= new UserBean();
        userBean.setUsername("agan");
        userBean.setPassword("123456");
        return userBean;
    }
    
}
```











