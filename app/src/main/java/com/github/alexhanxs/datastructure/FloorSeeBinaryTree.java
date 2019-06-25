package com.github.alexhanxs.datastructure;

import java.util.ArrayList;
import java.util.List;

public class FloorSeeBinaryTree {

    private static void floorSeeBinaryTree(Node root) {
        if (root == null) {
            return;
        }

        List<Node> array = new ArrayList<>();

        int cur = 0;
        int last = 1;

        array.add(root);
        while (cur < array.size()) {

            Node current = array.get(cur);

            System.out.print(current.item + "  ");
            if (current.left != null) {
                array.add(current.left);
            }

            if (current.right != null) {
                array.add(current.right);
            }

            cur++;
            if (cur == last) {
                System.out.println();
                last = array.size();
            }
        }
    }

    // 从下到上，从左到右遍历
    private static void floorSeeBinaryTreePro(Node root) {
        if (root == null) {
            return;
        }

        List<Node> array = new ArrayList<>();

        int cur = 0;
        int last = 2;

        array.add(root);
        array.add(null);
        while (cur < array.size()) {

            Node current = array.get(cur);

            cur++;

            if (current != null) {

                if (current.right != null) {
                    array.add(current.right);
                }

                if (current.left != null) {
                    array.add(current.left);
                }

                if (cur == last - 1) {
                    array.add(null);
                    last = array.size();
                }
            }
        }

        for (int i = array.size() - 1; i >= 0; i--) {
            Node node = array.get(i);
            if (node == null) {
                System.out.println();
            } else {
                System.out.print(node.item + "  ");
            }
        }
    }

    public static void main(String[] args) {
        Node<String> H = new Node<>("H", null, null);
        Node<String> I = new Node<>("I", null, null);
        Node<String> D = new Node<>("D", H, I);
        Node<String> E = new Node<>("E", null, null);
        Node<String> B = new Node<>("B", D, E);

        Node<String> K = new Node<>("K", null, null);
        Node<String> J = new Node<>("J", null, K);
        Node<String> G = new Node<>("G", J, null);
        Node<String> F = new Node<>("F", null, null);
        Node<String> C = new Node<>("C", F, G);
        Node<String> A = new Node<>("A", B, C);

        floorSeeBinaryTree(A);
        floorSeeBinaryTreePro(A);
    }
}
