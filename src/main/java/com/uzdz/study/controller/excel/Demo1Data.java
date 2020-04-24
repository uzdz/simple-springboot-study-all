package com.uzdz.study.controller.excel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Demo1Data {
    @ExcelProperty("分析数据结果")
    private String val1;

    @ExcelProperty("分析数据结果")
    private String val2;

    @ExcelProperty("分析数据结果")
    private String val3;

    @ExcelProperty("分析数据结果")
    private String val4;
}
