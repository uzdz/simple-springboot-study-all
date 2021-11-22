package com.uzdz.study.jpa.utils.dto;

import lombok.Data;

@Data
public class Condition {
    private String name;
    private String value;
    private Op operator;
}
