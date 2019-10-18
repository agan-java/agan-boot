

# 什么是druid，它解决了什么问题？

Druid是阿里巴巴开源平台上的一个项目，整个项目由数据库连接池、插件框架和SQL解析器组成。
druid主要是为了解决JDBC的一些限制，可以让程序员实现一些特殊的需求，比如向密钥服务请求凭证、统计SQL信息、
SQL性能收集、SQL注入检查、SQL翻译等，程序员可以通过定制来实现自己需要的功能。
官方地址：https://github.com/alibaba/druid
为监控而生的数据库连接池,重点是有个后台监控系统：http://localhost:9090/druid

# 采用druid实现多数据源
## 本案例的多数据源业务场景介绍
在编码之前，我们先来讲解我们这个例子的业务场景。
就用一个微信发红包大家都熟悉来讲解，你用余额给你的好友发了10元红包，
假如：余额和红包分别是2个独立数据库。
这个时候你的余额数据库对应的余额就应该减10元，你的好友的红包数据库就应该加10元。
## 步骤1：建立2个数据库
``` 

CREATE DATABASE `xa_account` /*!40100 DEFAULT CHARACTER SET utf8 */;
use xa_account;


CREATE TABLE `capital_account` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL COMMENT '用户ID',
	`balance_amount` decimal(10,0) DEFAULT '0' COMMENT '账户余额',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='账户信息表';
INSERT INTO `capital_account` (`id`,`user_id`,`balance_amount`) VALUES (1,1,2000);






CREATE DATABASE `xa_red_account` /*!40100 DEFAULT CHARACTER SET utf8 */;
use xa_red_account;

CREATE TABLE `red_packet_account` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL COMMENT '用户ID',
	`balance_amount` decimal(10,0) DEFAULT '0' COMMENT '账户余额',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='红包账户信息表';
INSERT INTO `red_packet_account` (`id`,`user_id`,`balance_amount`) VALUES (1,2,1000);
```

## 步骤2：pom文件加入依赖包

