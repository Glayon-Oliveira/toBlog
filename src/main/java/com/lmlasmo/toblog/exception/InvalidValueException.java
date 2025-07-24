package com.lmlasmo.toblog.exception;

public class InvalidValueException extends RuntimeException{

	private static final long serialVersionUID = 7535452481661909125L;

	public InvalidValueException(String message) {
		super(message);
	}

}
