package dataStructure.Tree.BinaryTree;

import dataStructure.Queue.LinkedQueue;
import dataStructure.Stack.LinkedStack;
import java.util.*;

/**
 * 二叉树的实现操作
 *      -树都具备递归的结构，它们都拥有着一致的原子结构，这也就是为什么树可以使用递归定义的原因，
 *      即使是一个十分复杂的树，我们也可以简化为原子的结构的求解过程，毕竟它们本质上是同类问题。
 *      -二叉树的存储结构主要采用的是链式存储结构，顺序存储结构仅适用于完全二叉树或满二叉树.
 *      采用二叉链表存储结构，每个结点只存储了到其孩子结点的单向关系，而没有存储到父结点的关系，
 *      这样的话，每次要获取父结点时将消耗较多的时间，因为需要从root根结点开始查找，
 *      花费的时间是遍历部分二叉树的时间，而且与该结点的位置有关。
 */
public class BinarySearchTree<T extends Comparable> implements Tree<T> {

    BinaryNode<T> root;

    public BinarySearchTree() {
        root = null;
    }


    @Override
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * 计算大小
     */
    @Override
    public int size() {
        return size(root);
    }

    /**
     * 递归实现：定义根节点root后，再用subtree实现递归
     */
    private int size(BinaryNode<T> subtree) {
        if (subtree == null)
            return 0;
        else {
            //对比汉诺塔:H(n)=H(n-1) + 1 + H(n-1)
            return size(subtree.left) + 1 + size(subtree.right);
//            int j = size1(subtree.left);
//            int k = size1(subtree.right);
//            return j + 1 + k;
        }
    }
//    private int size1(int i, BinaryNode<T> subtree) {
//        if (subtree == null)
//            return 0;
//        else {
//            int j = size1(i, subtree.left);
//            //在第二次递归,返回的时候,会因为subtree == null直接返回0,i就是参数本身,不会改变
//            i = i + 1;
//            int k = size1(i, subtree.right);
//            return j + 1 + k;
//        }
//    }

    /**
     * 计算树深度
     */
    @Override
    public int height() {
        return height(root);
    }

    /**
     * 递归实现计算树深度
     * 获取递归的最大深度
     * 分别递归树的左子树和右子树,返回给根节点的时候,
     * 当前的l为根左子树的最大深度,同理为右子树的最大深度
     * 然后判断l h和 r的值
     */
    private int height(BinaryNode<T> subtree) {
        if (subtree == null) {
            return 0;
        } else {
            int l = height(subtree.left);
            int r = height(subtree.right);
            return (l > r) ? (l + 1) : (r + 1);//返回并加上当前层,计算层数
        }
    }

    /**
     * 添加操作
     */
    @Override
    public void insert(T data) {
        if (data == null)
            throw new RuntimeException("数据不能为空!");
        //插入操作
        root = insert(data, root);
    }

    /**
     * 递归实现数节点的插入
     */
    private BinaryNode<T> insert(T data, BinaryNode<T> p) {
        if (p == null) {
            p = new BinaryNode<>(data, null, null);
        }
        //将当前数据与节点数据相比较
        int compareResult = data.compareTo(p.data);
        if (compareResult < 0) {//左子树
            p.left = insert(data, p.left);
        } else if (compareResult > 0) {//右子树
            p.right = insert(data, p.right);
        }
        //数据重复插入,跳过
        return p;
    }

    /**
     * 删除节点
     */
    @Override
    public void remove(T data) {
        if (data == null)
            throw new RuntimeException("需要删除的数据不能为空!");
        //删除结点,并返回当前的根节点
        root = remove(data, root);
    }

