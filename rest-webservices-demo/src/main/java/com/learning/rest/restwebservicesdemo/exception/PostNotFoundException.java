package com.learning.rest.restwebservicesdemo.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;

public class PostNotFoundException extends UserNotFoundException{

	public PostNotFoundException(Date timestamp, String message, HttpStatus status, String details) {
		super(timestamp, message, status, details);
		// TODO Auto-generated constructor stub
	}	

}
