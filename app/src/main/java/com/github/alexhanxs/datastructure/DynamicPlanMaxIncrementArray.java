package com.github.alexhanxs.datastructure;

import java.util.ArrayList;
import java.util.List;

public class DynamicPlanMaxIncrementArray {

    public void findMaxIncrementArray(int[] sourceArray) {

    }

    public List<int[]> findSubMaxIncrementArray(int[] subArray) {

        List<int[]> subMaxIncrementArray = new ArrayList<>();

        if (subArray.length == 1) {

            int[] array = new int[subArray.length];
            array[0] = subArray[0];
            subMaxIncrementArray.add(array);

            return subMaxIncrementArray;
        } else {

            int[] subs = new int[subArray.length - 1];
            System.arraycopy(subArray, 0, subs, 0, subs.length);

            List<int[]> subLastMaxIncrementArray = findSubMaxIncrementArray(subs);

            int last = subArray[subArray.length - 1];
            int subLast = subs[subs.length - 1];

            // 当最后一个数字比倒数第二个数要大的时候，只需要拿到以倒数第二个数伪结束的数组的最长增长队列来进行处理
            // 可能会存在两个或两个以上（长度相等），所以这个时候就需要用当前最后一个数与队列的最大的数作比较
            if (last > subLast) {
                for (int[] array : subLastMaxIncrementArray) {

                    int lastOfArray = array[array.length - 1];
                    if (lastOfArray < last) {
                        int[] newArray = new int[array.length + 1];
                        System.arraycopy(array, 0, newArray, 0, array.length);
                        newArray[newArray.length - 1] = last;

                        subMaxIncrementArray.add(newArray);
                    }
                }

                return subMaxIncrementArray;
            } else {

                // 当最后一个数字比倒数第二个数要小的时候，除了拿到前一个数为数组尾部的最大增长队列外
                // 还需要拿到从上一个数之前比当前最后一个数要小的数的最大增长队列来一起判断处理

                for (int i = subs.length - 1; i >= 0; i--) {
                    if (subs[i] < last) {
                        int[] arrayMin = new int[i + 1];
                        System.arraycopy(subs, 0, arrayMin, 0, i + 1);
                        subLastMaxIncrementArray.addAll(findSubMaxIncrementArray(arrayMin));
                        break;
                    }
                }


                // 开始进行比较
                int maxLength = -1;
                for (int[] array : subLastMaxIncrementArray) {
                    int lastOfArray = array[array.length - 1];
                    if (lastOfArray < last) {
                        
                        int[] newArray = new int[array.length + 1];
                        System.arraycopy(array, 0, newArray, 0, array.length);
                        newArray[newArray.length - 1] = last;

                        if (newArray.length < maxLength) {
                            continue;
                        } else if (newArray.length == maxLength) {
                            subMaxIncrementArray.add(newArray);
                        } else {
                            subMaxIncrementArray.clear();
                            subMaxIncrementArray.add(newArray);
                            maxLength = newArray.length;
                        }

                    } else {
                        if (array.length == maxLength) {
                            subMaxIncrementArray.add(array);

                        } else if (array.length > maxLength){
                            subMaxIncrementArray.clear();
                            subMaxIncrementArray.add(array);
                            maxLength = array.length;
                        }
                    }
                }

                return subMaxIncrementArray;
            }
        }
    }

    public static void main(String[] args) {

        int[] arraySource = {1, 2, 3, 9, 6, 5};

        DynamicPlanMaxIncrementArray dynamicPlanMaxIncrementArray = new DynamicPlanMaxIncrementArray();
        List<int[]> maxIncrementArrays = dynamicPlanMaxIncrementArray.findSubMaxIncrementArray(arraySource);

        int index = 0;
        for (int[] array : maxIncrementArrays) {
            int length = array.length;
            System.out.print("array" + index + " = [");
            for (int i = 0; i < length; i++) {
                System.out.print(array[i]);
                if (length - 1 != i) {
                    System.out.print(", ");
                } else {
                    System.out.print("]");
                    System.out.println();
                }
            }
            index++;
        }

    }

}