    /**
     * 分3种情况
     * 1.删除叶子结点(也就是该节点没有子结点)
     * 2.删除拥有一个子节点的结点(可能是左子节点也可能是右子节点)
     * 3.删除拥有两个子节点的结点
     * 替换当前节点node的数据为data
     */
    private BinaryNode<T> remove(T data, BinaryNode<T> node) {
        //没有找到要删除的元素,递归结束
        if (node == null) {
            return null;
        }
        int compareResult = data.compareTo(node.data);
        if (compareResult < 0) {//左边查找删除结点
            node.left = remove(data, node.left);
        } else if (compareResult > 0) {
            node.right = remove(data, node.right);
        } else if (node.left != null && node.right != null) {//有左子节点和右子节点
            //找到需要删除的节点,并且该节点有两个子节点
            //中继替换,找到右子树中最小的元素并替换需要删除的元素值
            node.data = findMin(node.right);//查找当前节点的最小右子节点,将当前节点替换成右子树的最小节点
            //移除用于替换的结点:替换当前节点的右子节点数据
            node.right = remove(node.data, node.right);

//            node.data = findMax(node.left);
//            node.left = remove(node.data, node.left);
        } else {//只有一个节点,直接替换
            node = (node.left != null) ? node.left : node.right;
        }
        return node;//返回当前结点,最后返回的就是根节点
    }

    /**
     * 查找最小值结点,即最左节点
     */
    @Override
    public T findMin() {
        //没有节点,直接返回null
        if (isEmpty()) return null;
        return findMin(root);
    }

    private T findMin(BinaryNode<T> p) {
        if (p == null)//结束条件
            return null;
        else if (p.left == null)//如果没有左结点,那么t就是最小的
            return p.data;
        return findMin(p.left);
    }


    /**
     * 查找最大值结点,即最右节点
     */
    @Override
    public T findMax() {
        if (isEmpty()) return null;
        return findMax(root);
    }

    private T findMax(BinaryNode<T> p) {
        if (p == null)//结束条件
            return null;
        else if (p.right == null)
            return p.data;
        return findMax(p.right);
    }

    @Override
    public BinaryNode<T> findNode(T data) {
        return findNode(data, root);
    }

    /**
     * 根据data查找结点
     */
    private BinaryNode<T> findNode(T data, BinaryNode<T> p) {
        if (p == null || data == null) {
            return null;
        }
        //计算比较结果
        int compareResult = data.compareTo(p.data);

        if (compareResult < 0) {//从左子树查找
            return findNode(data, p.left);
        } else if (compareResult > 0) {//从右子树查找
            return findNode(data, p.right);
        } else {//获取到匹配的数据
            return p;
        }
    }

    /**
     * 判断树T中是否包含data
     */
    @Override
    public boolean contains(T data) {
        return contains(data, root);
    }

    private boolean contains(T data, BinaryNode<T> p) {
        if (p == null || data == null) {
            return false;
        }
        //计算比较结果
        int compareResult = data.compareTo(p.data);
        //如果小于0,从左子树遍历
        if (compareResult < 0) {
            return contains(data, p.left);
        } else
            return compareResult == 0 || contains(data, p.right);
//        if (compareResult < 0) {
//            return contains(data, p.left);
//        } else if (compareResult > 0) {
//            return contains(data, p.right);
//        } else { //查找到数据
//            return true;
//        }
    }

    @Override
    public void clear() {
        root = null;
    }

    /**
     * 先序遍历
     */
    @Override
    public List<T> preOrder() {
        List<T> list = new ArrayList<>();
        preOrder(list, root);
        return list;
    }

    private void preOrder(List<T> list, BinaryNode<T> subtree) {
        if (subtree != null) {//递归结束条件
            list.add(subtree.data);//当前节点
            preOrder(list, subtree.left);//遍历左子树
            preOrder(list, subtree.right);//遍历右子树
        }
    }

    /**
     * 中序遍历
     */
    @Override
    public List<T> inOrder() {
        List<T> list = new ArrayList<>();
        inOrder(list, root);
        return list;
    }

    private void inOrder(List<T> list, BinaryNode<T> subtree) {
        if (subtree != null) {//递归结束条件
            inOrder(list, subtree.left);//遍历左子树
            list.add(subtree.data);//当前节点
            inOrder(list, subtree.right);//遍历右子树
        }
    }

    /**
     * 后序遍历
     */
    @Override
    public List<T> postOrder() {
        List<T> list = new ArrayList<>();
        postOrder(list, root);
        return list;
    }

