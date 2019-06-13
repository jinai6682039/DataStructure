package com.github.alexhanxs.datastructure;

import java.util.ArrayList;
import java.util.List;

public class FindNxMOnlyHas0or1 {

    private static List<Integer> middleResult = new ArrayList<>();
    private static List<Integer> middleResult2 = new ArrayList<>();

    private static void traversingFindMinX(int n) {

        // traversing
        boolean needBreak = false;
        int numIndex = 0;

//        long time1 = System.currentTimeMillis();
//        for (int i = 0; i < n; i++) {
//
//            if (needBreak) {
//                break;
//            }
//
//            int length = i == 0 ? 1 : i * 2;
//            if (length > n) {
//                needBreak = true;
//            }
//            for (int j = numIndex + 1; j <= numIndex + length; j++) {
//                middleResult2.add(getNextNumberComposingBy0or1(j) % n);
//            }
//            numIndex += length;
//        }
//        long time2 = System.currentTimeMillis();
//
//        for (int i = 0; i < n; i++) {
//
//            if (needBreak) {
//                break;
//            }
//
//            int length = i == 0 ? 1 : i * 2;
//            if (length > n) {
//                needBreak = true;
//            }
//            for (int j = numIndex + 1; j <= numIndex + length; j++) {
//                System.out.println(getNextNumberComposingBy0or1(j) + " % " + n
//                        + " = " + (getNextNumberComposingBy0or1(j) % n));
//            }
//            numIndex += length;
//        }

        // dynamic plan
        int size = (int) (Math.pow(2, n) - 1);
        int currentFlag = 0;

        long time3 = System.currentTimeMillis();
        for (int i = 1; i <= size; i++) {
            if (i == 1 || i % 2 == 0) {
                int residue = getNextNumberComposingBy0or1(i) % n;
                currentFlag = i;
                middleResult.add(residue);
            } else {
                int residue = middleResult.get(currentFlag - 1) + middleResult.get(i - currentFlag - 1);
                middleResult.add(residue);
            }
        }
        long time4 = System.currentTimeMillis();

        for (int j = 1; j <= middleResult.size(); j++) {
            System.out.println(getNextNumberComposingBy0or1(j) + " % " + n
                    + " = " + (middleResult.get(j - 1)));
        }

//        System.out.println("time traversing = " + (time2 - time1));

        System.out.println("time dynamic plan = " + (time4 - time3));
    }

    private static int getNextNumberComposingBy0or1(int index) {
        if (index <= 0) {
            return -1;
        }

        int nextNum = 0;
        for (int i = index, j = 0; i >= 1; ) {

            if (i % 2 != 0) {
                nextNum += Math.pow(10, j);
            }
            i = i >>> 1;
            j = j + 1;
        }
        return nextNum;
    }

    public static void main(String[] args) {
//        System.out.println(getNextNumberComposingBy0or1(4));

        traversingFindMinX(9);
    }
}
