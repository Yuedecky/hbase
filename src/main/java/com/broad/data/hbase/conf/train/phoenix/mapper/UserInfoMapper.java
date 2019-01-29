package com.broad.data.hbase.conf.train.phoenix.mapper;

import com.broad.data.hbase.conf.train.phoenix.entity.UserInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserInfoMapper {

    @Insert("upsert into  user_info(id,name) values(#{user.id},#{user.name})")
    void addUser(@Param("user") UserInfo userInfo);

    @Delete("delete from user_info where id = #{user.id}")
    void deleteUser(long id);

    @Select("select * from user_info where id=#{user.id}")
    @ResultMap(value = "userInfoMap")
    UserInfo findUser(long id);

    @Select("select * from user_info")
    List<UserInfo> findUsers();
}
