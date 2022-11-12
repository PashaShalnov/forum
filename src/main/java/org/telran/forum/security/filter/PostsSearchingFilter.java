package org.telran.forum.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(9)
public class PostsSearchingFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
//		HttpServletRequest request = (HttpServletRequest) req;
//		HttpServletResponse response = (HttpServletResponse) resp;
//
//		if (checkEndPoint(request.getMethod(), request.getServletPath())) {			
//			chain.doFilter(request, response);
//		}
		chain.doFilter(req, resp);
	}

//	private boolean checkEndPoint(String method, String servletPath) {
//		return servletPath.matches("/forum/posts/\\w+")
//				&& ("GET".equalsIgnoreCase(method) || "POST".equalsIgnoreCase(method));
//	}
}
