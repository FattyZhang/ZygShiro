package com.zyg.shiro.dao;

import org.apache.ibatis.annotations.Mapper;

import com.zyg.shiro.model.User;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);
    
    User selectByName(String user);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}