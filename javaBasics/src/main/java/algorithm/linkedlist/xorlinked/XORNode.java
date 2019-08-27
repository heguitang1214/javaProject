package algorithm.linkedlist.xorlinked;

/**
 * 异或结点
 */
public class XORNode<T> {

    /**
     * 数据
     */
    public T data;

    /**
     * 异或指针
     * 存储了后继结点与前驱结点的地址差，指针的差通过异或运行来实现的，我们这里使用⊕表示异或操作
     * pridiff=后继结点的地址⊕前驱结点的地址
     */
    private XORNode<T> ptrdiff;

    public XORNode() {
    }

    public XORNode(T data, XORNode<T> ptrdiff) {
        this.data = data;
        this.ptrdiff = ptrdiff;
    }
}
