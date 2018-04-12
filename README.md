##数据结构

所有代码都实现在仓库中

###1、判断二叉树是否为完全二叉树
使用**队列**将二叉树**层序遍历**。遍历时，如果当前节点的孩子节点为NULL，则用**空节点代替**。在完全遍历完一颗二叉树后，在依次出队，**直到第一次遇见队首元素为空节点**，这种情况可以分为两种场景：
1、此二叉树为**完全二叉树**，则剩下的队列中的节点全部都是叶子节点的**空孩子节点**。
2、此二叉树为**非完全二叉树**，则剩下的队列中还会存在**非空节点**。

层序遍历检测二叉树是否为完全二叉树：
```java
    public boolean isCompleteBinaryTree(Node root) {
        if (root == null)
            return false;

        MyQueue queue = new MyQueue();
        Node cur = root;
        queue.push(cur);
        while (cur != null) {
            queue.push(cur.left);
            queue.push(cur.right);

            queue.pop();
            cur = queue.getFirst();
        }

        while (!queue.isEmpty()) {
            if (queue.getFirst() != null) {
                return false;
            }
            queue.pop();
        }
        
        return true;
    }
```

###2、完全二叉小顶堆
左右子树的值都比其父节点的值大的完全二叉树。当一棵树为小顶堆，则其左右子树也是小顶堆。

对于完全二叉树的层序遍历所得到的序号有个特点：
**leftNo = parentNo * 2 + 1**
**rightNo = parentNo * 2 + 2**
**parentNo = (nodeNo - 1) / 2**
所以知道任意一个节点的次序，我们可以轻松的知道其父节点和孩子节点次序，这也就是为什么堆可以用数组来表示。
在java中PriorityQueue（优先队列）的结构就是小顶堆。

有两种方法在修改了小顶堆的某个节点后来重新调整新的小顶堆：向上调整与向下调整。

在PriorityQueue的add(E e)方法中就是使用的向上调整，整体逻辑是：将要插入的节点放在**队尾元素**，然后与**其父节点相比较**，如果其值**小于其父节点**的值，则将其**父节点下移**，直到**遇到第一个满足父节点值小于当前添加节点的值**的时候结束。
其具体实现为，时间复杂度为log(n)：
```java
add (E e) {
	int i = size;
	if (i > queue.length) {
		grow(i + 1);
	}
	size = i + 1;
	if (i == 0)
		queue[i] = e;
	else 
		fitUp(i, e);
}

fitUp (int index, E e) {
	while(index > 0) {
		int child = (index - 1) >>> 1;
		if (e.compareTo(queue[child]) > 0)
			break;
		queue[index] = queue[child];
		index = child;	
	}
	queue[index] = e;
}
```

在PriorityQueue移除队首元素时，也会去调整原来的小顶堆，使其成为新的小顶堆，这里调用的向下调整方法，其具体逻辑时：
首先查找要**删除的节点的index **，这里的index=0。然后用**队尾元素****替换**当前**删除index元素**，然后与其**左右孩子节点的较小的一方**比较。如果父节点**大于**其孩子节点，则将孩子节点**上移**，直到遇到第一个父节点值**比孩子节点小**的情况结束。其具体实现如下，时间复杂度为log(n)：
```java
E poll() {
	if (size == 0)
		return null;
	int s = --size;
	E result = queue[0];
	if (s == 0) {
		queue[0] = null;
	} else {
		E x = queue[s];
		queue[s] = null;
		fitDown(0, x);
	}
	return result;
}

fitDown(int index, E e) {
	int half = (size - 1) >>> 1;
	while (index < half) {
		int child = index * 2 + 1;
		int right = child + 1;
		E c = queue[child];
		if (right < size && c.copmareTo(queue[right])) {
			child = right;
			c = queue[right];
		}

		if (e.compareTo(c) < 0)
			break;
		queue[index] = c;
		index = child;
	}
	queue[index] = e;
}
```