    private void postOrder(List<T> list, BinaryNode<T> subtree) {
        if (subtree != null) {//递归结束条件
            postOrder(list, subtree.left);//遍历左子树
            postOrder(list, subtree.right);//遍历右子树
            list.add(subtree.data);//当前节点
        }
    }

    /**
     * 层次遍历
     */
    @Override
    public List<T> levelOrder() {
        List<T> list = new ArrayList<>();
        //使用队列进行记录,存放需要遍历的结点,左结点一定优先右节点遍历
        // //LinkedQueue为自定义的队列
        LinkedQueue<BinaryNode<T>> queue = new LinkedQueue<>();
        BinaryNode<T> node = this.root;
        while (node != null) {
            //记录经过的结点
            list.add(node.data);
            //先按层次遍历结点,左结点一定在右结点之前访问
            if (node.left != null) {
                //孩子结点入队
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
            //访问下一个结点
            node = queue.poll();
        }
        return list;
    }
//=================================================================================
    /**
     * 根据先根和中根遍历算法构造二叉树
     *
     * @param pList      先根/后根遍历次序数组
     * @param inList     中根遍历次序数组
     * @param isPreOrder 是否为先根遍历次序数组,true:先根,false:后根
     *                   Blog : http://blog.csdn.net/javazejian [原文地址,请尊重原创]
     */
    public BinarySearchTree(T[] pList, T[] inList, boolean isPreOrder) {
        if (pList == null || inList == null) {
            throw new RuntimeException("preList or inList can\'t be null");
        }
        if (isPreOrder) {
            //先根/中根次序构建二叉树
            this.root = createBinarySearchTreeByPreIn(pList, inList, 0, pList.length - 1, 0, inList.length - 1);
        } else {
            //后根/中根次序构建二叉树
            this.root = createBinarySearchTreeByPostIn(pList, inList, 0, pList.length - 1, 0, inList.length - 1);
        }
    }

    /**
     * 根据先根和中根遍历算法构造二叉树
     *
     * @param preList  先根遍历次序数组
     * @param inList   中根遍历次序数组
     * @param preStart
     * @param preEnd
     * @param inStart
     * @param inEnd    return root 最终返回的根结点
     */
    public BinaryNode<T> createBinarySearchTreeByPreIn(T[] preList, T[] inList, int preStart, int preEnd, int inStart, int inEnd) {
        //preList[preStart]必须根结点数据,创建根结点root
        BinaryNode<T> p = new BinaryNode<>(preList[preStart]);
        //如果没有其他元素,就说明结点已构建完成
        if (preStart == preEnd && inStart == inEnd) {
            return p;
        }
        //找出中根次序的根结点下标root
        int root = 0;

        for (root = inStart; root < inEnd; root++) {
            //如果中根次序中的元素值与先根次序的根结点相当,则该下标index即为inList中的根结点下标
            if (preList[preStart].compareTo(inList[root]) == 0) {
                break;
            }
        }

        //获取左子树的长度
        int leftLength = root - inStart;
        //获取右子树的长度
        int rightLength = inEnd - root;

        //递归构建左子树
        if (leftLength > 0) {
            //左子树的先根序列：preList[1] , ... , preList[i]
            //左子树的中根序列：inList[0] , ... , inList[i-1]
            p.left = createBinarySearchTreeByPreIn(preList, inList, preStart + 1, preStart + leftLength, inStart, root - 1);
        }

        //构建右子树
        if (rightLength > 0) {
            //右子树的先根序列：preList[i+1] , ... , preList[n-1]
            //右子树的中根序列：inList[i+1] , ... , inList[n-1]
            p.right = createBinarySearchTreeByPreIn(preList, inList, preStart + leftLength + 1, preEnd, root + 1, inEnd);
        }

        return p;
    }


    /**
     * 后根/中根遍历构建二叉树
     *
     * @param postList  后根遍历序列
     * @param inList    中根遍历序列
     * @param postStart
     * @param postEnd
     * @param inStart
     * @param inEnd
     * @return 根结点
     */
    public BinaryNode<T> createBinarySearchTreeByPostIn(T[] postList, T[] inList, int postStart, int postEnd, int inStart, int inEnd) {

        //构建根结点
        BinaryNode<T> p = new BinaryNode<>(postList[postEnd]);

        if (postStart == postEnd && inStart == inEnd) {
            return p;
        }

        //查找中根序列的根结点下标root
        int root = 0;

        for (root = inStart; root < inEnd; root++) {
            //查找到
            if (postList[postEnd].compareTo(inList[root]) == 0) {
                break;
            }
        }

        //左子树的长度
        int leftLenght = root - inStart;
        //右子树的长度
        int rightLenght = inEnd - root;

        //递归构建左子树
        if (leftLenght > 0) {
            //postStart+leftLenght-1:后根左子树的结束下标
            p.left = createBinarySearchTreeByPostIn(postList, inList, postStart, postStart + leftLenght - 1, inStart, root - 1);
        }

        //递归构建右子树
        if (rightLenght > 0) {
            p.right = createBinarySearchTreeByPostIn(postList, inList, postStart + leftLenght, postEnd - 1, root + 1, inEnd);
        }

        return p;
    }



/**=================================非递归实现先序/中序/后序遍历(开始)=============================================**/
    /**
     * 非递归的先序遍历
     * 使用辅助容器栈,进栈的时候遍历左子树,返回的时候遍历右子树
     */
    public List<T> preOrderTraverse() {
        List<T> result = new ArrayList<>();
        //构建用于存放结点的栈
        LinkedStack<BinaryNode<T>> stack = new LinkedStack<>();
        BinaryNode<T> node = this.root;
        //判断stack.isEmpty是判断栈顶没有数据,或者说栈顶数据为null
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                //访问该结点
                result.add(node.data);
                //将已访问过的结点入栈
                stack.push(node);
                //继续访问其左孩子
                node = node.left;
                //若node=null并且栈不为空,则说明已沿左子树访问完一条路径,需要从栈中弹出栈顶结点,并访问其右节点
            } else {
                node = stack.pop();
                //获取当前出栈节点的右子节点,继续循环
                node = node.right;
            }
        }
        return result;
    }

    /**
     * 非递归的中序遍历
     */
    public List<T> inOrderTraverse() {
        List<T> result = new ArrayList<>();
        //构建用于存放结点的栈
        LinkedStack<BinaryNode<T>> stack = new LinkedStack<>();
        BinaryNode<T> node = this.root;
        while (node != null || !stack.isEmpty()){
            //左子树全部入栈
            while(node != null){
                stack.push(node);
                node = node.left;
            }
            if (!stack.isEmpty()){
                node = stack.pop();//弹出当前节点
                result.add(node.data);
                node = node.right;//访问当前节点的右节点
            }
        }
        return result;
    }

    /**
     * 非递归后根遍历
     *  链表记录访问路径,利用树来判断是否进行入栈和出栈的操作
     */
    public List<T> postOrderTraverse() {
        List<T> result = new ArrayList<>();
        //构建用于存放结点的栈
        LinkedStack<BinaryNode<T>> stack = new LinkedStack<>();
        //当前节点
        BinaryNode<T> currentNode = this.root;
        BinaryNode<T> prev = this.root;
        while (currentNode != null || !stack.isEmpty()){
            //把左子树加入栈中,直到叶子结点为止
            while (currentNode != null){
                stack.push(currentNode);
                currentNode = currentNode.left;
            }
            if (!stack.isEmpty()){
                // 获取当前节点的右节点:获取的是栈顶的右子节点的指向,如果为空,就表示可以进行出栈操作
                //如果被访问过也可以进行出栈操作
                BinaryNode<T> temp = stack.peek().right;
                //没有右子节点  因为是同一节点,可直接比较地址值
                if (temp == null || temp == prev){//没有右子节点||右子节点已被访问过
                    currentNode = stack.pop();//记录出栈的元素
                    result.add(currentNode.data);
                    //记录已访问过的结点,判断是否访问过,因为置空只是对链表的操作,并不是对树节点的操作,树的原子结构就是数据+左右节点
                    prev = currentNode;
                    //置空当前栈顶的结点,让栈中的数据继续出栈
                    currentNode = null;
                }else {//有右子节点
                    //将右子节点赋值给当前节点,然后继续去循环查找
                    currentNode = temp;
                }
            }
        }
        return result;
    }

