package com.github.alexhanxs.datastructure;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// 这个有问题
public class DynamicPlanMaxIncrementArray {

    public List<int[]>[] record;

    public List<int[]> findSubMaxIncrementArray(int[] subArray) {


        if (record != null && record[subArray.length - 1] != null) {
            return record[subArray.length - 1];
        }

        List<int[]> subMaxIncrementArray = new ArrayList<>();

        if (subArray.length == 1) {

            int[] array = new int[subArray.length];
            array[0] = subArray[0];
            subMaxIncrementArray.add(array);

            if (record != null) {
                record[subArray.length - 1] = subMaxIncrementArray;
            }
            return subMaxIncrementArray;
        } else {

            int[] subs = new int[subArray.length - 1];
            System.arraycopy(subArray, 0, subs, 0, subs.length);

            List<int[]> subLastMaxIncrementArray = new ArrayList<>();
            subLastMaxIncrementArray.addAll(findSubMaxIncrementArray(subs));

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

                if (record != null) {
                    record[subArray.length - 1] = subMaxIncrementArray;
                }
                return subMaxIncrementArray;
            } else {

                // 当最后一个数字比倒数第二个数要小的时候，除了拿到前一个数为数组尾部的最大增长队列外
                // 还需要拿到从上一个数之前比当前最后一个数要小的数的最大增长队列来一起判断处理

                for (int i = subs.length - 1; i >= 0; i--) {
                    if (subs[i] < last) {
                        int[] arrayMin = new int[i + 1];
                        System.arraycopy(subs, 0, arrayMin, 0, i + 1);

                        // 优化 or 负优化？当遍历第一个比最后一个数要小的最长增长队列的长度 不满足要求时过滤
                        List<int[]> arrayMinMaxs = findSubMaxIncrementArray(arrayMin);
                        int subLength = subLastMaxIncrementArray.get(0).length;
                        for (int[] arrayMinMax : arrayMinMaxs) {
                            if (arrayMinMax.length < subLength - 1)
                                break;
                            subLastMaxIncrementArray.add(arrayMinMax);
                        }

//                        subLastMaxIncrementArray.addAll(findSubMaxIncrementArray(arrayMin));
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

                        } else if (array.length > maxLength) {
                            subMaxIncrementArray.clear();
                            subMaxIncrementArray.add(array);
                            maxLength = array.length;
                        }
                    }
                }
                if (record != null) {
                    record[subArray.length - 1] = subMaxIncrementArray;
                }
                return subMaxIncrementArray;
            }
        }
    }

    public static void main(String[] args) {

        int[] arraySource = {1, 2, 3, 19, 10, 9, 8, 7, 6, 21, 22, 23, 24, 20, 80, 79, 78, 77, 100,
                101, 102, 200, 199, 198, 197, 196, 300, 299, 298, 297, 295, 294, 293, 290, 400, 401,
                399, 398, 397, 396, 395, 394};
        DynamicPlanMaxIncrementArray dynamicPlanMaxIncrementArray = new DynamicPlanMaxIncrementArray();
        int index = 0;

        Date date = new Date();
        System.out.println("start time : " + date.getTime());
        List<int[]> maxIncrementArrays = dynamicPlanMaxIncrementArray.findSubMaxIncrementArray(arraySource);
        System.out.println("array count : " + maxIncrementArrays.size());
//        for (int[] array : maxIncrementArrays) {
//            int length = array.length;
//            System.out.print("array" + index + " = [");
//            for (int i = 0; i < length; i++) {
//                System.out.print(array[i]);
//                if (length - 1 != i) {
//                    System.out.print(", ");
//                } else {
//                    System.out.print("]");
//                    System.out.println();
//                }
//            }
//            index++;
//        }
        Date endDate = new Date();
        System.out.println("end time : " + endDate.getTime());
        System.out.println("cost time : " + (endDate.getTime() - date.getTime()));

        maxIncrementArrays.clear();
        index = 0;

        // begin dynamic planning
        dynamicPlanMaxIncrementArray.record = new ArrayList[arraySource.length];

        date = new Date();
        System.out.println("start time : " + date.getTime());
        maxIncrementArrays = dynamicPlanMaxIncrementArray.findSubMaxIncrementArray(arraySource);
        System.out.println("array count : " + maxIncrementArrays.size());
//        for (int[] array : maxIncrementArrays) {
//            int length = array.length;
//            System.out.print("array" + index + " = [");
//            for (int i = 0; i < length; i++) {
//                System.out.print(array[i]);
//                if (length - 1 != i) {
//                    System.out.print(", ");
//                } else {
//                    System.out.print("]");
//                    System.out.println();
//                }
//            }
//            index++;
//        }
        endDate = new Date();
        System.out.println("end time : " + endDate.getTime());
        System.out.println("cost time : " + (endDate.getTime() - date.getTime()));
    }

}
