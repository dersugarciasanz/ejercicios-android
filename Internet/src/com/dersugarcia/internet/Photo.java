package com.dersugarcia.internet;

public class Photo {
	private int id;
	private String url;
	
	public Photo(int id, String url) {
		setId(id);
		setUrl(url);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
