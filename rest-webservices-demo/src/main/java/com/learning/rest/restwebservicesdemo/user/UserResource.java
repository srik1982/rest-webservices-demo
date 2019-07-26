package com.learning.rest.restwebservicesdemo.user;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.learning.rest.restwebservicesdemo.exception.UserNotFoundException;

@RestController
public class UserResource {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	WebRequest webReq;
	
	/*
	 * I have hardcoded the etag here.
	 * But in a production system, when we do a post, put this would change the repo, so we would need to change the eTag.
	 * eTag can be a MD5 hash of the table. Or simply a version number of the table.
	 * I am not too good with either DB or JPA to know what is the best way to compute eTag.
	 */
	private String getETag() {
		return "\"v1\"";
	}
	
	@GetMapping("/users")
	public ResponseEntity<Resource<Users>> getUsers(){
		
		if(webReq.checkNotModified(getETag())) {
			return null;
		}
		
		List<User> users = userDao.getUsers();
		Users allUsers = new Users(users);
		Resource<Users> res = new Resource<Users>(allUsers);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").build().toUri();
		Link link = new Link(location.toString(), "singular");
		
		res.add(link);
		HttpHeaders headers = new HttpHeaders();
	    headers.setETag("\"v1\"");
	    CacheControl cc = CacheControl.maxAge(1, TimeUnit.HOURS).noTransform().cachePrivate();	    
	    headers.setCacheControl(cc);
		ResponseEntity<Resource<Users>> entity = new ResponseEntity<Resource<Users>>(res, headers,HttpStatus.OK);
		
		return entity;
	}
	
	@GetMapping("/users/{id}")
	public Resource<User> getUser(@PathVariable(value="id") int id2) {
		User user = userDao.getUser(id2);
		if(user == null) {
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
			throw new UserNotFoundException(new Date(), String.format("User with id %s not found",id2), HttpStatus.NOT_FOUND, location.toString());
		}
		
		Resource<User> resource = new Resource<User>(user);
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getUsers());
		resource.add(linkTo.withRel("all-users"));
		return resource;
	}
	
	@PostMapping(path = "users" , produces = {"application/json" , "application/asd+json" })
	public ResponseEntity<Resource<User>> createUser(@Valid @RequestBody User user) {
		User createdUser = userDao.addUser(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(createdUser.getId()).toUri();
		
		Resource<User> resource = new Resource<User>(createdUser);
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getUser(createdUser.getId()));
		resource.add(linkTo.withRel("all-users"));
		HttpHeaders headers = new HttpHeaders();
	    headers.add("location", location.toString());
		ResponseEntity<Resource<User>> entity = new ResponseEntity<Resource<User>>(resource, headers, HttpStatus.CREATED);
		
		return entity;
	}
	
	@DeleteMapping("users/{id}")
	public void deleteUser(@PathVariable int id) {
		userDao.deleteUser(id);
	}
	
	@GetMapping(path = "/users/{id}/filtered")
	public MappingJacksonValue getuser1(@PathVariable int id, @RequestParam(name = "version", defaultValue = "1.0") String ver) {
		User user = userDao.getUser(id);
		Resource<User> res = new Resource<User>(user);
		res.add(new Link(ServletUriComponentsBuilder.fromCurrentRequest().path(ver).toUriString()));
		
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(res);
		FilterProvider filters = new SimpleFilterProvider().addFilter("user.filter", SimpleBeanPropertyFilter.filterOutAllExcept("name"));
		mappingJacksonValue.setFilters(filters);
		
		
		
		return mappingJacksonValue;
	}
}
