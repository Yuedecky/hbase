package com.broad.data.hbase.conf.train.phoenix.mybatis;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "com.broad.data.hbase.phoenix.mapper.*", sqlSessionFactoryRef = "phoenixSqlSessionFactory")
public class DataSourceConfiguration {


    @Value("${spring.datasource.hikari.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.data-password}")
    private String password;

    @Value("${spring.datasource.data-username}")
    private String username;


    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Primary
    @Bean(name = "phoenixDatasource")
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setPassword(password);
        config.setUsername(username);
        config.setJdbcUrl(jdbcUrl);
        config.setDriverClassName(driverClassName);
        return new HikariDataSource(config);
    }


    @Bean(name = "phoenixSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier(value = "phoenixDatasource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setSqlSessionFactoryBuilder(new SqlSessionFactoryBuilder());
        return factoryBean.getObject();
    }

    @Bean(name = "primaryTransactionManager")
    @Primary
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(this.dataSource());
    }

}