``` 
<!--通用spring boot mapper-->
<dependency>
    <groupId>tk.mybatis</groupId>
    <artifactId>mapper-spring-boot-starter</artifactId>
    <version>2.0.3</version>
</dependency>
<!--druid数据库连接池-->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid-spring-boot-starter</artifactId>
    <version>1.1.18</version>
</dependency>
<!--mysql驱动-->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>
```
## 步骤3：修改配置文件
application.properties 必须配置2个数据源 
``` 

# 数据源配置
# 数据源 account
spring.datasource.druid.account.url=jdbc:mysql://192.168.0.138:3308/xa_account?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=UTC
spring.datasource.druid.account.username=root
spring.datasource.druid.account.password=agan
spring.datasource.druid.account.driver-class-name=com.mysql.jdbc.Driver
#初始化连接大小:连接池建立时创建的初始化连接数
spring.datasource.druid.account.initial-size=5
#最小空闲连接数:连接池中最小的活跃连接数
spring.datasource.druid.account.min-idle=15
#最大连接数:连接池中最大的活跃连接数
spring.datasource.druid.account.max-active=60
spring.datasource.druid.account.validation-query=SELECT 1
#获取连接时检测：是否在获得连接后检测其可用性
spring.datasource.druid.account.test-on-borrow=true
#空闲时检测：是否在连接空闲一段时间后检测其可用性
spring.datasource.druid.account.test-while-idle=true
#连接保持空闲而不被驱逐的最长时间
spring.datasource.druid.account.time-between-eviction-runs-millis=60000


#数据源 redpacket
spring.datasource.druid.redpacket.url=jdbc:mysql://192.168.0.138:3308/xa_red_account?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=UTC
spring.datasource.druid.redpacket.username=root
spring.datasource.druid.redpacket.password=agan
spring.datasource.druid.redpacket.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.druid.redpacket.initial-size=5
spring.datasource.druid.redpacket.min-idle=15
spring.datasource.druid.redpacket.max-active=60
spring.datasource.druid.redpacket.validation-query=SELECT 1
spring.datasource.druid.redpacket.test-on-borrow=true
spring.datasource.druid.redpacket.test-while-idle=true
spring.datasource.druid.redpacket.time-between-eviction-runs-millis=60000


# 合并多个datasource监控
spring.datasource.druid.use-global-data-source-stat=true

#配置druid显示监控统计信息
#开启Druid的监控平台 http://localhost:9090/druid
#1. StatViewServlet配置，说明请参考Druid Wiki，配置_StatViewServlet配置
spring.datasource.druid.stat-view-servlet.enabled=true
spring.datasource.druid.stat-view-servlet.url-pattern=/druid/*
spring.datasource.druid.stat-view-servlet.reset-enable=false
spring.datasource.druid.stat-view-servlet.login-username=admin
spring.datasource.druid.stat-view-servlet.login-password=agan
#spring.datasource.druid.stat-view-servlet.allow=
#spring.datasource.druid.stat-view-servlet.deny=

#2. WebStatFilter配置，说明请参考Druid Wiki，配置_配置WebStatFilter
spring.datasource.druid.web-stat-filter.enabled=true
spring.datasource.druid.web-stat-filter.url-pattern=/*
spring.datasource.druid.web-stat-filter.exclusions=*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*
#spring.datasource.druid.web-stat-filter.session-stat-enable=
#spring.datasource.druid.web-stat-filter.session-stat-max-count=
#spring.datasource.druid.web-stat-filter.principal-session-name=
#spring.datasource.druid.web-stat-filter.principal-cookie-name=
#spring.datasource.druid.web-stat-filter.profile-enable=

#3. Spring监控配置，说明请参考Druid Github Wiki，配置_Druid和Spring关联监控配置
#spring.datasource.druid.aop-patterns= # Spring监控AOP切入点，如x.y.z.service.*,配置多个英文逗号分隔


#Spring Boot2.1以上 默认禁用那种bean覆盖(作用 用于兼容低版本)
spring.main.allow-bean-definition-overriding=true
```
## 步骤4：将配置信息，注入druid
1. 配置2个数据源DataSource
``` 

@Configuration
@EnableConfigurationProperties
@EnableTransactionManagement(proxyTargetClass = true)
public class MybatisConfiguration {
    /**
     * account数据库配置前缀.
     */
    final static String ACCOUNT_PREFIX = "spring.datasource.druid.account";
    /**
     * redpacket数据库配置前缀.
     */
    final static String REDPACKET_PREFIX = "spring.datasource.druid.redpacket";

    /**
     * 配置Account数据库的数据源
     *
     * @return the data source
     */
    @Bean(name = "AccountDataSource")
    @ConfigurationProperties(prefix = ACCOUNT_PREFIX)  // application.properties中对应属性的前缀
    public DataSource accountDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 配置RedPacket数据库的数据源
     *
     * @return the data source
     */
    @Bean(name = "RedPacketDataSource")
    @ConfigurationProperties(prefix = REDPACKET_PREFIX)  // application.properties中对应属性的前缀
    public DataSource redPacketDataSource() {
        return DruidDataSourceBuilder.create().build();
    }
}
```

2.有了数据源，就要配置数据源的sessionFactory
配置account数据源的sessionFactory
``` 
@Configuration
@MapperScan(basePackages = {"com.agan.boot.mapper.account.mapper"}, sqlSessionFactoryRef = "accountSqlSessionFactory")
public class AccountDataSourceConfiguration {
    /**
     * mybatis的xml文件.
     */
    public static final String MAPPER_XML_LOCATION = "classpath*:com/agan/boot/mapper/account/mapper/*.xml";

    @Autowired
    @Qualifier("AccountDataSource")
    DataSource accountDataSource;

    /**
     * 配置Sql Session模板
     */
    @Bean
    public SqlSessionTemplate springSqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(accountSqlSessionFactory());
    }

    /**
     * 配置SQL Session工厂
     */
    @Bean
    public SqlSessionFactory accountSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(accountDataSource);
        //指定XML文件路径
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_XML_LOCATION));
        return factoryBean.getObject();
    }

    /**
     *  配置事务
     */
    @Bean(name="transactionManager")
    public DataSourceTransactionManager transactionManager(){
        return new DataSourceTransactionManager(accountDataSource);
    }
}
```

