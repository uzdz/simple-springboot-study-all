package com.uzdz.practices.jpa;

import org.hibernate.dialect.MySQL5Dialect;

public class MySQL5TableType extends MySQL5Dialect {

    @Override
    public String getTableTypeString() {
        return "ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }
}
