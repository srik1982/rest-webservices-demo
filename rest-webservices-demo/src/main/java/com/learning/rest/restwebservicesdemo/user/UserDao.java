package com.learning.rest.restwebservicesdemo.user;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class UserDao {

	List<User> users = new ArrayList<User>();
	int count = 3;
	
	UserDao(){
		users.add(new User(1,"Srikanth", new Date()));
		users.add(new User(2,"Shailaja", new Date()));
		users.add(new User(3,"Anarghya", new Date()));
	}
	
	public User addUser(User user) {
		user.setId(++count);
		users.add(user);
		return user;
	}
	
	public List<User> getUsers(){
		return users;
	}
	
	public User getUser(final int id) {
		Optional<User>  user = users.stream().filter(elem -> elem.getId() == id).findFirst();
		return user.isPresent() ? user.get() : null;
	}
	
	public void deleteUser(int id) {
		users = users.stream().filter(elem -> elem.getId() !=id).collect(Collectors.toList());
	}
}
