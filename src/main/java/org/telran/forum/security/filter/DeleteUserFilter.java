package org.telran.forum.security.filter;

import java.io.IOException;
import java.util.Base64;

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
@Order(30)
public class DeleteUserFilter implements Filter {
	
	final AccountRepository accountRepository;

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		if (checkEndPoint(request.getMethod(), request.getServletPath())) { 
			String token = request.getHeader("Authorization");
			String[] credentials  = getCredentialsFromToken(token);; 
			UserAccount user = accountRepository.findById(credentials[0]).orElse(null);
			String[] userToDelete = request.getRequestURI().split("/");
			if (!(user.getLogin().compareToIgnoreCase(userToDelete[userToDelete.length - 1]) == 0 || user.getRoles().contains("ADMINISTRATOR"))) {
				response.sendError(403, "Forbidden");
				return;
			}

		}
		chain.doFilter(request, response);
	}

	private boolean checkEndPoint(String method, String servletPath) {
		return "DELETE".equalsIgnoreCase(method) && servletPath.matches("/account/user/\\w+");
	}
	
	private String[] getCredentialsFromToken(String token) {
		String[] basicAuth = token.split(" ");
		String decode = new String(Base64.getDecoder().decode(basicAuth[1]));
		String[] credential = decode.split(":");
		return credential;
	}

}
