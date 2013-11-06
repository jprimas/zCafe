package com.zynga.zcafe.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StatusItem {

  private String url = "";
  private String menuTitle = "";
  private String orderId = "";
  private String status = "";
  private String userName = "";
  private String queueCount = "";
  private String orderDate = "";

  private StatusItem() {

  }

  public String getThumbImageUrl() {
    return url;
  }

  public String getMenuTitle() {
    return menuTitle;
  }

  public String getOrderId() {
    return orderId;
  }

  public String getStatus() {
    return status;
  }

  public String getUserName() {
    return userName;
  }

  public String getQueueCount() {
    return queueCount;
  }

  public String getOrderDate() {
    return orderDate;
  }

  public static StatusItem fromJson(JSONObject jsonObject) {
    StatusItem item = new StatusItem();
    try {
      item.orderId = jsonObject.getString("id");
      item.menuTitle = jsonObject.getJSONObject("menu").getString("title");
      item.userName = jsonObject.getString("userName");
      item.status = jsonObject.getString("status");
      item.queueCount = jsonObject.getString("queue");
      item.url = jsonObject.getJSONObject("menu").getString("thumbImageUrl");
      item.orderDate = jsonObject.getString("createdAt");
    } catch (JSONException e) {
      e.printStackTrace();
      return null;
    }
    return item;
  }

  public static ArrayList<StatusItem> fromJson(JSONArray jsonArray) {
    ArrayList<StatusItem> items = new ArrayList<StatusItem>(jsonArray.length());

    for (int i = 0; i < jsonArray.length(); i++) {
      JSONObject jsonItem = null;
      try {
        jsonItem = jsonArray.getJSONObject(i);
      } catch (Exception e) {
        e.printStackTrace();
        continue;
      }

      StatusItem item = StatusItem.fromJson(jsonItem);
      if (item != null) {
        items.add(item);
      }
    }

    return items;
  }

}
