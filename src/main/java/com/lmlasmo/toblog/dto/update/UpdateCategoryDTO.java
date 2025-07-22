package com.lmlasmo.toblog.dto.update;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lmlasmo.toblog.validation.NullableNotBlank;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateCategoryDTO {

	@JsonProperty
	@Min(1)
	@NotNull
	private Long id;

	@JsonProperty
	@Size(max = 255)
	@NullableNotBlank
	private String name;

	@JsonProperty
	@Size(max = 255)
	@Pattern(regexp = "^[a-z0-9-]+$", message = "O slug só pode conter letras minúsculas, números e '-'")
	@NullableNotBlank
	private String slug;

	@JsonProperty
	@Min(1)
	private Long parentId;

}
