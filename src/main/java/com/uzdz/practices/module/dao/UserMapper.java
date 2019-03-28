package com.uzdz.practices.module.dao;

import com.uzdz.practices.module.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    User selectById(@Param("id") Integer id);

    /**
     * 修改用户名
     * @param id
     * @param name
     * @return
     */
    int updateUser(@Param("id")Integer id, @Param("name") String name);
}