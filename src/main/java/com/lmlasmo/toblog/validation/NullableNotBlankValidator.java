package com.lmlasmo.toblog.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NullableNotBlankValidator implements ConstraintValidator<NullableNotBlank, String>{

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(value != null && value.isBlank()) return false;
		return true;
	}

}
