package com.example.retailexercise.model;

import java.io.Serializable;

import android.content.Context;

import com.example.retailexercise.helper.StoreDictSQLiteHelper;
import com.google.gson.annotations.SerializedName;

public class RetailItem implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Serialize name for JSON
	@SerializedName("id")
	private int id;
	@SerializedName("name")
	private String name;
	@SerializedName("desc")
	private String desc;
	@SerializedName("regp")
	private double regPrice;
	@SerializedName("salp")
	private double salPrice;
	@SerializedName("img")
	private String imgName;
	@SerializedName("colo")
	private String[] color;
	@SerializedName("stor")
	private int[] stores;
	
	/******* Constructor ******************/
	public RetailItem(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public RetailItem() {
		super();
	}

	/******* Getters and Setters **********/
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
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getImgName() {
		return imgName;
	}
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	public String[] getColor() {
		return color;
	}
	public void setColor(String[] color) {
		this.color = color;
	}
	public int[] getStoreName() {
		return stores;
	}
	public void setStoreName(int[] stores) {
		this.stores = stores;
	}
	public double getRegPrice() {
		return regPrice;
	}
	public void setRegPrice(double regPrice) {
		this.regPrice = regPrice;
	}
	public double getSalPrice() {
		return salPrice;
	}
	public void setSalPrice(double salPrice) {
		this.salPrice = salPrice;
	}

	// Helper function to get and set the non-string values in string
	public String getRegPriceStr() {
		return regPrice+"";
	}

	public void setRegPrice(String regPrice) {
		this.regPrice = Double.parseDouble(regPrice);
	}

	public String getSalPriceStr() {
		return salPrice+"";
	}

	public void setSalPrice(String salPrice) {
		this.salPrice = Double.parseDouble(salPrice);
	}
	public String getColorString(){
		String colorStr = color[0];
		for (int i = 1; i < color.length; i++) {
			colorStr += ","+color[i];
		}
		return colorStr;
	}
	public String getStoreString(){
		String storeStr = stores[0]+"";
		for (int i = 1; i < stores.length; i++) {
			storeStr += ","+stores[i];
		}
		return storeStr;
	}
	public void setStore(String storeIDs){
		String[] idStr = storeIDs.split(",");
		int[] ids = new int[idStr.length];
		for (int i = 0; i < idStr.length; i++) {
			ids[i] = Integer.parseInt(idStr[i]);
		}
		this.stores = ids;
	}
	public void setColor(String colorStr){
		this.color = colorStr.split(",");
	}

	// Generate human readable string for store info
	public String getStoreInfo(Context c){
		StringBuilder storeInfo = new StringBuilder();
		StoreDictSQLiteHelper db = new StoreDictSQLiteHelper(c);
		for(int storeID:this.stores){
			RetailStore store = db.selectStore(storeID);
			storeInfo.append(store.getName() + ", " + store.getLoc() + "\n");
		}
		return storeInfo.toString();
	}
}
