package com.zynga.zcafe.events;


public class OrderEvent extends Event {

  public OrderEvent(int status, String response) {
    super(status, response);
  }

}

