package com.learning.rest.restwebservicesdemo.exception;

import java.util.Arrays;
import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({
		UserNotFoundException.class
	})
	public final ResponseEntity<MyExceptionResponse> handleUNFException(UserNotFoundException ex, WebRequest request){
		return new ResponseEntity(new MyExceptionResponse(ex.getTimestamp(), ex.getMessage(), ex.getDetails()),ex.getStatus());
	}
	
	@ExceptionHandler({
		Exception.class
	})
	public final ResponseEntity<MyExceptionResponse> handleUncaughtExceptions(Exception ex, WebRequest request){
		return new ResponseEntity(new MyExceptionResponse(new Date(), ex.getMessage(), Arrays.asList(ex.getStackTrace()).toString()),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		return new ResponseEntity(new MyExceptionResponse(new Date(),	"Validation Failed", ex.getBindingResult().toString()), HttpStatus.BAD_REQUEST);
	}
	
	
}
