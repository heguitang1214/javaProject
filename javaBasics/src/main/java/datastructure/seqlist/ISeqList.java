package datastructure.seqlist;

/**
 * 顺序表顶级接口
 * @author Tang
 */
public interface ISeqList<T> {

    /**
     *判断链表是否为空
     */
    boolean isEmpty();

    /**
     * 链表长度
     */
    int length();

    /**
     * 获取元素
     */
    T get(int index);

    /**
     * 设置某个结点的的值
     */
    T set(int index, T data);

    /**
     * 根据index添加结点
     */
    boolean add(int index, T data);

    /**
     * 添加结点
     */
    boolean add(T data);

    /**
     * 根据index移除结点
     */
    T remove(int index);

    /**
     * 根据data移除结点
     */
    boolean remove(T data);

    /**
     * 根据data移除所有结点
     */
    boolean removeAll(T data);

    /**
     * 清空链表
     */
    void clear();

    /**
     * 是否包含data结点
     */
    boolean contains(T data);

    /**
     * 根据值查询下标
     */
    int indexOf(T data);

    /**
     * 根据data值查询最后一个出现在顺序表中的下标
     */
    int lastIndexOf(T data);

    /**
     * 输出格式
     */
    String toString();
}
