package com.lmlasmo.toblog.dto.read;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDTO {

	private Long id;
	private String name;
	private String slug;

	@JsonInclude(value = Include.NON_NULL)
	private CategoryDTO parent;

	@JsonInclude(value = Include.NON_NULL)
	private List<CategoryDTO> childrens;

}
