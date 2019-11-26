package com.uzdz.study.jpa;

import com.uzdz.study.module.dto.UserDto;
import com.uzdz.study.module.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor {

    @Query("select new com.uzdz.study.module.dto.UserDto(a.name) from User as a ")
    List<UserDto> selectByCod();

    @Query("select name from User ")
    List<UserDto> selectByCod3();
}
