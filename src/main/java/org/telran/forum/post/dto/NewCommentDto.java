package org.telran.forum.post.dto;

import java.util.Set;

import lombok.Getter;

@Getter
public class NewCommentDto {
	String title;
	String content;
    Set<String> tags;
}
