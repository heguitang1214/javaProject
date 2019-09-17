package algorithm.search.test;

import java.util.Random;

/**
 * 不固定层级的跳跃表
 *
 * @author Tang
 */
public class SkipList<T> {

    /**
     * 跳跃表头指针、尾指针
     */
    private SkipListNode<T> head, tail;

    /**
     * 节点总数
     */
    private int nodes;

    /**
     * 层数
     */
    private int listLevel;

    /**
     * 用于投掷硬币，随机数
     */
    private Random random;

    /**
     * 向上提升一个的概率(每两个节点就向上提升)
     */
    private static final double PROBABILITY = 0.5;

    public SkipList() {
        random = new Random();
        clear();
    }

    /**
     * 清空跳跃表
     */
    public void clear() {
        head = new SkipListNode<>(SkipListNode.HEAD_KEY, null);
        tail = new SkipListNode<>(SkipListNode.TAIL_KEY, null);
        horizontalLink(head, tail);
        listLevel = 0;
        nodes = 0;
    }

    public boolean isEmpty() {
        return nodes == 0;
    }

    public int size() {
        return nodes;
    }

    /**
     * 在最下面一层，找到要插入的位置前面的那个key
     */
    private SkipListNode<T> findNode(int key) {
        SkipListNode<T> p = head;
        while (true) {
            while (p.right.key != SkipListNode.TAIL_KEY && p.right.key <= key) {
                p = p.right;
            }
            if (p.down != null) {
                p = p.down;
            } else {
                break;
            }
        }
        return p;
    }

    /**
     * 查找是否存储key，存在则返回该节点，否则返回null
     */
    public SkipListNode<T> search(int key) {
        SkipListNode<T> p = findNode(key);
        if (key == p.getKey()) {
            return p;
        } else {
            return null;
        }
    }

    /**
     * 向跳跃表中添加key-value
     */
    public void put(int k, T v) {
        SkipListNode<T> p = findNode(k);
        //如果key值相同，替换原来的vaule即可结束
        if (k == p.getKey()) {
            p.value = v;
            return;
        }
        SkipListNode<T> q = new SkipListNode<T>(k, v);
        backLink(p, q);
        //当前所在的层级是0
        int currentLevel = 0;
        //抛硬币
        while (random.nextDouble() < PROBABILITY) {
            //如果超出了高度，需要重新建一个顶层
            if (currentLevel >= listLevel) {
                listLevel++;
                SkipListNode<T> p1 = new SkipListNode<>(SkipListNode.HEAD_KEY, null);
                SkipListNode<T> p2 = new SkipListNode<>(SkipListNode.TAIL_KEY, null);
                horizontalLink(p1, p2);
                vertiacallLink(p1, head);
                vertiacallLink(p2, tail);
                head = p1;
                tail = p2;
            }
            //将p移动到上一层
            while (p.up == null) {
                p = p.left;
            }
            p = p.up;

            //只保存key就ok
            SkipListNode<T> e = new SkipListNode<T>(k, null);
            //将e插入到p的后面
            backLink(p, e);
            //将e和q上下连接
            vertiacallLink(e, q);
            q = e;
            currentLevel++;
        }
        //层数递增
        nodes++;
    }

    /**
     * node1后面插入node2
     *
     * @param node1 节点1
     * @param node2 节点2
     */
    private void backLink(SkipListNode<T> node1, SkipListNode<T> node2) {
        node2.left = node1;
        node2.right = node1.right;
        node1.right.left = node2;
        node1.right = node2;
    }

    /**
     * 水平双向连接
     */
    private void horizontalLink(SkipListNode<T> node1, SkipListNode<T> node2) {
        node1.right = node2;
        node2.left = node1;
    }

    /**
     * 垂直双向连接
     */
    private void vertiacallLink(SkipListNode<T> node1, SkipListNode<T> node2) {
        node1.down = node2;
        node2.up = node1;
    }

    /**
     * 打印出原始数据
     */
    @Override
    public String toString() {
        if (isEmpty()) {
            return "跳跃表为空！";
        }
        StringBuilder builder = new StringBuilder();
        SkipListNode<T> p = head;
        while (p.down != null) {
            p = p.down;
        }

        while (p.left != null) {
            p = p.left;
        }
        if (p.right != null) {
            p = p.right;
        }
        while (p.right != null) {
            builder.append(p);
            builder.append("\n");
            p = p.right;
        }

        return builder.toString();
    }


    public static void main(String[] args) {
        SkipList<String> skipList = new SkipList<>();
        skipList.put(1, "100");
        skipList.put(2, "200");
        skipList.put(3, "300");

        System.out.println(skipList.toString());
    }


}