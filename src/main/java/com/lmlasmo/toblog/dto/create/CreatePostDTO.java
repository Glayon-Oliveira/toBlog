package com.lmlasmo.toblog.dto.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lmlasmo.toblog.validation.ImgURL;
import com.lmlasmo.toblog.validation.NullableNotBlank;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreatePostDTO {

	@JsonProperty
	@NotBlank
	@Size(max = 255)
	private String title;

	@JsonProperty
	@Size(max = 255)
	@Pattern(regexp = "^[a-z0-9-]+$", message = "O slug só pode conter letras minúsculas, números e '-'")
	@NullableNotBlank
	private String slug;

	@JsonProperty
	@NotBlank
	@Size(max = 255)
	private String summary;

	@JsonProperty
	@Size(max = 1024)
	@ImgURL
	private String imgUrl;

}
