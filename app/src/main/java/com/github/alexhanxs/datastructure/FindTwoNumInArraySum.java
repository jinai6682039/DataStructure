package com.github.alexhanxs.datastructure;

import java.util.ArrayList;
import java.util.List;

public class FindTwoNumInArraySum {

    private static List<List<Integer>> findThreeNum(List<Integer> num, int sum) {
        List<Integer> orderedNum = quickOrder(num);
        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < num.size(); i++) {
            List<Integer> childOrderedNum = new ArrayList<>();
            for (int j = 0; j < num.size(); j++) {
                if (j == i) {
                    continue;
                }
                childOrderedNum.add(orderedNum.get(j));
            }

            List<Integer> childTwoNum = findTwoNum(childOrderedNum, sum - orderedNum.get(i));
            if (childTwoNum != null) {
                childTwoNum.add(orderedNum.get(i));
                result.add(childTwoNum);
            }
        }
        return result;
    }

    private static List<Integer> findTwoNum(List<Integer> num, int sum) {
        List<Integer> orderedNum = quickOrder(num);

        int currentSmall = num.size() - 1;
        int currentLarge = 0;

        int sumBegin = orderedNum.get(currentLarge) + orderedNum.get(currentSmall);
        int i = 0;
        while (i < num.size()) {
            if (sumBegin == sum) {

                System.out.println("find two num is " + orderedNum.get(currentLarge) + " and " + orderedNum.get(currentSmall));
                List<Integer> array = new ArrayList<>();
                array.add(orderedNum.get(currentLarge));
                array.add(orderedNum.get(currentSmall));
                return array;
            } else if (sumBegin < sum) {

                currentSmall--;
                sumBegin = orderedNum.get(currentLarge) + orderedNum.get(currentSmall);
            } else if (sumBegin > sum) {

                currentLarge++;
                sumBegin = orderedNum.get(currentLarge) + orderedNum.get(currentSmall);
            }
            i++;
        }

        System.out.println("no two num in array sum equal " + sum);
        return null;
    }

    private static List<Integer> quickOrder(List<Integer> num) {
        int size = num.size();

        if (size == 1 || size == 0) {
            return num;
        } else if (size == 2) {
            List<Integer> array = new ArrayList<>();
            if (num.get(0) > num.get(1)) {
                array.add(num.get(1));
                array.add(num.get(0));
            } else {
                array.addAll(num);
            }

            return array;
        }

        int middleIndex = (size) / 2;
        int middleNum = num.get(middleIndex);

        List<Integer> leftSmallNum = new ArrayList<>();
        List<Integer> rightLargeNum = new ArrayList<>();

        for (int i = 0; i <= size - 1; i++) {
            if (i == middleIndex) {
                continue;
            }

            int currentNum = num.get(i);

            if (num.get(i) < middleNum) {
                leftSmallNum.add(currentNum);
            } else {
                rightLargeNum.add(currentNum);
            }
        }

        List<Integer> rightOrderNum = quickOrder(rightLargeNum);
        List<Integer> leftOrderNum = quickOrder(leftSmallNum);

        List<Integer> orderNum = new ArrayList<>(rightOrderNum);
        orderNum.add(middleNum);
        orderNum.addAll(leftOrderNum);
        return orderNum;
    }

    public static void main(String[] args) {

        List<Integer> sourceNum = new ArrayList<>();
        sourceNum.add(5);
        sourceNum.add(4);
        sourceNum.add(7);
        sourceNum.add(1);
        sourceNum.add(10);
        sourceNum.add(11);

        List<Integer> orderedNum = quickOrder(sourceNum);

        findTwoNum(sourceNum, 11);

        findThreeNum(sourceNum, 22);
    }
}
