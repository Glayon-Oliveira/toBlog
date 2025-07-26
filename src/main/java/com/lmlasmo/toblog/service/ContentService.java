package com.lmlasmo.toblog.service;

import static com.lmlasmo.toblog.util.text.TextNormalizer.reduceSpaces;

import java.util.Comparator;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmlasmo.toblog.dto.create.CreateContentDTO;
import com.lmlasmo.toblog.dto.update.UpdateContentDTO;
import com.lmlasmo.toblog.exception.NotFoundException;
import com.lmlasmo.toblog.model.Content;
import com.lmlasmo.toblog.repository.ContentRepository;
import com.lmlasmo.toblog.repository.resume.ContentResume.ContentBodyResume;
import com.lmlasmo.toblog.repository.resume.ContentResume.ContentIdOrderResume;
import com.lmlasmo.toblog.repository.resume.ContentResume.ContentIdOrderTypeResume;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class ContentService {

	private ContentRepository contentRepository;

	@Transactional
	public Mono<Content> createContent(CreateContentDTO create){
		Content content = new Content();
		content.setType(reduceSpaces(create.getType()));
		content.setBody(create.getBody());

		Long postId = create.getPostId();
		Long orderIndex = create.getOrderIndex();

		return validatePostId(postId).doOnNext(content::setPostId)
				.then(reserveOrderIndexByPostId(postId, orderIndex)
						.doOnNext(content::setOrderIndex))
				.then(contentRepository.save(content))
				.then(normalizerOrderIndex(postId))
				.thenReturn(content);
	}

	@Transactional
	public Mono<Content> updateContent(UpdateContentDTO update){
		return contentRepository.findById(update.getId())
				.switchIfEmpty(Mono.error(new NotFoundException("Content not found for id " + update.getId())))
				.flatMap(c -> Mono.when(
						Mono.justOrEmpty(update.getBody())
						.map(String::trim)
						.doOnNext(c::setBody),
						Mono.justOrEmpty(update.getOrderIndex())
						.flatMap(o -> reserveOrderIndexByContentId(c.getId(), o))
						.doOnNext(c::setOrderIndex)
						).thenReturn(c))
				.flatMap(contentRepository::save);
	}

	@Transactional
	public Mono<Void> delete(Long id){
		return contentRepository.deleteById(id);
	}

	public Flux<ContentIdOrderTypeResume> findByPost(Long postId){
		return contentRepository.findIdOrderTypeByPostId(postId);
	}

	public Mono<ContentBodyResume> findBodyByContentId(Long contentId){
		return contentRepository.findBodyById(contentId);
	}

	private Mono<Long> validatePostId(Long id){
		return contentRepository.existsById(id)
				.filter(Boolean::booleanValue)
				.switchIfEmpty(Mono.error(new NotFoundException("Post not found for postId " + id)))
				.thenReturn(id);
	}

	@Transactional
	private Mono<Long> reserveOrderIndexByContentId(Long contentId, Long orderIndex){
		return contentRepository.findPostIdByContentId(contentId)
				.flatMap(i -> reserveOrderIndexByPostId(i, orderIndex));
	}

	@Transactional
	private Mono<Long> reserveOrderIndexByPostId(Long postId, Long orderIndex){
		return contentRepository.findIdOrderResumeByPostId(postId)
				.sort(Comparator.comparingLong(ContentIdOrderResume::getOrderIndex))
				.filter(c -> c.getOrderIndex() >= orderIndex)
				.flatMap(c -> contentRepository.setContentOrderIndexById(c.getId(), c.getOrderIndex()+1))
				.then(Mono.just(orderIndex));
	}

	@Transactional
	private Mono<Void> normalizerOrderIndex(Long postId){
		return contentRepository.findIdOrderResumeByPostId(postId)
				.sort(Comparator.comparingLong(ContentIdOrderResume::getOrderIndex))
				.index()
				.filter(t -> t.getT1() != t.getT2().getOrderIndex())
				.flatMap(t -> contentRepository.setContentOrderIndexById(t.getT2().getId(), t.getT1()))
				.then();
	}

}