如果小顶堆要删除指定位置元素，这里也会分为两种情况：
1、删除元素为队尾元素，则直接将队尾置NULL
2、删除元素为非队尾元素，则和上面删除队首元素的逻辑相似：首先确定要删除的元素的index，然后用队尾元素替换该元素，然后移除向下调整。逻辑和上面一样。

###3、二叉排序（查找）树
满足以下条件的二叉树：
1、若**左子树**不为空，则所有左子树上的节点都比根节点要**小**。
2、若**右子树**不为空，则所有右子树上的节点都比根节点要**大**。
3、左右子树都为二次排序树，且**不存在值相等**的节点。

插入和删除二叉排序树的节点时，都需要对原有的二叉排序树做处理，这里的时间复杂度会估计插入节点的顺序有两种极端：
1、对于**乱序插入**的节点，生成的二次排序树的时间复杂度为**log(n)**
2、对于插入节点有着一定顺序，如从大到小，这时生成的二叉排序树是一颗**单向子树**，所以这时查找的时间复杂度和**顺序存储的查找**没有区别，为**n**。

二叉排序的插入：首先与**根节点**进行比较，若插入节点的值**比根节点小**，则继续与根节点的**左孩子**比较。若**比根节点的值大**，则与根节点的**右孩子**相比较。依次比较下去，直到与**第一个空接点**进行比较，这个时候就可以根据此空节点的**父节点与插入节点的大小关系**，确定插入的是空节点的**父节点的左子树**还是**右子树**。
```java
    public void insert(E e) {
        if (root == null)
            root = new Node<E>(e, null, null);

        Node<E> parent = null;
        Node<E> cur = root;

        while (cur != null) {
            if (e.compareTo(cur.item) > 0) {
                parent = cur;
                cur = cur.right;
            } else if (e.compareTo(cur.item) < 0) {
                parent = cur;
                cur = cur.left;
            } else {
                // 相同的值，失败
            }
        }

        Node<E> insert = new Node<E>(e, null, null);

        if (e.compareTo(parent.item) > 0) {
            parent.right = insert;
        } else {
            parent.left = insert;
        }
    }
```

在二叉排序树进行删除时，首先通过根节点向下查找要删除的节点，然后根据删除节点的属性会对下面三种情况进行区分处理，：
1、若删除的节点为**叶子节点**，则直接删除
2、若删除的节点为**单子树节点**，则直接将其子树接在其父节点的**对应子树**上。
3、若删除的节点为**双子树节点**，则从右子树中找到**最小的节点RMin**，其父节点为**RMinP**。如果**RMin**存在右子树，则将其右子树接在**RMinP的左子树**上。然后用**RMin**节点**替换被删除的节点**。

其具体实现如下：
```java
    public void remove(E e) {
        Node<E> cur = root;
        Node<E> curParent = null;

        boolean isLeft = false;

        // 查找对应要删除的节点
        while(cur != null) {
            if (e.compareTo(cur.item) > 0) {
                curParent = cur;
                cur = cur.right;
                isLeft = false;
            } else if (e.compareTo(cur.item) < 0){
                curParent = cur;
                cur = cur.left;
                isLeft = true;
            } else {
                break;
            }
        }

        if (cur == null) {
            // 不存在此节点
            return;
        }

		// 双子树节点
        if (cur.right != null && cur.left != null) {
            Node<E> RMin = cur.right;
            Node<E> RMinP = cur;

			// 查找右子树最小节点
            while (RMin.left != null) {
                RMinP = RMin;
                RMin = RMin.left;
            }

			// 若最小节点存在右子树，则将右子树接在最小节点的父节点的左子树上
            RMinP.left = null;
            if (RMin.right != null) {
                RMinP.left = RMin.right;
            }

			// 替换节点
            if (isLeft) {
                curParent.left = RMin;
            } else {
                curParent.right = RMin;
            }
            RMin.right = cur.right;
            RMin.left = cur.left;
        } else if (cur.right == null && cur.left == null) { 
        // 叶子节点
            if (isLeft) {
                curParent.left = null;
            } else {
                curParent.right = null;
            }
        } else {
        // 单子树节点
            if (isLeft) {
                if (cur.right != null) {
                    curParent.left = cur.right;
                } else {
                    curParent.left = cur.left;
                }
            } else {
                if (cur.right != null) {
                    curParent.right = cur.right;
                } else {
                    curParent.right = cur.left;
                }
            }
        }
    }
```

