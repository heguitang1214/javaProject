package algorithm.search;

import java.util.Arrays;
import java.util.Random;

/**
 * 跳跃表：跳表的一种实现方法。跳表中存储的是正整数，并且存储的是不重复的。
 *
 * @author heguitang
 */
public class SkipList {

    /**
     * 链表最大层数
     */
    private static final int MAX_LEVEL = 16;

    /**
     * 层数
     */
    private int levelCount = 1;

    /**
     * 带头链表
     */
    private Node head = new Node();

    /**
     * 随机函数
     */
    private Random random = new Random();

    /**
     * 添加节点
     *
     * @param value 节点数据
     */
    private void insert(int value) {
        // 获取当前节点的索引层数
        int level = randomLevel();
        System.out.println("当前数据：" + value + " 的索引层数为：" + level);

        Node newNode = new Node();
        newNode.data = value;
        newNode.maxLevel = level;
        Node[] update = new Node[level];
        for (int i = 0; i < level; i++) {
            update[i] = head;
        }

        // 在update[]中记录每个级别小于insert value的最大值
        Node p = head;
        for (int i = level - 1; i >= 0; i--) {
            while (p.forwards[i] != null && p.forwards[i].data < value) {
                p = p.forwards[i];
            }
            // 在搜索路径中使用更新保存节点
            update[i] = p;
        }

        // 在搜索路径节点下一个节点成为单词的新节点（下一个）
        for (int i = 0; i < level; ++i) {
            newNode.forwards[i] = update[i].forwards[i];
            update[i].forwards[i] = newNode;
        }

        // 更新节点高度
        if (levelCount < level) {
            levelCount = level;
        }
    }

    /**
     * 查询节点
     *
     * @param value 节点数据
     * @return 查找节点
     */
    public Node find(int value) {
        Node p = head;
        for (int i = levelCount - 1; i >= 0; --i) {
            while (p.forwards[i] != null && p.forwards[i].data < value) {
                p = p.forwards[i];
            }
        }

        if (p.forwards[0] != null && p.forwards[0].data == value) {
            return p.forwards[0];
        } else {
            return null;
        }
    }

    /**
     * 随机 level 次，如果是奇数层数 +1，防止伪随机
     *
     * @return 随机层数
     */
    private int randomLevel() {
        int level = 1;
        for (int i = 1; i < MAX_LEVEL; i++) {
            if (random.nextInt() % 2 == 1) {
                level++;
            }
        }
        return level;
    }


    /**
     * 跳跃表节点：没有引入next指针
     */
    private static class Node {
        /**
         * 节点数据
         */
        private int data = -1;

        /**
         *
         */
        private Node[] forwards = new Node[MAX_LEVEL];

        /**
         * 节点最大层数
         */
        private int maxLevel = 0;

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("{ data: ");
            sb.append(data);
            sb.append("; levels: ");
            sb.append(maxLevel);
            sb.append("; ");
//            sb.append("[长度：").append(forwards.length).append("]");
//            sb.append(Arrays.toString(forwards));
            sb.append(" }");
            return sb.toString();
        }
    }


    public static void main(String[] args) {
        SkipList skipList = new SkipList();
        skipList.insert(10);
        skipList.insert(12);
        skipList.insert(15);
        skipList.insert(18);
        skipList.insert(20);
        skipList.insert(23);

        Node node = skipList.find(15);
        System.out.println(node);
        System.out.println(Arrays.toString(node.forwards));



        System.out.println(skipList.find(17));
        System.out.println(skipList.find(18));
        System.out.println(skipList.find(20));
        System.out.println(skipList.find(23));
    }
}
