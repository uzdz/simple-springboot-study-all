package com.uzdz.study.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * @author: uzdz
 * @date: 2018/8/27 11:44
 */
@Component
public class JedisUtil {

	private static final Logger logger = LoggerFactory.getLogger(JedisUtil.class);

	@Autowired
	private JedisPool jedisPool;

	public void destory() {
		jedisPool.destroy();
	}

	public Jedis getResource() {
		return jedisPool.getResource();
	}

	public void returnResource(Jedis jedis) {
		if (jedis == null) {
			return;
		}
		try {
			jedis.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 保存对象到redis
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public String set(String key, String value) {
		Jedis jedis = null;
		try {
			jedis=getResource();
			return jedis.set(key, value);
		} catch (Exception e) {
			logger.error("调用redis异常", e);
		} finally {
			returnResource(jedis);
		}
		return null;
	}

    /**
     * 保存对象到redis
     *
     * @param key
     * @return
     */
    public String get(String key) {
        Jedis jedis = null;
        try {
            jedis=getResource();
            return jedis.get(key);
        } catch (Exception e) {
			logger.error("调用redis异常", e);
        } finally {
        	//returnResource(jedis);
		}
        return null;
    }

	/**
	 * 获取Map缓存
	 *
	 * @param key 键
	 * @return 值
	 */
	public Map<String, String> getMap(String key) {
		Map<String, String> value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(key)) {
				value = jedis.hgetAll(key);
			}
		} catch (Exception e) {
			logger.warn("getMap {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}

	/**
	 * 添加hash
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean hset(String key, String field, String value) {
		Jedis jedis = getResource();
		try {
			Long num = jedis.hset(key, field, value);
			if (num == 1) {
				return true;
			}
		} catch (Exception e) {
			logger.warn("key = {} member = {}", key, field, e);
		} finally {
			returnResource(jedis);
		}
		return false;
	}
}