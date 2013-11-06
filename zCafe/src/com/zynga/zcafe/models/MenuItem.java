package com.zynga.zcafe.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MenuItem {

  private JSONObject jsonObject;
  private String title;
  private String description;
  private String imageName;
  private String thumbImageUrl;
  private String imageUrl;
  private Long id;

  private MenuItem() {
    title = "";
    description = "";
    imageName = "";
    thumbImageUrl = "";
    imageUrl = "";
    id = null;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public String getImageName() {
    return imageName;
  }

  public String getThumbImageUrl() {
    return thumbImageUrl;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public String getId() {
    return String.valueOf(id);
  }

  public static MenuItem fromJson(JSONObject json) {
    MenuItem item = new MenuItem();
    try {
      item.jsonObject = json;
      item.title = json.getString("title");
      item.description = json.getString("description");
      item.imageName = json.getString("imageName");
      item.thumbImageUrl = json.getString("thumbImageUrl");
      item.imageUrl = json.getString("imageUrl");
      item.id = json.getLong("id");
    } catch (JSONException e) {
      e.printStackTrace();
      return null;
    }
    return item;
  }

  public static ArrayList<MenuItem> fromJson(JSONArray jsonArray) {
    ArrayList<MenuItem> items = new ArrayList<MenuItem>(jsonArray.length());
    
    for (int i = 0; i < jsonArray.length(); i++) {
      JSONObject json = null;
      try {
        json = jsonArray.getJSONObject(i);
      } catch (JSONException e) {
        e.printStackTrace();
        continue;
      }
      MenuItem menuItem = MenuItem.fromJson(json);
      if (menuItem != null) {
        items.add(menuItem);
      }
    }
    
    return items;
  }

}
