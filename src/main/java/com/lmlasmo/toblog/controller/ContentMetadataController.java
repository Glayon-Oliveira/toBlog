package com.lmlasmo.toblog.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lmlasmo.toblog.dto.create.ContentMetadataCreateDTO;
import com.lmlasmo.toblog.dto.update.ContentMetadataUpdateDTO;
import com.lmlasmo.toblog.model.ContentMetadata;
import com.lmlasmo.toblog.service.ContentMetadataService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/api/content/metadata")
public class ContentMetadataController {

	private ContentMetadataService metadataService;

	@PostMapping
	public Mono<ContentMetadata> create(@RequestBody @Valid ContentMetadataCreateDTO create){
		return metadataService.create(create);
	}

	@PutMapping
	public Mono<ContentMetadata> update(@RequestBody @Valid ContentMetadataUpdateDTO update){
		return metadataService.update(update);
	}

	@DeleteMapping("/{id}")
	public Mono<Void> delete(@PathVariable Long id){
		return metadataService.delete(id);
	}

	@GetMapping(params = "contentId")
	public Flux<ContentMetadata> findByContent(@RequestParam Long contentId){
		return metadataService.findByContentId(contentId);
	}

}