配置redaccount数据源的sessionFactory
``` 
@Configuration
@MapperScan(basePackages = {"com.agan.boot.mapper.redaccount.mapper"}, sqlSessionFactoryRef = "redPacketSqlSessionFactory")
public class RedAccountDataSourceConfiguration {
    /**
     * mybatis的xml文件.
     */
    public static final String MAPPER_XML_LOCATION = "classpath*:com/agan/boot/mapper/redaccount/mapper/*.xml";


    @Autowired
    @Qualifier("RedPacketDataSource")
    DataSource redPacketDataSource;

    /**
     * 配置Sql Session模板
     */
    @Bean
    public SqlSessionTemplate redPacketSqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(redPacketSqlSessionFactory());
    }

    /**
     * 配置SQL Session工厂
     */
    @Bean
    public SqlSessionFactory redPacketSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(redPacketDataSource);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_XML_LOCATION));
        return factoryBean.getObject();
    }

    /**
     *  配置事务
     */
    @Bean(name="transactionManager")
    public DataSourceTransactionManager transactionManager(){
        return new DataSourceTransactionManager(redPacketDataSource);
    }
}
```
## 步骤5：生成account表和 redpacket表对应的mybatis文件
采用mybatis-generator来自动生成代码，具体见《MyBatis 代码生成器Generator》

## 步骤6：service 体验类 
PayService 作用：模拟发红包，账户余额减钱，红包余额加钱
``` 

@Service
public class PayService {

    @Autowired
    private CapitalAccountMapper capitalAccountMapper;
    @Autowired
    private RedPacketAccountMapper redPacketAccountMapper;

    /**
     * 账户余额 减钱
     * @param userId
     * @param account
     */
    @Transactional(rollbackFor = Exception.class)
    public void payAccount(int userId,int account) {
        CapitalAccount ca=new CapitalAccount();
        ca.setUserId(userId);
        CapitalAccount capitalDTO=this.capitalAccountMapper.selectOne(ca);
//        System.out.println(capitalDTO);
        //从账户里面扣除钱
        capitalDTO.setBalanceAmount(capitalDTO.getBalanceAmount()-account);
        this.capitalAccountMapper.updateByPrimaryKey(capitalDTO);

    }

    /**
     * 红包余额 加钱
     * @param userId
     * @param account
     */
    @Transactional(rollbackFor = Exception.class)
    public void payRedPacket(int userId,int account) {
        RedPacketAccount red= new RedPacketAccount();
        red.setUserId(userId);
        RedPacketAccount redDTO=this.redPacketAccountMapper.selectOne(red);
//        System.out.println(redDTO);
        //红包余额 加钱
        redDTO.setBalanceAmount(redDTO.getBalanceAmount()+account);
        this.redPacketAccountMapper.updateByPrimaryKey(redDTO);

        //int i=9/0;
    }

    @Transactional(rollbackFor = Exception.class)
    public void pay(int fromUserId,int toUserId,int account){
        //账户余额 减钱
        this.payAccount(fromUserId,account);
        //红包余额 加钱
        this.payRedPacket(toUserId,account);
    }
}

```

## 步骤7：controller测试体验类
``` 

@RestController
public class PayController {

    @Autowired
    private PayService payService;

    @RequestMapping(value = "/pay", method = RequestMethod.GET)
    public void create(){
        this.payService.pay(1,2,10);
    }

}
```

