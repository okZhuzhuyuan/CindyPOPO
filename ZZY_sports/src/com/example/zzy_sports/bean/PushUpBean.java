package com.example.zzy_sports.bean;

public class PushUpBean implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1677824170658264212L;
	private int id;
	private String name;
	private String times;
	private String date;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTimes() {
		return times;
	}
	public void setTimes(String times) {
		this.times = times;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

}
