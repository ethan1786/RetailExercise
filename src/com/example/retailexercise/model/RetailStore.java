package com.example.retailexercise.model;

import com.google.gson.annotations.SerializedName;

public class RetailStore {
	@SerializedName("id")
	private int id;
	@SerializedName("name")
	private String name;
	@SerializedName("loc")
	private String loc;
	
	public RetailStore(int id, String name, String loc) {
		super();
		this.id = id;
		this.name = name;
		this.loc = loc;
	}

	public RetailStore() {
		super();
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getLoc() {
		return loc;
	}
	
}
