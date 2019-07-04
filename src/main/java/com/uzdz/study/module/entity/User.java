package com.uzdz.study.module.entity;

import java.io.Serializable;

public class User implements Serializable {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 名称
     */
    private String name;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}