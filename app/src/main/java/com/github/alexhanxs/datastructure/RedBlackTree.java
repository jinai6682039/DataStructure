package com.github.alexhanxs.datastructure;

import static com.github.alexhanxs.datastructure.Node.BLACK;
import static com.github.alexhanxs.datastructure.Node.NIL;
import static com.github.alexhanxs.datastructure.Node.RED;

/**
 * Created by Alexhanxs on 2018/4/8.
 */

public class RedBlackTree<E extends Comparable> {

    Node<E> root;

    /**
     * LL
     *
     * @param node 需要调整的节点
     * @return 下一个需要判断调整的节点
     */
    public Node<E> gPRotateRight(Node<E> node) {

        Node<E> p = node.parent;
        Node<E> gp = p.parent;

        p.color = BLACK;
        gp.color = RED;

        // 向右旋转
        gp.left = p.right;
        p.right = gp;

        // 处理父子关系
        if (gp.left != NIL) {
            gp.left.parent = gp;
        }
        if (gp.parent != null) {
            if (gp.item.compareTo(gp.parent.item) > 0) {
                gp.parent.right = p;
                p.parent = gp.parent;
            } else {
                gp.parent.left = p;
                p.parent = gp.parent;
            }
        } else {
            // 重新赋值根节点
            root = p;
            p.parent = null;
        }

        gp.parent = p;

        return p;
    }

    /**
     * RR
     *
     * @param node 需要调整的节点
     * @return 下一个需要判断调整的节点
     */
    public Node<E> gPRotateLeft(Node<E> node) {

        Node<E> p = node.parent;
        Node<E> gp = p.parent;

        p.color = BLACK;
        gp.color = RED;

        // 向左旋转
        gp.right = p.left;
        p.left = gp;

        // 处理父子关系
        if (gp.right != NIL) {
            gp.right.parent = gp;
        }
        if (gp.parent != null) {
            if (gp.item.compareTo(gp.parent.item) > 0) {
                gp.parent.right = p;
                p.parent = gp.parent;
            } else {
                gp.parent.left = p;
                p.parent = gp.parent;
            }
        } else {
            root = p;
            p.parent = null;
        }

        gp.parent = p;

        return p;
    }

    /**
     * LR
     *
     * @param node 需要调整的节点
     * @return 下一个需要判断调整的节点
     */
    public Node<E> nodeRotateLeftThenRight(Node<E> node) {

        Node<E> p = node.parent;
        Node<E> gp = p.parent;

        // 首先将p节点沿着其右子树左转
        p.right = node.left;
        node.left = p;

        if (p.right != NIL) {
            p.right.parent = p;
        }

        gp.left = node;
        node.parent = gp;
        p.parent = node;
        // 接着进行祖父节点的右旋操作
        return gPRotateRight(p);
    }

    /**
     * RL
     *
     * @param node 需要调整的节点
     * @return 下一个需要判断调整的节点
     */
    public Node<E> nodeRotateRightThenLeft(Node<E> node) {
        Node<E> p = node.parent;
        Node<E> gp = p.parent;

        // 首先将p节点沿着其左子树右转
        p.left = node.right;
        node.right = p;

        if (p.left != NIL) {
            p.left.parent = p;
        }

        node.parent = gp;
        p.parent = node;
        gp.right = node;

        return gPRotateLeft(p);
    }

    public void insert(E data) {
        insert(data, root);
    }

    public void insert(E data, Node<E> insertNodeParent) {

        if (insertNodeParent == null) {
            root = new Node<E>(data, NIL, NIL, null);
            startRebuildRBTree(root);
        } else {
            if (data.compareTo(insertNodeParent.item) > 0) {
                if (insertNodeParent.right != NIL) {
                    insert(data, insertNodeParent.right);
                } else {
                    Node<E> insertNode = new Node<>(data, NIL, NIL, insertNodeParent);
                    insertNodeParent.right = insertNode;
                    startRebuildRBTree(insertNode);
                }
            } else if (data.compareTo(insertNodeParent.item) < 0) {
                if (insertNodeParent.left != NIL) {
                    insert(data, insertNodeParent.left);
                } else {
                    Node<E> insertNode = new Node<>(data, NIL, NIL, insertNodeParent);
                    insertNodeParent.left = insertNode;
                    startRebuildRBTree(insertNode);
                }
            } else {
                // duplicate data
            }
        }
    }

    public Node<E> getGPNode(Node<E> node) {
        if (node.parent != null) {
            return node.parent.parent;
        }
        return null;
    }

    public Node<E> getUncleNode(Node<E> node) {
        if (getGPNode(node) != null) {
            if (node.parent.item.compareTo(getGPNode(node).item) > 0) {
                return getGPNode(node).left;
            } else {
                return getGPNode(node).right;
            }
        }
        return null;
    }

