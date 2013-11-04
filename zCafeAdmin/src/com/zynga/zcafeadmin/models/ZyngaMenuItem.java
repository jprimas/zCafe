package com.zynga.zcafeadmin.models;

import org.json.JSONException;
import org.json.JSONObject;


public class ZyngaMenuItem {
	
	private int id;
	private String title;
	private String description;
	private String imageName;
	private String thumbImageUrl;
	private String imageUrl;
	
	public ZyngaMenuItem(){
		id = -1;
		title = "";
		description = "";
		imageName = "";
		thumbImageUrl = "";
		imageUrl = "";
	}
	
	public static ZyngaMenuItem toZyngaMenuItemObject(JSONObject obj){
		if(obj == null){
			return null;
		}
		try{
			ZyngaMenuItem item = new ZyngaMenuItem();
			if(obj.has("id")){
				item.setId(obj.getInt("id"));
			}
			if(obj.has("title")){
				item.setTitle(obj.getString("title"));
			}
			if(obj.has("description")){
				item.setDescription(obj.getString("description"));
			}
			if(obj.has("imageName")){
				item.setImageName(obj.getString("imageName"));
			}
			if(obj.has("thumbImageUrl")){
				item.setThumbImageUrl(obj.getString("thumbImageUrl"));
			}
			if(obj.has("imageUrl")){
				item.setImageUrl(obj.getString("imageUrl"));
			}
			return item;
			
		}catch (JSONException e){
			e.printStackTrace();
		}
		
		return null;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getThumbImageUrl() {
		return thumbImageUrl;
	}

	public void setThumbImageUrl(String thumbImageUrl) {
		this.thumbImageUrl = thumbImageUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
}
