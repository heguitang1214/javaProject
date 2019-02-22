package currentLimiting.baseCode;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 接口管理器
 */
public class MonitorManage {
    private static Map<String, Monitor> beans = new HashMap<>();

    static {
        Method[] methods = ProductServiceImpl.class.getDeclaredMethods();
        for (Method method : methods) {
            beans.put(method.getName(), new Monitor(method.getName()));
        }
    }

    public static Monitor getMonitor(String methodName) {
        return beans.get(methodName);
    }
}
