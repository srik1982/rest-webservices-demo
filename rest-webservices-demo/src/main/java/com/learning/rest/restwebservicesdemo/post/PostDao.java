package com.learning.rest.restwebservicesdemo.post;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.learning.rest.restwebservicesdemo.exception.PostNotFoundException;

@Component
public class PostDao {
	private Map<Integer, List<Post>> userPosts = new HashMap<Integer, List<Post>>();
	private int count = 3;
	
	public PostDao() {
		List<Post> posts = new ArrayList<Post>();
		
		posts.add(new Post(1,"Nice photo", new Date()));
		posts.add(new Post(2,"Awesome", new Date()));
		posts.add(new Post(3,"Jingchak", new Date()));
		
		userPosts.put(1, posts);
		
		posts = new ArrayList<Post>();
		
		posts.add(new Post(1,"which place is this?", new Date()));
		posts.add(new Post(2,"Cute Kids", new Date()));
		posts.add(new Post(3,"Evergreen", new Date()));
		
		userPosts.put(2, posts);
		
		posts = new ArrayList<Post>();
		
		posts.add(new Post(1,"Shut Up", new Date()));
		posts.add(new Post(2,"I have no words", new Date()));
		posts.add(new Post(3,"Best in the world", new Date()));
		
		userPosts.put(3, posts);
	}
	
	public Post addPost(int userId, Post post) {
		if(!userPosts.containsKey(userId)) {
			throw new RuntimeException("User not found");
		}
		post.setPostId(++count);
		userPosts.get(userId).add(post);
		return post;
	}
	
	public List<Post> getPosts(int userId){
		return userPosts.get(userId);
	}
	
	public boolean deletePost(int userId, int postId) {
		if(!userPosts.containsKey(userId)) {
			throw new RuntimeException("User not found");
		}
		List<Post> posts = userPosts.get(userId);
		int ct = posts.size();
		posts =	posts.stream().filter(elem -> elem.getPostId() != postId).collect(Collectors.toList());
		return ct != posts.size();
	}
	
	public Post getPost(int userId, int postId) {
		if(!userPosts.containsKey(userId)) {
			throw new RuntimeException("User not found");
		}
		List<Post> posts = userPosts.get(userId);
		Optional<Post> opt = posts.stream().filter(elem -> elem.getPostId() == postId).findFirst();
		if(opt.isPresent()) {
			return opt.get();
		}else {
			throw new RuntimeException("Post Not found");
		}
	}	
}
