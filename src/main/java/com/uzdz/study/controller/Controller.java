package com.uzdz.study.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.uzdz.study.config.MessageConstant;
import com.uzdz.study.controller.excel.Demo1Data;
import com.uzdz.study.controller.excel.DemoData;
import com.uzdz.study.module.dao.UserMapper;
import com.uzdz.study.module.entity.Root;
import com.uzdz.study.module.entity.User;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.async.RedisAsyncCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
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
import java.util.stream.Collectors;

@RestController
public class Controller {

    @Autowired(required = false)
    private RedisClient redisClient;

    public static Map<String,String> goodsCategory= new ConcurrentHashMap<>();

    @Autowired(required = false)
    private UserMapper userMapper;

    @Value("${geek.name}")
    private String name;

    @Autowired(required = false)
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/test")
    public String test() {
        Map<Integer, String> msg = MessageConstant.msg;
        Map<Integer, String> msg1 = MessageConstant.msg;

        if (msg == msg1) {
            return "success";
        }

        return "fail";
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

    @GetMapping("jdbc")
    public void jdbc() {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select id as '1', name as '2', create_time as '3' from t_user");

        List<Object> k = maps.stream().map((data) -> data.get("c")).collect(Collectors.toList());

        System.out.println(k.get(0));
    }

    public static void main(String[] args) {
        String str = "{\"99\":\"5\",\"0\":\"1\",\"10\":\"2\"}";

        Map<String, Integer> map = JSON.parseObject(str, Map.class);

        Map<String, Integer> stringIntegerMap = sortByValue(map, false);

        List<String> data = new ArrayList<>();
        for(Map.Entry<String, Integer> entry : stringIntegerMap.entrySet()){
            data.add(entry.getKey());
        }

        data.stream().forEach(System.out::println);
    }

    /**
     * 根据map的value排序
     *
     * @param map 待排序的map
     * @param isDesc 是否降序，true：降序，false：升序
     * @return 排序好的map
     */
    public static  <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map, boolean isDesc) {
        Map<K, V> result = Maps.newLinkedHashMap();
        if (isDesc) {
            map.entrySet().stream().sorted(Map.Entry.<K, V>comparingByValue().reversed())
                    .forEach(e -> result.put(e.getKey(), e.getValue()));
        } else {
            map.entrySet().stream().sorted(Map.Entry.<K, V>comparingByValue())
                    .forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
        }
        return result;
    }
}