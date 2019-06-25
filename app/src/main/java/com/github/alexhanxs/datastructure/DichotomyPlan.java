package com.github.alexhanxs.datastructure;

import java.util.HashMap;

// 编程之美 3.11 二分法程序改错
public class DichotomyPlan {

    // 在一个有序（非降序）数组中查找最大i，使arr[i] = target
    private static int findMaxIndex(int[] array, int target) {
        int start = 0;
        int end = array.length - 1;
        while (start < end - 1) {
            int middle = start + (end - start) / 2;
            if (array[middle] <= target) {
                start = middle;
            } else {
                end = middle - 1;
            }
        }

        if (array[end] == target) {
            return end;
        } else if (array[start] == target) {
            return start;
        } else {
            return -1;
        }
    }

    // 在一个有序（非降序）数组中查找任意i，使arr[i] = target
    private static int findEqual(int[] array, int target) {
        int start = 0;
        int end = array.length - 1;
        while (start < end) {
            int middle = start + (end - start) / 2;
            if (array[middle] == target) {
                return middle;
            } else if (array[middle] > target) {
                end = middle - 1;
            } else {
                start = middle + 1;
            }
        }

        return -1;
    }

    // 在一个有序（非降序）数组中查找最小i，使arr[i] = target
    private static int findMinIndex(int[] array, int target) {
        int start = 0;
        int end = array.length - 1;
        while (start < end - 1) {
            int middle = start + (end - start) / 2;
            if (array[middle] < target) {
                start = middle + 1;
            } else {
                end = middle;
            }
        }

        if (array[start] == target) {
            return start;
        } else if (array[end] == target) {
            return end;
        } else {
            return -1;
        }
    }

    // 在一个有序（非降序）数组中查找最大i，使arr[i] < target
    private static int findMaxIndexLessThen(int[] array, int target) {
        int start = 0;
        int end = array.length - 1;

        while (start < end - 1) {
            int middle = start + (end - start) / 2;
            if (array[middle] < target) {
                start = middle;
            } else {
                end = middle - 1;
            }
        }

        if (array[end] < target) {
            return end;
        }
        if (array[start] < target) {
            return start;
        } else {
            return -1;
        }
    }

    // 在一个有序（非降序）数组中查找最小i，使arr[i] > target
    private static int findMinIndexLargeThen(int[] array, int target) {
        int start = 0;
        int end = array.length - 1;

        while (start < end - 1) {
            int middle = start + (end - start) / 2;
            if (array[middle] <= target) {
                start = middle + 1;
            } else {
                end = middle;
            }
        }

        if (array[start] > target) {
            return start;
        } else if (array[end] > target) {
            return end;
        } else {
            return -1;
        }
    }

    // 判断单链表是否有圆环
    // 从头开始遍历，第一个访问次数达到2的节点为环开始节点
    private static Node findFirstCycleNode(Node head) {
        Node cur = head;
        Node start;

        HashMap<Node, Integer> findTime = new HashMap<>();

        while (cur != null) {
            start = head.left;
            for (; ; ) {
                if (!findTime.containsKey(start)) {
                    findTime.put(start, 1);
                } else {
                    return start;
                }
                if (start.left != null) {
                    start = start.left;
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        int array[] = {1, 2, 2, 2, 2, 2};
        System.out.println("findMaxIndex = " + findMaxIndex(array, 2));
        System.out.println("findEqual = " + findEqual(array, 2));
        System.out.println("findMinIndex = " + findMinIndex(array, 2));
        System.out.println("findMaxIndexLessThen = " + findMaxIndexLessThen(array, 1));
        System.out.println("findMinIndexLargeThen = " + findMinIndexLargeThen(array, 1));
    }
}
