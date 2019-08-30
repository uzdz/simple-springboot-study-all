package com.uzdz.study.module.dao;

import com.uzdz.study.module.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    User selectById(int a, String ab);

    /**
     * 修改用户名
     * @param id
     * @param name
     * @return
     */
    int updateUser(@Param("id")Integer id, @Param("name") String name);
}