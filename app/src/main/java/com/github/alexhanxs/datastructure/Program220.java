package com.github.alexhanxs.datastructure;

import java.util.ArrayList;
import java.util.List;

public class Program220 {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();

        for (int i = 2; i < 32; i++) {
            list.add(i);
        }

        int count = 0;

        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            int hit = 0;
            int hit1 = -1;
            int hit2 = -1;
            for (int j = 0; (j < list.size() && hit <= 2); j++) {
                if (i % list.get(j) != 0) {
                    hit++;
                    if (hit == 1) {
                        hit1 = j;
                    } else if (hit == 2) {
                        hit2 = j;
                    } else {
                        break;
                    }
                }

                if (hit == 2 && (hit1 + 1 == hit2)) {
                    System.out.println("find num = " + i);
                    count++;
                    break;
                }
            }

            if (count == 10)
                break;
        }
    }
}
