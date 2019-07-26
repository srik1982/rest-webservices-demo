package com.learning.rest.restwebservicesdemo.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostResource {
	
	@Autowired
	private PostDao postDao;
	
	@GetMapping("/users/{userId}/posts")
	public List<Post> getPosts(@PathVariable int userId){
		return postDao.getPosts(userId);
	}
	
	@GetMapping(path = "/users/{userId}/posts/{postId}")
	public Post getPost(@PathVariable int userId, @PathVariable int postId ) {
		return postDao.getPost(userId, postId);
	}
	
	@PostMapping("/users/{userId}/posts/")
	public Post createPost(@PathVariable int userId, @RequestBody Post post) {
		return postDao.addPost(userId, post);
	}
	
	@DeleteMapping("/users/{userId}/posts/{postId}")
	public void deletePost(@PathVariable int userId, @PathVariable int postId) {
		postDao.deletePost(userId, postId);
	}

}
