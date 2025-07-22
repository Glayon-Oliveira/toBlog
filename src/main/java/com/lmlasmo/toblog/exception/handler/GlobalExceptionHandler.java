package com.lmlasmo.toblog.exception.handler;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebInputException;

import com.lmlasmo.toblog.exception.InvalidParentCategoryException;
import com.lmlasmo.toblog.exception.NotFoundException;
import com.lmlasmo.toblog.exception.ValueAlreadyExistsException;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

	@ExceptionHandler(exception = {NotFoundException.class})
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public Map<String, String> handlerNotFoundException(NotFoundException ex, ServerWebExchange exchange){
		return bodyError(HttpStatus.NOT_FOUND, exchange, Map.of("message", ex.getMessage()));
	}

	@ExceptionHandler(exception = {ValueAlreadyExistsException.class})
	@ResponseStatus(code = HttpStatus.CONFLICT)
	public Map<String, String> handlerValueAlreadyExistsException(ValueAlreadyExistsException ex, ServerWebExchange exchange){
		return bodyError(HttpStatus.CONFLICT, exchange, Map.of("message", ex.getMessage()));
	}

	@ExceptionHandler(exception = {InvalidParentCategoryException.class})
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public Map<String, String> handlerInvalidParentCategoryException(InvalidParentCategoryException ex, ServerWebExchange exchange){
		return bodyError(HttpStatus.BAD_REQUEST, exchange, Map.of("message", ex.getMessage()));
	}

	@ExceptionHandler(WebExchangeBindException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, String> handleValidationException(WebExchangeBindException ex, ServerWebExchange exchange) {
		Map<String, String> errors = bodyError(HttpStatus.BAD_REQUEST, exchange, null);
		ex.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
		return errors;
	}

	@ExceptionHandler(ServerWebInputException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, String> handleRequestException(ServerWebInputException ex, ServerWebExchange exchange) {
		return bodyError(HttpStatus.BAD_REQUEST, exchange,
				Map.of("message", "Invalid request parameter: " + ex.getReason()));
	}

	public static Map<String, String> bodyError(HttpStatus status, ServerWebExchange exchange, Map<String, String> extra){
		if(extra == null) extra = Map.of();
		Map<String, String> body = new LinkedHashMap<>(Map.of(
				"timestamp", Instant.now().toString(),
				"status", Integer.toString(status.value()),
				"error", status.getReasonPhrase(),
				"path", exchange.getRequest().getPath().value()
				));
		body.putAll(extra);
		return body;
	}

}
