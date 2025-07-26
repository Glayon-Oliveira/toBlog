package com.lmlasmo.toblog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lmlasmo.toblog.dto.create.CreateContentDTO;
import com.lmlasmo.toblog.dto.update.UpdateContentDTO;
import com.lmlasmo.toblog.model.Content;
import com.lmlasmo.toblog.repository.resume.ContentResume.ContentBodyResume;
import com.lmlasmo.toblog.repository.resume.ContentResume.ContentIdOrderTypeResume;
import com.lmlasmo.toblog.service.ContentService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/api/content")
@ResponseStatus(code = HttpStatus.OK)
public class ContentController {

	private ContentService contentService;

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Mono<Content> create(@RequestBody @Valid CreateContentDTO create){
		return contentService.createContent(create);
	}

	@PutMapping
	public Mono<Content> update(@RequestBody @Valid UpdateContentDTO update){
		return contentService.updateContent(update);
	}

	@DeleteMapping("/{id}")
	public Mono<Void> delete(@PathVariable Long id){
		return contentService.delete(id);
	}

	@GetMapping(params = "postId")
	public Flux<ContentIdOrderTypeResume> findByPost(@RequestParam Long postId){
		return contentService.findByPost(postId);
	}

	@GetMapping(path = "/{contentId}/body")
	public Mono<ContentBodyResume> findBodyByContentId(@PathVariable Long contentId){
		return contentService.findBodyByContentId(contentId);
	}

}
