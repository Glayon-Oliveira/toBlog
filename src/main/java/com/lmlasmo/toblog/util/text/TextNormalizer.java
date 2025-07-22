package com.lmlasmo.toblog.util.text;

import java.text.Normalizer;

public interface TextNormalizer {

	public static String normalizeSlug(String slug) {
		if (slug == null || slug.isBlank()) new NullPointerException("Slug is null");

		String normalized = reduceSpaces(slug);
		normalized = Normalizer.normalize(slug, Normalizer.Form.NFD)
				.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

		normalized = normalized.toLowerCase();
		normalized = normalized.replaceAll("[\\s_]+", "-");
		normalized = normalized.replaceAll("[^a-z0-9-]", "");
		normalized = normalized.replaceAll("-{2,}", "-").replaceAll("^-|-$", "");

		return normalized;
	}

	public static String reduceSpaces(String value) {
		if(value == null) return null;
		return value.trim().replaceAll("\\s+", " ");
	}

}
