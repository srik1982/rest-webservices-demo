package com.learning.rest.restwebservicesdemo.hello;


import org.springframework.boot.SpringApplication;
import javax.servlet.Filter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

@ComponentScan("com.learning.rest")

@EntityScan("com.learning.rest")

@SpringBootApplication
public class RestWebservicesDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestWebservicesDemoApplication.class, args);
	}
}
