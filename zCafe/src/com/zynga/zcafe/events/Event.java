package com.zynga.zcafe.events;


public class Event {

  int status = 0; // Success
  String response = "";

  protected Event() {
  }

  public Event(int status, String response) {
    this.status = status;
    this.response = response;
  }

  public String getResponse() {
    return response;
  }

  public int getStatus() {
    return status;
  }

}
