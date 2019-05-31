package com.github.alexhanxs.datastructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LianLianKan {

    public static List<Node> map = new ArrayList<>();
    public static int limitX = 10;
    public static int limitY = 10;
    public static int EMPTY = -1;

    static class Node {
        int x;
        int y;
        int score;

        Node(int x, int y) {
            this.x = x;
            this.y = y;
            this.score = EMPTY;
        }

        Node(int x, int y, int score) {
            this.x = x;
            this.y = y;
            this.score = score;
        }

        HashMap<Integer, List<Node>> directAccessNode(int cross) {

            HashMap<Integer, List<Node>> accessNodeMap = new HashMap<>();
            List<Node> beginPoint = new ArrayList<Node>();
            beginPoint.add(this);

            accessNodeMap.put(0, beginPoint);
            accessNodeMap.put(1, directAccessNode());

            if (cross > 0) {

                List<Node> directAccessNode = directAccessNode();
                directAccessNode.add(this);
                int i = 2;

                while (i <= cross) {
                    List<Node> crossAccessNode = crossAccessNode(directAccessNode);
                    if (crossAccessNode == null || crossAccessNode.size() == 0) {
                        break;
                    }
                    accessNodeMap.put(i, crossAccessNode);
                    List<Node> currentAccessNode = new ArrayList<>();
                    directAccessNode.addAll(crossAccessNode);
                    currentAccessNode.addAll(directAccessNode);
                    i++;
                }
            }
            return accessNodeMap;
        }

        List<Node> crossAccessNode(List<Node> sourceNodes) {
            List<Node> crossAccessNode = new ArrayList<>();

            if (sourceNodes == null || sourceNodes.size() == 0) {
                return crossAccessNode;
            }

            for (Node sourceNode : sourceNodes) {
                List<Node> desinationNode = sourceNode.directAccessNode();

                for (Node addNode : desinationNode) {
                    if (!crossAccessNode.contains(addNode) && !sourceNodes.contains(addNode)) {
                        crossAccessNode.add(addNode);
                    }
                }
            }

            return crossAccessNode;
        }

        List<Node> directAccessNode() {

//            System.out.println("current node(" + x + " ," + y + ")");

            List<Node> accessMap = new ArrayList<>();

            int xTemp = x;
            while (xTemp + 1 < limitX) {
                xTemp++;
                Node accessNode = new Node(xTemp, y);

                accessNode.dump();
                if (contains(map, accessNode)) {
                    accessMap.add(accessNode);
                    break;
                } else {
                    Node middleNode = map.get(map.indexOf(accessNode));
                    if (middleNode.score != EMPTY) {
                        accessNode.score = middleNode.score;
                        accessMap.add(accessNode);
                        break;
                    } else {
                        accessMap.add(accessNode);
                    }
                }
            }

            xTemp = x;
            while (xTemp > 0) {
                xTemp--;
                Node accessNode = new Node(xTemp, y);

                if (contains(map, accessNode)) {
                    accessMap.add(accessNode);
                    break;
                } else {
                    Node middleNode = map.get(map.indexOf(accessNode));
                    if (middleNode.score != EMPTY) {
                        accessNode.score = middleNode.score;
                        accessMap.add(accessNode);
                        break;
                    } else {
                        accessMap.add(accessNode);
                    }
                }
            }

            int yTemp = y;
            while (yTemp + 1 < limitY) {
                yTemp++;
                Node accessNode = new Node(x, yTemp);

                if (contains(map, accessNode)) {
                    accessMap.add(accessNode);
                    break;
                } else {
                    Node middleNode = map.get(map.indexOf(accessNode));
                    if (middleNode.score != EMPTY) {
                        accessNode.score = middleNode.score;
                        accessMap.add(accessNode);
                        break;
                    } else {
                        accessMap.add(accessNode);
                    }
                }
            }

            yTemp = y;
            while (yTemp > 0) {
                yTemp--;
                Node accessNode = new Node(x, yTemp);

                if (contains(map, accessNode)) {
                    accessMap.add(accessNode);
                    break;
                } else {
                    Node middleNode = map.get(map.indexOf(accessNode));
                    if (middleNode.score != EMPTY) {
                        accessNode.score = middleNode.score;
                        accessMap.add(accessNode);
                        break;
                    } else {
                        accessMap.add(accessNode);
                    }
                }
            }

            return accessMap;
        }

        int lessCrossToNode(Node destination) {
            if (!map.contains(destination) || destination == null) {
                return 0;
            }

            if (destination.equals(this)) {
                return 0;
            }


            return 0;
        }

        int lessDistanceToNode(Node destination) {
            return 0;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Node && ((Node) obj).x == this.x && ((Node) obj).y == this.y;
        }

        public String dump() {
            return "Node(" + x + " ," + y + "), score = " + score;
        }
    }

    public static boolean contains(List<Node> map, Node destination) {
        if (map == null || map.size() == 0 || destination == null) {
            return false;
        }

        for (Node node : map) {
            if (node.equals(destination) && node.score > -1 && node.score == destination.score)
                return true;
        }

        return false;
    }

    public final static void main(String[] args) {
        for (int y = 0; y < limitY; y++) {
            for (int x = 0; x < limitX; x++) {
                if (y == 5) {
                    if (x >= 1 && x <= 8) {
                        map.add(new Node(x, y, -1));
                        continue;
                    }
                }
                map.add(new Node(x, y, x + y));
            }
        }

        HashMap<Integer, List<Node>> accessNode = map.get(50).directAccessNode(3);

        int size = accessNode.size();
        int i = 0;
        while (i < size) {
            List<Node> crossAccessNode = accessNode.get(i);
            System.out.println("cross count = " + i + " , access node : ");
            for (Node node : crossAccessNode) {
                System.out.println(node.dump());
            }
            i++;
        }
    }
}



