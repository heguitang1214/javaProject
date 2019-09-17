package algorithm.search.test;

/**
 * 跳跃表的节点,包括key-value和上下左右4个指针
 *
 * @author Tang
 */
public class SkipListNode<T> {

    public int key;

    /**
     * 节点数据
     */
    public T value;

    /**
     * 上下左右 四个指针
     */
    SkipListNode<T> up, down, left, right;

    /**
     * 负无穷
     */
    static final int HEAD_KEY = Integer.MIN_VALUE;

    /**
     * 正无穷
     */
    static final int TAIL_KEY = Integer.MAX_VALUE;


    SkipListNode(int k, T v) {
        key = k;
        value = v;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof SkipListNode<?>)) {
            return false;
        }
        SkipListNode<T> ent;
        try {
            // 检测类型
            ent = (SkipListNode<T>) o;
        } catch (ClassCastException ex) {
            return false;
        }
        return (ent.getKey() == key) && (ent.getValue() == value);
    }

    @Override
    public String toString() {
        return "key-value:" + key + "-" + value;
    }
}