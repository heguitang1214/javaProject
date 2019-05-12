package com.tang.hystrix.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

/**
 * 
 * @author 小五老师-云析学院
 * @since 2019年5月6日 下午9:30:49
 */
@WebFilter(urlPatterns="/*",asyncSupported=true)
public class HystrixRequestContextServletFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HystrixRequestContext context = HystrixRequestContext.initializeContext();
		try {
			chain.doFilter(request, response);
		} finally {
			context.close();
		}
	}

	@Override
	public void destroy() {
	}
}
