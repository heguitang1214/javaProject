package algorithm.linkedlist.mycollection;

/**
 * 修改版接口实现
 *
 * @param <T> 泛型
 */
public interface IList<T> {
    /**
     * list大小
     *
     * @return 集合大小
     */
    int size();

    /**
     * 是否为空
     *
     * @return 是否为空
     */
    boolean isEmpty();

    /**
     * 是否包含data
     *
     * @param data 数据
     * @return 是否包含
     */
    boolean contains(T data);


    /**
     * 清空数据
     */
    void clear();

    /**
     * 根据index获取数据
     *
     * @param index 下表
     * @return 数据
     */
    T get(int index);

    /**
     * 替换数据
     *
     * @param index 下标
     * @param data  数据
     * @return 原始数据
     */
    T set(int index, T data);

    /**
     * 尾部添加数据
     *
     * @param data 数据
     * @return 是否添加成功
     */
    boolean add(T data);

    /**
     * 根据index添加数据
     *
     * @param index 下表
     * @param data  数据
     */
    void add(int index, T data);

    /**
     * 移除数据
     *
     * @param data 数据
     * @return 是否移除成功
     */
    boolean remove(T data);

    /**
     * 根据index删除数据
     *
     * @param index 下标
     * @return 是否删除成功
     */
    T remove(int index);

    /**
     * 根据data获取下标
     *
     * @param data 数据
     * @return 获取的对应的下标
     */
    int indexOf(T data);

    /**
     * 根据data获取最后一个下标
     *
     * @param data 数据
     * @return 该数据最后的下标
     */
    int lastIndexOf(T data);


}
