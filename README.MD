### 漫谈SpringBoot与SpringCloud分布式服务架构
	01 SpringBoot 入门
		1.为什么越来越多的开发者选择使用SpringBoot？它解决了什么问题？
		2.SpringBoot的入门例子
		3.SpringBoot 常用配置
		4.SpringBoot 自定义配置
		5.SpringBoot的yml配置详解
		大厂面试题：请讲下SpringBoot的日志框架原理？
		配置日志的存储路径、设置日志的格式
	02 在springboot中使用lombok
		为什么要使用lombok,它解决了什么问题？
		idea安装lombok插件
		案例实战：体验lombok的第一大功能@Data注解
		案例实战：体验lombok的第二大功能@Slf4j注解
	03 Spring Boot 异步框架
		为什么要用异步框架，它解决什么问题？
		案例实战：SpringBoot异步调用
		案例实战：为@Async实现一个自定义线程池
	04 用swagger为SpringBoot生成接口文档
		为什么要用swagger,它解决了什么问题？
		案例实战：把springboot的接口，自动生成接口文档
	05 SpringBoot《接口返回值统一标准格式》
		为什么要对springboot的接口返回值统一标准格式?
		初级程序员对《接口返回值统一标准格式》的代码封装
		高级程序员对《接口返回值统一标准格式》的代码封装
	06 SpringBoot《全局异常处理器》
		springboot为什么需要全局异常处理器？
		案例实战：编码实现一个springboot《全局异常处理器》
		案例实战：把自定义异常 集成 进《全局异常处理器》
		案例实战：把《全局异常处理器》集成进《接口返回值统一标准格式》
	07 SpringBoot《参数校验器》:Validator
		为什么要用Validator参数校验器，它解决了什么问题？
		案例实战：实现一个SpringBoot的参数校验功能
		案例实战：根据需求自定义一个validator注解
		案例实战：把validator异常加入《全局异常处理器》
	08 SpringBoot《参数校验器》:Assert
		什么是Assert参数校验？
		为什么用了Validator参数校验，还必须再用Assert参数校验？
		案例实战：修改用户信息时，用Assert校验用户是否存在？
		常用的Assert场景
		案例实战：把Assert异常加入《全局异常处理器》
	09 SpringBoot安全:接口攻击
		为什么API接口是不安全的？
		看看淘宝的API接口安全是怎么做的？
		什么是接口篡改攻击？
		大厂面试题：接口如何实现请求内容防止篡改？
		什么是重放攻击？
		大厂面试题：接口如何实现请求内容防止重放？
		案例实战：SpringBoot的签名保护，防篡改攻击
		案例实战：SpringBoot的签名保护，防重放攻击
	10 SpringBoot安全 : guava限流
		互联网系统为什么要限流？
		那什么是限流？如何实现？
		重点讲解令牌桶算法
		案例实战：采用guava实现SpringBoot限流
		案例实战：启用自定义注解实现接口限流
	11 SpringBoot安全 : hystrix限流
		1.一场可怕的灾难性雪崩效应
		2.雪崩的6种解决方案
			如何解决灾难性雪崩效应？
			解决方案一：服务降级
			解决方案二：服务请求缓存
			解决方案三：服务请求合并
			解决方案四：服务熔断
			解决方案五：隔离技术之线程池隔离
			解决方案六：隔离技术之信号量隔离
			线程池隔离 和 信号量隔离，有什么区别？
			什么情况下，用线程池隔离？
			什么情况下，用信号量隔离？
		feign的雪崩处理
			服务降级处理
			服务降级后的异常记录
		如何预防雪崩？
			一种可视化的数据监控dashboard
			如何在集群条件下，收集监控数据？
			采用RabbitMQ，收集监控数据
	12 SpringBoot安全 : sentinel限流
		1.为什么要用sentinel ?它和hystrix有什么区别？
		2.搭建sentinel dashboard工作台
		3.sentinel实战例子
	13 SpringBoot源码剖析
		解密@SpringBootApplication启动原理
		解密@SpringBootConfiguration
		剖析@SpringBootConfiguration秘密
		大厂面试题：@Configuration有什么作用？
		案例实战：如何把一个对象，注册到Spring IoC 容器中
		解密@ComponentScan
		剖析springboot的@ComponentScan秘密
		为什么要用@ComponentScan？它解決什么问题？
		案例实战：体验@ComponentScan的作用
		解密@EnableAutoConfiguration
		剖析springboot的@EnableAutoConfiguration秘密
		大厂面试题：讲下@Import有什么作用？
		大厂面试题：讲下spring的ImportSelector接口有什么作用？
		案例实战： 编写一个@Enable的开关注解
		解密spring 的ImportSelector和spring.factories原理
		案例实战：自己动手编码实现的spring.factories文件
	14 SpringBoot集成mybatis攻略
		什么是 MyBatis？
		为什么使用 MyBatis？
		什么是通用的tk mapper ?
		那为什么需要通用的tk mapper ?
		案例实战：MyBatis 代码生成器Generator
		案例实战：SpringBoot配置mybatis的步骤 
		案例实战：mybatis如何实现不用写SQL的增删改操作？
		案例实战：mybatis如何实现不用写SQL的复杂查询操作？
		案例实战：mybatis如何实现不用写SQL的分页查询操作？
	15 SpringBoot采用druid实现多数据源
		什么是druid，它解决了什么问题？
		案例实战：springboot+mybatis+druid实现多数据源
		多数据源超级大坑，要特别注意分布式事务
	16 SpringBoot实战Rabbit MQ
		如何安装Rabbit MQ ?
		为什么要使用Rabbit MQ？它解决什么问题？
		编写Rabbit MQ的入门例子
		Rabbit MQ通信为什么需要信道？为什么不是TCP直接通信？
		交换器的实现方式
		采用spring boot实现direct交换器消息流
		采用spring boot实现topic交换器消息流
		采用spring boot实现fanout交换器消息流
		消息处理
		消息丢了怎么办？那就持久化它！
		如何让消息安全到达目的地？
	17 微服务架构基础
		为什么要用微服务？
			什么是单体架构
			单体架构有什么特点？
			什么是微服务？
			微服务的优缺点
			MVC、 RPC、SOA 与微服务的架构区别？
		如何设计微服务？它设计原则是什么？
	18 微服务SpringCloud 
		为什么要选择spring cloud?
		什么是spring cloud?
		spring cloud 和dubbo的区别？
		为什么springcloud要设计一套新的版本升级规则？
		springcloud的版本规则，和它的设计目的
		Springcloud与17个子项目的版本兼容说明
	19 注册中心 eureka
		微服务架构为什么需要注册中心，它解决了什么问题？
			什么是注册中心？
			为什么需要注册中心？它解决了什么问题？
			什么是注册中心Eureka？
		集群高可用的Eureka
			一个Eureka注册中心的入门例子
			建设高可用集群版的注册中心Eureka
			在高可用的Eureka上，构建provider服务
			在高可用的Eureka上，构建consumer服务
		深入剖析Eureka
			剖析注册中心eureka的架构原理
			基于分布式CAP定理，分析注册中心2大主流框架：Eureka和ZooKeeper的区别？
		优雅停服
			在什么条件下，Eureka会启动自我保护？为什么要启动自我保护？
			如何优雅停止服务？并快速安全的让Eureka注销服务？
		如何加强Eureka注册中心的安全认证？
		
		
		
