package com.zynga.zcafeadmin.models;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ZyngaCoffee {
	
	private int id;
	private int userId;
	private int menuId;
	private String status;
	private String notes;
	private int count;
	private String userName;
	private ZyngaMenuItem item;
	private String createdAt;
	private String updatedAt;
	
	public ZyngaCoffee(){
		id = -1;
		userId = 0;
		menuId = 0;
		status = null;
		notes = "";
		count = 0;
		userName = "";
		item = null;
		createdAt = null;
		updatedAt = null;
	}
	
	public static ZyngaCoffee toZyngaCoffeeObject(JSONObject obj){
		if(obj == null){
			return null;
		}
		try{
			ZyngaCoffee coffee = new ZyngaCoffee();
			if(obj.has("id")){
				coffee.setId(obj.getInt("id"));
			}
			if(obj.has("userId")){
				coffee.setUserId(obj.getInt("userId"));
			}
			if(obj.has("menuId")){
				coffee.setMenuId(obj.getInt("menuId"));
			}
			if(obj.has("status")){
				coffee.setStatus(obj.getString("status"));
			}
			if(obj.has("notes")){
				coffee.setNotes(obj.getString("notes"));
			}
			if(obj.has("count")){
				coffee.setCount(obj.getInt("count"));
			}
			if(obj.has("userName")){
				coffee.setUserName(obj.getString("userName"));
			}
			if(obj.has("menu")){
				coffee.setItem(ZyngaMenuItem.toZyngaMenuItemObject(obj.getJSONObject("menu")));
			}
			if(obj.has("createdAt")){
			    coffee.setCreatedAt(obj.getString("createdAt"));
			}
			if(obj.has("updatedAt")){
			    coffee.setUpdatedAt(obj.getString("updatedAt"));
			}
			return coffee;
			
		}catch (JSONException e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static ArrayList<ZyngaCoffee> toZyngaCoffeeObjectArray(JSONArray array){
		ArrayList<ZyngaCoffee> coffeeArray = new ArrayList<ZyngaCoffee>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject obj = null;
			try {
				obj = array.getJSONObject(i);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			ZyngaCoffee coffee = toZyngaCoffeeObject(obj);
			coffeeArray.add(coffee);
		}
		return coffeeArray;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public ZyngaMenuItem getItem() {
		return item;
	}

	public void setItem(ZyngaMenuItem item) {
		this.item = item;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	
}
