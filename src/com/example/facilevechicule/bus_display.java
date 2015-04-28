package com.example.facilevechicule;

import java.io.Serializable;

public class bus_display implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String name;
	protected String time;
	protected String via;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getVia() {
		return via;
	}
	public void setVia(String via) {
		this.via = via;
	}
	
	
	
}
