package com.agan.boot.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @author 阿甘
 * @see 课程地址：https://study.163.com/course/courseMain.htm?courseId=1004348001&share=2&shareId=1016671292
 * @version 1.0
 * 注：如有任何疑问欢迎加入QQ群977438372 进行讨论
 */
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
