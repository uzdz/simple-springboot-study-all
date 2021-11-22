package com.uzdz.study.jpa;

import com.uzdz.study.module.entity.User;

/**
 * @author ：uzdz
 */
public interface UserService {

    User findById(Long id);
}
