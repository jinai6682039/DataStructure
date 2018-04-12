package com.github.alexhanxs.datastructure;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Alexhanxs on 2018/3/23.
 */

public class MyQueue {

    public MyQueue () {
        queue = new LinkedList<>();
    }

    private LinkedList<Node> queue;

    public void push(Node e) {
        queue.addLast(e);
    }

    public Node pop() {
        return queue.removeFirst();
    }

    public boolean isAllNullValue() {

        for (Iterator<Node> iterator = queue.iterator(); iterator.hasNext();) {
           Node node = iterator.next();
           if (node != null)
               return false;
        }

        return true;
    }

    public Node getFirst() {
        return queue.getFirst();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    private static Node buildtTree() {
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

        return A;
    }

    public static boolean isCompleteBinaryTree(Node root) {
        if (root == null)
            return false;

        MyQueue queue = new MyQueue();
        queue.push(root);
        Node cur = queue.getFirst();
        while (cur != null) {
            queue.push(cur.left);
            queue.push(cur.right);
            queue.pop();
            cur = queue.getFirst();
        }

        while (!queue.isEmpty()) {
            if (queue.getFirst() != null)
                return false;
            queue.pop();
        }

        return true;
    }

    public static void first(Node root, StringBuilder record) {

        if (root == null)
            return;

        root.seeNode();
        record.append(root.item);
        first(root.left, record);
        first(root.right, record);
    }

    public static void middle(Node root, StringBuilder record) {
        if (root == null)
            return;

        middle(root.left, record);
        root.seeNode();
        record.append(root.item);
        middle(root.right, record);
    }

    public static void behind(Node root, StringBuilder record) {
        if (root == null)
            return;

        behind(root.left, record);
        behind(root.right, record);
        root.seeNode();
        record.append(root.item);
    }

    public static void main(String[] args) {
        Node node = buildtTree();
        StringBuilder builder = new StringBuilder();
        if (!isCompleteBinaryTree(node)) {
            middle(node, builder);
            builder.append("\n");
            behind(node, builder);
        }
        System.out.print(builder.toString());
    }
}
