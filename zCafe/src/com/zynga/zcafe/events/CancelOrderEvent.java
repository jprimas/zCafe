package com.zynga.zcafe.events;

public class CancelOrderEvent extends Event {
  public CancelOrderEvent(int status, String response) {
    super(status, response);
  }
}
