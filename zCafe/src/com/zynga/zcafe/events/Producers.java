package com.zynga.zcafe.events;

import com.squareup.otto.Produce;
import com.zynga.zcafe.inject.modules.CafeModule.MainThreadBus;
import com.zynga.zcafe.models.MenuItem;

public class Producers {

  private static Producers instance = null;

  private MainThreadBus bus;

  private MenuItem menuItem;

  private OrderStatusEvent orderStatusEvent;
  private UaIdEvent uaIdEvent;

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

}
