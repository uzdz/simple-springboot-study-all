package com.uzdz.study.test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestMain {

    public static void main(String[] args) {

//        List<String> strings = Arrays.asList("1", "2", "3", "4");
//
//        strings.parallelStream().forEach((data) -> {
//            System.out.println(data);
//            try {
//                TimeUnit.SECONDS.sleep(3);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//
//        System.out.println("stop");


        String value = "1293.000";

        try {
            String s = new BigDecimal(value).setScale(3, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();
            System.out.println(s);

        } catch (Exception e) {
            // recover error
        }



    }

    /**
     * 字符串保留有效位数
     * @param val
     * @param endSize
     * @return
     */
    public static String keepValidDigits(String val, int endSize) {
        int index = val.indexOf(".");

        if (index == -1) {
            return val;
        }

        String ck = "";

        boolean start = false;

        int current = 0;

        for(int i = 0; i < val.length(); i++){
            String subStr = val.substring(i, i + 1);

            if (i > index && (!subStr.equals("0") || start)) {
                start = true;

                if (start) {
                    current = current + 1;
                }
            }

            ck = ck + subStr;

            if (current == endSize) {
                break;
            }
        }

        return ck;
    }
}
