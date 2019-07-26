package com.learning.rest.restwebservicesdemo.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PackageTest {

	@GetMapping("/package")
	public String packageTest() {
		return "Works within the package!";
	}
}
