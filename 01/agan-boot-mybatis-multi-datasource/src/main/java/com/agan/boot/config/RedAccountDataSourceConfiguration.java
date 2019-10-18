package com.agan.boot.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @author 阿甘
 * @see 课程地址：https://study.163.com/course/courseMain.htm?courseId=1004348001&share=2&shareId=1016671292
 * @version 1.0
 * 注：如有任何疑问欢迎加入QQ群977438372 进行讨论
 */
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
