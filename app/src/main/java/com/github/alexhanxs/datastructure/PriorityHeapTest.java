package com.github.alexhanxs.datastructure;

public class PriorityHeapTest {

    private int[] fitArray;

    public void fitDown(int fitPosition) {

        if (fitArray == null || fitArray.length == 0 || fitArray.length == 1) {
            return;
        }

        int size = fitArray.length;
        int childLeftPos = (fitPosition << 1) + 1;

        while (childLeftPos < size) {
            if (childLeftPos + 1 < size) {
                if (fitArray[childLeftPos] > fitArray[childLeftPos + 1]) {
                    childLeftPos++;
                }
            }

            if (fitArray[fitPosition] < fitArray[childLeftPos]) {
                break;
            }

            int temp = fitArray[fitPosition];
            fitArray[fitPosition] = fitArray[childLeftPos];
            fitArray[childLeftPos] = temp;

            fitPosition = childLeftPos;
            childLeftPos = (childLeftPos << 1) + 1;
        }
    }

    public void fitUp(int fitPosition) {

        if (fitArray == null || fitArray.length == 0 || fitArray.length == 1) {
            return;
        }

        int size = fitArray.length;
        if (fitPosition >= size) {
            return;
        }

        int fatherPos = (fitPosition - 1) / 2;

        while (fatherPos >= 0) {
            if (fitArray[fitPosition] > fitArray[fatherPos]) {
                break;
            }

            int temp = fitArray[fitPosition];
            fitArray[fitPosition] = fitArray[fatherPos];
            fitArray[fatherPos] = temp;

            fitPosition = fatherPos;
            fatherPos = (fatherPos - 1) >> 1;
        }
    }

    public static void main(String[] args) {
        PriorityHeapTest test = new PriorityHeapTest();
        int[] sourceArray = new int[] {
                14, 13, 12, 11, 10, 9, 8, 7,
 4 ,
                5, 3, 2, 6, 0, 1};
        test.fitArray = new int[sourceArray.length];

        int i = 0;
        while (i  < sourceArray.length) {
            test.fitArray[i] = sourceArray[i];
            test.fitUp(i);
            i++;
        }

        System.out.print("array : ");
        for (int j : test.fitArray) {
            System.out.print(j + ", ");
        }

        System.out.println();

        test.fitArray = sourceArray;
        i = test.fitArray.length / 2 - 1;
        while (i >= 0) {
            test.fitDown(i);
            i--;
        }

        System.out.print("array : ");
        for (int j : test.fitArray) {
            System.out.print(j + ", ");
        }

        System.out.println();

        test.fitArray = sourceArray;
        i = test.fitArray.length - 1;
        while (i > test.fitArray.length / 2 - 1) {
            test.fitUp(i);
            i--;
        }

        System.out.print("array : ");
        for (int j : test.fitArray) {
            System.out.print(j + ", ");
        }
    }

}
