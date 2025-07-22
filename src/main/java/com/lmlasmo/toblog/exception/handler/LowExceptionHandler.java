package com.lmlasmo.toblog.exception.handler;

import java.util.Map;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;

@RestControllerAdvice
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
@Order(Ordered.LOWEST_PRECEDENCE)
public class LowExceptionHandler {

	@ExceptionHandler(RuntimeException.class)
	public Map<String, String> handleGenericException(RuntimeException ex, ServerWebExchange exchange) {
		return GlobalExceptionHandler.bodyError(HttpStatus.INTERNAL_SERVER_ERROR, exchange,
				Map.of("message", "Unexpected error: " + ex.getMessage()));
	}

}
