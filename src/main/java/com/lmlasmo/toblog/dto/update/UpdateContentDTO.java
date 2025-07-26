package com.lmlasmo.toblog.dto.update;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lmlasmo.toblog.validation.NullableNotBlank;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateContentDTO {

	@JsonProperty
	@Min(1)
	@NotNull
	private Long id;

	@JsonProperty
	@NullableNotBlank
	private String body;

	@JsonProperty
	@Min(0)
	private Long orderIndex;

}
