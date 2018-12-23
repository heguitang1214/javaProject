package designPatterns.filter;

/**
 * @author he_guitang
 * @version [1.0 , 2018/5/21]
 * 具体的过滤器:敏感词的过滤器
 */
public class SesitiveFilter implements Filter {
    @Override
    public void doFilter(Request request, Response response, FilterChain filterChain) {
        request.requestStr = request.requestStr.replace("被就业", "就业")
                .replace("敏感", "sb") + "=====>调用request敏感词过滤器";
        System.out.println("request SesitiveFilter调用完毕!");
        filterChain.doFilter(request, response, filterChain);
        response.responseStr += "=====>调用response敏感词过滤器";
        System.out.println("response SesitiveFilter调用完毕!");
    }
}

