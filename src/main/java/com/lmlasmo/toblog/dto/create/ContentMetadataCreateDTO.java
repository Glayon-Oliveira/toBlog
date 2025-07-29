package com.lmlasmo.toblog.dto.create;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContentMetadataCreateDTO {

	@JsonProperty
	@Min(1)
	@NotNull
	private Long contentId;

	@JsonProperty
	@NotBlank
	private String metaType;

	@JsonProperty
	@NotBlank
	private String metaContent;

}
