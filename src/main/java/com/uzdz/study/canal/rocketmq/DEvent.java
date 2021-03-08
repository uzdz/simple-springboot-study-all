package com.uzdz.study.canal.rocketmq;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DEvent {

    /**
     * 主键名称
     */
    String pkName;

    /**
     * 变更类型
     */
    String type;

    /**
     * 最新记录值
     */
    List<Map<String, String>> dynamicDataList;
}

