package com.uzdz.study.module.enums;

import lombok.Getter;

public enum EventStatus {
    NEW("新用户"), AUTH("正式用户"), NORMAL("新客"), MEMBER("老客");
    @Getter
    private String name;

    EventStatus(String name) {
        this.name = name;
    }
}
