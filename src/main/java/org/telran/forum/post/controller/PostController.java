package org.telran.forum.post.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.telran.forum.post.dto.CommentDto;
import org.telran.forum.post.dto.CreatePostDto;
import org.telran.forum.post.dto.DatePeriodDto;
import org.telran.forum.post.dto.NewCommentDto;
import org.telran.forum.post.dto.PostDto;
import org.telran.forum.post.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/forum")
@RequiredArgsConstructor
public class PostController {

	final PostService forumService;

	@PostMapping("/post/{author}")
	public PostDto addPost(@PathVariable String author, @RequestBody CreatePostDto createPostDto) {
		return forumService.addPost(author, createPostDto);
	}
	
	@GetMapping("/post/{id}")
	public PostDto findPostById(@PathVariable String id) { 
		return forumService.findPostById(id);
	}

	@PutMapping("/post/{id}/like")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void addLike(@PathVariable String id) {
		forumService.addLike(id);
	}
	
	@GetMapping("/posts/author/{author}")
	public List<PostDto> findPostsByAuthor(@PathVariable String author) {
		return forumService.findPostsByAuthor(author);
	}
	
	@PutMapping("/post/{id}/comment/{author}")
	public PostDto addComment(@PathVariable String author, @PathVariable String id, @RequestBody CommentDto commentDto) {
		return forumService.addComment(author, id, commentDto);
	}
	
	@PutMapping("/post/{id}")
	public PostDto updatePost(@PathVariable String id, @RequestBody NewCommentDto newCommentDto) {
		return forumService.updatePost(id, newCommentDto);
	}

	@DeleteMapping("/post/{id}")
	PostDto deletePost(@PathVariable String id) {
		return forumService.deletePost(id);
	}
	
	@PostMapping("/posts/tags")
	public List<PostDto> findPostsByTags(@RequestBody String[] tags) {
		return forumService.findPostsByTags(tags);
	}

	@PostMapping("/posts/period")
	public List<PostDto> findPostsByDates(@RequestBody DatePeriodDto datePeriodDto) {
		return forumService.findPostsByDates(datePeriodDto);
	}
}
