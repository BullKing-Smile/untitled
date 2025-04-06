package com.coocpu.security_db_api_demo.mapper;

import com.coocpu.security_db_api_demo.entity.Authorities;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthoritiesMapper {
    int insert(Authorities record);

    int insertSelective(Authorities record);
}