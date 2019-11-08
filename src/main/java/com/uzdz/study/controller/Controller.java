package com.uzdz.study.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.uzdz.study.controller.excel.DemoData;
import com.uzdz.study.module.dao.UserMapper;
import com.uzdz.study.module.entity.Root;
import com.uzdz.study.module.entity.User;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.async.RedisAsyncCommands;
import org.apache.skywalking.apm.toolkit.trace.ActiveSpan;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

@RestController
public class Controller {

    @Autowired(required = false)
    private RedisClient redisClient;

    public static Map<String,String> goodsCategory= new ConcurrentHashMap<>();

    @Autowired(required = false)
    private UserMapper userMapper;

    @Value("${geek.name}")
    private String name;

    @Trace
    @GetMapping("/hello/{value1}")
    public String hello(@PathVariable(value = "value1") String name) {
        Root root = new Root();
        ActiveSpan.info("this is shywalking info");

        ActiveSpan.tag("this is shywalking tag", "uzdz");
        System.out.println(goodsCategory.get("uzdz"));

        goodsCategory.put("uzdz", name);

        System.out.printf("shywalking trace id is : " + TraceContext.traceId());

        return goodsCategory.get("uzdz");
    }

    @SentinelResource("redisResouce")
    @GetMapping("/redisInfo")
    public String getRedisInfo() throws ExecutionException, InterruptedException {

        if (redisClient == null) {
            return "failed start redis client";
        }

        RedisAsyncCommands<String, String> async = redisClient.connect().async();

        RedisFuture<String> info = async.info();

        return info.get();
    }

    @GetMapping("/get1")
    public Object error1(HttpServletRequest httpServletRequest) {
        User user = userMapper.selectById(12);

        return "success";
    }

//    @GetMapping("/apollo")
//    public Object apollo() {
//        Config config = ConfigService.getAppConfig();
//
//        config.addChangeListener(new ConfigChangeListener() {
//            @Override
//            public void onChange(ConfigChangeEvent changeEvent) {
//                System.out.println("Changes for namespace " + changeEvent.getNamespace());
//                for (String key : changeEvent.changedKeys()) {
//                    ConfigChange change = changeEvent.getChange(key);
//                    System.out.println(String.format("Found change - key: %s, oldValue: %s, newValue: %s, changeType: %s", change.getPropertyName(), change.getOldValue(), change.getNewValue(), change.getChangeType()));
//                }
//            }
//        });
//
//        String someKey = "abc.abc";
//        String someDefaultValue = "unknown";
//        String value = config.getProperty(someKey, someDefaultValue);
//
//        return value;
//    }

    private List<DemoData> data() {
        List<DemoData> list = new ArrayList<DemoData>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }

    @GetMapping("/excel")
    public void excel(HttpServletResponse response) throws IOException {

        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("测试", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream(), DemoData.class).autoCloseStream(false).sheet("模板")
                    .doWrite(data());
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