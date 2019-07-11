package com.uzdz.study.util;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.TemporalField;
import java.util.Random;

/**
 * {@link } for .
 *
 * @author uzdz
 * @date: 2019/7/11 14:56
 * @since 0.1.0
 */
public class RandomKeyUtil {

    public static String getRandomString(int length) {
        //定义字符
        StringBuffer buffer = new StringBuffer("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
        StringBuffer sb = new StringBuffer();
        Random r = new Random();
        int range = buffer.length();
        //循环字符长度
        for (int i = 0; i < length; i ++) {
            //生成随机字符
            sb.append(buffer.charAt(r.nextInt(range)));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String randomString = getRandomString(12);

        System.out.printf(randomString);
    }


    /**
     * 计算当前时间与23.59.59时间的相差秒数
     * @return
     */
    public static long nowTimeBetweenEndTimeDuration() {
        return Duration.between(
                LocalTime.of(23, 59, 59),
                LocalTime.now().withNano(0)).getSeconds();
    }
}

