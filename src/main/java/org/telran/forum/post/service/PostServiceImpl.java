package org.telran.forum.post.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.telran.forum.post.dao.ForumRepository;
import org.telran.forum.post.dto.CommentDto;
import org.telran.forum.post.dto.CreatePostDto;
import org.telran.forum.post.dto.DatePeriodDto;
import org.telran.forum.post.dto.NewCommentDto;
import org.telran.forum.post.dto.PostDto;
import org.telran.forum.post.dto.exceptions.PostNotFoundException;
import org.telran.forum.post.model.Comment;
import org.telran.forum.post.model.Post;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

	final ForumRepository forumRepository;
	final ModelMapper modelMapper;
	
	@Override
	public PostDto addPost(String author, CreatePostDto createPostDto) {
		Post post = new Post(author, createPostDto.getTitle(), createPostDto.getContent());
		post.addTags(createPostDto.getTags());
		forumRepository.save(post);
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostDto findPostById(String id) {
		Post post = forumRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public void addLike(String id) {
		Post post = forumRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		post.setLikes(post.getLikes() + 1);
		forumRepository.save(post); 

	}

	@Override
	public List<PostDto> findPostsByAuthor(String author) {
		return forumRepository.findByAuthorInIgnoreCase(author)
				.map(p -> modelMapper.map(p, PostDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public PostDto addComment(String author, String id, CommentDto commentDto) {
		Post post = forumRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		Comment comment = new Comment(author, commentDto.getMessage());
		post.addComments(comment);
		forumRepository.save(post);
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostDto deletePost(String id) {
		Post post = forumRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		forumRepository.deleteById(id);
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> findPostsByTags(String[] tags) {
		return forumRepository.findByTagsInIgnoreCase(tags)
				.map(p -> modelMapper.map(p, PostDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public	List<PostDto> findPostsByDates(DatePeriodDto datePeriodDto) {
		return forumRepository.findByDateCreatedBetween(datePeriodDto.getDateFrom(), datePeriodDto.getDateTo())
				.map(e -> modelMapper.map(e, PostDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public PostDto updatePost(String id, NewCommentDto newCommentDto) {
		Post post = forumRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		if (newCommentDto.getContent() != null) {
			post.setContent(newCommentDto.getContent());
		}
		if (newCommentDto.getTags() != null) {
			post.addTags(newCommentDto.getTags());
		}
		if (newCommentDto.getTitle() != null) {
			post.setTitle(newCommentDto.getTitle());
		}
		forumRepository.save(post);
		return modelMapper.map(post, PostDto.class);
	}

}
