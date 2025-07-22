package com.lmlasmo.toblog.exception;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = -676483394361579841L;

	public NotFoundException(String message) {
		super(message);
	}

}
