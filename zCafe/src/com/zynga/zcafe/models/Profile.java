package com.zynga.zcafe.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Profile {

  private String userId = "";
  private String name = "";
  private String udid = "";
  private String uaid = "";
  private String device = "android";
  private JSONObject profileJson = new JSONObject();

  @SuppressWarnings("unused")
  private Profile() {
  }

  public Profile(String userId, String userName, String udid, String uaid, String device) {
    this.userId = userId;
    this.name = userName;
    this.udid = udid;
    this.uaid = uaid;
    this.device = device;
    setProfile();
  }

  private Profile put(String key, String value) {
    try {
      profileJson.put(key, value);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return this;
  }

  private void setProfile() {
    put("userId", this.userId).put("name", this.name).put("udid", this.udid)
        .put("uaid", this.uaid).put("device", this.device);
  }

  public JSONObject getProfileJson() {
    return profileJson;
  }

  public String getUdId() {
    return udid;
  }

  public String getUserId() {
    return userId;
  }

  public String getName() {
    return name;
  }

  public String getUaId() {
    return uaid;
  }

  public String getDevice() {
    return device;
  }

}
