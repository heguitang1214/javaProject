package thread.concurrentCollection;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by heguitang on 2019/2/3.
 * 线程并发知识
 */
public class Base {

    public static void main(String[] args) {

        //代理的实现，外面包装一层synchronized，内部继续调用Map的方法。
        Map<String, String> map = Collections.synchronizedMap(new HashMap<String, String>());


    }

}