###4、平衡（排序）二叉树AVL
平衡二叉树是对二叉排序树的一个改良版，为二叉排序树出现的极端情况做了一个控制：任意一个节点的**左右子树**的**深度相差不能超过1**。

在添加和删除操作中，会产生以下四种情况需要调节:
1、**LL型**：在某个节点的左子树添加一个左孩子节点，导致深度失衡。
2、**LR型**：在某个节点的左子树添加一个右孩子节点，导致深度失衡。
3、**RR型**：在某个节点的右子树添加一个右孩子节点，导致深度失衡。
4、**RL型**：在某个节点的右子树添加一个左孩子节点，导致深度失衡。

对于LL型，此时需要在此失衡节点沿着其**左子树右转**，也就是将**失衡节点**接在其左子节点的**右子树**上，若左子节点有右子树，则将**左子节点的右子树**接在**失衡节点的左子树**上。
对于RR型：此时先将失衡节点沿着其**右子树左转**，也就是将**失衡节点**接在其右孩子节点的**左子树**上，若同时其**右孩子节点拥有左子树**，则将其接在**失衡节点的右子树**上。
对于LR型：此时经过简单的一次旋转是无法修复失衡节点的，需要进行两次旋转。首先将**失衡节点的左孩子节点**沿着其**右子树**进行一次**左转**。然后将**失衡节点**沿着其**左子树右转**。
对于RL型：此时也需要进行两次旋转。首先将**失衡节点的右孩子节点**沿着其**左子树**进行一次**右转**，然后再将**失衡节点**沿着其**右子树**进行一次**左转**。

这里对上述情形的实现：
```java
// LL
public Node<E> nodeLeftTreeTurnRight(Node<E> node) {
	Node<E> leftChild = node.left;
	node.left = leftChild.right;
	leftChild.right = node;
	
	return leftChild;
}

// RR
public Node<E> nodeRightTreeTurnLeft(Node<E> node) {
	Node<E> rightChild= node.right;
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
```

在进行平衡二叉树的插入动作时，其实和二叉排序树的插入实现类似，都是先确定插入的位置，然后从插入的位置递归到根节点，以此判断是否需要进行旋转调整，其具体实现为：
```java
	// 实现一个层序遍历方法用于查看结果
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
```

