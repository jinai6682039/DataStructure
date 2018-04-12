package com.github.alexhanxs.datastructure;

/**
 * Created by Alexhanxs on 2018/3/26.
 */

public class AVLBinTree<E extends Comparable> {

    Node<E> root;

    // left child tree rotate
    public Node<E> nodeTurnLeft(Node<E> node) {
        Node<E> leftChild = node.left;
        node.left = leftChild.right;
        leftChild.right = node;

        node.height = node.height();
        leftChild.height = leftChild.height;

        return leftChild;
    }

    // right child tree rotate
    public Node<E> nodeTurnRight(Node<E> node) {
        Node<E> rightChild = node.right;
        node.right = rightChild.left;
        rightChild.left = node;

        return rightChild;
    }

    // first rotate right child tree of left child node, then rotate left child tree
    public Node<E> nodeTurnLeftAndRight(Node<E> node) {
        node.left = nodeTurnRight(node.left);
        return nodeTurnLeft(node);
    }

    // first rotate left child tree of right child node, then rotate right child tree
    public Node<E> nodeTurnRightAndLeft(Node<E> node) {
        node.right = nodeTurnLeft(node.right);
        return nodeTurnRight(node);
    }

    // LL
    public Node<E> nodeLeftTreeTurnRight(Node<E> node) {
        Node<E> leftChild = node.left;
        node.left = leftChild.right;
        leftChild.right = node;

        return leftChild;
    }

    // RR
    public Node<E> nodeRightTreeTurnLeft(Node<E> node) {
        Node<E> rightChild = node.right;
        node.right = rightChild.left;
        rightChild.left = node;

        return rightChild;
    }

    // LR
    public Node<E> nodeTurnLeftThenTurnRight(Node<E> node) {
        node.left = nodeRightTreeTurnLeft(node.left);
        return nodeLeftTreeTurnRight(node);
    }

    // RL
    public Node<E> nodeTurnRightThenTurnLeft(Node<E> node) {
        node.right = nodeLeftTreeTurnRight(node.right);
        return nodeRightTreeTurnLeft(node);
    }

    public Node<E> insert(E item) {
        Node<E> insertNode = insert(item, root);
        root = insertNode;
        return insertNode;
    }

    public Node<E> insert(E item, Node<E> insertNode) {
        if (insertNode == null) {
            // 如果找到插入的节点，则创建新节点
            insertNode = new Node<E>(item, null, null);
        } else {

            if (item.compareTo(insertNode.item) > 0) {
                // 插入当前节点的右子树
                insertNode.right = insert(item, insertNode.right);

                // 递归判断当前节点是否需要调整
                if (getHeight(insertNode.right) - getHeight(insertNode.left) >= 2) {

                    if (item.compareTo(insertNode.right.item) > 0) {
                        // RR
                        // 插入了当前节点的右孩子的右子树
                        insertNode = nodeRightTreeTurnLeft(insertNode);
                    } else {
                        // RL
                        // 插入了当前节点的右孩子的左子树
                        insertNode = nodeTurnRightThenTurnLeft(insertNode);
                    }
                }

            } else if (item.compareTo(insertNode.item) < 0) {
                // 插入当前节点的左子树
                insertNode.left = insert(item, insertNode.left);

                // 递归判断当前节点是否需要调整
                if (getHeight(insertNode.left) - getHeight(insertNode.right) >= 2) {
                    if (item.compareTo(insertNode.left.item) > 0) {
                        // LR
                        // 插入了当前节点的左孩子的右子树
                        insertNode = nodeTurnLeftThenTurnRight(insertNode);
                    } else {
                        // LL
                        // 插入了当前节点的左孩子的左子树
                        insertNode = nodeLeftTreeTurnRight(insertNode);
                    }
                }

            } else {
                // 重复值 duplicate data
            }
        }
        insertNode.height = insertNode.height();
        return insertNode;
    }

    public Node<E> delete(E item) {
        root = delete(item, root);
        return root;
    }

