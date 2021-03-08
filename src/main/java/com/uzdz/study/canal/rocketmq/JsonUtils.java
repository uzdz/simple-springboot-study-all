package com.uzdz.study.canal.rocketmq;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

/**
 * @author ：uzdz
 */
public class JsonUtils {


    public static Map<String, Object> JsonToMap(String jsonStr) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> params = objectMapper.readValue(jsonStr,Map.class);
            return params;
        } catch (Exception e) {
            return null;
        }
    }
}
