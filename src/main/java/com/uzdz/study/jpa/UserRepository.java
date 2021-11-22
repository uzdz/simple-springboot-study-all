package com.uzdz.study.jpa;

import com.uzdz.study.module.dto.UserDto;
import com.uzdz.study.module.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor {

    @Query("select new com.uzdz.study.module.dto.UserDto(a.name) from User as a ")
    List<UserDto> selectByCod();

    @Query("select new User(a.id, a.name, a.like, a.createTime) from User as a where a.id = 11")
    User selectByUserByIdEq1();

    @Query("from User where id = ?1")
    User selectByUserByIdEq(Integer id);

    @Query("select name from User ")
    List<UserDto> selectByCod3();

    @Transactional
    @Modifying
    @Query("delete from User where id = 1")
    int updateFromProperties();

    @Transactional
    @Modifying
    @Query(value = "update my_user set name = ?1 where ids in (?2)", nativeQuery = true)
    int updateFromProperties1(String name, List<Long> ids);

    @Transactional
    @Modifying
    @Query(value = "update my_user set name = ?1 where ids = ?2", nativeQuery = true)
    int updateById(String name, Integer id);

    User findByName(String name);

    int deleteUserById(int id);
}