    public Node<E> delete(E item, Node<E> deleteNode) {
        if (item.compareTo(deleteNode.item) > 0) {
            deleteNode.right = delete(item, deleteNode.right);
        } else if (item.compareTo(deleteNode.item) < 0) {
            deleteNode.left = delete(item, deleteNode.left);
        } else {
            if (deleteNode.right == null && deleteNode.left == null) {
                // 删除叶子节点的节点
                deleteNode = null;
            } else if (deleteNode.right == null && deleteNode.left != null) {
                // 删除单左子树的节点
                Node<E> max = deleteNode.left;
                while (max.right != null) {
                    max = max.right;
                }

                deleteNode.item = max.item;
                deleteNode.left = delete(max.item, deleteNode.left);
            } else {
                // 删除双子树的节点 or
                // 删除单右子树的节点
                Node<E> min = deleteNode.right;
                while (min.left != null) {
                    min = min.left;
                }
                deleteNode.item = min.item;
                deleteNode.right = delete(min.item, deleteNode.right);
            }
        }

        // 进行旋转调整深度
        if (deleteNode != null) {
            if (getHeight(deleteNode.left) - getHeight(deleteNode.right) >= 2) {
                if (getHeight(deleteNode.left.left) > getHeight(deleteNode.left.right)) {
                    // LL
                    deleteNode = nodeLeftTreeTurnRight(deleteNode);
                } else {
                    // LR
                    deleteNode = nodeTurnLeftThenTurnRight(deleteNode);
                }
            } else if (getHeight(deleteNode.left) - getHeight(deleteNode.right) <= -2) {
                if (getHeight(deleteNode.right.left) > getHeight(deleteNode.right.right)) {
                    // RL
                    deleteNode = nodeTurnRightThenTurnLeft(deleteNode);
                } else {
                    // RR
                    deleteNode = nodeRightTreeTurnLeft(deleteNode);
                }
            }
        }

        return deleteNode;
    }

    public int getHeight(Node node) {
        if (node == null)
            return 0;
        else
            return node.height();
    }

//
//    public Node<E> insert(E item, Node<E> insertNode) {
//
//        if (insertNode == null) {
//            insertNode = new Node<>(item, null, null);
//        } else {
//            if (item.compareTo(insertNode.item) > 0) {
//                insertNode.right = insert(item, insertNode.right);
//                if (insertNode.right.height() - insertNode.left.height() >= 2) {
//                    if (item.compareTo(insertNode.right.item) > 0) {
//                        // RR
//                        nodeTurnRight(insertNode);
//                    } else {
//                        // RL
//                        nodeTurnRightAndLeft(insertNode);
//                    }
//                }
//            } else if (item.compareTo(insertNode.item) < 0) {
//                insertNode.left = insert(item, insertNode.left);
//                if (insertNode.left.height() - insertNode.right.height() >= 2) {
//                    if (item.compareTo(insertNode.left.item) > 0) {
//                        // LR
//                        nodeTurnLeftAndRight(insertNode);
//                    } else {
//                        // LL
//                        nodeTurnLeft(insertNode);
//                    }
//                }
//            } else {
//                // duplicate data
//            }
//        }
//
//        insertNode.height = insertNode.height();
//        return insertNode;
//    }

    public void floorSee(Node<E> root, StringBuilder record) {
        MyQueue queue = new MyQueue();
        queue.push(root);
        Node cur = queue.getFirst();

        while (!queue.isEmpty()) {
            if (!queue.isAllNullValue()) {
                if (cur != null) {
                    record.append(cur.item).append("_");
                    queue.push(cur.left);
                    queue.push(cur.right);
                } else {
                    record.append("null").append("_");
                }

                queue.pop();
                if (!queue.isEmpty()) {
                    cur = queue.getFirst();
                }
            } else {
                break;
            }
        }
    }


    public void first(Node root, StringBuilder record) {

        if (root == null)
            return;

        root.seeNode();
        record.append(root.item).append("_");
        first(root.left, record);
        first(root.right, record);
    }

    public static void main(String[] args) {
        AVLBinTree<Integer> tree = new AVLBinTree<>();
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
        tree.floorSee(tree.root, builder);

        tree.delete(24);

        StringBuilder builder1 = new StringBuilder();
        tree.floorSee(tree.root, builder1);

        tree.floorSee(tree.root, builder1);
    }
}
