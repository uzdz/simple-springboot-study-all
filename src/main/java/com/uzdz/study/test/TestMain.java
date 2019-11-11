package com.uzdz.study.test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestMain {

    public static void main(String[] args) {

        List<String> strings = Arrays.asList("1", "2", "3", "4");

        strings.parallelStream().forEach((data) -> {
            System.out.println(data);
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("stop");
    }
}
