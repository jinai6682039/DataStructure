package com.github.alexhanxs.datastructure;

public class FibonacciUseDivideAndConquer {

    public static class Matrix {
        int xSize;
        int ySize;

        long[][] num;

        public Matrix(int xSize, int ySize, long[][] num) {
            this.xSize = xSize;
            this.ySize = ySize;
            this.num = num;
        }

        private long[] matrixPow(long[] powNum) {
            if (powNum.length != ySize) {
                return null;
            }

            long[] result = new long[xSize];

            for (int i = 0; i < powNum.length; i++) {
                for (int j = 0; j < xSize; j++) {
                    result[j] += (powNum[i] * num[i][j]);
                }
            }

            return result;
        }
    }

    public static void main(String[] args) {

        long[][] num = {{1, 1}, {1, 0}};
        long[] pow = {1, 0};

        Matrix matrix = new Matrix(2, 2, num);
        matrix.matrixPow(pow);

        int index = 2;
        while (index < 100) {
            pow = matrix.matrixPow(pow);
            System.out.println("fibonacci index " + index + ":" + pow[0]);
            index++;
        }
    }

}
