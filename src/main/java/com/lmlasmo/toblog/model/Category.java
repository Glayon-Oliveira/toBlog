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
@Table(name = "categories")
public class Category {

	@Id
	private Long id;
	private String name;
	private String slug;

	@Column("parent_id")
	private Long parentId;

}
