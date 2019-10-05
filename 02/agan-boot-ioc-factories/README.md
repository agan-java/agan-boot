### 一、本课程目标：
1. 搞明白spring 的ImportSelector的原理 和 spring 的 spring.factories 文件是用来干嘛的？
2. @EnableAutoConfiguration 如何通过 spring.factories 来实现bean的注册？
3. 动手编码练习：自定义一个的spring.factories文件



### 二、解密spring 的ImportSelector和spring.factories原理
找到springboot的 selectImports()源码 ，如下：

``` 
@Override
public Iterable<Entry> selectImports() {
    if (this.autoConfigurationEntries.isEmpty()) {
        return Collections.emptyList();
    }
    Set<String> allExclusions = this.autoConfigurationEntries.stream()
            .map(AutoConfigurationEntry::getExclusions).flatMap(Collection::stream).collect(Collectors.toSet());
    Set<String> processedConfigurations = this.autoConfigurationEntries.stream()
            .map(AutoConfigurationEntry::getConfigurations).flatMap(Collection::stream)
            .collect(Collectors.toCollection(LinkedHashSet::new));
    processedConfigurations.removeAll(allExclusions);

    return sortAutoConfigurations(processedConfigurations, getAutoConfigurationMetadata()).stream()
            .map((importClassName) -> new Entry(this.entries.get(importClassName), importClassName))
            .collect(Collectors.toList());
}
```
重点分析
Set<String> processedConfigurations = this.autoConfigurationEntries.stream()
					.map(AutoConfigurationEntry::getConfigurations).flatMap(Collection::stream)
先看 this.autoConfigurationEntries是怎么来的？它是一个ArrayList

private final List<AutoConfigurationEntry> autoConfigurationEntries = new ArrayList<>();

