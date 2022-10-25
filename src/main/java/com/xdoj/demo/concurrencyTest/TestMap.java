package com.xdoj.demo.concurrencyTest;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class TestMap {
    static ConcurrentHashMap<String, List<String>> map = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> list1 = new ArrayList<>();
                list1.add("device1");
                list1.add("device2");

                List<String> oldList = map.putIfAbsent("topic1", list1);

                if(oldList != null){
                    oldList.addAll(list1);
                }
                System.out.println(JSON.toJSONString(map));
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> list1 = new ArrayList<>();
                list1.add("device11");
                list1.add("device22");

                List<String> oldList = map.putIfAbsent("topic1", list1);

                if(oldList != null){
                    oldList.addAll(list1);
                }

                System.out.println(JSON.toJSONString(map));
            }
        });

        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> list1 = new ArrayList<>();
                list1.add("device111");
                list1.add("device222");

                map.put("topic2", list1);

                System.out.println(JSON.toJSONString(map));
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
