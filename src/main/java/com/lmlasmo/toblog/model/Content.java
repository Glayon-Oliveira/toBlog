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
@Table(name = "content")
public class Content {

	@Id
	private Long id;
	private String body;

	@Column("post_id")
	private Long postId;

	@Column("order_index")
	private Long orderIndex;

	@Column("content_type")
	private String type;

}
