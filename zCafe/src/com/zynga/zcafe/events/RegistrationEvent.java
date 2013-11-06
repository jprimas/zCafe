package com.zynga.zcafe.events;


public class RegistrationEvent {

  private String response;
  private int status;

  public RegistrationEvent(String response, int status) {
    this.response = response;
    this.status = status;
  }

  public String getResponse() {
    return response;
  }

  public int getStatus() {
    return status;
  }


}
