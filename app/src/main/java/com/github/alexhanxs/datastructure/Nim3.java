package com.github.alexhanxs.datastructure;

import java.util.ArrayList;
import java.util.List;

public class Nim3 {

    public static boolean buildSafeZone(int x, int y) {
        int a = 1;
        int n = 2;

        if (x > y) {
            int temp = x;
            x = y;
            y = temp;
        }

        if (x == 1 && y == 2) {
            return false;
        }

        List<Integer> list = new ArrayList<>();
        List<Integer> list_a = new ArrayList<>();
        list_a.add(a);
        list.add(a);
        list.add(n);

        System.out.println("safe zone (" + a + " ," + n + ")");

        while (x >= a) {
            // 找到自然数中最小的一个未出现的作为下一个a
            if (list.contains(a)) {
                a++;
                continue;
            }
            list_a.add(a);
            list.add(a);
            list.add(a + n);
            System.out.println("safe zone (" + a + " ," + (a + n) + ")");
            n++;
        }

        if ((x == a - 1) && (y == (a + n - 2))) {
            return false;
        } else {
            return true;
        }
    }

    public static void main(String[] args) {
        int x = 100000;
        int y = 162000;
        System.out.println("if i choose zone (" + x + " ," + y + "), i will "
                + (buildSafeZone(x, y) ? "win." : "lose."));
    }
}
