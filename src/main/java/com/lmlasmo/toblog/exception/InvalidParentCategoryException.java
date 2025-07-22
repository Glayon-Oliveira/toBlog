package com.lmlasmo.toblog.exception;

public class InvalidParentCategoryException extends RuntimeException {

	private static final long serialVersionUID = 6640244938791284793L;

	public InvalidParentCategoryException(String message) {
		super(message);
	}

}
