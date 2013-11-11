package com.zynga.zcafe.models;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public class Friend {

  private String name = "";
  private String account = "";
  private String photoUrl = "";

  @SuppressWarnings("unused")
  private Friend() {
  }

  public Friend(String name, String account, String photoUrl) {
    this.name = name;
    this.account = account;
    this.photoUrl = photoUrl;
  }


  public String getName() {
    return name;
  }

  public String getAccount() {
    return account;
  }
  
  public String getPhotoUrl() {
    return photoUrl;
  }
  
  public static class Builder {
    private String name = "";
    private String account = "";
    private String photoUrl = "";
    
    public Builder() {
      
    }
    
    
    public Builder setName(String name) {
      this.name = name;
      return this;
    }
    
    public Builder setAccount(String account) {
      this.account = account;
      return this;
    }
    
    
    public Builder setPhotoUrl(String url) {
      this.photoUrl = url;
      return this;
    }
    
    public Friend build() {
      return new Friend(this);
    }
  }
  
  private Friend(Builder b) {
    this.name = b.name;
    this.account = b.account;
    this.photoUrl = b.photoUrl;
  }
  
  public static ArrayList<Friend> buildFriendsMock() {
    String photoUrl = "http://wendymoore.net/wp-content/uploads/2012/06/Google-+-profile1-150x147.jpg";
    ArrayList<Friend> friends = new ArrayList<Friend>();
    Friend friend1 = new Friend.Builder().setAccount("tlee").setName("Tony Lee").setPhotoUrl(photoUrl).build();
    Friend friend2 = new Friend.Builder().setAccount("jprimas").setName("Josh Primas").setPhotoUrl(photoUrl).build();
    Friend friend3 = new Friend.Builder().setAccount("vsharma").setName("Vaibhav Sharma").setPhotoUrl(photoUrl).build();
    Friend friend4 = new Friend.Builder().setAccount("nidgupta").setName("Nidhi Gupta").setPhotoUrl(photoUrl).build();
    Friend friend5 = new Friend.Builder().setAccount("gasharma").setName("Gaurav Sharma").setPhotoUrl(photoUrl).build();
    friends.add(friend1);
    friends.add(friend2);
    friends.add(friend3);
    friends.add(friend4);
    friends.add(friend5);
    return friends;
  }

}
