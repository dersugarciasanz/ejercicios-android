package com.dersugarcia.earthquakes;

public class EarthQuake {
	
	private int id;
	private String place;
	private long time;
	private String detail;
	private double magnitude;
	private double latitude;
	private double longitude;
	private String url;
	private long created_at;
	private long updated_at;
	
	public EarthQuake(int id, String place, long time, String detail,
			double magnitude, double latitude, double longitude, String url,
			long created_at, long updated_at) {
		
		this(place, time, detail, magnitude, latitude, longitude, url);
		this.id = id;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}
	
	public EarthQuake(String place, long time, String detail,
			double magnitude, double latitude, double longitude, String url) {
		
		this.place = place;
		this.time = time;
		this.detail = detail;
		this.magnitude = magnitude;
		this.latitude = latitude;
		this.longitude = longitude;
		this.url = url;
	}

	public int getId() {
		return id;
	}
	public String getPlace() {
		return place;
	}
	public long getTime() {
		return time;
	}
	public String getDetail() {
		return detail;
	}
	public double getMagnitude() {
		return magnitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public String getUrl() {
		return url;
	}
	public long getCreated_at() {
		return created_at;
	}
	public long getUpdated_at() {
		return updated_at;
	}

}
