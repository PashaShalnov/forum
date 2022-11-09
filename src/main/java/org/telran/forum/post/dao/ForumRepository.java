package org.telran.forum.post.dao;

import java.time.LocalDate;
import java.util.stream.Stream;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.telran.forum.post.model.Post;

@Repository
public interface ForumRepository extends CrudRepository<Post, String> {
	
	Stream<Post> findByAuthorInIgnoreCase(String author);
	
	Stream<Post> findByTagsInIgnoreCase(String[] tags);
	
	Stream<Post> findByDateCreatedBetween(LocalDate datefrom, LocalDate dateTo);
	
}
