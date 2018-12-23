package dataStructure.myHashMap;

/**
 * Created by 11256 on 2018/8/30.
 * map接口
 */
public interface Map<K, V> {

    V put(K key, V value);

    V get(K key);

    int size();

    interface Entry<K, V> {
        V setValue(V value);

        Entry<K, V> setNext(Entry<K, V> entry);

    }


}
