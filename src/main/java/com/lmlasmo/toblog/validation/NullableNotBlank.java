package com.lmlasmo.toblog.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Payload;

@Documented
@Target({ FIELD, PARAMETER })
@Retention(RUNTIME)
public @interface NullableNotBlank {

	String message() default "The image url must be a valid URL (http/https) ending with a valid image extension";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