```java
// 首先实现一个根据任意节点返回其深度的方法
	public int heightRecursive(Node root, int totalHeight) {
		return root != null ? Math.max(heightRecursive(root.left, totalHeight + 1),
		heightRecursive(root.right, totalHeight + 1));
	}

	public int height() {
		retrun heightRecursive(this, 0);
	}

    public int getHeight(Node node) {
        if (node == null)
            return 0;
        else
            return node.height();
    }

    public Node<E> insert(E item) {
        root  = insert(item, root);
        return root;
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
```
而在平衡二叉树进行删除操作时，其逻辑也和二叉排序树的删除逻辑类似，而是再其基础上再加上了调整算法。
与二叉排序树一样，这里的删除需要对几种删除的节点进行特殊区分处理:
在平衡二叉树中寻找要删除的节点**DelNode**
1、对于删除节点为**叶子节点**，则直接删除此节点，然后从**删除节点的父节点**向上遍历到**root节点**，判断是否需要进行旋转调整。
2、对于删除节点仅有**左子树**，则在其**左子树**中找到**最大**的节点**LMax**，用**LMax**替换此删除节点**DelNode**。此时要删除的节点由原来**DelNode**替换为**LMax**，接下来在**原DelNode**的左子树中继续调用删除方法，删除**LMax**节点（重新寻找删除节点，然后继续判断删除节点的类型，直到是叶子节点为止）。
3、对于删除节点仅有**右子树**，则在其**右子树**中找到**最小**的节点**RMin**，用**RMin**替换要删除的节点**DelNode**。此时要**删除的节点**也由**DelNode**替换为**Rmin**节点。接下来在**原DelNode**节点的**右子树**中**删除RMin**节点（重新寻找判断寻找删除节点，根据删除节点的类型来区分处理，直到找到的节点为**叶子节点**）。
4、对于删除节点为双子树节点，则进行的处理和仅有右子树节点（第3种）处理一样，在右子树中寻找最小节点RMin替换该删除节点DelNode，后续处理完全一致。
上述处理的具体实现：
```java
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
                // 寻找左子树的最大节点
                while (max.right != null) {
                    max = max.right;
                }
				// 替换节点
                deleteNode.item = max.item;
                // 替换删除节点，并在左子树中删除新的删除节点
                deleteNode.left = delete(max.item, deleteNode.left);
            } else {
                // 删除双子树的节点 or
                // 删除单右子树的节点
                Node<E> min = deleteNode.right;
                // 寻找右子树的最小节点
                while (min.left != null) {
                    min = min.left;
                }
                // 替换节点
                deleteNode.item = min.item;
                // 替换删除节点，并在右子树中删除新的删除节点
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
```


###5、红黑树
红黑树也是一棵特殊的二叉排序树。与AVL树类似，红黑树也在二叉排序树的基础上又增加了一系列限制，避免了二叉排序树的最差情况的发生。其附加的条件有五个：
1、节点可以是**红色和黑色**。
2、根节点为**黑色**，插入的节点为**红色**。
3、所以的**叶子节点（NIL）都为黑色**。这里的叶子节点不是指那些带有数据的节点，红黑树有一个特殊规定就是所有**带有数据的节点**都必须有**两个子节点**（可以是**带有数据的节点**和作为此**子树结束的标志**的**NIL**节点），所以**红黑树的叶子节点其实都为NIL节点**。
4、每个**红色节点**必须有**两个黑色**的子节点（包括NIL节点），也就是同一条路径上不能存在**两个相邻的红色节点**。
5、任意一个节点到其**叶子节点（NIL叶子节点）**的每条路径上经过的**黑色节点个数**都是相等的

这里的**第四条**其实就保证了从**任意节点**到其**叶子节点**的**最长**路径最多是**最短**路径的**两倍**（最短为全黑，最长为黑红相间）。第三条也说明了红黑树的每个带有值的节点都有两个子节点，其中可能有一到两个NIL节点。