### 课程地址：
一：《Redis一线互联网实战系列课程》
1：Redis与SpringBoot一线互联网实战①
https://study.163.com/course/introduction/1209618839.htm?share=1&shareId=1028852228

2：Redis与SpringBoot一线互联网实战 ②
https://study.163.com/course/introduction/1209654807.htm?share=1&shareId=1028852228

3：Redis与SpringBoot一线互联网实战③
https://study.163.com/course/introduction/1209678836.htm?share=1&shareId=1028852228

4：Redis与SpringBoot一线互联网实战④
https://study.163.com/course/introduction/1209750851.htm?share=1&shareId=1028852228

5：Redis与SpringBoot一线互联网实战⑤
https://study.163.com/course/introduction/1209867819.htm?share=1&shareId=1028852228

6：Spring源码分析：Spring与Redis网络通信原理剖析⑥
https://study.163.com/course/introduction/1210203966.htm?share=1&shareId=1028852228

二：《SpringCloud 实战篇系列课程》
1：SpringCloud 实战篇(基础)：漫谈SpringCloud与SpringBoot微服务架构
  https://study.163.com/course/introduction/1004348001.htm?share=1&shareId=1028852228

2：SpringCloud 实战篇(进阶)：漫谈SpringCloud分布式服务架构
  https://study.163.com/course/introduction/1004721019.htm?share=1&shareId=1028852228

3：SpringCloud 自动化部署实战篇：漫谈SpringCloud实战 Kubernetes/k8s/docker 分布式架构
  https://study.163.com/course/introduction/1005993007.htm?share=1&shareId=1028852228

三：《SpringCloud分布式事务实战系列课程》
1：SpringCloud分布式事务实战(基础)：刚性事务实战
  https://study.163.com/course/introduction/1209240846.htm?share=1&shareId=1028852228

2：SpringCloud分布式事务实战(进阶)：淘宝下订单实战
  https://study.163.com/course/introduction/1209367806.htm?share=1&shareId=1028852228

3：SpringCloud分布式事务实战(高级)：淘宝支付实战
   https://study.163.com/course/introduction/1209227818.htm?share=1&shareId=1028852228

四：《深入剖析dubbo源码的设计原理》
   https://study.163.com/course/introduction/1003828012.htm?share=1&shareId=1028852228


系列课程打包
一：《Java微服务架构攻略》 https://study.163.com/series/1001469005.htm?share=1&shareId=1028852228
二：《分布式事务攻略》 https://study.163.com/series/1202843601.htm?share=1&shareId=1028852228
三：《Redis一线互联网实战》 https://study.163.com/series/1202877601.htm?share=1&shareId=1028852228
四：《java架构师》  https://study.163.com/series/1202811603.htm?share=1&shareId=1028852228

### 学习交流
在学习的过程中有任何困难，都可以直接加阿甘老师微信：agan-java 交流探讨。