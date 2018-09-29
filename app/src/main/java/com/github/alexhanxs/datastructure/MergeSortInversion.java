package com.github.alexhanxs.datastructure;

/**
 * createTime: 2018/9/29
 * by hanxu17
 */
public class MergeSortInversion {

    public int mergeSort(int[] array, int leftIndex, int middleIndex, int rightIndex) {

        int leftStart = 0;
        int middleStart = middleIndex - leftIndex;
        int rightStart = middleStart + 1;

        int inversionCount = 0;
        int sortArrayAddIndex = leftIndex;
        int[] sortArray;

        if (rightIndex - leftIndex < 1) {
            return inversionCount;
        }

        sortArray = new int[rightIndex - leftIndex + 1];
        System.arraycopy(array, leftIndex, sortArray, 0, sortArray.length);

        while (leftStart <= middleStart && rightStart <= sortArray.length - 1) {
            if (sortArray[leftStart] > sortArray[rightStart]) {
                inversionCount += middleStart - leftStart + 1;
                array[sortArrayAddIndex++] = sortArray[rightStart];
                rightStart++;
            } else {
                array[sortArrayAddIndex++] = sortArray[leftStart];
                leftStart++;
            }
        }

        while (leftStart <= middleStart) {
            array[sortArrayAddIndex++]
                    = sortArray[leftStart];
            leftStart++;
        }

        while (rightStart <= sortArray.length - 1) {
            array[sortArrayAddIndex++]
                    = sortArray[rightStart];
            rightStart++;
        }

        return inversionCount;
    }

    public int sort(int[] array, int leftIndex, int rightIndex) {

        if (leftIndex >= rightIndex) {
            return 0;
        }

        int middleIndex = leftIndex + ((rightIndex - leftIndex) >> 1);

        int totalInverSionCount = 0;

        totalInverSionCount += sort(array, leftIndex, middleIndex);
        totalInverSionCount += sort(array, middleIndex + 1, rightIndex);
        totalInverSionCount += mergeSort(array, leftIndex, middleIndex, rightIndex);

        return totalInverSionCount;
    }

    public int sort(int[] array) {
        return sort(array, 0, array.length - 1);
    }

    public static void main(String[] args) {
        MergeSortInversion mergeSortInversion = new MergeSortInversion();

        int[] array1 = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        int[] array2 = {9, 8, 7, 6, 1, 4, 3, 2, 5};
        int[] array3 = {1, 5, 6, 7, 4};
        int[] array4 = {1, 5, 6, 7, 4, 3, 11, 15, 13, 2, 8};

        System.out.println("MergeSortInversion count of array1 = " + mergeSortInversion.sort(array1));
        System.out.println("MergeSortInversion count of array2 = " + mergeSortInversion.sort(array2));
        System.out.println("MergeSortInversion count of array3 = " + mergeSortInversion.sort(array3));
        System.out.println("MergeSortInversion count of array4 = " + mergeSortInversion.sort(array4));

    }
}
