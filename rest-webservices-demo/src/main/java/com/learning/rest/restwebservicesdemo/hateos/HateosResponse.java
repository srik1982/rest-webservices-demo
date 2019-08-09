package com.learning.rest.restwebservicesdemo.hateos;

import org.springframework.hateoas.ResourceSupport;

public class HateosResponse extends ResourceSupport{
	private String name = "Srikanth";
	private String greeting = "Hello Srikanth";
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGreeting() {
		return greeting;
	}
	public void setGreeting(String greeting) {
		this.greeting = greeting;
	}	
}
