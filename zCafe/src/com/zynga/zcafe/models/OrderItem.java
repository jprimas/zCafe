package com.zynga.zcafe.models;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class OrderItem {

  private Profile profile = null;
  private String menuId = null;
  private String notes = null;
  private String count = null;
  private JSONObject orderItemJson = new JSONObject();

  public OrderItem() {

  }

  public OrderItem(Profile profile) {
    this.profile = profile;
    setProfile(profile);
  }

  private OrderItem put(String key, String value) {
    try {
      orderItemJson.put(key, value);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return this;
  }

  public OrderItem setMenuId(String menuId) {
    put("menuId", menuId);
    return this;
  }

  public OrderItem setNotes(String notes) {
    put("notes", notes);
    return this;
  }

  public OrderItem setCount(String count) {
    put("count", count);
    return this;
  }

  public OrderItem setProfile(Profile profile) {
    this.profile = profile;
    Iterator<?> keys = profile.getProfileJson().keys();
    while (keys.hasNext()) {
      String key = (String) keys.next();
      try {
        String value = profile.getProfileJson().getString(key);
        Log.i(key, value);
        if (key.equals("name")) {
          put("userName", value);
        } else {
          put(key, value);
        }
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }

    return this;
  }

  public JSONObject getOrderItemJson() {
    return orderItemJson;
  }

}
