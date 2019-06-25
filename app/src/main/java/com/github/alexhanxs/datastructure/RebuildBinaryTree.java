package com.github.alexhanxs.datastructure;

public class RebuildBinaryTree {

    static class Node {
        Node left;
        Node right;
        // 左子树到根节点的最大距离
        int maxLeftDistance;
        // 右子树到根节点的最大距离
        int maxRightDistance;

        String value;

        public Node(String value, Node left, Node right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

    public static Node rebuildBinaryTree(String frontOrder, String middleOrder) {
        if ((frontOrder == null || frontOrder.equals("")) && (middleOrder == null || middleOrder.equals(""))) {
            return null;
        }

        Node root = new Node(frontOrder.charAt(0) + "", null, null);

        if (frontOrder.length() == 1) {
            return root;
        }

        char rootChar = frontOrder.charAt(0);

        int rootPosition = findRootNodePosition(rootChar, middleOrder);
        String rightChildFront = frontOrder.substring(1, rootPosition + 1);
        String leftChildFront = frontOrder.substring(rootPosition + 1);

        String rightChildMiddle = middleOrder.substring(0, rootPosition);
        String leftChildMiddle = middleOrder.substring(rootPosition + 1);

        Node rightChild = rebuildBinaryTree(rightChildFront, rightChildMiddle);
        Node leftChild = rebuildBinaryTree(leftChildFront, leftChildMiddle);

        root.left = leftChild;
        root.right = rightChild;

        return root;
    }

    public static int findRootNodePosition(char root, String middleOrder) {
        if (middleOrder == null || middleOrder.equals("")) {
            return -1;
        }
        for (int i = 0; i < middleOrder.length(); i++) {
            if (middleOrder.charAt(i) == root) {
                return i;
            }
        }
        return -1;
    }


    // 判断是否中序和前序是否有效
    public static boolean isStringValid(String frontOrder, String middleOrder) {
        if ((frontOrder == null || frontOrder.equals("")) && (middleOrder == null || middleOrder.equals(""))) {
            return true;
        }

        int rootPosition = findRootNodePosition(frontOrder.charAt(0), middleOrder);
        if (rootPosition == -1) {
            return false;
        }

        String rightChildFront = frontOrder.substring(1, rootPosition + 1);
        String leftChildFront = frontOrder.substring(rootPosition + 1);

        String rightChildMiddle = middleOrder.substring(0, rootPosition);
        String leftChildMiddle = middleOrder.substring(rootPosition + 1);

        return isStringValid(rightChildFront, rightChildMiddle) && isStringValid(leftChildFront, leftChildMiddle);
    }

    public static void main(String[] args) {

        String frontString = "abdgcef";
        String middleString = "dbgaecf";

        System.out.println("isValid = " + isStringValid(frontString, middleString));

        String frontString1 = "abc";
        String middleString1 = "abc";

        System.out.println("isValid = " + isStringValid(frontString1, middleString1));

        Node root = rebuildBinaryTree(frontString1, middleString1);
    }
}
