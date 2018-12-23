package designPatterns.filter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author he_guitang
 * @version [1.0 , 2018/5/21]
 * 过滤器管理器
 */
public class FilterChain implements Filter {
    private List<Filter> filters = new ArrayList<>();
    private int index = 0;

    FilterChain addFilter(Filter filter){
        this.filters.add(filter);
        return this;
    }

    @Override
    public void doFilter(Request request, Response response, FilterChain filterChain) {
        //遍历到最后一个过滤器,然后返回
        if (index == filters.size()) return;
        //依次获取集合中的过滤器,调用过滤器的方法进行过滤
        Filter f = filters.get(index);
        index ++;
        f.doFilter(request, response, filterChain);
    }
}


