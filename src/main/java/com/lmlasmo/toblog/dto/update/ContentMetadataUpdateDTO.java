package com.lmlasmo.toblog.dto.update;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lmlasmo.toblog.validation.NullableNotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContentMetadataUpdateDTO {

	private Long id;

	@JsonProperty
	@NullableNotBlank
	private String metaContent;

}
