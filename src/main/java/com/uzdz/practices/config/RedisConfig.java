package com.uzdz.practices.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author: uzdz
 * @date: 2018/8/31 16:33
 * @description:
 */
@Configuration
public class RedisConfig {
    private static Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    @Autowired
    private RedisProperties redisProperties;

    @Bean
    public JedisPool jedisPool() {
        logger.info("###【redis注入成功】[host/port] => {}, {}", redisProperties.getHost(), redisProperties.getPort());
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(redisProperties.getJedis().getPool().getMaxIdle());
        jedisPoolConfig.setMaxWaitMillis(redisProperties.getJedis().getPool().getMaxWait().toMillis());
        JedisPool jedisPool = StringUtils.isEmpty(redisProperties.getPassword()) ?
                new JedisPool(jedisPoolConfig, redisProperties.getHost(), redisProperties.getPort(), (int) redisProperties.getTimeout().getSeconds()):
                new JedisPool(jedisPoolConfig, redisProperties.getHost(), redisProperties.getPort(), (int) redisProperties.getTimeout().getSeconds(), redisProperties.getPassword());
        return jedisPool;
    }
}