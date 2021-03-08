package com.uzdz.study.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.uzdz.study.excel.Demo1Data;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：uzdz
 */
public class ExcelController {

    @GetMapping("/excel")
    public void excel(HttpServletResponse response) throws IOException {

        List<Demo1Data> list = new ArrayList<>();
        list.add(Demo1Data.builder().val1("a").val2("b").val3("c").val4("d").build());
        list.add(Demo1Data.builder().val1("a").val2("").val3("").val4("").build());
        list.add(Demo1Data.builder().val1("").val2("").val3("").val4("").build());
        list.add(Demo1Data.builder().val1("a").val2("b").val3("c").val4("d").build());

        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("测试", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream(), Demo1Data.class).autoCloseStream(false).sheet("模板")
                    .doWrite(list);
        } catch (Exception e) {
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String, String> map = new HashMap<String, String>();
            map.put("status", "failure");
            map.put("message", "下载文件失败" + e.getMessage());
            response.getWriter().println(JSON.toJSONString(map));
        }
    }
}
