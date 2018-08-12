package com.github.alexhanxs.datastructure;

import java.io.Serializable;

/**
 * Created by Alexhanxs on 2018/8/12.
 */

public class WayNode implements Serializable {
    public int x;
    public int y;

    public WayNode(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String getNodeInfo() {
        return "[" + x + ", " + y + "]";
    }

    public WayNode nextXNode() {
        return new WayNode(x + 1, y);
    }

    public WayNode nextYNode() {
        return new WayNode(x, y + 1);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof WayNode))
            return false;
        return ((WayNode) obj).y == this.y && ((WayNode) obj).x == this.x;
    }
}
