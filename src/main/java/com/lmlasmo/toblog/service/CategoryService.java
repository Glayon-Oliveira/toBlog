package com.lmlasmo.toblog.service;

import static com.lmlasmo.toblog.util.text.TextNormalizer.normalizeSlug;
import static com.lmlasmo.toblog.util.text.TextNormalizer.reduceSpaces;

import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmlasmo.toblog.dto.create.CreateCategoryDTO;
import com.lmlasmo.toblog.dto.read.CategoryDTO;
import com.lmlasmo.toblog.dto.update.UpdateCategoryDTO;
import com.lmlasmo.toblog.exception.InvalidParentCategoryException;
import com.lmlasmo.toblog.exception.NotFoundException;
import com.lmlasmo.toblog.exception.ValueAlreadyExistsException;
import com.lmlasmo.toblog.model.Category;
import com.lmlasmo.toblog.repository.CategoryRepository;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class CategoryService {

	private CategoryRepository categoryRepository;

	@Transactional
	public Mono<Category> create(CreateCategoryDTO create) {
		Category category = new Category();
		String name = create.getName();
		Long parentId = create.getParentId();
		Mono<String> slug = validateNewSlug(create.getSlug());

		return validateNewName(name).doOnNext(category::setName)
				.then(slug.switchIfEmpty(validateNewSlug(name))
						.doOnNext(category::setSlug))
				.then(validateParent(parentId)
						.doOnNext(category::setParentId))
				.then(categoryRepository.save(category));
	}

	@Transactional
	public Mono<Category> update(UpdateCategoryDTO update){
		String name = update.getName();
		Mono<String> slug = validateNewSlug(update.getSlug());

		return categoryRepository.findById(update.getId())
				.switchIfEmpty(Mono.error(new NotFoundException("Category not found for id " + update.getId())))
				.flatMap(c -> Mono.when(
						validateNewName(name).doOnNext(c::setName),
						slug.doOnNext(c::setSlug),
						validateParentHierarchy(update.getParentId(), c.getId())
						.switchIfEmpty(Mono.fromRunnable(()->c.setParentId(null)))
						.doOnNext(c::setParentId)
						).thenReturn(c))
				.flatMap(categoryRepository::save);
	}

	@Transactional
	public Mono<Void> delete(Long id) {
		return categoryRepository.deleteById(id)
				.switchIfEmpty(Mono.error(new NotFoundException("Category not found for id " + id)));
	}

	public Flux<Category> findAll(){
		return categoryRepository.findAll();
	}

	public Mono<Category> findById(Long id){
		return categoryRepository.findById(id)
				.switchIfEmpty(Mono.error(new NotFoundException("Category not found for id " + id)));
	}

	public Mono<Category> findByNameContaining(String name){
		return categoryRepository.findByNameContainingIgnoreCase(name)
				.switchIfEmpty(Mono.error(new NotFoundException("Category not found for name " + name)));
	}

	public Mono<Category> findBySlug(String slug){
		return categoryRepository.findBySlugIgnoreCase(slug)
				.switchIfEmpty(Mono.error(new NotFoundException("Category not found for slug " + slug)));
	}

	public Flux<Category> findByIds(Set<Long> ids){
		return categoryRepository.findAllById(ids);
	}

	public Flux<Category> findByParentId(Long id){
		return categoryRepository.findByParentId(id)
				.switchIfEmpty(Mono.error(new NotFoundException("Parent category not found for id " + id)));
	}

	public Mono<CategoryDTO> findInCascadeById(Long id){
		return categoryRepository.findById(id)
				.flatMap(c -> {
					CategoryDTO category = new CategoryDTO();
					category.setId(c.getId());
					category.setName(c.getName());
					category.setSlug(c.getSlug());

					if(c.getParentId() == null) return Mono.just(category);

					return findInCascadeById(c.getParentId())
							.doOnNext(c1 -> category.setParent(c1))
							.thenReturn(category);
				});
	}

	public Flux<CategoryDTO> findInCascadeHierarchyByParentId(Long id){
		return categoryRepository.findByParentId(id)
				.flatMap(c -> {
					CategoryDTO category = new CategoryDTO();
					category.setId(c.getId());
					category.setName(c.getName());
					category.setSlug(c.getSlug());

					return findInCascadeHierarchyByParentId(c.getId())
							.collectList()
							.doOnNext(l -> category.setChildrens(l))
							.thenReturn(category);
				});
	}

	private Mono<String> validateNewName(String name){
		if(name == null) return Mono.empty();
		String normalize = reduceSpaces(name);
		return categoryRepository.existsByNameIgnoreCase(normalize)
				.flatMap(e -> e ? Mono.error(new ValueAlreadyExistsException("Name '" + normalize +"' is already in use")) : Mono.just(name));
	}

	private Mono<String> validateNewSlug(String slug){
		if(slug == null) return Mono.empty();
		String normalize = normalizeSlug(slug);
		return categoryRepository.existsBySlugIgnoreCase(slug)
				.flatMap(e -> e ? Mono.error(new ValueAlreadyExistsException("Slug '" + normalize +"' is already in use"))
						: Mono.just(normalize));
	}

	private Mono<Long> validateParent(Long id){
		if(id == null) return Mono.empty();
		return categoryRepository.existsById(id)
				.flatMap(e -> e ? Mono.just(id)
						: Mono.error(new NotFoundException("Parent category not found for id " + id)));
	}

	private Mono<Long> validateParentHierarchy(Long parentId, Long start){
		if(start == null || parentId == null) return Mono.empty();
		if(parentId.equals(start)) return Mono.error(new InvalidParentCategoryException("Parent category cannot be the same as the category"));

		return validateParent(parentId)
				.flatMap(i -> categoryRepository.findById(parentId)
						.filter(c -> !start.equals(c.getParentId()))
						.switchIfEmpty(Mono.error(new InvalidParentCategoryException("Invalid parent hierarchy"))))
				.flatMap(c -> validateParentHierarchy(c.getParentId(), start))
				.thenReturn(parentId);
	}

}
