package com.github.alexhanxs.datastructure;

import java.util.Arrays;

public class BigTopHeap<E extends Comparable> {

    Object[] queue;

    int growStep = 8;

    public BigTopHeap() {
        queue = new Object[8];
    }

    private void grow() {
        int newLength = queue.length;
        if (queue.length < 32) {
            newLength += growStep;
        } else {
            newLength += newLength >>> 1;
        }

        queue = Arrays.copyOf(queue, newLength);
    }

    public void add(E e) {
        int realSize = getRealSize();

        if (realSize == queue.length) {
            grow();
        }

        if (realSize == 0) {
            queue[0] = e;
        } else {
            fitUp(realSize, e);
        }
    }

    public void fitUp(int addIndex, E e) {

        while (addIndex > 0) {
            int parent = (addIndex - 1) >>> 1;
            if (e.compareTo(queue[parent]) > 0) {
                queue[addIndex] = queue[parent];
                addIndex = parent;
            } else {
                break;
            }

        }

        queue[addIndex] = e;
    }

    public int getItemIndex(E e) {
        for (int i = 0; i < getRealSize(); i++) {
            if (e.compareTo(queue[i]) == 0)
                return i;
        }
        return -1;
    }

    public void remove(E e) {
        int deleteIndex = getItemIndex(e);
        if (deleteIndex == getRealSize() - 1) {
            queue[deleteIndex] = null;
        } else {
            E last = (E) queue[getRealSize() - 1];
            queue[getRealSize() - 1] = null;
            findDown(deleteIndex, last);
        }
    }

    public void findDown(int fitIndex, E e) {
        // 找到第一个叶子节点
        int half = (getRealSize() - 1) >>> 1;
        while (fitIndex < half) {
            int childLeft = fitIndex * 2 + 1;
            int childRight = childLeft + 1;

            E maxChild = (E) queue[childLeft];
            int nextFitIndex = childLeft;
            if (queue[childRight] != null) {
                E rightChild = (E) queue[childRight];
                if (maxChild.compareTo(rightChild) < 0) {
                    maxChild = rightChild;
                    nextFitIndex = childRight;
                }
            }

            if (e.compareTo(maxChild) < 0) {
                queue[fitIndex] = maxChild;
            }

            fitIndex = nextFitIndex;
        }

        queue[fitIndex] = e;
    }

    public E pop() {
        E e = (E) queue[0];

        if (getRealSize() == 0) {
//            return null;
        } else if (getRealSize() == 1) {
            queue[0] = null;
//            return e;
        } else {
            E last = (E) queue[getRealSize() - 1];
            findDown(0, last);
        }

        return e;
    }

    public int getRealSize() {
        int i = 0;
        for (; i < queue.length; i++) {
            Object o = queue[i];
            if (o == null)
                break;
        }
        return i;
    }

    public void floorSee(StringBuilder sb) {
        int curFloor = 0;
        int curFloorStart = (int) Math.pow(2, curFloor) - 1;
        int curFloorEnd = curFloorStart + (int) Math.pow(2, curFloor) - 1;

        for (int i = curFloorStart; i <= curFloorEnd && i < getRealSize(); i++) {

            Object o = queue[i];
            if (queue != null) {
                sb.append(o).append("_");
            } else {
                break;
            }
            if (i == curFloorEnd) {
                sb.append("\n");
                curFloor++;
                curFloorStart = (int) Math.pow(2, curFloor) - 1;
                curFloorEnd = curFloorStart + (int) Math.pow(2, curFloor) - 1;
            }
        }
    }

    public static void main(String[] args) {
        BigTopHeap<Integer> heap = new BigTopHeap<>();
        heap.add(20);
        heap.add(24);
        heap.add(19);
        heap.add(5);
        heap.add(25);
        heap.add(30);
        heap.add(1);

//        heap.pop();
//        heap.pop();
        heap.remove(19);

        heap.add(99);

        StringBuilder builder = new StringBuilder();
        heap.floorSee(builder);

        System.out.print(builder.toString());
    }
}
