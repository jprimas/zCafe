package com.zynga.zcafe.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Profile {

  private String userId = "";
  private String name = "";
  private String account = "";
  private String udid = "";
  private String uaid = "";
  private String photoUrl = "";
  private String device = "android";
  private JSONObject profileJson = new JSONObject();

  @SuppressWarnings("unused")
  private Profile() {
  }

  private Profile(String userId, String name, String udid, String uaid, String device) {
    this.userId = userId;
    this.name = name;
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
  
  public String getPhotoUrl() {
    return photoUrl;
  }
  
  public static class Builder {
    private String id = "";
    private String name = "";
    private String account = "";
    private String udId = "";
    private String uaId = "";
    private String device = "";
    private String photoUrl = "";
    
    public Builder() {
      
    }
    
    public Builder setId(String id) {
      this.id = id;
      return this;
    }
    
    public Builder setName(String name) {
      this.name = name;
      return this;
    }
    
    public Builder setAccount(String account) {
      this.account = account;
      return this;
    }
    
    public Builder setUdid(String udId) {
      this.udId = udId;
      return this;
    }
    
    public Builder setUaId(String uaId) {
      this.uaId = uaId;
      return this;
    }
    
    public Builder setDevice(String device) {
      this.device = device;
      return this;
    }
    
    public Builder setPhotoUrl(String url) {
      this.photoUrl = url;
      return this;
    }
    
    public Profile build() {
      return new Profile(this);
    }
  }
  
  private Profile(Builder b) {
    this.userId = b.id;
    this.name = b.name;
    this.account = b.account;
    this.udid = b.udId;
    this.uaid = b.uaId;
    this.device = b.device;
    this.photoUrl = b.photoUrl;
    setProfile();
  }
  

}
