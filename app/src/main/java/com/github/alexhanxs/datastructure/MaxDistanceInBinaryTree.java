package com.github.alexhanxs.datastructure;

// 编程之美 3.8求二叉树中节点的最大距离
public class MaxDistanceInBinaryTree {

    public static int maxDistance = 0;

    static class Node {
        Node left;
        Node right;
        // 左子树到根节点的最大距离
        int maxLeftDistance;
        // 右子树到根节点的最大距离
        int maxRightDistance;

        int value;

        public Node(int value, Node left, Node right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

    private static void findMaxDistance(Node root) {
        if (root.left == null) {
            root.maxLeftDistance = 0;
        } else {
            findMaxDistance(root.left);
        }

        if (root.right == null) {
            root.maxRightDistance = 0;
        } else {
            findMaxDistance(root.right);
        }

        if (root.left != null) {
            root.maxLeftDistance = Math.max(root.left.maxLeftDistance, root.left.maxRightDistance) + 1;
        }

        if (root.right != null) {
            root.maxRightDistance = Math.max(root.right.maxLeftDistance, root.right.maxRightDistance) + 1;
        }

        if (root.maxRightDistance + root.maxLeftDistance > maxDistance) {
            maxDistance = root.maxLeftDistance + root.maxRightDistance;
        }
    }

    public static void main(String[] args) {
        Node a = new Node(1, null, null);
        Node b = new Node(2, a, null);
        Node c = new Node(3, b, null);

        Node d = new Node(4, null, null);
        Node e = new Node(5, null, d);

        Node f = new Node(6, null, null);

        Node g = new Node(7, e, f);

        Node h = new Node(8, c, g);
        Node i = new Node(9, null, h);

        maxDistance = 0;

        findMaxDistance(i);

        System.out.println("max distance = " + maxDistance);
    }
}
