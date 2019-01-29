package com.broad.data.hbase.phoenix;

import com.broad.data.hbase.conf.train.phoenix.entity.UserInfo;
import com.broad.data.hbase.conf.train.phoenix.mapper.UserInfoMapper;
import com.broad.data.hbase.conf.train.phoenix.mybatis.DataSourceConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@Import(DataSourceConfiguration.class)
@PropertySource("classpath:application.properties")
@ComponentScan("com.broad.data.hbase.conf.train.phoenix")
@MapperScan("com.broad.data.hbase.conf.train.phoenix.mapper")
public class UserInfoTest {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Test
    public void testAddUser(){
        final UserInfo info = new UserInfo();
        info.setId(100L);
        info.setName("broad");
        userInfoMapper.addUser(info);
    }


}
