package org.telran.forum.accounting.dto.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@ResponseStatus(HttpStatus.CONFLICT)
public class UserExistsException extends RuntimeException {

	private static final long serialVersionUID = -6746465463311573247L;

	public UserExistsException(String login) {
		super("User " + login + " already exists");
	}
}
