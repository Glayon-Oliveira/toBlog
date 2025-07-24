package com.lmlasmo.toblog.service;

import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmlasmo.toblog.exception.InvalidValueException;
import com.lmlasmo.toblog.model.Post;
import com.lmlasmo.toblog.repository.CategoryRepository;
import com.lmlasmo.toblog.repository.PostCategoryLinkRepository;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class PostCategoryService {

	private PostCategoryLinkRepository postCategoryRepository;
	private CategoryRepository categoryRepository;

	public Flux<Post> findByCategoryId(Long id){
		return postCategoryRepository.findByCategoryId(id);
	}

	public Flux<Post> findByCategorySlug(String slug){
		return postCategoryRepository.findByCategorySlug(slug);
	}

	@Transactional
	public Mono<Void> linkCategoriesToPost(Long postId, Set<Long> categoriesIds){
		return validateCategoryIds(categoriesIds)
				.flatMap(i -> postCategoryRepository.linkCategoryById(postId, i))
				.then();
	}

	@Transactional
	public Mono<Void> unlinkCategoriesToPost(Long postId, Set<Long> categoriesIds){
		return validateCategoryIds(categoriesIds)
				.flatMap(i -> postCategoryRepository.unlinkCategoryById(postId, i))
				.then();
	}

	private Flux<Long> validateCategoryIds(Set<Long> categoriesIds){
		return Flux.fromIterable(categoriesIds)
				.flatMap(i -> categoryRepository.existsById(i)
						.filter(Boolean::booleanValue)
						.switchIfEmpty(Mono.error(new InvalidValueException("Category not found for id " + i)))
						.thenReturn(i)
						);
	}

}
