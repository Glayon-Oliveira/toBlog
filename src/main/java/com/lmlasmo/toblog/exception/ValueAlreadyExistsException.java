package com.lmlasmo.toblog.exception;

public class ValueAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 5338766056150934961L;

	public ValueAlreadyExistsException(String message) {
		super(message);
	}

}
