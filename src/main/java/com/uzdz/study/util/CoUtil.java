package com.uzdz.study.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CoUtil {

    /**
     * 年月日 时间
     */
    private static String pre_compute_day = null;

    /**
     * 机器当前开机时间 结算时间点
     */
    private static Date current_open_start_datetime = null;

    /**
     * 当天开启时长 秒
     */
    private static int open_second = 0;

    /**
     * 计算返回当天已开机时长
     * @param openClose 当前是否开机
     * @return
     */
    public static Map<String, Object> compute(boolean openClose) {

        Map<String, Object> map = new HashMap<>(16);

        // 时间转换器
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        // 当前时间
        Date currentDatetime= new Date();
        String formatCurrentDay = df.format(currentDatetime);

        // 未初始化过，且未开机状态，无需处理，返回 0 second 即可
        if (!openClose && pre_compute_day == null) {
            map.put("currentSecond", open_second);
            return map;
        }

        // 今天已开机过，但目前处于未开机状态
        if (!openClose && current_open_start_datetime == null) {
            map.put("currentSecond", open_second);
            return map;
        }

        // 判断是否跨天，如果跨天需要初始化并返回昨天数据
        if (pre_compute_day != null && !formatCurrentDay.equals(pre_compute_day)) {
            map.put("crossDay", true);
            map.put("preDayOpenSecond", open_second);

            // 初始化
            current_open_start_datetime = null;
            pre_compute_day = null;
            open_second = 0;
            return compute(openClose);
        }

        // 新的一天，且首次开机。记录开机天
        if (openClose && pre_compute_day == null) {
            pre_compute_day = formatCurrentDay;
        }

        // 开机，记录开机时间
        if (openClose && current_open_start_datetime == null) {
            current_open_start_datetime = currentDatetime;
        }

        if (current_open_start_datetime != null) {
            // 需要累计目前已启动时长
            int second = (int)((currentDatetime.getTime() - current_open_start_datetime.getTime()) / 1000);
            open_second += second;
            current_open_start_datetime = currentDatetime;
        }

        // todo 此段代码，用于决定，如果当前关机，那么此段算开机时间还是不算
        if (!openClose) {
            current_open_start_datetime = null;
        }

        map.put("currentSecond", open_second);
        return map;
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println(compute(true));

        TimeUnit.SECONDS.sleep(10);

        System.out.println(compute(true));

        TimeUnit.SECONDS.sleep(10);

        System.out.println(compute(false));

        TimeUnit.SECONDS.sleep(10);

        System.out.println(compute(true));
    }
}
