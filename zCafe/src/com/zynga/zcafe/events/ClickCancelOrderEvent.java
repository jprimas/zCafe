package com.zynga.zcafe.events;

import android.util.SparseArray;
import android.view.View;

public class ClickCancelOrderEvent {
  
  private SparseArray<View> viewCache;

  public ClickCancelOrderEvent(SparseArray<View> cache) {
    this.viewCache = cache;
  }
  
  public SparseArray<View> getViewCache() {
    return viewCache;
  }
}