在红黑树进行插入和删除操作时，都有可能破坏上述的五个限制条件，所以需要和AVL一样做一个调整。
这里首先对红黑树的插入做一个简单的介绍：
由于上面第二个条件可知，插入的节点是红色的，所以就会根据其插入的父节点来进行一下特殊处理：
1、如果**父节点**不存在，则其为**root**节点，直接将其颜色修改为**黑色**。
2、如果存在父节点，其**父节点的颜色为黑色**，则当插入红色的节点时，不会破环上述的五个条件的任意一个，**无需做任何处理**。
3、存在父节点，其父亲节点是**红色**的，而且其叔父节点也是**红色**（此时由于其父节点为红色，那么其祖父节点必然存在，而且必定为黑色，所以必定存在叔父节点，这里的**叔父节点**可能是**NIL节点**也可能是**包含数据的红节点**，这是由第四条限制决定的）。新插入红色节点**虽然没有改变路径上黑色节点的个数**，但是破坏了第四条的规定，所以需要进行**颜色调整**。这里将**父节点与叔父节点**的颜色与**祖父节点**的**颜色对调**，使原祖父节点构成的子树依旧满足红黑树。但此次调整可能会**破坏**由**祖父节点开始向上的其他节点的红黑树属性**，所以需要从**祖父节点开始继续进行判断**。
4、存在父节点，其父亲节点为**红色**，其叔父节点为**NIL节点**。这里由于破坏了**第四条定律**，所以需要进行一个旋转和颜色调整，将**重复红色节点转移**到其**叔父子树**上。此时插入的节点可能在其父节点的左右子树上，其父亲节点也有可能在其祖父节点的左右子树上，所以也要区分处理。
这里就和AVL非常相似了，也分为了**LL、LR、RR、RL**四种情况。
这里插入节点设为n、其父节点为p，祖父节点为gp。
LL（n在p的左子树上，p在gp的左子树上）：此时与AVL的LL类似，由于n和p节点为两个相邻的红色节点破坏了第四条限制，所以需要对gp节点的左子树进行一次右旋，由p节点升级为新的子树的根节点，然后更改p节点和pg节点的颜色，使p（pg）的左右子节点各增加一个红色节点。
LR（n在p的右子树上，p在gp的左子树上）：与AVL的LR类似，也要进行两次旋转。首先将p节点沿着其右子树左转，将n节点升级为原gp节点的左子树的根节点。经过第一次旋转就变成了LL模式，接下来按照LL的处理就好了。
RR（n在p的右子树上，p在gp的右子树上）：与AVL的RR类似，见过一次旋转就可以局部修复。对gp节点沿着其右子树进行一次左旋，使p节点升级成新的子树根节点，然后更改p与gp的颜色。
RL（n在p的左子树上，p在gp的右子树上）：与AVL的RL类似，也要经过两次旋转。首先将p节点沿着其左子树进行一次右旋，使n节点升级为新的子树根节点。此时又回到了RR模式，接下来按照RR的处理就好了。

具体代码实现：
```java
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
```


在删除节点时，这里其实也和AVL树类似：首先寻找到要**删除的节点D**。再找到之后，判断是否拥有两个非NIL节点的子树，如果有，则在**右子树找最小**MinR（或**左子树取最大**），然后用**MinR替换删除节点**。此时就要进行删除的节点由D节点变成了MInR节点。与此同时，MinR**最多只有一个非NIL**节点的子节点。所以**最终删除任意节点**都可以转化为**删除具有单一子树的某个节点**（当删除节点有两个NIL节点时，可以将左右任意一个NIL节点当成其子树来进行删除操作）。

在上述转化为删除某个单一子树的节点后，此时**新的要删除的节点**为**D**，其**非NID儿子节点**（在删除节点有两个NIL节点时也可能为NIL节点）为**N**。首先我们会用**N节点替换D节点**。然后会根据**原D节点父亲节点P**、**原D节点兄弟节点S**、**S的左右节点Sl与Sr**来判断后续处理。其情况会有如下几种情况存在：

如果D节点为**红色**，则N节点必为**黑色(NIL节点)**，这时删除D节点（P->D->N => P->N)时不会违返红黑树的任意一个规定。
如果D节点为**黑色**，N节点为**红色**，此时由于D为单子树，所以删除D后，只需要将N节点颜色换成黑色就好了。

如果**D、N节点都为黑色**，则会分为以下几种情况：
1、若**N为新的根节点**，则结束调整。
2、若**P，S，Sl，Sr都为黑色**，则将**S**节点重新绘制为**红色**，然后接着在**P节点处开始向上判断处理**。
3、如果**P为红色**，**S、Sl、Sr都为黑色** ，则将**S与P节点的颜色互相对调**，此时就完成了调整。

