package org.telran.forum.post.model;

import java.time.LocalDateTime;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = {"user", "dateCreated", "message"})
public class Comment {
	@Setter
	String user;
	@Setter
	String message;
	LocalDateTime dateCreated = LocalDateTime.now();
	@Setter
	int likes = 0;
	
	
	public Comment(String author, String message) {
		this.user  = author; 
		this.message = message;
	}
}
