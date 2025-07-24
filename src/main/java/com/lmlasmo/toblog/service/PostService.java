package com.lmlasmo.toblog.service;

import static com.lmlasmo.toblog.util.text.TextNormalizer.normalizeSlug;
import static com.lmlasmo.toblog.util.text.TextNormalizer.reduceSpaces;

import java.time.Instant;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmlasmo.toblog.dto.create.CreatePostDTO;
import com.lmlasmo.toblog.dto.update.UpdatePostDTO;
import com.lmlasmo.toblog.exception.NotFoundException;
import com.lmlasmo.toblog.exception.ValueAlreadyExistsException;
import com.lmlasmo.toblog.model.Post;
import com.lmlasmo.toblog.repository.PostRepository;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class PostService {

	protected PostRepository postRepository;

	@Transactional
	public Mono<Post> create(CreatePostDTO create){
		Post post = new Post();
		post.setImgUrl(create.getImgUrl());
		post.setCreatedAt(Instant.now());
		post.setUpdatedAt(post.getCreatedAt());

		String title = reduceSpaces(create.getTitle());
		String slug = create.getSlug();
		String summary = reduceSpaces(create.getSummary());

		return Mono.just(title).doOnNext(post::setTitle)
				.then(validateNewSlug(slug)
						.switchIfEmpty(validateNewSlug(title))
						.doOnNext(post::setSlug))
				.then(Mono.justOrEmpty(summary)
						.doOnNext(post::setSummary))
				.then(postRepository.save(post));
	}

	@Transactional
	public Mono<Void> delete(Long id){
		return postRepository.deleteById(id);
	}

	@Transactional
	public Mono<Post> update(UpdatePostDTO update){
		String title = reduceSpaces(update.getTitle());
		String summary = reduceSpaces(update.getSummary());
		String imgURL = update.getImgUrl();
		Mono<String> slug = validateNewSlug(update.getSlug());

		return postRepository.findById(update.getId())
				.switchIfEmpty(Mono.error(new NotFoundException("Post not found for id " + update.getId())))
				.doOnNext(p -> p.setUpdatedAt(Instant.now()))
				.flatMap(p -> Mono.when(
						Mono.justOrEmpty(title)
						.doOnNext(p::setTitle),
						slug.switchIfEmpty(validateNewSlug(title))
						.doOnNext(p::setSlug),
						Mono.justOrEmpty(summary)
						.doOnNext(p::setSummary),
						Mono.justOrEmpty(imgURL)
						.doOnNext(p::setImgUrl)
						).thenReturn(p))
				.flatMap(postRepository::save);
	}

	public Flux<Post> findAll(){
		return postRepository.findAll();
	}

	public Mono<Post> findById(Long id){
		return postRepository.findById(id).switchIfEmpty(Mono.error(new NotFoundException("Post not found for id " + id)));
	}

	public Flux<Post> findBySlug(String slug) {
		return postRepository.findBySlug(slug);
	}

	public Flux<Post> findByTitleContaining(String title) {
		return postRepository.findByTitleContainingIgnoreCase(title);
	}

	private Mono<String> validateNewSlug(String slug){
		if(slug == null || slug.isBlank()) return Mono.empty();
		String normalize = normalizeSlug(slug);
		return postRepository.existsBySlugIgnoreCase(normalize)
				.flatMap(e -> e ? Mono.error(new ValueAlreadyExistsException("Slug '" + normalize +"' is already in use")) : Mono.just(normalize));
	}

}
