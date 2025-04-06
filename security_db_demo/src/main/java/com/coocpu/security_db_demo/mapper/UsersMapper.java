package com.coocpu.security_db_demo.mapper;

import com.coocpu.security_db_demo.entity.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UsersMapper {
    int deleteByPrimaryKey(String username);

    int insert(Users record);

    int insertSelective(Users record);

    Users selectByPrimaryKey(@Param("id") Long id);

    Users selectByUsername(@Param("username") String username);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);
}