package com.github.alexhanxs.datastructure;

public class FindSumMaxInMatrix {

    // 二维矩阵的查找，也可以想一维一样转化为线性情况
    // 这里将一列看成一个一维数组中的一项
    private static int findSumMaxInMatrix(int[][] sug, int xSize, int ySize) {
        int maxSum = Integer.MIN_VALUE;
        for (int a = 0; a < ySize; a++) {
            for (int c = a; c < ySize; c++) {
                int startMax = addTotalColumn(sug, xSize - 1, a, c);
                int max = startMax;

                for (int i = xSize - 2; i >= 0; i--) {
                    startMax = Math.max(addTotalColumn(sug, i, a, c), addTotalColumn(sug, i, a, c) + startMax);
                    max = Math.max(startMax, max);
                }

                if (max > maxSum) {
                    maxSum = max;
                }
            }
        }

        return maxSum;
    }

    private static int addTotalColumn(int[][] sug, int column, int yMin, int yMax) {
        int sum = 0;

        for (int i = yMin; i <= yMax; i++) {
            sum += sug[i][column];
        }

        return sum;
    }

    public static void main(String[] args) {
        int[][] matrix = {
                {1, -2, 3, -4},
                {5, -6, 7, -8},
                {9, -10, 11, -12}};

        System.out.println("Max sum = " + findSumMaxInMatrix(matrix, 4, 3));
    }
}
