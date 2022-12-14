package org.telran.forum.security.filter;

import java.io.IOException;
import java.security.Principal;
import java.util.Base64;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telran.forum.accounting.dao.AccountRepository;
import org.telran.forum.accounting.model.UserAccount;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Order(10)
public class AuthenticationFilter implements Filter {
	
	final AccountRepository accountRepository;

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		if (checkEndPoint(request.getMethod(), request.getServletPath())) { 
			String token = request.getHeader("Authorization");
			if(token == null) {				
				if (request.getServletPath().matches("/forum/posts/\\w+/*\\w*")
				&& ("GET".equalsIgnoreCase(request.getMethod()) || "POST".equalsIgnoreCase(request.getMethod()))
				) {
					request.getRequestDispatcher(((HttpServletRequest) request).getServletPath()).forward(request, response);
					return;
				}
				response.sendError(401);
				return;
			}
			String[] credentials; 
			try {
				credentials = getCredentialsFromToken(token);
			} catch (Exception e) {
				response.sendError(401, "Invalid token");
				return;
			}
			UserAccount user = accountRepository.findById(credentials[0]).orElse(null);
			if (user == null || !BCrypt.checkpw(credentials[1], user.getPassword())) {
				response.sendError(401, "login or password is invalid");
				return;
			}
			request = new WrappedRequest(request, user.getLogin());
		}
		chain.doFilter(request, response);
	}

	private String[] getCredentialsFromToken(String token) {
		String[] basicAuth = token.split(" ");
		String decode = new String(Base64.getDecoder().decode(basicAuth[1]));
		String[] credential = decode.split(":");
		return credential;
	}
	
	private boolean checkEndPoint(String method, String servletPath) {
		return !("POST".equalsIgnoreCase(method) && servletPath.matches("/account/register/?"));
	}

	private class WrappedRequest extends HttpServletRequestWrapper {
		String login;
		
		public WrappedRequest(HttpServletRequest request, String login) {
			super(request);
			this.login = login;
		}
		
		@Override
		public Principal getUserPrincipal() {
			return () -> login; //?????? ???????????????????????????? ??????????????????
		}
	}
}
