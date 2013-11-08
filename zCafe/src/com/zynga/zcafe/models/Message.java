package com.zynga.zcafe.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Message {

  private String photoUrl = "";
  private String name = "";
  private String udid = "";
  private String message = "";
  private String date = "";

  private Message() {

  }

  private Message(String name, String udid, String message) {
    this.name = name;
    this.message = message;
    this.udid = udid;
  }

  public String getPhotoUrl() {
    return photoUrl;
  }

  public String getName() {
    return name;
  }

  public String getUdId() {
    return udid;
  }

  public String getMessage() {
    return message;
  }

  public String getDate() {
    return date;
  }

  public JSONObject toJsonObject() {
    JSONObject json = new JSONObject();
    try {
      json.put("name", name);
      json.put("udid", udid);
      json.put("message", message);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return json;
  }

  public static Message fromJson(JSONObject json) {
    Message message = new Message();
    try {
      message.name = json.getString("name");
      message.photoUrl = json.getString("photoUrl");
      message.udid = json.getString("udid");
      message.message = json.getString("message");
      message.date = json.getString("date");
    } catch (JSONException e) {
      e.printStackTrace();
      return null;
    }
    return message;
  }

  public static ArrayList<Message> fromJson(JSONArray jsonArray) {
    ArrayList<Message> messages = new ArrayList<Message>(jsonArray.length());

    for (int i = 0; i < jsonArray.length(); i++) {
      JSONObject json = null;
      try {
        json = jsonArray.getJSONObject(i);
      } catch (JSONException e) {
        e.printStackTrace();
        continue;
      }
      Message message = Message.fromJson(json);
      if (message != null) {
        messages.add(message);
      }
    }

    return messages;
  }

  public static Message build(String name, String udid, String message) {
    return new Message(name, udid, message);
  }
}
