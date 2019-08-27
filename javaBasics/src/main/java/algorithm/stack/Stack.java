package algorithm.stack;

/**
 * 栈接口抽象数据类型
 */
public interface Stack<T> {

    /**
     * 栈是否为空
     *
     * @return 是否为空
     */
    boolean isEmpty();

    /**
     * data元素入栈
     *
     * @param data 入栈数据
     */
    void push(T data);

    /**
     * 返回栈顶元素,未出栈
     *
     * @return 返回栈顶数据
     */
    T peek();

    /**
     * 出栈,返回栈顶元素,同时从栈中移除该元素
     *
     * @return 返回出栈数据
     */
    T pop();

}
