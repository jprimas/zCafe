package com.zynga.zcafe.events;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.squareup.otto.Produce;
import com.squareup.otto.Subscribe;
import com.zynga.zcafe.inject.modules.CafeModule.MainThreadBus;
import com.zynga.zcafe.models.Friend;
import com.zynga.zcafe.models.MenuItem;

public class Producers {

  private static Producers instance = null;

  private MainThreadBus bus;

  private MenuItem menuItem;

  private OrderStatusEvent orderStatusEvent;
  private UaIdEvent uaIdEvent;
  private ArrayList<Friend> friendArray = new ArrayList<Friend>();

  public static Producers getInstance(MainThreadBus bus) {
    if (instance == null) {
      instance = new Producers(bus);
    }
    return instance;
  }

  private Producers(MainThreadBus bus) {
    this.menuItem = null;
    this.bus = bus;
    bus.register(this);
  }

  public Producers setMenuItem(MenuItem menuItem) {
    this.menuItem = menuItem;
    return this;
  }

  public Producers setOrderStatusEvent(OrderStatusEvent event) {
    this.orderStatusEvent = event;
    return this;
  }

  public Producers setUaIdEvent(UaIdEvent event) {
    this.uaIdEvent = event;
    return this;
  }

  public MainThreadBus getBus() {
    return this.bus;
  }

  @Produce
  public MenuItem getMenuItem() {
    return menuItem;
  }

  @Produce
  public OrderStatusEvent getOrderStatusEvent() {
    return orderStatusEvent;
  }

  @Produce
  public UaIdEvent getUaIdEvent() {
    return uaIdEvent;
  }
  
  @Produce
  public GetFriendArrayEvent getFriendArray() {
    Log.i("PRODUCER", "GetFriendArrayEvent");
    GetFriendArrayEvent event = new GetFriendArrayEvent(this.friendArray);
    return event;
  }
  
  @Subscribe
  public void onGetFriendsEvent(GetFriendsEvent event) {
    Log.i("GETFRIENDSEVENT", "RECEIVE EVENT");
    this.friendArray.clear();
    this.friendArray.addAll(event.getFriendArray());
    
  }

}