    public void startRebuildRBTree(Node<E> insertNode) {
        if (insertNode.parent == null) {
            insertNode.color = BLACK;
        } else if (insertNode.parent.color == BLACK) {
            return;
        } else if (insertNode.parent.color == RED) {
            if (getUncleNode(insertNode) != null) {
                if (getUncleNode(insertNode).color == RED) {
                    getGPNode(insertNode).color = RED;
                    getUncleNode(insertNode).color = BLACK;
                    insertNode.parent.color = BLACK;
                    startRebuildRBTree(getGPNode(insertNode));
                } else {
                    if (insertNode.parent.item.compareTo(getGPNode(insertNode).item) > 0) {
                        if (insertNode.item.compareTo(insertNode.parent.item) > 0) {
                            // RR
                            gPRotateLeft(insertNode);
                        } else {
                            // RL
                            nodeRotateRightThenLeft(insertNode);
                        }
                    } else {
                        if (insertNode.item.compareTo(insertNode.parent.item) > 0) {
                            // LR
                            nodeRotateLeftThenRight(insertNode);
                        } else {
                            // LL
                            gPRotateRight(insertNode);
                        }
                    }
                }
            }
        }
    }


    // delete

    public boolean isBlack(Node<E>... args) {
        for (Node<E> node : args) {
            if (node.color != BLACK)
                return false;
        }

        return true;
    }

    public boolean isRed(Node<E>... args) {
        for (Node<E> node : args) {
            if (node.color != RED)
                return false;
        }
        return true;
    }

    public void delete(E item) {
        delete(item, root);
    }

    // 寻找删除的节点
    public void delete(E item, Node<E> deleteNode) {
        if (item.compareTo(deleteNode.item) > 0) {
            delete(item, deleteNode.right);
        } else if (item.compareTo(deleteNode.item) < 0) {
            delete(item, deleteNode.left);
        } else {
            if (deleteNode.right != NIL) {
                // 右子树找最小，然后替换删除节点为单子树节点
                Node<E> minR = deleteNode.right;
                Node<E> minRP = deleteNode;

                while (minR.left != NIL) {
                    minRP = minR;
                    minR = minR.left;
                }

                deleteNode.item = minR.item;
                delete(minR);

            } else if (deleteNode.right == NIL && deleteNode.left != NIL) {
                // 左子树找最大，然后替换删除节点
                Node<E> maxL = deleteNode.left;
                Node<E> maxLP = deleteNode;

                while (maxL.right != null) {
                    maxLP = maxL;
                    maxL = maxL.right;
                }

                deleteNode.item = maxL.item;
                delete(maxL);
            } else {
                if (deleteNode.parent == null)
                    // 删除只有一个节点的红黑树
                    root = NIL;
                else {
                    delete(deleteNode);
                }
            }
        }
    }

    public void delete(Node<E> deleteNode) {

        Node<E> parent = deleteNode.parent;
        boolean isParentLeft = parent.left == deleteNode;

        Node<E> child = deleteNode.left != NIL ? deleteNode.left : deleteNode.right;
        boolean isChildLeft = deleteNode.left != NIL;

        if (isParentLeft) {
            parent.left = child;
        } else {
            parent.right = child;
        }

        if (child != NIL)
            child.parent = parent;

        if (deleteNode.color == BLACK) {
            if (child.color == RED) {
                child.color = BLACK;
            } else {
                startRebuildRBTreeByDelete(child, parent);
            }
        }
    }

