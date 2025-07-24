package com.lmlasmo.toblog.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.lmlasmo.toblog.model.Post;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostCategoryLinkRepository extends ReactiveCrudRepository<Post, Long> {

	@Query("""
			SELECT p.* FROM post p
			JOIN post_category pc ON pc.post_id = p.id
			WHERE pc.category_id = :categoryId
			""")
			public Flux<Post> findByCategoryId(Long categoryId);

			@Query("""
					SELECT p.* FROM post p
					JOIN post_category pc ON pc.post_id = p.id
					JOIN category c ON c.id = pc.category_id
					WHERE c.slug = :slug
					""")
					public Flux<Post> findByCategorySlug(String slug);

					@Query("INSERT INTO post_category (post_id, category_id) VALUES(:postId, :categoryId)")
					public Mono<Integer> linkCategoryById(Long postId, Long categoryId);

					@Query("DELETE FROM post_query WHERE post_id = :postId AND category_id = :categoryId")
					public Mono<Integer> unlinkCategoryById(Long postId, Long categoryId);

}
