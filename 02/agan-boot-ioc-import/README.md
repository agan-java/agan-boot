#剖析springboot的@EnableAutoConfiguration注解

##一：本课程目标：
1. 学习springboot的核心注解@EnableAutoConfiguration，并掌握其中原理
2. 学习@Import有什么作用？编码实现一个@Import例子。
3. 模仿@EnableAutoConfiguration的原理，自己编码设计一个@Enable*的开关注解

##二：解读@EnableAutoConfiguration
@EnableAutoConfiguration是@SpringBootApplication中3大核心注解最重要的一个。
源码如下：
``` 
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@AutoConfigurationPackage
@Import(AutoConfigurationImportSelector.class)
public @interface EnableAutoConfiguration {

	String ENABLED_OVERRIDE_PROPERTY = "spring.boot.enableautoconfiguration";

	/**
	 * Exclude specific auto-configuration classes such that they will never be applied.
	 * @return the classes to exclude
	 */
	Class<?>[] exclude() default {};

	/**
	 * Exclude specific auto-configuration class names such that they will never be
	 * applied.
	 * @return the class names to exclude
	 * @since 1.3.0
	 */
	String[] excludeName() default {};

}
```
其中最关键的是@Import(AutoConfigurationImportSelector.class)，我们先来讲解@Import

## 三、大厂面试题：讲下@Import有什么作用？
@Import作用： 将指定的类实例注入到spring IOC容器中。

## 四、编码实现@Import例子
### 步骤1：创建一个bean
创建这个bean的目的是把它注入springioc容器中
``` 
public class UserBean {

}
```

### 步骤2：新建一个service
采用@Import来，将UserBean注入到spring ioc 容器中
``` 
@Component
@Import({UserBean.class})
public class UserService {


}

```
### 步骤3：启动类
``` 

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(UserService.class);
        UserService userService = context.getBean(UserService.class);
        UserBean userBean=context.getBean(UserBean.class);

        System.out.println(userService.toString());
        System.out.println(userBean.toString());
    }

}

```
结果：
```
com.agan.boot.ioc.imports.UserService@52525845
com.agan.boot.ioc.imports.UserBean@3b94d659
```


## 五、大厂面试题：讲下spring的ImportSelector接口有什么作用？
从AutoConfigurationImportSelector源码，进入后，发现了6个核心接口，如下：
``` 
public class AutoConfigurationImportSelector implements DeferredImportSelector, BeanClassLoaderAware,
		ResourceLoaderAware, BeanFactoryAware, EnvironmentAware, Ordered 
```
但是最核心的是DeferredImportSelector接口。最核心的！！！

从DeferredImportSelector接口的源码中，看出了它继承了ImportSelector，源码如下：
``` 
public interface DeferredImportSelector extends ImportSelector {
```
再看ImportSelector的源码
``` 
public interface ImportSelector {

	/**
	 * Select and return the names of which class(es) should be imported based on
	 * the {@link AnnotationMetadata} of the importing @{@link Configuration} class.
	 */
	String[] selectImports(AnnotationMetadata importingClassMetadata);

}
```
从以上源码可以看出：
ImportSelector接口值定义了一个selectImports方法，它的作用收集需要将class注册到spring ioc容器里面。
ImportSelector接口一般和@Import一起使用，一般用@Import会引入ImportSelector实现类后，会把实现类中得返回class数组都注入到spring ioc 容器中。

## 五、案例实战： 模仿@EnableAutoConfiguration注解，写一个@Enable*的开关注解
很多开关注解类，例如：@EnableAsync 、@EnableSwagger2、@EnableAutoConfiguration
@Enable代表的意思就是开启一项功能，起到了开关的作用。
这些开关注解类的原理是什么？
底层是用ImportSelector接口来实现的。

### 步骤1： 新建2个bean
`` 
public class UserBean {

}

public class RoleBean {
}
``
### 步骤2：自定义一个ImportSelector类，记得实现ImportSelector接口
通过ImportSelector的selectImports方法，返回2个calass
"com.agan.boot.ioc.selector.UserBean"
"com.agan.boot.ioc.selector.RoleBean"
目的：将收集到的2个class注册到spring ioc容器里面
``` 
public class UserImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{"com.agan.boot.ioc.selector.UserBean",
        "com.agan.boot.ioc.selector.RoleBean"};
    }
}
```
### 步骤3：自定义一个开关类
一般采用@Import会引入ImportSelector实现类(UserImportSelector.class)后，
会把实现类中得返回class数组
new String[]{"com.agan.boot.ioc.selector.UserBean",
        "com.agan.boot.ioc.selector.RoleBean"};
都注入到spring ioc 容器中。
``` 
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.TYPE)
@Import(UserImportSelector.class)
public @interface EnableUserConfig {
}

```

### 步骤4：增加一个配置类，用于设置加入@EnableUserConfig
``` 

@EnableUserConfig
public class UserConfig {

}

```

### 步骤5：体验测试类
``` 

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(UserConfig.class);
        RoleBean roleBean = context.getBean(RoleBean.class);
        UserBean userBean = context.getBean(UserBean.class);

        System.out.println(userBean.toString());
        System.out.println(roleBean.toString());
    }

}

```
执行结果：
``` 
com.agan.boot.ioc.selector.UserBean@6e2c9341
com.agan.boot.ioc.selector.RoleBean@32464a14
```

## 六、课后练习题：

模仿@EnableAutoConfiguration、@EnableAsync 、@EnableSwagger2注解的原理
自己编码设计一个@Enable*的开关注解。

















