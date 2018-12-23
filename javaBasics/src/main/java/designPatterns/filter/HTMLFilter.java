package designPatterns.filter;

/**
 * @author he_guitang
 * @version [1.0 , 2018/5/21]
 * 具体的过滤器:HTML过滤器
 */

public class HTMLFilter implements Filter {
    @Override
    public void doFilter(Request request, Response response, FilterChain filterChain) {
        request.requestStr = request.requestStr.replace('<', '[')
                .replace('>', ']') + "=====>调用requestHTMLFilter";
        System.out.println("request HTMLFilter调用完毕!");
        filterChain.doFilter(request, response, filterChain);
        response.responseStr += "=====>调用responseHTMLFilter";
        System.out.println("response HTMLFilter调用完毕!");
    }
}


