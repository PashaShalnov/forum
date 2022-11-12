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
@Order(40)
public class PostCommentFilter implements Filter {

	final AccountRepository accountRepository;

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		String token = request.getHeader("Authorization");
		String[] credentials = getCredentialsFromToken(token);
		UserAccount userAccount = accountRepository.findById(credentials[0]).orElse(null);
		String[] link = request.getRequestURI().split("/");
		String authorLinkElem = link[link.length - 1];
		

		if (checkEndPoint(request.getMethod(), request.getServletPath())) {
			if ("PUT".equalsIgnoreCase(request.getMethod())) {
				if (!(authorLinkElem.toLowerCase().equals(userAccount.getLogin().toLowerCase()))) {
					response.sendError(403, "Who are you?");
					return;
				}
			}
		}
		chain.doFilter(request, response);
	}

	private boolean checkEndPoint(String method, String servletPath) {
		return servletPath.matches("/forum/post/\\w+/comment/\\w+");
	}

	private String[] getCredentialsFromToken(String token) {
		String[] basicAuth = token.split(" ");
		String decode = new String(Base64.getDecoder().decode(basicAuth[1]));
		String[] credential = decode.split(":");
		return credential;
	}

}
