package org.telran.forum.post.service;

import java.util.List;

import org.telran.forum.post.dto.CommentDto;
import org.telran.forum.post.dto.CreatePostDto;
import org.telran.forum.post.dto.DatePeriodDto;
import org.telran.forum.post.dto.NewCommentDto;
import org.telran.forum.post.dto.PostDto;

public interface PostService {
	PostDto addPost(String author, CreatePostDto createPostDto);

	PostDto findPostById(String id);

	void addLike(String id);

	List<PostDto> findPostsByAuthor(String author);

	PostDto addComment(String author, String id, CommentDto commentDto);

	PostDto deletePost(String id);

	PostDto updatePost(String id, NewCommentDto newCommentDto);

	List<PostDto> findPostsByTags(String[] tags);

	List<PostDto> findPostsByDates(DatePeriodDto datePeriodDto);
}
