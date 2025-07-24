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

import com.lmlasmo.toblog.dto.create.CreatePostDTO;
import com.lmlasmo.toblog.dto.update.UpdatePostDTO;
import com.lmlasmo.toblog.model.Post;
import com.lmlasmo.toblog.service.PostService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/api/post")
@ResponseStatus(code = HttpStatus.OK)
public class PostController {

	private PostService postService;

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Mono<Post> create(@RequestBody @Valid CreatePostDTO create){
		return postService.create(create);
	}

	@PutMapping
	public Mono<Post> update(@RequestBody @Valid UpdatePostDTO update){
		return postService.update(update);
	}

	@DeleteMapping("/{id}")
	public Mono<Void> delete(@PathVariable Long id){
		return postService.delete(id);
	}

	@GetMapping
	public Flux<Post> findAll(){
		return postService.findAll();
	}

	@GetMapping("/{id}")
	public Mono<Post> findById(@PathVariable Long id){
		return postService.findById(id);
	}

	@GetMapping(params = "slug")
	public Flux<Post> findBySlug(@RequestParam String slug){
		return postService.findBySlug(slug);
	}

	@GetMapping(params = "title")
	public Flux<Post> findByTitleContaining(@RequestParam String title){
		return postService.findByTitleContaining(title);
	}

}
