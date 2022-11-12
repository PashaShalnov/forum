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
import org.telran.forum.post.dao.ForumRepository;
import org.telran.forum.post.model.Post;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Order(15)
public class PostCRUDFilter implements Filter {

	final AccountRepository accountRepository;
	final ForumRepository forumRepository;

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		String token = request.getHeader("Authorization");
		String[] credentials = getCredentialsFromToken(token);
		UserAccount userAccount = accountRepository.findById(credentials[0]).orElse(null);
		String[] link = request.getRequestURI().split("/");
		String lastLinkElem = link[link.length - 1];

		if (checkEndPoint(request.getMethod(), request.getServletPath())) {
			System.out.println("or am i here?");
			if ("POST".equalsIgnoreCase(request.getMethod()) && !(userAccount.getLogin().compareToIgnoreCase(lastLinkElem) == 0)) {
				response.sendError(403, "Forbidden");
				return;
			}
			if ("PUT".equalsIgnoreCase(request.getMethod())) {
				Post post = forumRepository.findById(lastLinkElem).orElse(null);
				if(!(post.getAuthor().equals(userAccount.getLogin()))) {
					response.sendError(403, "Only the author can edit his_her own post");
					return;
				}
			}
			if ("DELETE".equalsIgnoreCase(request.getMethod())) {
				Post post = forumRepository.findById(lastLinkElem).orElse(null);
				if( !(userAccount.getRoles().contains("MODERATOR")) && !(post.getAuthor().equals(userAccount.getLogin()))) {
					response.sendError(403, "You don't have permission for this");
					return;
				}
//			"GET" covered by AuthenticationFilter
			}
		}
		chain.doFilter(request, response);
	}

	private boolean checkEndPoint(String method, String servletPath) {
		return  servletPath.matches("/forum/post/\\w+");
	}

	private String[] getCredentialsFromToken(String token) {
		String[] basicAuth = token.split(" ");
		String decode = new String(Base64.getDecoder().decode(basicAuth[1]));
		String[] credential = decode.split(":");
		return credential;
	}

}
