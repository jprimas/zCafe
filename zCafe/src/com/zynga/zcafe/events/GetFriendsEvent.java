package com.zynga.zcafe.events;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zynga.zcafe.models.Friend;

public class GetFriendsEvent extends Event {
  
  private static final String PHOTO_URL =
      "http://wendymoore.net/wp-content/uploads/2012/06/Google-+-profile1-150x147.jpg";

  public GetFriendsEvent(int status, String response) {
    super(status, response);
  }
  
  public ArrayList<Friend> getFriendArray() {
    ArrayList<Friend> friends = new ArrayList<Friend>();
    JSONObject json = null;
    try {
      json = new JSONObject(response);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    
    JSONArray jsonArray;
    try {
      jsonArray = json.getJSONArray("stringList");
      for (int i = 0; i < jsonArray.length(); i++) {
        Friend friend = new Friend.Builder().setAccount(jsonArray.getString(i))
                .setName(jsonArray.getString(i))
                .setPhotoUrl(PHOTO_URL)
                .build();
        friends.add(friend);
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }

    
    return friends;
  }

}
