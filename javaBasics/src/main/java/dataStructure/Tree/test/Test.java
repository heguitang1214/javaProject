package dataStructure.Tree.test;

import dataStructure.Tree.BinaryTree.BinarySearchTree;

/**
 * @author he_guitang
 * @version [1.0 , 2018/8/6]
 */
public class Test {
    public static void main(String[] args) {
        BinarySearchTree<Integer> node = new BinarySearchTree<>();
        node.insert(75);
        node.insert(5);
        node.insert(52);
        node.insert(98);
        node.insert(54);
        node.insert(23);
        node.insert(65);
        node.insert(100);
        node.insert(110);
        node.insert(120);
        node.insert(130);
        node.insert(64);
        node.print();
        node.remove(75);
        System.out.println("=========================================================================================");
        node.print();

        System.out.println("**递归先序遍历" + node.preOrder());
        System.out.println("**递归中序遍历" + node.inOrder());
        System.out.println("**递归后序遍历" + node.postOrder());
        System.out.println("**递归层次遍历" + node.levelOrder());

        System.out.println("非递归先序遍历" + node.preOrderTraverse());
        System.out.println("非递归中序遍历" + node.inOrderTraverse());
        System.out.println("非递归后序遍历" + node.postOrderTraverse());

        System.out.println("深度:" + node.height());
        System.out.println("大小:" + node.size());
        System.out.println("最小:" + node.findMin());
        System.out.println("最大:" + node.findMax());

        System.out.println("是否包含节点:" + node.contains(75));
        System.out.println("查找数据:" + node.findNode(100).data);




    }
}
