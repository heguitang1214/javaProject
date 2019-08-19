package algorithm.linkedlist.doublelinked;

/**
 * 排序（升序）循环双链表
 * 升序排序链表
 */
public class SortLoopHeadLinkedList<T extends Comparable<? extends T>> extends LoopHeadLinkedList<T> {

    /**
     * 顺序插入
     *
     * @param data 数据
     * @return 是否添加成功
     */
    @Override
    public boolean add(T data) {

        if (data == null || !(data instanceof Comparable)) {
            throw new NullPointerException("data can\'t be null or data instanceof Comparable must be true");
        }
        // 这里需要转一下类型,否则idea编辑器上检验不通过.
        Comparable cmp = data;

        // 如果data值比最后一个结点大,那么直接调用父类方法,在尾部添加.
        if (this.isEmpty() || cmp.compareTo(this.head.prev.data) > 0) {
            return super.add(data);
        }

        DNode<T> p = this.head.next;
        // 查找插入点
        while (p != head && cmp.compareTo(p.data) > 0) {
            p = p.next;
        }
        DNode<T> q = new DNode<>(data, p.prev, p);
        p.prev.next = q;
//        p.next.prev = q.prev;
        p.prev = q;

        return true;
    }


    public static void main(String[] args) {
        SortLoopHeadLinkedList<Integer> list = new SortLoopHeadLinkedList<>();
        list.add(50);
        list.add(40);
        list.add(80);
        list.add(20);
        list.add(100);
        list.add(20);
        System.out.println("init list-->" + list.toString());
    }
}
