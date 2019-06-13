package com.github.alexhanxs.datastructure;

import java.util.ArrayList;
import java.util.List;

public class FindArrayMaxAndMin {

    private static List<Integer> findArrayMaxAndMin(List<Integer> findArray, int startIndex, int endIndex) {
        if (endIndex - startIndex <= 1) {
            if (findArray.get(startIndex) < findArray.get(endIndex)) {
                List<Integer> array = new ArrayList<>();
                array.add(findArray.get(endIndex));
                array.add(findArray.get(startIndex));
                return array;
            } else {
                List<Integer> array = new ArrayList<>();
                array.add(findArray.get(startIndex));
                array.add(findArray.get(endIndex));
                return array;
            }
        }

        List<Integer> arrayLeft = findArrayMaxAndMin(findArray, startIndex, startIndex + (endIndex - startIndex) / 2);
        List<Integer> arrayRight = findArrayMaxAndMin(findArray, startIndex + (endIndex - startIndex + 1) / 2, endIndex);

        List<Integer> array = new ArrayList<>();

        if (arrayLeft.get(0) > arrayRight.get(0)) {
            array.add(arrayLeft.get(0));
        } else {
            array.add(arrayRight.get(0));
        }

        if (arrayLeft.get(1) < arrayRight.get(1)) {
            array.add(arrayLeft.get(1));
        } else {
            array.add(arrayRight.get(1));
        }

        return array;
    }

    private static List<Integer> findSecondMax(List<Integer> findArray, int start, int end) {
        if (end - start == 1) {
            if (findArray.get(start) < findArray.get(end)) {
                List<Integer> array = new ArrayList<>();
                array.add(findArray.get(end));
                array.add(findArray.get(start));
                return array;
            } else {
                List<Integer> array = new ArrayList<>();
                array.add(findArray.get(start));
                array.add(findArray.get(end));
                return array;
            }
        } else if (end == start) {
            List<Integer> array = new ArrayList<>();
            array.add(findArray.get(start));
            array.add(0);
            return array;
        }

        List<Integer> arrayLeft = findSecondMax(findArray, start, start + (end - start) / 2);
        List<Integer> arrayRight = findSecondMax(findArray, start + (end - start + 1) / 2, end);

        List<Integer> array = new ArrayList<>();

        if (arrayLeft.get(1) > arrayRight.get(0)) {
            return arrayLeft;
        } else if (arrayLeft.get(0) > arrayRight.get(0)) {
            array.add(arrayLeft.get(0));
            array.add(arrayRight.get(0));
            return array;
        } else if (arrayLeft.get(0) > arrayRight.get(1)) {
            array.add(arrayRight.get(0));
            array.add(arrayLeft.get(0));
            return array;
        } else {
            return arrayRight;
        }
    }

    public static void main(String[] args) {
        List<Integer> arrayLisy = new ArrayList<>();
        arrayLisy.add(10);
        arrayLisy.add(5);
        arrayLisy.add(1000);
        arrayLisy.add(700);
        arrayLisy.add(120);
        arrayLisy.add(1);

        List<Integer> array = findArrayMaxAndMin(arrayLisy, 0, arrayLisy.size() - 1);
        System.out.println("Max num = " + array.get(0));
        System.out.println("Min num = " + array.get(1));

        List<Integer> arraya = findSecondMax(arrayLisy, 0, arrayLisy.size() - 1);
        System.out.println("Max num = " + arraya.get(0));
        System.out.println("Second Max num = " + arraya.get(1));
    }
}
