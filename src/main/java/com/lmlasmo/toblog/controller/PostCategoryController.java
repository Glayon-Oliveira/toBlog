package com.lmlasmo.toblog.controller;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lmlasmo.toblog.model.Post;
import com.lmlasmo.toblog.service.PostCategoryService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/post")
@ResponseStatus(code = HttpStatus.OK)
public class PostCategoryController {

	private PostCategoryService postCategoryService;

	@GetMapping(params = "categoryId")
	public Flux<Post> findByCategory(@RequestParam Long categoryId){
		return postCategoryService.findByCategoryId(categoryId);
	}

	@GetMapping(params = "categorySlug")
	public Flux<Post> findByCategory(@RequestParam String categorySlug){
		return postCategoryService.findByCategorySlug(categorySlug);
	}

	@PutMapping(path = "/{id}", params = "addCategoryIds")
	public Mono<Void> addCategoryToPost(@PathVariable Long id, @RequestParam("addCategoryIds") Set<Long> ids){
		return postCategoryService.linkCategoriesToPost(id, ids);
	}

	@PutMapping(path = "/{id}", params = "removeCategoryIds")
	public Mono<Void> removeCategoryToPost(@PathVariable Long id, @RequestParam("removeCategoryIds") Set<Long> ids){
		return postCategoryService.unlinkCategoriesToPost(id, ids);
	}

}
