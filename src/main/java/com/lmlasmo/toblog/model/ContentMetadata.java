package com.lmlasmo.toblog.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "content_metadata")
public class ContentMetadata {

	@Id
	private Long id;

	@Column("content_id")
	private Long contentId;

	@Column("meta_type")
	private String metaType;

	@Column("mete_content")
	private String metaContent;

}
