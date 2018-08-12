package com.github.alexhanxs.datastructure;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexhanxs on 2018/8/12.
 */

public class WayRecord {

    public WayNode node;

    public boolean canGoX;
    public boolean canGoY;

    public boolean isEnd;

    public WayRecord wayX;
    public WayRecord wayY;

    List<List<WayNode>> ways = new ArrayList<>();

    public void getAllWay() throws IOException, ClassNotFoundException {
        ways.clear();
        if (canGoX) {

            List<WayNode> way = new ArrayList<>();
            way.add(node);
            List<List<WayNode>> wayxs = new ArrayList<>();
            wayxs.add(way);
            ways.addAll(getWay(wayxs, wayX));
        }

        if (canGoY) {

            List<WayNode> way = new ArrayList<>();
            way.add(node);
            List<List<WayNode>> wayys = new ArrayList<>();
            wayys.add(way);
            ways.addAll(getWay(wayys, wayY));
        }

    }

    public List<List<WayNode>> getWay(List<List<WayNode>> ways, WayRecord wayNext) throws IOException, ClassNotFoundException {
        if (wayNext.isEnd) {
            for (List<WayNode> singleWay : ways) {
                singleWay.add(wayNext.node);
            }
            return ways;
        } else {

            List<List<WayNode>> ways_ = new ArrayList<>();
            if (wayNext.canGoX) {
                List<List<WayNode>> wayXs = WayRecord.deepCopy(ways);
                for (List<WayNode> singleWay : wayXs) {
                    singleWay.add(wayNext.node);
                }
                ways_.addAll(getWay(wayXs, wayNext.wayX));
            }

            if (wayNext.canGoY) {
                List<List<WayNode>> wayYs = WayRecord.deepCopy(ways);
                for (List<WayNode> singleWay : wayYs) {
                    singleWay.add(wayNext.node);
                }
                ways_.addAll(getWay(wayYs, wayNext.wayY));
            }

            return ways_;
        }
    }

    public void showWays() throws IOException, ClassNotFoundException {
        getAllWay();

        if (ways != null) {
            System.out.println(ways.size());
            for (List<WayNode> way : ways) {
                StringBuilder sb = new StringBuilder();
                for (WayNode node : way) {
                    sb.append(node.getNodeInfo() + " ");
                }
                System.out.println(sb.toString());
            }
        }
    }

    public static List<List<WayNode>> deepCopy(List<List<WayNode>> src) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(src);

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        @SuppressWarnings("unchecked")
        List<List<WayNode>> dest = (List<List<WayNode>>) in.readObject();
        return dest;
    }
}
