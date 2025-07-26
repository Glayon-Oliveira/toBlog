package com.lmlasmo.toblog.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.lmlasmo.toblog.model.Content;
import com.lmlasmo.toblog.repository.resume.ContentResume.ContentBodyResume;
import com.lmlasmo.toblog.repository.resume.ContentResume.ContentIdOrderResume;
import com.lmlasmo.toblog.repository.resume.ContentResume.ContentIdOrderTypeResume;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ContentRepository extends ReactiveCrudRepository<Content, Long>{

	@Query("""
			SELECT ct.id, ct.order_index FROM content ct
			JOIN post_content pct ON pct.content_id = ct.id
			WHERE pct.post_id = :postId
			""")
			public Flux<ContentIdOrderResume> findIdOrderResumeByPostId(Long postId);

			@Query("""
					SELECT ct.id, ct.order_index, ct.content_type FROM content ct
					JOIN post_content pct ON pct.content_id = ct.id
					WHERE pct.post_id = :postId
					""")
					public Flux<ContentIdOrderTypeResume> findIdOrderTypeByPostId(Long postId);

					@Query("SELECT c.body FROM content c WHERE c.id = :id ")
					public Mono<ContentBodyResume> findBodyById(Long id);

					@Query("""
							SELECT p.id FROM post p
							JOIN post_content pct ON pct.post_id = p.id
							WHERE pct.content_id = :contentId
							""")
							public Mono<Long> findPostIdByContentId(Long contentId);

							@Query("UPDATE content SET order_index = :index WHERE id = :id")
							public Mono<Integer> setContentOrderIndexById(Long id, Long index);

}
