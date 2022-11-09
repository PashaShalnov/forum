package org.telran.forum.post.dto.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@ResponseStatus(HttpStatus.NOT_FOUND)
public class PostNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 6022486588062947182L;

	public PostNotFoundException(String id) {
		super("Post with id = " + id + " not found");
	}
}