    //
    public void startRebuildRBTreeByDelete(Node<E> deleteNode, Node<E> parentNode) {
        if (parentNode == null)
            return;

        Node<E> p = parentNode;
        Node<E> s = p.left == deleteNode ? p.right : p.left;
        Node<E> sr = s.right;
        Node<E> sl = s.left;

        boolean isDeleteLeft = p.left == deleteNode;

        if (isBlack(p, s, sr, sl)) {
            s.color = RED;
            startRebuildRBTreeByDelete(p, p.parent);
        } else if (isRed(p) && isBlack(s, sr, sl)) {
            s.color = RED;
            p.color = BLACK;
        } else if (isBlack(s)) {
            if (isDeleteLeft) {
                // N为P的左子树
                if (isRed(sr)) {
                    // N拥有一个右子节点为红色的黑色兄弟节点，进行旋转调整
                    s.color = p.color;
                    p.color = BLACK;
                    sr.color = BLACK;

                    p.right = sl;
                    if (p.right != NIL) {
                        p.right.parent = p;
                    }
                    s.left = p;

                    if (p.parent != null) {
                        s.parent = p.parent;
                        if (p.parent.left == p) {
                            p.parent.left = s;
                        } else {
                            p.parent.right = s;
                        }
                        p.parent = s;
                    } else {
                        root = s;
                        s.parent = null;
                    }

                } else if (isRed(sl)) {
                    // N拥有一个左子节点sl为红色的黑色兄弟节点s，需要进行一次s子树的旋转调整，将s调整为拥有红色右子节点的新s
                    s.color = RED;
                    sl.color = BLACK;

                    s.left = sl.right;
                    if (s.left != NIL) {
                        s.left.parent = s;
                    }
                    sl.right = s;

                    s.parent = sl;
                    sl.parent = p;
                    p.left = sl;

                    // 再次对删除节点进行重建RB树的判断，再次进行一次旋转
                    startRebuildRBTreeByDelete(deleteNode, p);
                }
            } else {
                // N为P的右子树
                if (isRed(sl)) {
                    // N拥有一个左子节点为红色的黑色兄弟节点，进行旋转调整
                    s.color = p.color;
                    p.color = BLACK;
                    sl.color = BLACK;

                    p.left = sr;
                    if (p.left != NIL) {
                        p.left.parent = p;
                    }
                    s.right = p;

                    if (p.parent != null) {
                        s.parent = p.parent;
                        if (p.parent.left == p) {
                            p.parent.left = s;
                        } else {
                            p.parent.right = s;
                        }
                        p.parent = s;

                    } else {
                        root = s;
                        s.parent = null;
                    }

                } else if (isRed(sr)) {
                    // N拥有一个右子节点为红色的兄弟节点，对s子树的右子树进行一次旋转。
                    s.color = RED;
                    sr.color = BLACK;

                    s.right = sr.left;
                    if (s.right != NIL) {
                        s.right.parent = s;
                    }
                    sr.left = s;

                    s.parent = sr;
                    sr.parent = p;
                    p.right = sr;

                    // 在进行一次旋转
                    startRebuildRBTreeByDelete(deleteNode, p);
                }
            }
        } else if (isRed(s)) {
            // 上述节点都是在S为黑色完成的，此时我们需要进行一次旋转，以进入上述几种判断
            // 如果s为红色，p、sl、sr必定为黑色。
            if (isDeleteLeft) {
                // N为p的左子树
                // 将P按照其右子树向下旋转，使sl成为N的新兄弟节点
                p.color = RED;
                s.color = BLACK;

                p.right = sl;
                if (p.right != NIL) {
                    p.right.parent = p;
                }
                s.left = p;

                if (p.parent != null) {
                    s.parent = p.parent;
                    if (p.parent.left == p) {
                        p.parent.left = s;
                    } else {
                        p.parent.right = s;
                    }
                    p.parent = s;
                } else {
                    s.parent = null;
                    root = s;
                }

                startRebuildRBTreeByDelete(deleteNode, p);
            } else {
                // N为p的右子树
                // 将p按照其左子树向下旋转，使sr成为N的新兄弟节点
                p.color = RED;
                s.color = BLACK;

                p.left = sr;
                if (p.left != NIL) {
                    p.left.parent = p;
                }
                s.right = p;

                if (p.parent != null) {
                    s.parent = p.parent;
                    if (p.parent.left == p) {
                        p.parent.left = s;
                    } else {
                        p.parent.right = s;
                    }
                    p.parent = s;
                } else {
                    s.parent = null;
                    root = s;
                }

                startRebuildRBTreeByDelete(deleteNode, p);
            }
        }
    }

    public void floorSee(StringBuilder sb) {
        MyQueue queue = new MyQueue();
        MyQueue queue2 = new MyQueue();

        queue.push(root);

        while (!queue.isEmpty() || !queue2.isEmpty()) {
            MyQueue emptyQ;
            MyQueue fullQ;
           if (queue.isEmpty()) {
               emptyQ = queue;
               fullQ = queue2;
           } else {
               emptyQ = queue2;
               fullQ = queue;
           }

           while (!fullQ.isEmpty()) {
               Node<E> node = fullQ.getFirst();
               fullQ.pop();
               if (node != NIL) {
                   sb.append(node.item).append(node.color == BLACK ? "(B)" : "(R)").append("__");
                   emptyQ.push(node.left);
                   emptyQ.push(node.right);
               } else {
                   sb.append("NIL(B)__");
               }
           }

           sb.append("\n");
        }
    }

    public final static void main(String[] args) {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        StringBuilder builder = new StringBuilder();
        tree.insert(12);
        tree.insert(1);
        tree.insert(9);
        tree.insert(2);
        tree.insert(0);
        tree.insert(11);
        tree.insert(7);
        tree.insert(19);
        tree.insert(4);
        tree.insert(15);
        tree.insert(18);

        tree.insert(5);
        tree.insert(14);
        tree.insert(13);
        tree.insert(10);
        tree.insert(16);

        tree.insert(6);
        tree.insert(3);
        tree.insert(8);
        tree.insert(17);

        tree.delete(12);
        tree.delete(1);
        tree.delete(9);
        tree.delete(2);

        tree.floorSee(builder);

        System.out.print(builder.toString());
//        Log.e("test", builder.toString());
    }

}