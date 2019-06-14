package com.github.alexhanxs.datastructure;

import java.util.ArrayList;
import java.util.List;

// 输出所有的递增子序列
public class DynamicPlanMaxIncrementArrayPro {

    public static List<int[]>[] record;

    public static List<int[]> findMaxINcrementArrayPro(int[] array) {
        if (record == null) {
            record = new ArrayList[array.length];
        }

        if (record[array.length - 1] != null) {
            return record[array.length - 1];
        }

        if (array.length == 1) {

            int[] result = new int[array.length];
            result[0] = array[0];
            List<int[]> subMaxIncrementArray = new ArrayList<>();
            subMaxIncrementArray.add(array);

            if (record != null) {
                record[array.length - 1] = subMaxIncrementArray;
            }
            return subMaxIncrementArray;
        } else {

            // 拿到当前array除掉最后一位的子array
            int[] subs = new int[array.length - 1];
            System.arraycopy(array, 0, subs, 0, subs.length);

            // 拿到此数字之前所有数字的最小递增数列集合
            List<int[]> subLastMaxIncrementArray = new ArrayList<>();
            subLastMaxIncrementArray.addAll(findMaxINcrementArrayPro(subs));

            int last = array[array.length - 1];
            int subLast = subs[subs.length - 1];

            List<int[]> subMaxIncrementArray = new ArrayList<>();

            // 当前末尾数要比倒数第二要小，进行特殊处理
            if (last < subLast) {
                // 寻找数组中从当前末尾的第一个比此末尾数要小的数
                for (int i = subs.length - 1; i >= 0; i--) {
                    if (subs[i] > last) {
                        continue;
                    }

                    // 拿到从数组开始到从当前数往前的
                    // 第一个比当前数要小的数的最小递增数组的集合
                    int[] arrayMin = new int[i + 1];
                    System.arraycopy(subs, 0, arrayMin, 0, i + 1);

                    List<int[]> minNum = new ArrayList<>(findMaxINcrementArrayPro(arrayMin));
                    subLastMaxIncrementArray.addAll(minNum);
                    break;
                }
            }

            // 判断是否要将当前数字加入到minArray中的某些array中去还是新建一个array
            boolean needAddNewArray = true;
            for (int[] minArray : subLastMaxIncrementArray) {
                int lastOfArray = minArray[minArray.length - 1];
                if (lastOfArray > last) {
                    subMaxIncrementArray.add(minArray);
                    continue;
                } else {
                    // 找到了可以插入last的array，则不去新建一个array来单独存放last
                    needAddNewArray = false;

                    int[] newArray = new int[minArray.length + 1];
                    System.arraycopy(minArray, 0, newArray, 0, minArray.length);
                    newArray[newArray.length - 1] = last;

                    subMaxIncrementArray.add(newArray);
                }
            }

            // 如果在minArray中不存在能能放入当前last的array，则新建一个array
            if (needAddNewArray) {
                int[] newArray = new int[1];
                newArray[0] = last;
                subMaxIncrementArray.add(newArray);
            }

            // 动态规划，记录中间值
            if (record != null) {
                record[array.length - 1] = subMaxIncrementArray;
            }
            return subMaxIncrementArray;
        }
    }

    public static void main(String[] args) {

        int[] arraySource = {1, 2, 3, 19, 10, 9, 8, 7, 6, 21, 22, 23, 24, 20, 80, 79, 78, 77, 100,
                101, 102, 200, 199, 198, 197, 196, 300, 299, 298, 297, 295, 294, 293, 290, 400, 401,
                399, 398, 397, 396, 395, 394};

        int[] arraySource1 = {1, 11, 12, 13, 7, 8, 9};

        List<int[]> maxIncrementArrays = findMaxINcrementArrayPro(arraySource1);
    }
}
