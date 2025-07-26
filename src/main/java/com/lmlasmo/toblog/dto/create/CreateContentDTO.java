package com.lmlasmo.toblog.dto.create;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateContentDTO {

	@JsonProperty
	@NotBlank
	@Size(max = 20)
	private String type;

	@JsonProperty
	@NotBlank
	private String body;

	@JsonProperty
	@NotBlank
	@Min(0)
	private Long orderIndex;

	@JsonProperty
	@NotNull
	@Min(1)
	private Long postId;

}
