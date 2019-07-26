package com.learning.rest.restwebservicesdemo.exception;

import java.util.Date;

public interface MyException {

	public Date getTimestamp();

	public String getMessage();

	public String getDetails();
}
