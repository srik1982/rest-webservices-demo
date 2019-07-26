package com.learning.rest.restwebservicesdemo.post;

import java.util.Date;

public class Post {
	private int postId;
	private String mesage;
	private Date postTime;
	
	public Post() {
		
	}
	public Post(int postId, String mesage, Date postTime) {
		super();
		this.postId = postId;
		this.mesage = mesage;
		this.postTime = postTime;
	}
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public String getMesage() {
		return mesage;
	}
	public void setMesage(String mesage) {
		this.mesage = mesage;
	}
	public Date getPostTime() {
		return postTime;
	}
	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
}