**这里的判断基于N为P的左子树，S为P的右子树**
4、**P、Sl**为**任意颜色**、**S为黑色、Sr为红色**。这种情况下，只需要将**S子树上的黑色节点转移一个**到**N子树**，然后**将Sr调整为黑色**，就可以**完成调整**。首先将P沿着S节点向下旋转，使S成为此子树的新的根节点，然后对调S与P的颜色，在将Sr的颜色换为黑色，此时在N所在的左子树上就又加入了一个黑色节点，并且右子树的黑色节点树也没有改变，调整完成。
**这里的判断基于N为P的右子树，S为P的左子树**
5、**P、Sr**为**任意颜色**、**S为黑色、Sl为红色**。这种情况下，与第四种类似，也是将左子树的一个黑色节点转移到右子树上，然后将Sl修改为黑色。首先P沿着S节点子树向下旋转，然后交换P与S的颜色，最后将Sl颜色修改为黑色，这时候调整就完成了。
在进入**第4和第5两种判断时**，是有条件的-**S为黑色，且S的某个特定的子节点为红色**，所以某些情况下还需进行调整才能满足4、5两条的进入条件。
6、如果**N为P的左子树，S为P的右子树**，此时需要调整的情况是与第4种情况对应：**P任意颜色**、**S， Sr为黑色、Sl为红色**，此时需要再S子树这边沿着Sl子树向上旋转，然后对调S与Sl的颜色，使Sl成为N的新兄弟节点，且拥有一个红色的右子节点。如果**N为P的右子树，S为P的左子树**，此时需要调整的情况是与第5种情况对应：**P任意颜色**、**S， Sl为黑色、Sr为红色**，此时需要将S沿着Sr子树向上旋转然后对调S与Sr的颜色，使Sr成为原S子树的新根节点，此时N拥有了一个拥有左红节点S的兄弟节点Sr。这两种情况再旋转后就可以进入4、5两种进行判断。

以上是几种可以直接进行调整处理的情况，下面还有几种情况需要经过调整来进入上述调整规则。可以看到上述除了第一种情况，其他的N都有一个黑色的兄弟节点，所以接下来的任务就是将不满足N有一个黑色兄的的情况转换为拥有一个黑色的兄弟节点：

7、当**S为红色时**，**P、Sr、Sl必定为黑色**。此时将**P节点沿着S所在的子树**（左右子树都有可能）**向下旋转**，使**S**成为新的**子树根节点**，然后**对调P与S**的颜色，此时**N**就有了一个**黑色**的兄弟节点**Sl或Sr**。由于P为红色，此时就可以进入到3、4、5、6这几种情况进行调整。（**这里调整最多进行三次，7 -> 6 -> 4 or 5**）。

其具体实现如下
```java
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

	// 进行测试
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

```


###总结
上述讲了二叉排序树以及其的两个变体AVL与RBTree。这里再对这几种树的特性进行一个总结：

**二叉排序树（二叉搜索树）**：对任意一个节点，其左子树上的节点恒比此节点小，其右子树上的节点恒比此节点大。
**二叉排序树的插入**：从根节点开始与要插入的值进行比较，如果比当前节点值大，则继续在右子树上进行插入比较，若小，则在左子树上进行比较。直到找到一个NULL的节点，这个节点就是插入节点的位置。
**二叉排序树的删除**：首先在树中进行比较寻找要删除的节点。在找到需要删除的节点后，遵循一个递归原则：当删除节点存在右子树的时候，在右子树中寻找最小的节点替换删除节点；当删除节点不存在右子树，存在左子树时，在左子树上寻找最大的节点代替删除节点；若左右子树都不存在，则直接删除该节点。也就是右子树找最小，左子树找最大，然后用找到的节点替换删除节点，同时在对应的子树上更新需要删除的节点为刚刚寻找到最小或最大节点，继续递归调用删除方法，直到找到一个叶子节点在结束。

