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

            if (last > subLast) {
                for (int[] array : subLastMaxIncrementArray) {

                    int lastOfArray = array[array.length - 1];
                    if (lastOfArray < last) {
                        int[] newArray = new int[array.length + 1];
                        System.arraycopy(array, 0, newArray, 1, array.length);
                        newArray[newArray.length - 1] = last;

                        subMaxIncrementArray.add(newArray);
                    }
                }

                return subMaxIncrementArray;
            } else {

                for (int i = subs.length - 1; i > 0; i--) {
                    if (subs[i] < last) {
                        int[] arrayMin = new int[i];
                        System.arraycopy(subs, 0, arrayMin, 0, i);
                        subLastMaxIncrementArray.addAll(findSubMaxIncrementArray(arrayMin));
                        break;
                    }
                }

                int maxLength = -1;

                for (int[] array : subLastMaxIncrementArray) {
                    int lastOfArray = array[array.length - 1];
                    if (lastOfArray < last) {
                        
                        int[] newArray = new int[array.length + 1];
                        System.arraycopy(array, 0, newArray, 1, array.length);
                        newArray[newArray.length - 1] = last;



                        subMaxIncrementArray.add(newArray);
                    } else {

                    }
                }

                return subMaxIncrementArray;
            }


        }
    }

}
