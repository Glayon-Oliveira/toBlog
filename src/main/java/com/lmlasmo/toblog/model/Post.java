package com.lmlasmo.toblog.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "post")
public class Post {

	@Id
	private Long id;
	private String title;
	private String slug;
	private String summary;

	@Column("img_url")
	private String imgUrl;

	@Column("created_at")
	private Instant createdAt;

	@Column("updated_at")
	private Instant updatedAt;

}
