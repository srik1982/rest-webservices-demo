package com.learning.rest.restwebservicesdemo.user;

import java.util.List;

public class Users {
	private List<User> users;
	
	public Users(List<User> users) {
		this.users = users;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	} 
	
}
