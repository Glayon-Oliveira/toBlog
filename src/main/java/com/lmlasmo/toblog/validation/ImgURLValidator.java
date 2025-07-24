package com.lmlasmo.toblog.validation;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ImgURLValidator implements ConstraintValidator<ImgURL, String> {

	private static final Set<String> ALLOWED_EXTENSIONS = Set.of("jpg", "jpeg", "png", "gif", "webp", "bmp", "svg");

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null || value.isBlank()) {
			return true;
		}

		try {
			URL url = new URL(value);
			String protocol = url.getProtocol();

			if (!protocol.equals("http") && !protocol.equals("https")) return false;

			String path = url.getPath();
			int pointIndex = path.lastIndexOf('.');
			if (pointIndex == -1) return false;

			String extension = path.substring(pointIndex + 1).toLowerCase();
			return ALLOWED_EXTENSIONS.contains(extension);

		} catch (MalformedURLException e) {
			return false;
		}
	}
}
