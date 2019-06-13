package com.github.alexhanxs.datastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FindMaxK {

//    public static int[] quickSort(int[] source, int k) {
//        if (k < 0) {
//            return null;
//        } else if (source.length <= k) {
//            return source;
//        }
//
//
//    }

    public static List<Integer> recursiveQuickSort(List<Integer> source, int k) {

        int middle = source.get(0);

        List<Integer> lessThen = new ArrayList<>();
        List<Integer> largeThen = new ArrayList<>();

        for (int i = 1; i < source.size(); i++) {
            int current = source.get(i);
            if (current > middle) {
                largeThen.add(current);
            } else {
                lessThen.add(current);
            }
        }

        if (lessThen.size() > largeThen.size()) {
            largeThen.add(middle);
        } else {
            lessThen.add(middle);
        }

        int largeSize = largeThen.size();

        if (largeSize == k) {
            return largeThen;
        } else if (largeSize > k) {
            return recursiveQuickSort(largeThen, k);
        } else {
            List<Integer> total = new ArrayList<>();

            total.addAll(recursiveQuickSort(lessThen, k - largeSize));
            total.addAll(largeThen);
            return total;
        }

    }

    public static void main(String[] args) {
        List<Integer> source = new ArrayList<>();

        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            int randomNum = random.nextInt(10000);
            source.add(randomNum);
        }

        List<Integer> maxK = recursiveQuickSort(source, 10);

        for (int i = 0; i < 10; i++) {
            System.out.println("Max num " + i + ": " + maxK.get(i));
        }
    }
}
