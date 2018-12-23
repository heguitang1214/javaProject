package dataStructure.Tree.BinaryTree;

import java.io.Serializable;

/**
 * 二叉树结点实体类
 */
public class BinaryNode<T extends Comparable> implements Serializable {

    public BinaryNode<T> left;//左结点
    public BinaryNode<T> right;//右结点
    public T data;//数据


    public BinaryNode(T data, BinaryNode left, BinaryNode right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }

    public BinaryNode(T data) {
        this(data, null, null);

    }

    /**
     * 判断是否为叶子结点
     */
    public boolean isLeaf() {
        return this.left == null && this.right == null;
    }

}

