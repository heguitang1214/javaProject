package utils;

import java.util.Collection;
import java.util.Map;

/**
 * 集合判断工具类
 */
public final class CollectionUtils {

    /**
     * 判断Map是否为空或者空集合
     *
     * @param map map
     * @return 为null或者为空集合返回true
     */
    public static boolean isNullOrEmpty(Map map) {
        return null == map || map.isEmpty();
    }

    /**
     * 判断集合是空或者NULL
     */
    public static boolean isNullOrEmpty(Collection collection) {
        return null == collection || collection.isEmpty();
    }


    /**
     * 判断集合不是NULL也不是Empty
     */
    public static boolean isNotNullAndEmpty(Collection collection) {
        return !isNullOrEmpty(collection);
    }

    public static boolean isNotNullAndEmpty(Map map) {
        return !isNullOrEmpty(map);
    }


    public static <K, V> boolean isNotEmpty(Map<K, V> map) {
        return !(map == null || map.isEmpty());
    }

    public static <K, V> boolean isEmpty(Map<K, V> map) {
        return !isNotEmpty(map);
    }
}
