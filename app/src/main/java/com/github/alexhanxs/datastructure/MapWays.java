package com.github.alexhanxs.datastructure;

import java.io.IOException;

/**
 * Created by Alexhanxs on 2018/8/12.
 */

public class MapWays {

    public boolean test(WayNode s, WayNode e, WayRecord pre) {

        if (s.equals(e)) {
            pre.isEnd = true;
            pre.node = e;
            return true;
        } else {
            boolean canGox = false, canGoy = false;
            WayRecord wayX = new WayRecord(), wayY = new WayRecord();

            if (s.x < e.x) {
                canGox = test(s.nextXNode(), e, wayX);
            }

            if (s.y < e.y) {
                canGoy = test(s.nextYNode(), e, wayY);
            }

            if (canGox || canGoy) {
                pre.node = s;
                pre.canGoX = canGox;
                pre.canGoY = canGoy;
                pre.wayX = canGox ? wayX : null;
                pre.wayY = canGoy ? wayY : null;
            }

            return canGox || canGoy;
        }
    }

    public static void main(String[] args) {
        MapWays mapWays = new MapWays();

        WayRecord root = new WayRecord();
        mapWays.test(new WayNode(0,0), new WayNode(2, 2), root);

        try {
           root.showWays();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println();
    }
}
