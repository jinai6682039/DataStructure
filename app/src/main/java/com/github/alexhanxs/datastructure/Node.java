package com.github.alexhanxs.datastructure;

/**
 * Created by Alexhanxs on 2018/3/23.
 */

public class Node<E> {

    public static int BLACK = 0x00;
    public static int RED   = 0x01;

    public static Node NIL = new Node(null, null, null, null, BLACK);

    int height;
    E item;
    int color = RED;
    Node<E> left = null;
    Node<E> right = null;
    Node<E> parent = null;

    public Node(E item, Node<E> left, Node<E> right) {
        this.item = item;
        this.left = left;
        this.right = right;
        this.color = RED;
    }

    public Node(E item, Node<E> left, Node<E> right, Node<E> parent) {
        this.item = item;
        this.left = left;
        this.right = right;
        this.parent = parent;
        this.color = RED;
    }

    public Node(E item, Node<E> left, Node<E> right, Node<E> parent, int color) {
        this.item = item;
        this.left = left;
        this.right = right;
        this.parent = parent;
        this.color = color;
    }

    public Node(E item, Node<E> left, Node<E> right, int height) {
        this.item = item;
        this.left = left;
        this.right = right;
        this.height = height;
        this.color = RED;
    }

    public void seeNode() {
        System.out.print(item);
    }

    public int height() {
        return heightRecursive(this, 0);
    }

    public int height(Node<E> root) {
        return heightRecursive(root, 0);
    }

    private int heightRecursive(Node<E> root, int totalHeight) {
        return root != null ? Math.max(heightRecursive(root.left, totalHeight + 1),
                heightRecursive(root.right, totalHeight + 1)) : totalHeight;
    }

}
