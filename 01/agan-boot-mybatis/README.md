# SpringBoot集成mybatis攻略

## 一、课程目标：
1. 为什么使用 MyBatis ？
2. 为什么需要通用的tk mapper ?
3. 感受《MyBatis 代码生成器Generator》自动生成代码的快感
4. 体验不用写SQL的mybatis通用增删改查操作

## 一、什么是 MyBatis？
MyBatis 是一款优秀的持久层框架，支持常规的SQL查询，同时也支持定制化SQL、存储过程以及高级映射。
MyBatis消除了几乎所有的JDBC代码和参数的手工设置以及对结果集的检索封装。
MyBatis可以使用简单的XML或注解用于配置和原始映射，将接口和Java的POJO（Plain Old Java Objects，普通的Java对象）映射成数据库中的记录。


## 二、为什么使用 MyBatis
在我们传统的 JDBC 中，我们除了需要自己提供 SQL 外，还必须操作 Connection、Statment、ResultSet，不仅如此，为了访问不同的表，不同字段的数据，
我们需要些很多雷同模板化的代码，即繁琐又枯燥。
而我们在使用了 MyBatis 之后，只需要提供 SQL 语句就好了，其余的诸如：建立连接、操作 Statment、ResultSet，处理 JDBC 相关异常等等都可以交给
 MyBatis 去处理，我们的关注点于是可以就此集中在 SQL 语句上，关注在增删改查这些操作层面上。
并且 MyBatis 支持使用简单的 XML 或注解来配置和映射原生信息，
将接口和 Java 的 POJOs(Plain Old Java Objects,普通的 Java对象)映射成数据库中的记录。

一句话：不用写jdbc代码，只写sql就行！！！

## 四、什么是通用的tk mapper ?
在使用MyBatis 的同时，建议大家再搭配使用"通用tk Mapper4"，它是一个可以实现任意 MyBatis 通用方法的框架，
项目提供了常规的增删改查操作以及Example 相关的单表操作。通用 Mapper 是为了解决 MyBatis 使用中 90% 的基本操作，
使用它可以很方便的进行开发，可以节省开发人员大量的时间。
官方地址：https://github.com/abel533/Mapper/wiki

## 三、那为什么需要通用的tk mapper ?
mybatis最大的一个问题，就是要写大量的SQL在XML中，因为除了必须的特殊复杂的业务逻辑SQL外，还要为大量的类似增删改查SQL。
另外，当数据库表结构变更时，所有对应的SQL和实体类都要改一遍，那个痛苦啊。故，通用tk mapper应运而生。

一句话：不用写jdbc代码，同时也不用写sql ！（对于互联网公司来说，97%不是写sql,剩下3%要写多表关联查询sql）

## 四、案例实战：SpringBoot配置mybatis的步骤 
### 步骤1：pom文件引入依赖包
``` 

<!--通用spring boot mapper-->
<dependency>
    <groupId>tk.mybatis</groupId>
    <artifactId>mapper-spring-boot-starter</artifactId>
    <version>2.0.3</version>
</dependency>
<!--mysql驱动-->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>

<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.8</version>
</dependency>
```
### 步骤2：采用mybatis-generator自动生成代码
拷贝《案例实战：MyBatis 代码生成器Generator》生成的代码即可

### 步骤3：application.properties 加入mysql配置信息
``` 
#指定mapper.xml的位置
mybatis.mapper-locations=classpath*:com/agan/boot/mapper/xml/*.xml

#数据库驱动和ip
spring.datasource.driverClassName=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://192.168.0.138:3308/boot_user?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull
spring.datasource.username=root
spring.datasource.password=agan
```
特别注意：一定要配置mybatis.mapper-locations mybatis的xml路径，不然启动不了！！！

### 步骤4：配置扫描mapper类的路径包 
在启动类中，加入mybatis的mapper
@MapperScan("com.agan.boot.mapper")
如果不配的话，mybatis找不到UserMapper文件
``` 
//指定要扫描的Mapper类的包的路径
@MapperScan("com.agan.boot.mapper")
@SpringBootApplication
public class MybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisApplication.class, args);
    }

}
```
### 步骤5：测试类
UserController








