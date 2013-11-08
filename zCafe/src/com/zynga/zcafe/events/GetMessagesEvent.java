package com.zynga.zcafe.events;

public class GetMessagesEvent extends Event {

  public GetMessagesEvent(int status, String response) {
    super(status, response);
  }

}