**AVL二叉平衡树**：除了拥有二叉排序树的所有特性外，还加入了一个新的限制：任意节点的左右子树高度相差不超过1。这样就避免了二叉排序树的极端情况。使二叉平衡树的查找和删除的时间复杂度在最好和最坏都为log(n)。
**AVL的插入**：首先插入的流程和二叉排序树是一致的，在寻找到插入节点的位置后，从新插入的节点开始，向上递归到根节点进行一个判断--判断是否违反了AVL的高度限制。如果某个节点的左右子树差大于1时，需要对此节点进行一系列旋转操作（对一个节点最多两次），来修复此节点为根节点的子树。这里就要涉及到四种需要调整高度的情形：LL、LR、RR、RL。
LL：由于在某个右孩子为NULL的节点GP的左孩子节点P的**左子树上**插入了一个新的节点N，导致高度失衡。
此时需要将GP沿着其P所在的子树向右旋转，使P成为新的根节点（这里的根节点都是对应子树的）。
LR：由于在某个右孩子为NULL的节点GP的左孩子节点P的**右子树上**插入了一个新的节点N，导致高度失衡。
此时需要进行两次旋转，第一次旋转将P沿着其N节点的子树向下旋转，使N成为GP的子节点，P成为N的子节点。此时就回到了LL的情形，这时就按照LL来进行一次处理。
RR和RL情况与LL和LR类似。
**AVL的删除**：与AVL的插入一样，其和二叉排序树的删除也是类似的。也是在删除的时候左子树找最小节点，右子树找最大节点，然后替换删除节点，递归调用删除方法。直到找到一个叶子节点，将其从树中移除，并从此叶子节点的父节点向上递归到根节点，判断是否需要旋转调整高度。这里的旋转与插入的旋转是一样的处理。

这里可以看到，对于一棵AVL树，其插入和删除进行的旋转操作的次数是未知的，都需要遍历到根节点才结束。这就是为什么AVL树的插入和删除的效率并不高的原因。针对这种情况，可以引进不是追求完全平衡的红黑树。

**RBTree红黑树**：除了拥有二叉排序树的特性外，为了避免二叉平衡树的操作耗时的原因以及二叉排序树的极端情况，引入了新的五条限制：
1、所有节点都是有颜色的（红色或黑色）。
2、根节点为黑色，新插入树中的节点都是红色（没有调整之前）。
3、所有的叶子节点都是黑色的NIL节点，同时每个节点（除NIL节点外）都有左右两个子节点，其中可能存在一个以上的NIL节点。
4、任意一条子树路径上都不存在两个相邻的红色节点,也就是红色节点的子节点必须都是黑色的。
5、任意节点到期NIL节点所经过的黑色节点个数必须相等。

这里可以从这五条性质中读取处一些额外的信息：对于任意节点到期叶子节点的最长路径与最短路径相差最多两倍。NIL节点标志这子树路径的结束。

**RBTree插入**：在对RBTree进行插入操作时，其也遵循二叉平衡树的插入操作。先按照插入值大于当前比较节点的值时去当前节点右子树上寻找插入节点的位置。当插入值小于当前比较节点的值时，在当前节点的左子树上继续寻找。直到找某个NULL的节点位置。此时这个插入节点为N，其父亲节点为P，其祖父节点为GP、其叔父节点为U。
这里就要分几种情况进行区分处理：
1、由于N为红色，当其父亲节点P为黑色时，不需要做任何调整。
2、N为红色、P为红色、GP肯定存在，并且一定为黑色。此时根据U节点颜色来判断所需要进行的操作。
若u为红色，则将p、u的颜色与gp颜色对调。使左右子树各增加了一个黑色节点，在GP为根节点的子树上没有破坏任何规则，但由于经过GP的子树都增加了一个黑色节点，所有需要从GP节点开始继续进行RB树的调整。
若U为黑色、则需要进行旋转调整，将GP节点对应的P所在的子树上的红色节点移动一个到GP的另一个子树上。这样就没有破坏任何规则。此时也可以说是与二叉排序树类似，分为四种情况：LL、LR、RR、RL。
LL：P为GP的左子树、N为P的左子树。
LR：P为GP的左子树、N为P的右子树。
RR：P为GP的右子树、N为P的右子树。
RL：P为GP的右子树、N为P的左子树。
对于LL与RR，都只需要一次旋转，都是将GP沿着P所在的子树向下旋转，使P成为新的子树根节点，然后调换P与GP的颜色，保证红黑树的属性没有遭到破坏。
对于LR与RL，需要进行两次旋转。首先都是将P沿着N所在的子树向下旋转，使N成为GP的子节点，P成为N的子节点。进入对应的LL与RR情形。
这里可以看到对于RBTree的插入的旋转次数是可以预见的，最多不会超过两次。