/**=================================非递归实现先序/中序/后序遍历(结束)=============================================**/


/**=================================非递归删除(开始)=============================================**/
    //非递归删除
    public boolean removeUnrecure1(T data) {
        if (data == null) {
            throw new RuntimeException("需要删除掉的数据不能为空!");
        }
        //从根结点开始查找
        BinaryNode<T> current = this.root;
        //记录父结点
        BinaryNode<T> parent = this.root;
        //判断左右孩子的flag
        boolean isLeft = true;
        //找到要删除的结点
        while (data.compareTo(current.data) != 0) {
            //更新父结点记录
            parent = current;
            int result = data.compareTo(current.data);

            if (result < 0) {//从左子树查找
                isLeft = true;
                current = current.left;
            } else if (result > 0) {//从右子树查找
                isLeft = false;
                current = current.right;
            }
            //如果没有找到,返回null
            if (current == null) {
                return false;
            }
        }
        //----------到这里说明已找到要删除的结点
        //删除的是叶子结点
        if (current.left == null && current.right == null) {
            if (current == this.root) {
                this.root = null;
            } else if (isLeft) {
                parent.left = null;
            } else {
                parent.right = null;
            }
        }
        //删除带有一个孩子结点的结点,当current的right不为null
        else if (current.left == null) {
            if (current == this.root) {
                this.root = current.right;
            } else if (isLeft) {//current为parent的左孩子
                parent.left = current.right;
            } else {//current为parent的右孩子
                parent.right = current.right;
            }
        }
        //删除带有一个孩子结点的结点,当current的left不为null
        else if (current.right == null) {
            if (current == this.root) {
                this.root = current.left;
            } else if (isLeft) {//current为parent的左孩子
                parent.left = current.left;
            } else {//current为parent的右孩子
                parent.right = current.left;
            }
        }
        //删除带有两个孩子结点的结点
        else {
            //找到当前要删除结点current的右子树中的最小值元素
            BinaryNode<T> successor = findSuccessor(current);

            if (current == root) {
                this.root = successor;
            } else if (isLeft) {
                parent.left = successor;
            } else {
                parent.right = successor;
            }
            //把当前要删除的结点的左孩子赋值给successor
            successor.left = current.left;
        }
        return true;
    }

