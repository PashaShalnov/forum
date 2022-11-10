package org.telran.forum.security.filter;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.Base64;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.telran.forum.accounting.dao.AccountRepository;
import org.telran.forum.accounting.dto.UserDto;
import org.telran.forum.accounting.model.UserAccount;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
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
				response.sendError(401);
				return;
			}
			
			String[] credentials = getCredentialsFromToken(token);
			UserAccount user = accountRepository.findById(credentials[0]).orElse(null);
			if (user == null || !(user.getPassword().equals(credentials[1]))) {
				response.sendError(401);
				return;
			}
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
		return !("POST".equalsIgnoreCase(method) && servletPath.equals("account/register"));
	}
}