**RBTree的删除**：这个操作比较复杂，但是还是有规律可循。首先的操作和AVL或者二叉排序树的删除类似。进行一次左子树找最大，右子树找最小的操作。然后使用第一次找到的节点D替换原要进行删除的节点。此时我们需要进行删除的节点就为D节点。这里我们可以看下，D节点最多拥有一个非NIL节点，就算其有两个NIL节点，我们也可以将其中一个NIL当成其子树。所有经过这个，我们可以将RBTree的删除任意节点转化为删除某一个特定的单子树节点的行为。

对于此种情形：需要删除的单子树节点为D、删除节点的子节点N、删除节点的父节点P、删除节点的兄弟节点S、删除节点的兄弟节点的左右子节点Sl、Sr。
首先将D节点用N节点替换，然后根据上述节点的颜色进行一个区分操作：
当D节点为红色，则无需进行任何其他调整。
当D节点为黑色，若N节点为红色，则直接将N节点修改为黑色，就完成了调整。
当D、N都为黑色，则需要进行进一步区分调整：
若P、S、Sr、Sl都为黑色，则需要将S替换为红色，这样左右两子树都减少了一个黑色节点，P节点满足红黑树性质。但由于经过P节点路径都减少了一个黑色节点，所有要对P节点再次进行一个颜色调整判断。
若S、Sr、Sl为黑色，P为红色，可以将P节点与S节点互换颜色，这样将S的黑色节点上提到根节点，是左右子树依旧满足红黑树性质，完成调整。
下面情况对应N为P的左子树：
若S为黑色、Sr为红色，其他节点任意颜色，此时我们可以将S子树上的一个黑色节点转移到N所在子树上。具体是通过P沿着S所在子树向下旋转，是S成为P的父节点。然后调换P与S的颜色，再将Sr的颜色改为黑色。结束调整。
若S为黑色，Sl为红色、Sr为黑色。此时我们需要先转化为上面这种情况，使N节点拥有一个其右子节点为红色的黑色节点。首先将S沿着Sl向下旋转，使S成为Sl的子节点，Sl成为N的新的兄弟节点，然后对调S与Sl的颜色，就进入上面的情况。这里进行**两次旋转**就可以完成调整。
对应N为P的右子树也是有对称的操作。
除此之外，上面情况有一个很明显的共同点：N的兄弟节点为黑色。所以对与S为红色的时候，我们只需进行一次旋转，使N有一个黑色的兄弟节点。当S为红色，P、Sr、Sl都为黑色。所以只需将P沿着S所在的子树向下旋转，使S成为新的根节点、Sl或Sr（分别对应N为P的左子树和右子树）成为N的新的兄弟节点，在对调S与P的颜色。此时N节点就有一个黑色的兄弟节点，就可以进入上面几种情况进行旋转调整。
这里可以看到，在RBTree进行删除时，其旋转操作也是可以遇见的，最多进行三次旋转。这就是为上面RBTree的效率比AVL高的原因。