思考问题：
autoConfigurationEntries是怎么赋值的？
在process(）方法里面找到了答案
``` 
public void process(AnnotationMetadata annotationMetadata, DeferredImportSelector deferredImportSelector) {
    Assert.state(deferredImportSelector instanceof AutoConfigurationImportSelector,
            () -> String.format("Only %s implementations are supported, got %s",
                    AutoConfigurationImportSelector.class.getSimpleName(),
                    deferredImportSelector.getClass().getName()));
    //收集要注册到spring ioc 的class
    AutoConfigurationEntry autoConfigurationEntry = ((AutoConfigurationImportSelector) deferredImportSelector)
            .getAutoConfigurationEntry(getAutoConfigurationMetadata(), annotationMetadata);
    //把收集到的class 填进autoConfigurationEntries
    this.autoConfigurationEntries.add(autoConfigurationEntry);
    for (String importClassName : autoConfigurationEntry.getConfigurations()) {
        this.entries.putIfAbsent(importClassName, annotationMetadata);
    }
}
```
看到这里 我们继续看getAutoConfigurationEntry(getAutoConfigurationMetadata(), annotationMetadata);

``` 
protected AutoConfigurationEntry getAutoConfigurationEntry(AutoConfigurationMetadata autoConfigurationMetadata,
        AnnotationMetadata annotationMetadata) {
    if (!isEnabled(annotationMetadata)) {
        return EMPTY_ENTRY;
    }
    AnnotationAttributes attributes = getAttributes(annotationMetadata);
    //收集到 要注册的 spring ioc 的 class
    List<String> configurations = getCandidateConfigurations(annotationMetadata, attributes);
    configurations = removeDuplicates(configurations);
    Set<String> exclusions = getExclusions(annotationMetadata, attributes);
    checkExcludedClasses(configurations, exclusions);
    configurations.removeAll(exclusions);
    configurations = filter(configurations, autoConfigurationMetadata);
    fireAutoConfigurationImportEvents(configurations, exclusions);
    return new AutoConfigurationEntry(configurations, exclusions);
}
```
继续看 getCandidateConfigurations(annotationMetadata, attributes);这行代码，源码如下：
``` 
protected List<String> getCandidateConfigurations(AnnotationMetadata metadata, AnnotationAttributes attributes) {
    List<String> configurations = SpringFactoriesLoader.loadFactoryNames(getSpringFactoriesLoaderFactoryClass(),
            getBeanClassLoader());
    Assert.notEmpty(configurations, "No auto configuration classes found in META-INF/spring.factories. If you "
            + "are using a custom packaging, make sure that file is correct.");
    return configurations;
}
```
继续跟进 SpringFactoriesLoader.loadFactoryNames
``` 
public static List<String> loadFactoryNames(Class<?> factoryClass, @Nullable ClassLoader classLoader) {
    String factoryClassName = factoryClass.getName();
    return loadSpringFactories(classLoader).getOrDefault(factoryClassName, Collections.emptyList());
}
```
继续跟进loadSpringFactories(classLoader)

``` 
private static Map<String, List<String>> loadSpringFactories(@Nullable ClassLoader classLoader) {
    MultiValueMap<String, String> result = cache.get(classLoader);
    if (result != null) {
        return result;
    }

    try {
    //重要发现，它去加载配置文件FACTORIES_RESOURCE_LOCATION
        Enumeration<URL> urls = (classLoader != null ?
                classLoader.getResources(FACTORIES_RESOURCE_LOCATION) :
                ClassLoader.getSystemResources(FACTORIES_RESOURCE_LOCATION));
        result = new LinkedMultiValueMap<>();
        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            UrlResource resource = new UrlResource(url);
            Properties properties = PropertiesLoaderUtils.loadProperties(resource);
            for (Map.Entry<?, ?> entry : properties.entrySet()) {
                String factoryClassName = ((String) entry.getKey()).trim();
                for (String factoryName : StringUtils.commaDelimitedListToStringArray((String) entry.getValue())) {
                    result.add(factoryClassName, factoryName.trim());
                }
            }
        }
        cache.put(classLoader, result);
        return result;
    }
    catch (IOException ex) {
        throw new IllegalArgumentException("Unable to load factories from location [" +
                FACTORIES_RESOURCE_LOCATION + "]", ex);
    }
}
```
以上代码 有个重大发现，它去加载了配置文件FACTORIES_RESOURCE_LOCATION，故我们只要知道这个FACTORIES_RESOURCE_LOCATION配置文件用来干嘛的？
就能直接解开谜语了，对吧 ？

```

public final class SpringFactoriesLoader {

	/**
	 * The location to look for factories.
	 * <p>Can be present in multiple JAR files.
	 */
	public static final String FACTORIES_RESOURCE_LOCATION = "META-INF/spring.factories";
```
spring.factories是springboot 的解耦扩展机制，这种机制实际上是仿照了java的SPI扩展机制来实现的。
那什么是JAVA  SPI呢？
关于java  spi 大家可以看下阿甘老师以前的课程《深度解剖dubbo源码》。

你可以在META-INF/spring.factories文件里面配置你自己的实现类名称，然后spring会读取spring.factories文件的内容，并实例化进IOC容器。
我们一起来看下  spring.factories文件有哪些东西？
在 spring-boot-autoconfigure-2.1.8.RELEASE.jar的META-INF/spring.factories
```
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration,\
org.springframework.boot.autoconfigure.aop.AopAutoConfiguration,\
org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration,\
org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration,\
org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration,\
org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration,\
org.springframework.boot.autoconfigure.cloud.CloudServiceConnectorsAutoConfiguration,\
org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration,\
org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration,\
org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration,\
org.springframework.boot.autoconfigure.couchbase.CouchbaseAutoConfiguration,\
org.springframework.boot.autoconfigure.dao.PersistenceExceptionTranslationAutoConfiguration,\
org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.cassandra.CassandraReactiveDataAutoConfiguration,\
```
以上最终的目的就是把spring.factories里面的class加载到spring ioc容器中。


### 三、案例实战：自己动手编码实现的spring.factories文件
只要在src/main/resource目录下的META-INF创建spring.factories文件即可

#### 步骤1：新建一个@Configuration配置类
注意：包名package agan.core;
``` 
public class Agan {
    public String info(){
        return " teacher";
    }
}

@Configuration
public class AganConfig {

    @Bean
    public Agan agan(){
        return new Agan();
    }
}
```
#### 步骤2：新建spring.factories
在src/main/resource目录下的META-INF创建spring.factories文件即可
``` 
org.springframework.boot.autoconfigure.EnableAutoConfiguration=agan.core.AganConfig
```
#### 步骤3：体验类
``` 
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

```
结果：
``` 
 teacher
```


### 四、总结@SpringBootApplication启动原理
以上所有注解就只干一件事：把bean注册到spring ioc容器。
通过4种方式来实现：
1. @SpringBootConfiguration 通过@Configuration 与@Bean结合，注册到Spring ioc 容器。
2. @ComponentScan  通过范围扫描的方式，扫描特定注解类，将其注册到Spring ioc 容器。
3. @Import 通过导入的方式，将指定的class注入到spring ioc容器里面 。
4. @EnableAutoConfiguration 通过spring.factories的配置，来实现bean的注册到Spring ioc 容器。


###五：课后练习题
参考本课程的代码，建2个maven工程，一个是school工程，另一个是student工程
1.student工程，只有一个类，如下：
``` 
package com.student.demo;

public class Student {
    public String info(){
        return "student";
    }
}
```
2.school工程就一个启动类
``` 
package com.school.demo;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {

		ConfigurableApplicationContext run = SpringApplication.run(Application.class, args);
		Student bean = run.getBean(Student.class);
		System.out.println(bean.info());
	}

```
把以上2个工程，采用spring.factories整合在一起，使school工程能正常运行。
（注意：以上2个类的包名别改，就按我的要求建类名和包名）









