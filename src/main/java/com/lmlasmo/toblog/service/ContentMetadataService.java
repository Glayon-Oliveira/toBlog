package com.lmlasmo.toblog.service;

import static com.lmlasmo.toblog.util.text.TextNormalizer.reduceSpaces;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmlasmo.toblog.dto.create.ContentMetadataCreateDTO;
import com.lmlasmo.toblog.dto.update.ContentMetadataUpdateDTO;
import com.lmlasmo.toblog.exception.NotFoundException;
import com.lmlasmo.toblog.model.ContentMetadata;
import com.lmlasmo.toblog.repository.ContentMetadataRepository;
import com.lmlasmo.toblog.repository.ContentRepository;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class ContentMetadataService {

	private ContentRepository contentRepository;
	private ContentMetadataRepository metadataRepository;

	@Transactional
	public Mono<ContentMetadata> create(ContentMetadataCreateDTO create){
		ContentMetadata metadata = new ContentMetadata();
		metadata.setMetaType(reduceSpaces(create.getMetaType()));
		metadata.setMetaContent(reduceSpaces(create.getMetaContent()));

		return contentRepository.existsById(create.getContentId())
				.filter(Boolean::booleanValue)
				.switchIfEmpty(Mono.error(new NotFoundException("Content not found for id " + create.getContentId())))
				.doOnNext(b -> metadata.setContentId(create.getContentId()))
				.then(metadataRepository.save(metadata));
	}

	public Mono<ContentMetadata> update(ContentMetadataUpdateDTO update) {
		return metadataRepository.findById(update.getId())
				.switchIfEmpty(Mono.error(new NotFoundException("Metadata not found for id " + update.getId())))
				.doOnNext(m -> m.setMetaContent(reduceSpaces(update.getMetaContent())))
				.flatMap(metadataRepository::save);
	}

	public Mono<Void> delete(Long id){
		return metadataRepository.deleteById(id);
	}

	public Flux<ContentMetadata> findByContentId(Long id){
		return contentRepository.existsById(id)
				.filter(Boolean::booleanValue)
				.switchIfEmpty(Mono.error(new NotFoundException("Content not found for id " + id)))
				.thenMany(metadataRepository.findByContentId(id));
	}

}
