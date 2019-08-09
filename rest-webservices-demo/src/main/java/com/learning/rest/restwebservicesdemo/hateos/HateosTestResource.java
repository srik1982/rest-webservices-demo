package com.learning.rest.restwebservicesdemo.hateos;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/hateos")
public class HateosTestResource {
	
	/*
	 * Just Uses Link and Resource class
	 */
	@GetMapping("basic")
	public ResponseEntity<Resource<HateosResponse>> test(HttpServletRequest request){
		String uri = request.getRequestURL().toString();
		Link link = new Link(uri,"self");
		Resource<HateosResponse> res = new Resource<HateosResponse>(new HateosResponse(), link);
		return new ResponseEntity<Resource<HateosResponse>>(res, HttpStatus.OK);
	}
	
	/*
	 * Uses Link but ResourceSupport extended by HateosResponse
	 */
	@GetMapping("support")
	public ResponseEntity<HateosResponse> testSupport(HttpServletRequest request){
		String uri = request.getRequestURL().toString();				
		HateosResponse res = new HateosResponse();
		res.add(new Link(uri,"self"));
		return new ResponseEntity<HateosResponse>(res, HttpStatus.OK);
	}
	
	/*
	 * Uses ControllerLinkBuilder.
	 * Use this to get base link to the class and then append more
	 */
	@GetMapping("controller")
	public ResponseEntity<HateosResponse> testController(){
		ControllerLinkBuilder linkBuilder = ControllerLinkBuilder.linkTo(HateosTestResource.class).slash("/dangling");
		HateosResponse res = new HateosResponse();
		res.add(linkBuilder.withRel("no-where"));
		return ResponseEntity.ok(res);
	}
	
	/*
	 * Uses ControllerLinkBuilder.
	 * Use this to get to method the class
	 */
	@GetMapping("controller-more")
	public ResponseEntity<HateosResponse> testControllerAdvanced(){
			ControllerLinkBuilder linkBuilder = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).testAdvance());
			HateosResponse res = new HateosResponse();
			res.add(linkBuilder.withRel("to-controller"));
			return ResponseEntity.ok(res);
	}
	
	/*
	 * Gets the requrest URI automatically from the request and then append more.
	 */
	@GetMapping("advanced")
	public ResponseEntity<HateosResponse> testAdvance(){
		String uri = ServletUriComponentsBuilder.fromCurrentRequestUri().queryParam("timewindow", "3d").build().toString();
		HateosResponse res = new HateosResponse();
		res.add(new Link(uri));
		return ResponseEntity.ok(res);
	}
}
