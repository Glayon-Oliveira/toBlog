package com.lmlasmo.toblog.dto.create;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateCategoryDTO {

	@JsonProperty
	@Size(max = 255)
	@NotBlank
	private String name;

	@JsonProperty(required = false)
	@Size(max = 255)
	@Pattern(regexp = "^[a-z0-9-]+$", message = "O slug só pode conter letras minúsculas, números e '-'")
	private String slug;

	@JsonProperty(required = false)
	@Min(1)
	@Max(Long.MAX_VALUE)
	private Long parentId;

}
