package utils;

import java.util.Collection;
import java.util.Map;

/**
 * @author carter
 * create time    2017-10-10
 */
public final class CollectionUtils {


    /**
     * 判断Map是否为空或者空集合
     * @param map
     * @return 为null或者为空集合返回true
     */
    public static boolean isNullOrEmpty(Map map)
    {
        if(null == map) return true;
        if(map.isEmpty()) return true;
        return false;
    }

    /**
     * 判断集合是空或者NULL
     * @param collection
     * @return
     */
    public static boolean isNullOrEmpty(Collection collection)
    {
        if(null == collection) return true;
        if(collection.isEmpty()) return true;
        return false;
    }


    /**
     * 判断集合不是NULL也不是Empty
     * @param collection
     * @return
     */
    public static boolean isNotNullAndEmpty(Collection collection)
    {
        return !isNullOrEmpty(collection);
    }
    
    public static boolean isNotNullAndEmpty(Map map) {
        return !isNullOrEmpty(map);
    }


    public static <K,V>  boolean isNotEmpty(Map<K, V> map) {
        if(map==null) return false;
        if(map.isEmpty()) return false;
        return true;
    }
    
    public static <K,V>  boolean isEmpty(Map<K, V> map) {
        return !isNotEmpty(map);
    }
}
