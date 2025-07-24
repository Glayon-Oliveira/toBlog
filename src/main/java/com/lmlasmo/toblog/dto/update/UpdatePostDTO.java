package com.lmlasmo.toblog.dto.update;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lmlasmo.toblog.validation.ImgURL;
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
public class UpdatePostDTO {

	@JsonProperty
	@Min(1)
	@NotNull
	private Long id;

	@JsonProperty
	@Size(min = 1, max = 255)
	@NullableNotBlank
	private String title;

	@JsonProperty
	@Size(min = 1, max = 255)
	@Pattern(regexp = "^[a-z0-9-]+$", message = "O slug só pode conter letras minúsculas, números e '-'")
	@NullableNotBlank
	private String slug;

	@JsonProperty
	@Size(min = 1, max = 255)
	@NullableNotBlank
	private String summary;

	@JsonProperty
	@Size(max = 1024)
	@ImgURL
	private String imgUrl;

}
