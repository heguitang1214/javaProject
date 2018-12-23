package designPatterns.filter;


/**
 * @author he_guitang
 * @version [1.0 , 2018/5/21]
 */
public class Test {
    public static void main(String[] args) {
        String msg = "大家好:), <script> ,敏感就是被就业了";
        Request request = new Request();
        request.setRequestStr(msg);
        Response response = new Response();
        response.setResponseStr("response响应敏感<<<");
        FilterChain fc = new FilterChain();
        fc.addFilter(new HTMLFilter()).addFilter(new SesitiveFilter());
        fc.doFilter(request,response, fc);
        System.out.println(request.getRequestStr());
        System.out.println(response.getResponseStr());
    }
//    request HTMLFilter调用完毕!
//    request SesitiveFilter调用完毕!
//    response SesitiveFilter调用完毕!
//    response HTMLFilter调用完毕!
//    大家好:), [script] ,sb就是就业了=====>调用requestHTMLFilter=====>调用request敏感词过滤器
//    response响应敏感<<<=====>调用response敏感词过滤器=====>调用responseHTMLFilter
}

