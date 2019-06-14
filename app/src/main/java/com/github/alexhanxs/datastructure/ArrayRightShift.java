package com.github.alexhanxs.datastructure;

public class ArrayRightShift {

    private static int[] reverseArray(int[] array, int start, int end) {
        for (; start < end; start++, end--) {
            int temp = array[start];
            array[start] = array[end];
            array[end] = temp;
        }

        return array;
    }

    // 用2*N次逆序来完成循环右移
    private static int[] rightShift(int[] array, int size, int shift) {
        shift %= size;
        reverseArray(array, 0, size - 1);
        reverseArray(array, 0, shift - 1);
        reverseArray(array, shift, size - 1);
        return array;
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5};

        rightShift(array, 5, 2);
    }
}
