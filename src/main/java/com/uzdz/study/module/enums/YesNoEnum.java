package com.uzdz.study.module.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: yes/no
 * @author: mipengchong
 * @create: 2019-09-02 19:47
 **/
@Getter
@AllArgsConstructor
public enum YesNoEnum {
    NO("N", 0),
    YES("Y", 1);

    private String name;

    private int value;

}
