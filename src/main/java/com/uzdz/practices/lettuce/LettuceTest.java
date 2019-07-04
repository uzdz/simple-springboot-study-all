package com.uzdz.practices.lettuce;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

import java.math.BigDecimal;

public class LettuceTest {

    public static void main(String[] args) {
        RedisURI redisURI = RedisURI.create("127.0.0.1", 6379);

        RedisClient redisClient = RedisClient.create(redisURI);

        StatefulRedisConnection<String, String> connect = redisClient.connect();

        RedisCommands<String, String> sync = connect.sync();

        sync.set("uzdz", "dongzhen");

        String uzdz = sync.get("uzdz");

        System.out.printf(uzdz);

        connect.close();

        redisClient.shutdown();
    }
}
