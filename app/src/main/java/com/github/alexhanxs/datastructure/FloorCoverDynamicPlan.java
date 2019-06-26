package com.github.alexhanxs.datastructure;

import java.util.ArrayList;
import java.util.List;

// 编程之美4.2
// 1 * 2砖块铺满n * m地板 的铺法
public class FloorCoverDynamicPlan {

    private int width;
    private int height;

    // 动态规划的状态压缩算法
    // 对于矩阵XY中任意一个点瓷砖可能存在如下三种情况 （y为竖直方向，x为水平方向）
    // 1、空着先不放 （0表示）
    // 2、当(x, y - 1)是空着的，可以竖着摆一块瓷砖 （用01表示，竖直方向）
    // 3、当x方向还有空间，则横着摆一块瓷砖 （用11表示,水平方向）
    // 最后一排一定全是1

    // 对第一层进行特殊处理
    private static boolean fitFirstLine(int lineStatus) {
        List<Integer> binaryArray = getBinaryArray(lineStatus);

        for (int i = 0; i < binaryArray.size(); ) {
            int binary = binaryArray.get(i);
            if (binary == 1) {
                if (i != binaryArray.size() - 1) {
                    int binaryNext = binaryArray.get(i + 1);
                    if (binaryNext != 1) {
                        return false;
                    } else {
                        i = i + 2;
                    }
                } else {
                    return false;
                }
            } else if (binary == 0) {
                i++;
            }
        }
        return true;
    }

    // 除了第一层外的层数，需要判断当前层和上一层是否匹配
    private static boolean fitOtherLine(int lineStatus, int preLineStatus, int lineStatusLength) {
        List<Integer> binaryArray = getBinaryArray(lineStatus);
        List<Integer> preBinaryArray = getBinaryArray(preLineStatus);

        int size = lineStatusLength;

        if (size > preBinaryArray.size()) {
            int addSize = size - preBinaryArray.size();
            for (; addSize > 0; addSize--) {
                preBinaryArray.add(0);
            }
        }
        if (size > binaryArray.size()) {
            int addSize = size - binaryArray.size();
            for (; addSize > 0; addSize--) {
                binaryArray.add(0);
            }
        }

        for (int i = 0; i < size; ) {
            if (binaryArray.get(i) == 0) {
                if (preBinaryArray.get(i) == 0) {
                    return false;
                }
                i++;
            } else {
                if (preBinaryArray.get(i) == 1) {

                    if (i == size - 1) {
                        return false;
                    }

                    if (preBinaryArray.get(i + 1) != 1 || binaryArray.get(i + 1) != 1) {
                        return false;
                    }
                    i = i + 2;
                }
                i++;
            }
        }

        return true;
    }

    private static int calucateSumWay(int width, int height) {

        if (width % 2 != 0 && height % 2 != 0) {
            return 0;
        }

        if (width > height) {
            int temp = width;
            width = height;
            height = temp;
        }

        int lineStatusLength = (int) Math.pow(2, width);

        int[][] sumWay = new int[height][lineStatusLength];

        for (int i = 0; i < lineStatusLength; i++) {
            if (fitFirstLine(i)) {
                sumWay[0][i] = 1;
            }
        }

        for (int i = 1; i < height; i++) {
            for (int m = 0; m < lineStatusLength; m++) {
                for (int n = 0; n < lineStatusLength; n++) {
                    if (fitOtherLine(m, n, width)) {
                        sumWay[i][m] += sumWay[i - 1][n];
                    }
                }
            }
        }

        return sumWay[height - 1][lineStatusLength - 1];
    }

    private static List<Integer> getBinaryArray(int target) {
        List<Integer> binaryArray = new ArrayList<>();
        if (target == 0) {
            binaryArray.add(0);
            return binaryArray;
        }

        while (target > 1) {
            binaryArray.add(0, (target & 0x01));
            target = target >>> 1;
        }
        binaryArray.add(0, (target & 0x01));

        return binaryArray;
    }


    public static void main(String[] args) {
        System.out.println("" + fitFirstLine(15));
        System.out.println("" + fitOtherLine(3, 0, 2));

        System.out.println("" + calucateSumWay(8, 8));
    }
}
