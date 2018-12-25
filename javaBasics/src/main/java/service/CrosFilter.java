package service;
import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 允许 JS 跨域设置，配置跨域操作
 * 使用 nginx 注意在 nginx.conf 中配置
 * http { ...... add_header Access-Control-Allow-Origin *; ...... }
 *
 * 非 ngnix 下，如果该方法设置不管用、可以尝试增加下行代码。
 * response.setHeader("Access-Control-Allow-Origin", "*");
 */
public class CrosFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {


        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setContentType("text/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, HEAD, OPTIONS, DELETE, PUT");
        response.setHeader("Access-Control-Allow-Origin", "*");

        response.setHeader("Access-Control-Allow-Credentials","true");
        response.setHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers,Access-Control-Allow-Origin");
        filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {

    }



}
