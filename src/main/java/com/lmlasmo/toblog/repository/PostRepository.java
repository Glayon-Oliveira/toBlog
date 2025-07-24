package com.lmlasmo.toblog.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.lmlasmo.toblog.model.Post;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostRepository extends ReactiveCrudRepository<Post, Long> {

	public Flux<Post> findBySlug(String slug);

	public Flux<Post> findByTitleContainingIgnoreCase(String title);

	public Mono<Boolean> existsBySlugIgnoreCase(String slug);

}
