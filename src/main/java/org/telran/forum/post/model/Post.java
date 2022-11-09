package org.telran.forum.post.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode(of = "id")
public class Post {
	@Id
	String id;
	@Setter
	String title;
	@Setter
	String content;
	@Setter
	String author;
	LocalDateTime dateCreated;
	@Setter
	Set<String> tags = new HashSet<>();
	@Setter
	int likes = 0;
	Set<Comment> comments = new HashSet<>();

	public Post(String author, String title, String content) {
		this.author = author;
		this.title = title;
		this.content = content;
		this.dateCreated = LocalDateTime.now();
	}

	public void addTags(String tag) {
		tags.add(tag);
	}

	public void addTags(Set<String> tag) {
		tags.addAll(tag);
	}

	public void addComments(Comment comment) {
		comments.add(comment);
	}
}
