package com.lmlasmo.toblog.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.lmlasmo.toblog.model.Category;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoryRepository extends ReactiveCrudRepository<Category, Long>{

	public Flux<Category> findByParentId(Long parentId);

	public Mono<Category> findByNameContainingIgnoreCase(String name);

	public Mono<Category> findBySlugIgnoreCase(String slug);

	public Mono<Boolean> existsBySlugIgnoreCase(String slug);

	public Mono<Boolean> existsByNameIgnoreCase(String name);

	public Mono<Long> countByParentId(Long id);

}
