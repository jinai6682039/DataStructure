package com.github.alexhanxs.datastructure;

public class FindSumMaxInArray {

    private static int findMaxSumInArray(int[] array) {
        int size = array.length;

        int startMax;
        int max;

        // startMax是从其对应的size的位置开始，到数组末尾这一段子数组中相加的最大sum
        // 此sum的起点一定是startmax对应size
        startMax = array[size - 1];
        // max是以对应的size位置开始，到数组末尾这一段数组中相加的最大sum
        // 可能的数值在startMax和size + 1位置上对应的max中较大的值
        max = array[size - 1];

        for (int i = size - 2; i >= 0; i--) {
            // 更新startmax，可能为对应size位置上的数值 或 size位置上的数值 + （size + 1）位置上的startmax和的较大值
            startMax = Math.max(array[i], array[i] + startMax);
            // 更新max值，可能是当前size的startmax 和 (size + 1)对应位置上的max中较大的值
            max = Math.max(startMax, max);
        }

        return max;
    }

    public static void main(String[] args) {
        int[] array = {0, -2, 3, 5, -1, 2};
        System.out.println("Max sum : " + findMaxSumInArray(array));
    }
}
