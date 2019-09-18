package com.uzdz.study.jpa;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.SimpleRestriction;
import com.uzdz.study.module.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor {
}
