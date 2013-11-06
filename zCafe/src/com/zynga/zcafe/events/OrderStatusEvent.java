package com.zynga.zcafe.events;


public class OrderStatusEvent extends Event {

  public OrderStatusEvent(int status, String response) {
    super(status, response);
  }

}
