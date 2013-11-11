package com.zynga.zcafe.events;

import java.util.ArrayList;

import com.zynga.zcafe.models.Friend;

public class GetFriendArrayEvent {
  
  private ArrayList<Friend> friends;

  public GetFriendArrayEvent(ArrayList<Friend> friends) {
    this.friends = friends;
  }
  
  public ArrayList<Friend> getFriends() {
    return friends;
  }

}
