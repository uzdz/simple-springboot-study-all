package com.uzdz.study;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.easy.database.annotations.DataSourceSelector;
import com.uzdz.study.module.dao.UserMapper;
import com.uzdz.study.module.entity.User;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.async.RedisAsyncCommands;
import org.apache.skywalking.apm.toolkit.trace.ActiveSpan;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

@RestController
public class Controller {

    @Autowired(required = false)
    private RedisClient redisClient;
    public static Map<String,String> goodsCategory= new ConcurrentHashMap<>();

    @Autowired
    private UserMapper userMapper;

    @Trace
    @GetMapping("/hello/{value1}")
    public String hello(@PathVariable(value = "value1") String name) {

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

    @DataSourceSelector(value = "user")
    @GetMapping("/get1")
    public Object error1(HttpServletRequest httpServletRequest) {
        User user = userMapper.selectById(12,"abc");

        return "success";
    }
}