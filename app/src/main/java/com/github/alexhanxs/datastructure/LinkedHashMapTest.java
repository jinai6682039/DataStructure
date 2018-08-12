package com.github.alexhanxs.datastructure;

import android.annotation.TargetApi;
import android.os.Build;

import java.text.DateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Alexhanxs on 2018/4/16.
 */

public class LinkedHashMapTest {
    @TargetApi(Build.VERSION_CODES.N)
    public static void main(String[] args) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>(16, 0.75f, true);

        map.put("1", "10");
        map.put("3", "3");
        map.put("5", "5");
        map.put("7", "7");
        map.put("2", "2");
        map.put("7", "1");
        map.replace("1", "11");
//        map.put("5", "8");

        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }

        LinkedHashMap<String, String> mapmap = new LinkedHashMap<>(16, 0.75f, false);
        mapmap.put("1", "10");
        mapmap.put("3", "3");
        mapmap.put("5", "5");
        mapmap.put("7", "7");
        mapmap.put("2", "2");
        mapmap.put("7", "1");
        Iterator iterator1 = mapmap.entrySet().iterator();
        while (iterator1.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator1.next();
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }

    }
}
