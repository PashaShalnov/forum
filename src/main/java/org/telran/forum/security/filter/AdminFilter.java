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
import org.telran.forum.accounting.dao.AccountRepository;
import org.telran.forum.accounting.model.UserAccount;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Order(20) //from small to big
public class AdminFilter implements Filter {
	
	final AccountRepository accountRepository;

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		if (checkEndPoint(request.getMethod(), request.getServletPath())) { 
			UserAccount userAccount = accountRepository.findById(request.getUserPrincipal().getName()).get();
			if(!userAccount.getRoles().contains("Administrator".toUpperCase())) {
				response.sendError(403);
				return;
			}
		}
		chain.doFilter(request, response);
	}

	private boolean checkEndPoint(String method, String servletPath) {
		return servletPath.matches("/account/user/\\w+/role/\\w+/?");
	}

}
