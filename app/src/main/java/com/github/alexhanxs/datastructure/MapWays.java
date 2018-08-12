package com.github.alexhanxs.datastructure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexhanxs on 2018/8/12.
 */

public class MapWays {

    public boolean test(WayNode s, WayNode e, WayRecord pre, List<WayNode> forbiddenNodes) {

        if (s.equals(e)) {
            pre.isEnd = true;
            pre.node = e;
            return true;
        } else {
            for (WayNode forbiddenNode : forbiddenNodes) {
                if (forbiddenNode.equals(s)) {
                    pre.node = s;
                    pre.canGoX = false;
                    pre.canGoY = false;
                    pre.wayX = null;
                    pre.wayY = null;
                    return false;
                }
            }
            boolean canGox = false, canGoy = false;
            WayRecord wayX = new WayRecord(), wayY = new WayRecord();

            if (s.x < e.x) {
                canGox = test(s.nextXNode(), e, wayX, forbiddenNodes);
            }

            if (s.y < e.y) {
                canGoy = test(s.nextYNode(), e, wayY, forbiddenNodes);
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
        List<WayNode> forbiddenNodes = new ArrayList<>();

        mapWays.test(new WayNode(0, 0), new WayNode(2, 2), root, forbiddenNodes);

        try {
            root.showWays();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        root = new WayRecord();
        forbiddenNodes.add(new WayNode(1, 2));
        forbiddenNodes.add(new WayNode(1, 1));
        mapWays.test(new WayNode(0, 0), new WayNode(2, 2), root, forbiddenNodes);

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
