package designPatterns.filter;


/**
 * @author he_guitang
 * @version [1.0 , 2018/5/21]
 * 过滤器接口
 */
public interface Filter {

    void doFilter(Request request, Response response, FilterChain filterChain);

}


