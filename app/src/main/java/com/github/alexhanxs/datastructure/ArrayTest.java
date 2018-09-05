package com.github.alexhanxs.datastructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Alexhanxs on 2018/8/30.
 */

public class ArrayTest {
    public static void main(String[] args) {
        List<String> list1 = new ArrayList<String>();
        List<String> list2 = new ArrayList<String>();
        for (int i = 0; i < 10000; i++) {
            list1.add("test" + i);
            list2.add("test" + i * 2);
        }
        getDiffrent(list1, list2);
        getDiffrent3(list1, list2);
        getDiffrent5(list1, list2);
        getDiffrent4(list1, list2);
        getDiffrent2(list1, list2);//
        //    getDiffrent3 total times 32271699//        getDiffrent5 total times 12239545//        getDiffrent4 total times 16786491//        getDiffrent2 total times 2438731459
    }

    /**
     * 获取两个List的不同元素
     *
     * @param list1
     * @param list2
     * @return
     */
    private static List<String> getDiffrent5(List<String> list1, List<String> list2) {
        long st = System.nanoTime();
        List<String> diff = new ArrayList<>();
        List<String> maxList = list1;
        List<String> minList = list2;
        if (list2.size() > list1.size()) {
            maxList = list2;
            minList = list1;
        }
        Map<String, Integer> map = new HashMap<>(maxList.size());
        for (String string : maxList) {
            map.put(string, 1);
        }
        for (String string : minList) {
            if (map.get(string) != null) {
                map.put(string, 2);
                continue;
            }
            diff.add(string);
        }
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                diff.add(entry.getKey());
            }
        }
        System.out.println("getDiffrent5 total times " + (System.nanoTime() - st));
        return diff;

    }

    /**
     * 获取两个List的不同元素
     *
     * @param list1
     * @param list2
     * @return
     */
    private static List<String> getDiffrent4(List<String> list1, List<String> list2) {
        long st = System.nanoTime();
        Map<String, Integer> map = new HashMap<String, Integer>(list1.size() + list2.size());
        List<String> diff = new ArrayList<String>();
        List<String> maxList = list1;
        List<String> minList = list2;
        if (list2.size() > list1.size()) {
            maxList = list2;
            minList = list1;
        }
        for (String string : maxList) {
            map.put(string, 1);
        }
        for (String string : minList) {
            Integer cc = map.get(string);
            if (cc != null) {
                map.put(string, ++cc);
                continue;
            }
            map.put(string, 1);
        }
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                diff.add(entry.getKey());
            }
        }
        System.out.println("getDiffrent4 total times " + (System.nanoTime() - st));
        return diff;

    }

    /**
     * 获取两个List的不同元素
     *
     * @param list1
     * @param list2
     * @return
     */
    private static List<String> getDiffrent3(List<String> list1, List<String> list2) {
        long st = System.nanoTime();
        Map<String, Integer> map = new HashMap<String, Integer>(list1.size() + list2.size());
        List<String> diff = new ArrayList<String>();
        for (String string : list1) {
            map.put(string, 1);
        }
        for (String string : list2) {
            Integer cc = map.get(string);
            if (cc != null) {
                map.put(string, ++cc);
                continue;
            }
            map.put(string, 1);
        }
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                diff.add(entry.getKey());
            }
        }
        System.out.println("getDiffrent3 total times " + (System.nanoTime() - st));
        return diff;
    }

    /**
     * 获取连个List的不同元素
     *
     * @param list1
     * @param list2
     * @return
     */
    private static List<String> getDiffrent2(List<String> list1, List<String> list2) {
        long st = System.nanoTime();
        list1.retainAll(list2);
        System.out.println("getDiffrent2 total times " + (System.nanoTime() - st));
        return list1;
    }

    /**
     * 获取两个List的不同元素
     *
     * @param list1
     * @param list2
     * @return
     */
    private static List<String> getDiffrent(List<String> list1, List<String> list2) {
        long st = System.nanoTime();
        List<String> diff = new ArrayList<String>();
        for (String str : list1) {
            if (!list2.contains(str)) {
                diff.add(str);
            }
        }
        System.out.println("getDiffrent total times " + (System.nanoTime() - st));
        return diff;
    }
}
