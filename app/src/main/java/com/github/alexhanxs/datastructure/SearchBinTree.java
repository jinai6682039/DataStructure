package com.github.alexhanxs.datastructure;

/**
 * Created by Alexhanxs on 2018/3/23.
 */

public class SearchBinTree<E extends Comparable<E>> {

    private Node<E> root;

    public boolean search(E e) {
        Node<E> cur = root;
        while (cur != null) {
            if (e.compareTo(cur.item) < 0) {
                cur = cur.left;
            } else if (e.compareTo(cur.item) > 0) {
                cur = cur.right;
            } else {
                return true;
            }
        }
        return false;
    }

    public boolean insert(E e) {
        if (root == null) {
            root = new Node<>(e, null, null);
            return true;
        }
        Node<E> curParent = null;
        Node<E> cur = root;
        while (cur != null) {
            if (e.compareTo(cur.item) < 0) {
                curParent = cur;
                cur = cur.left;
            } else if (e.compareTo(cur.item) > 0) {
                curParent = cur;
                cur = cur.right;
            } else {
                // already have same item in the tree
                return false;
            }
        }
        cur = new Node<>(e, null, null);
        if (e.compareTo(curParent.item) < 0) {
            curParent.left = cur;
        } else {
            curParent.right = cur;
        }

        return true;
    }


//    public void remove(E e) {
//        Node<E> cur = root;
//        Node<E> curParent = null;
//
//        boolean isLeft = false;
//
//        // 查找对应要删除的节点
//        while(cur != null) {
//            if (e.compareTo(cur.item) > 0) {
//                curParent = cur;
//                cur = cur.right;
//                isLeft = false;
//            } else if (e.compareTo(cur.item) < 0){
//                curParent = cur;
//                cur = cur.left;
//                isLeft = true;
//            } else {
//                break;
//            }
//        }
//
//        if (cur == null) {
//            // 不存在此节点
//            return;
//        }
//
//        if (cur.right != null && cur.left != null) {
//            Node<E> RMin = cur.right;
//            Node<E> RMinP = cur;
//
//            while (RMin.left != null) {
//                RMinP = RMin;
//                RMin = RMin.left;
//            }
//
//            RMinP.left = null;
//            if (RMin.right != null) {
//                RMinP.left = RMin.right;
//            }
//
//            if (isLeft) {
//                curParent.left = RMin;
//            } else {
//                curParent.right = RMin;
//            }
//
//            RMin.right = cur.right;
//            RMin.left = cur.left;
//        } else if (cur.right == null && cur.left == null) {
//            if (isLeft) {
//                curParent.left = null;
//            } else {
//                curParent.right = null;
//            }
//        } else {
//            if (isLeft) {
//                if (cur.right != null) {
//                    curParent.left = cur.right;
//                } else {
//                    curParent.left = cur.left;
//                }
//            } else {
//                if (cur.right != null) {
//                    curParent.right = cur.right;
//                } else {
//                    curParent.right = cur.left;
//                }
//            }
//        }
//    }

    public boolean remove(E e) {
        Node<E> cur = root;
        Node<E> curParent = null;
        boolean isLeft = false;
        while (cur != null) {
            if (e.compareTo(cur.item) < 0) {
                curParent = cur;
                cur = cur.left;
                isLeft = true;
            } else if (e.compareTo(cur.item) > 0) {
                curParent = cur;
                cur = cur.right;
                isLeft = false;
            } else {
                break;
            }
        }
        if (cur == null)
            return false;

        // 双子树
        if (cur.right != null && cur.left != null) {
            Node rightMin = cur.right;
            Node rightMinParent = cur;

            // 找到右子树中最小的节点
            while (rightMin.left != null) {
                rightMinParent = rightMin;
                rightMin = rightMin.left;
            }
            // 先清除最小节点的父节点的左子树（最小节点肯定是子树的最左下节点）
            rightMinParent.left = null;
            // 如果最小节点含有右子树，则将最小节点的右子树接在最小节点的父节点的左子树上
            if (rightMin.right != null) {
                rightMinParent.left = rightMin.right;
            }
            // 替换节点
            if (isLeft) {
                curParent.left = rightMin;
            } else {
                curParent.right = rightMin;
            }

            // 重新接回左子树和右子树
            rightMin.left = cur.left;
            rightMin.right = cur.right;
        } else if (cur.right == null && cur.left == null) {
            if (isLeft) {
                curParent.left = null;
            } else {
                curParent.right = null;
            }
        } else {
            if (isLeft) {
                if (cur.right == null) {
                    curParent.left = cur.left;
                } else {
                    curParent.left = cur.right;
                }
            } else {
                if (cur.right == null) {
                    curParent.right = cur.left;
                } else {
                    curParent.right = cur.right;
                }
            }
        }
        return true;
    }

    public void first(Node root, StringBuilder record) {

        if (root == null)
            return;

        root.seeNode();
        record.append(root.item).append("_");
        first(root.left, record);
        first(root.right, record);
    }

    private Node buildtTree() {
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

    public static void main(String[] args) {
        SearchBinTree<Integer> tree = new SearchBinTree<>();
        tree.insert(45);
        tree.insert(24);
        tree.insert(53);
        tree.insert(12);
        tree.insert(37);
        tree.insert(93);
        tree.insert(11);
        tree.insert(15);
        tree.insert(30);
        tree.insert(38);
        tree.insert(28);
        tree.insert(25);
        tree.insert(29);

        StringBuilder builder = new StringBuilder();
        tree.first(tree.root, builder);
//
//        tree.remove(24);
//        builder = new StringBuilder();
//        tree.first(tree.root, builder);

        System.out.print(tree.root.height());
        System.out.print(tree.root.left.right.height());
        System.out.print(tree.root.right.right.height());
        System.out.print(builder.toString());
    }
}
