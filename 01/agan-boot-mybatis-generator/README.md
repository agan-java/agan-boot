# MyBatis 代码生成器


## 一、什么是MyBatis 代码生成器？
MyBatis Generator（简称为：MyBatis 代码生成器） 是MyBatis 官方出品的一款，用来自动生成MyBatis的 mapper、xml、entity 的框架，
让程序员在开发的过程中省去很多重复的工作。
操作非常简单，只要在配置文件中，配置好要生成的表名和包名，然后运行命令，就能自动生成mapper、xml、entity 等一堆文件。

官网地址：http://www.mybatis.org/generator/

## 二、MyBatis Generator搭建步骤
### 步骤1:新建数据库
``` 

CREATE DATABASE `boot_user` /*!40100 DEFAULT CHARACTER SET utf8 */;
use boot_user;

CREATE TABLE `users` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(50) NOT NULL DEFAULT '' COMMENT '密码',
  `sex` tinyint(4) NOT NULL DEFAULT '0' COMMENT '性别 0=女 1=男 ',
  `deleted` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '删除标志，默认0不删除，1删除',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户表';

SET FOREIGN_KEY_CHECKS = 1;

```
### 步骤2:pom.xml依赖包
1. pom.xml的插件
加入 mybatis-generator-maven-plugin
``` 
    <build>
        <resources>
            <resource>
                <directory>${basedir}/src/main/java</directory>
                <includes>
                    <include>**/*.xml</include>
                </includes>
            </resource>
            <resource>
                <directory>${basedir}/src/main/resources</directory>
            </resource>
        </resources>
        <plugins>
            <plugin>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <source>${jdk.version}</source>
                    <target>${jdk.version}</target>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.mybatis.generator</groupId>
                <artifactId>mybatis-generator-maven-plugin</artifactId>
                <version>1.3.6</version>
                <configuration>
                    <configurationFile>${basedir}/src/main/resources/generatorConfig.xml</configurationFile>
                    <overwrite>true</overwrite>
                    <verbose>true</verbose>
                </configuration>
                <dependencies>
                    <dependency>
                        <groupId>mysql</groupId>
                        <artifactId>mysql-connector-java</artifactId>
                        <version>${mysql.version}</version>
                    </dependency>
                    <dependency>
                        <groupId>tk.mybatis</groupId>
                        <artifactId>mapper</artifactId>
                        <version>${mapper.version}</version>
                    </dependency>
                </dependencies>
            </plugin>
        </plugins>
    </build>
```
2. pom.xml加入依赖包

``` 
    <dependencies>
        <!-- Mybatis Generator -->
        <dependency>
            <groupId>org.mybatis.generator</groupId>
            <artifactId>mybatis-generator-core</artifactId>
            <version>1.3.2</version>
            <scope>compile</scope>
            <optional>true</optional>
        </dependency>
        <!--通用Mapper-->
        <dependency>
            <groupId>tk.mybatis</groupId>
            <artifactId>mapper</artifactId>
            <version>${mapper.version}</version>
        </dependency>
    </dependencies>
```
### 步骤3:2个核心的配置文件
resources配置文件 config.properties
重点要注意：生成的包名
``` 
# 生成的包名
package.name=com.agan.boot

# 数据库配置信息
jdbc.driverClass = com.mysql.jdbc.Driver
jdbc.url = jdbc:mysql://192.168.0.138:3308/boot_user
jdbc.user = root
jdbc.password =agan

```

最核心的配置文件generatorConfig.xml

``` 
<?xml version="1.0" encoding="UTF-8"?>


<!DOCTYPE generatorConfiguration
        PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">

<generatorConfiguration>
    <properties resource="config.properties"/>
    <!-- 配置对象环境
    context的targetRuntime属性设置为MyBatis3Simple是为了避免生成Example相关的代码和方法。如果需要则改为Mybatis3.
    defaultModelType="flat"目的是使每个表只生成一个实体类
     -->
    <context id="Mysql" targetRuntime="MyBatis3Simple" defaultModelType="flat">
        <!-- 配置起始与结束标识符, 数据库使用mysql,所以前后的分隔符都设为”`” -->
        <property name="beginningDelimiter" value="`"/>
        <property name="endingDelimiter" value="`"/>
        <!--添加自定义的继承接口-->
        <plugin type="tk.mybatis.mapper.generator.MapperPlugin">
            <property name="mappers" value="tk.mybatis.mapper.common.Mapper"/>
            <property name="caseSensitive" value="true"/>
        </plugin>
        <!--jdbc的数据库连接 -->
        <jdbcConnection driverClass="${jdbc.driverClass}"
                        connectionURL="${jdbc.url}"
                        userId="${jdbc.user}"
                        password="${jdbc.password}">
        </jdbcConnection>

        <!-- 配置生成的实体类位置
            targetPackage     指定生成的model生成所在的包名
            targetProject     指定在该项目下所在的路径
        -->
        <javaModelGenerator targetPackage="${package.name}.entity" targetProject="src/main/java"/>
        <!--xml 映射文件生成的位置 -->
        <sqlMapGenerator targetPackage="${package.name}.mapper.xml" targetProject="src/main/java"/>
        <!-- mapper接口生成的位置 -->
        <javaClientGenerator targetPackage="${package.name}.mapper" targetProject="src/main/java" type="XMLMAPPER"/>


        <!-- 设置数据库表名 -->
        <table tableName="temp" domainObjectName="User">
            <generatedKey column="id" sqlStatement="JDBC"/>
        </table>
    </context>
</generatorConfiguration>
```
以上配置 ，一定要修改以下内容
``` 
<!-- 设置数据库表名 -->
<table tableName="temp" domainObjectName="User">
    <generatedKey column="id" sqlStatement="JDBC"/>
</table>
```
### 步骤4:运行generator插件
双击 mybatis-generator:generate

特别注意：