/**=================================非递归删除(结束)=============================================**/

    /**
     * 查找中继结点--右子树最小值结点
     */
    public BinaryNode<T> findSuccessor(BinaryNode<T> delNode) {
        BinaryNode<T> successor = delNode;
        BinaryNode<T> successorParent = delNode;
        BinaryNode<T> current = delNode.right;
        //不断查找左结点,直到为空,则successor为最小值结点
        while (current != null) {
            successorParent = successor;
            successor = current;
            current = current.left;
        }
        //如果要删除结点的右孩子与successor不相等,则执行如下操作(如果相当,则说明删除结点)
        if (successor != delNode.right) {
            successorParent.left = successor.right;
            //把中继结点的右孩子指向当前要删除结点的右孩子
            successor.right = delNode.right;
        }
        return successor;
    }

    /**
     * 将树转换成字符串并打印在控制台上，用L表示左子数，R表示右子数
     */
    public void print() {
        LinkedList<BinaryNode<T>> tree = getCompleteBinaryTree();
        //获取树的深度
        int depth = height();
        Iterator<BinaryNode<T>> iterator = tree.iterator();

        int maxPosition = 1;

        for (int floor = 1; floor <= depth; floor++) { // 层数，从1开始
            maxPosition = 1 << (floor - 1);//左移相当于1*2^(floor-1)

            //输出元素前需要打印的空白符
            //左移相当于1*2^( depth - floor ) - 1
            printBlank((1 << (depth - floor)) - 1);

            //开始打印元素
            for (int position = 0; position < maxPosition; position++) {
                if (iterator.hasNext()) {
                    BinaryNode<T> node = iterator.next();
                    if (node != null) {
                        System.out.print(node.data);
                    } else {
                        System.out.print(" ");
                    }
                    //再次打印间隔空白符
                    printBlank((1 << (depth - floor + 1)) - 1);
                }
            }
            //换行
            System.out.println();

        }
    }

    /**
     * 打印空白
     */
    private void printBlank(int length) {
        while (length-- > 0) {
            System.out.print(" ");
        }
    }

    /*
     * 将二叉树用空节点补充成完全二叉树，并以水平遍历形式返回
     */
    private LinkedList<BinaryNode<T>> getCompleteBinaryTree() {
        Queue<BinaryNode<T>> queue = new LinkedList<>();
        LinkedList<BinaryNode<T>> tree = new LinkedList<>(); // 把树补充成完全二叉树，放在这个链表中
        queue.add(root);
        BinaryNode<T> empty = null;
        int nodeCount = 1; // 队列中非空节点数
        while (queue.size() > 0 && nodeCount > 0) {
            BinaryNode<T> node = queue.remove();
            if (node != null) {
                nodeCount--;
                tree.add(node);
                BinaryNode<T> left = node.left;
                BinaryNode<T> right = node.right;
                if (left == null) {
                    queue.add(empty);
                } else {
//                    queue.add(linkFlag);
                    queue.add(left);
                    nodeCount++;
                }
                if (right == null) {
                    queue.add(empty);
                } else {
                    queue.add(right);
                    nodeCount++;
                }
            } else {
                tree.add(empty);
                queue.add(empty);
                queue.add(empty);
            }
        }
        return tree;
    }

    /**
     * 测试
     */
    public static void main(String args[]) {
        Integer pre[] = {1, 2, 4, 7, 3, 5, 8, 9, 6};
        Integer in[] = {4, 7, 2, 1, 8, 5, 9, 3, 6};

        String[] preList = {"A", "B", "D", "G", "C", "E", "F", "H"};
        String[] inList = {"D", "G", "B", "A", "E", "C", "H", "F"};
        String[] postList = {"G", "D", "B", "E", "H", "F", "C", "A"};
        /**
         * 先根遍历:A,B,D,G,C,E,F,H
         * 中根遍历:D,G,B,A,E,C,H,F
         * 后根遍历:G,D,B,E,H,F,C,A
         */
        //先根/中根
//        BinarySearchTree<String> cbtree = new BinarySearchTree<>(preList,inList,true);
        //后根/中根
        BinarySearchTree<String> cbtree = new BinarySearchTree<>(postList, inList, false);
//        BinarySearchTree<String> cbtree = new BinarySearchTree<>();
//        cbtree.printTree(cbtree.root);
//        BinarySearchTree<Integer> cbtree = new BinarySearchTree<>();
//        cbtree.insert(10);
//        cbtree.insert(40);
//        cbtree.insert(2);
//        cbtree.insert(90);
//        cbtree.insert(11);
//        cbtree.insert(9);
//        cbtree.insert(30);
//        cbtree.insert("A");
//        cbtree.insert("B");
//        cbtree.insert("C");
//        cbtree.insert("D");
//        cbtree.insert("E");
//        cbtree.insert("F");
        System.out.println("先根遍历:" + cbtree.preOrder());
//        System.out.println("非递归先根遍历:"+cbtree.preOrderTraverse());
        System.out.println("中根遍历:" + cbtree.inOrder());
//        System.out.println("非递归中根遍历:"+cbtree.inOrderTraverse());
        System.out.println("后根遍历:" + cbtree.postOrder());
//        System.out.println("非递归后根遍历:"+cbtree.postOrderTraverse());
//        System.out.println("查找最大结点(根据搜索二叉树):"+cbtree.findMax());
//        System.out.println("查找最小结点(根据搜索二叉树):"+cbtree.findMin());
//        System.out.println("判断二叉树中是否存在E:"+cbtree.contains("E"));
//        System.out.println("删除的结点返回根结点:"+cbtree.remove("E",cbtree.root).data);
//
//        System.out.println("findNode->"+cbtree.findNode("D",cbtree.root).data);
//        System.out.println("删除E结点:先根遍历:" + cbtree.preOrder());
        System.out.println("树的结构如下:");
        cbtree.print();

    }
}
