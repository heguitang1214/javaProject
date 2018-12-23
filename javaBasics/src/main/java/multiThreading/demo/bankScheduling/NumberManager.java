package multiThreading.demo.bankScheduling;

import java.util.ArrayList;
import java.util.List;

/**
 * 数量管理器
 */
public class NumberManager {
    private int lastNumber = 0;
    private List queueNumbers = new ArrayList();

    /**
     * 添加新的管理器
     */
    public synchronized Integer generateNewNumber() {
        queueNumbers.add(++lastNumber);
        return lastNumber;
    }

    /**
     * 获取管理器
     */
    public synchronized Integer fetchNumber() {
        if (queueNumbers.size() > 0) {
            return (Integer) queueNumbers.remove(0);
        } else {
            return null;
        }
    }
}
