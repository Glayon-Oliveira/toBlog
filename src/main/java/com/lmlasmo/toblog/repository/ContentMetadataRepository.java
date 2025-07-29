package com.lmlasmo.toblog.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.lmlasmo.toblog.model.ContentMetadata;

import reactor.core.publisher.Flux;

public interface ContentMetadataRepository extends ReactiveCrudRepository<ContentMetadata, Long>{

	public Flux<ContentMetadata> findByContentId(Long id);

}
