package com.lmlasmo.toblog.controller;

import java.util.Set;

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

import com.lmlasmo.toblog.dto.create.CreateCategoryDTO;
import com.lmlasmo.toblog.dto.read.CategoryDTO;
import com.lmlasmo.toblog.dto.update.UpdateCategoryDTO;
import com.lmlasmo.toblog.model.Category;
import com.lmlasmo.toblog.service.CategoryService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/api/category")
@ResponseStatus(code = HttpStatus.OK)
public class CategoryController {

	private CategoryService categoryService;

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Mono<Category> create(@RequestBody @Valid CreateCategoryDTO create){
		return categoryService.create(create);
	}

	@PutMapping
	public Mono<Category> update(@RequestBody @Valid UpdateCategoryDTO update){
		return categoryService.update(update);
	}

	@DeleteMapping("/{id}")
	public Mono<Void> delete(@PathVariable Long id){
		return categoryService.delete(id);
	}

	@GetMapping
	public Flux<Category> findAll(){
		return categoryService.findAll();
	}

	@GetMapping(params = "name")
	public Mono<Category> findByName(@RequestParam String name){
		return categoryService.findByNameContaining(name);
	}

	@GetMapping(params = "slug")
	public Mono<Category> findBySlug(@RequestParam String slug){
		return categoryService.findBySlug(slug);
	}

	@GetMapping("/hierarchy")
	public Flux<CategoryDTO> findInCascadeHierarchyById(@RequestParam(required = false) Long parentId){
		return categoryService.findInCascadeHierarchyByParentId(parentId);
	}

	@GetMapping(params = {"ids"})
	public Flux<Category> findByIds(@RequestParam Set<Long> ids){
		return categoryService.findByIds(ids);
	}

	@GetMapping("/{id}")
	public Mono<?> findInCascadeById(@PathVariable Long id, @RequestParam(required = false, defaultValue = "true") Boolean isCascade){
		return isCascade ? categoryService.findInCascadeById(id) : categoryService.findById(id);
	}

	@GetMapping(params = {"parentId"})
	public Flux<Category> findByParentId(@RequestParam Long parentId){
		return categoryService.findByParentId(parentId);
	}

}
