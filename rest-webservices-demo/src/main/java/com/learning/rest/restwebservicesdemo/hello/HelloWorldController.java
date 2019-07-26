package com.learning.rest.restwebservicesdemo.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
public class HelloWorldController {
	
	@Autowired
	private WebRequest webRequest;
	
	@GetMapping(path = "/hello")
	public Resource<String> test() {
		Resource res = new Resource("Hello World!.  ");
		res.add(new Link(webRequest.getHeaderNames().toString()));
		return res;
	}
	
	@GetMapping(path = "/hello-bean")
	public HelloWorldBean getBean() {
		return new HelloWorldBean("Hello! I am a bean.");
	}

}
