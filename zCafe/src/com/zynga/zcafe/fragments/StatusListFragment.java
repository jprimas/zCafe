package com.zynga.zcafe.fragments;

import java.util.ArrayList;

import javax.inject.Inject;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.otto.Subscribe;
import com.zynga.zcafe.CafeApplication;
import com.zynga.zcafe.R;
import com.zynga.zcafe.adapters.StatusAdapter;
import com.zynga.zcafe.events.OrderStatusEvent;
import com.zynga.zcafe.inject.modules.CafeModule.MainThreadBus;
import com.zynga.zcafe.models.StatusItem;
import com.zynga.zcafe.services.CafeService;

public class StatusListFragment extends BaseListFragment {

  @Inject
  MainThreadBus bus;

  @Inject
  CafeApplication app;

  @Inject
  CafeService service;

  StatusAdapter adapter;
  ListView lvItems;
  TextView tvLoading;
  ProgressBar progressBar;

  @Inject
  public StatusListFragment() {
    super();
  }

  @Override
  protected void init() {
    ArrayList<StatusItem> items = new ArrayList<StatusItem>();
    FragmentManager manager = this.activity.getSupportFragmentManager();
    adapter = new StatusAdapter(this, manager, app.getApplicationContext(), items);
    lvItems = (ListView) getView().findViewById(R.id.lvFragmentItemsList);
    lvItems.setAdapter(adapter);
    lvItems.setClickable(false);
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    Log.i("ONATTACH", "ATTACH");
  }

  @Override
  public void onCreate(Bundle state) {
    super.onCreate(state);
    Log.i("ONCREATE", "CREATE");
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    progressBar = (ProgressBar) getView().findViewById(R.id.progressBar);
    tvLoading = (TextView)	getView().findViewById(R.id.tvLoading);
    progressBar.setVisibility(ProgressBar.VISIBLE);
    tvLoading.setVisibility(ProgressBar.VISIBLE);
  }

  @Override
  public void onResume() {
    super.onResume();
    Log.i(this.toString(), "RESUME");
    bus.register(this);
    bus.register(adapter);
    String orderStatusUrl = app.getResources().getString(R.string.api_url)
        + app.getResources().getString(R.string.order_status_get_url) + "/"
        + app.getProfile().getUdId();
    getOrderStatus(orderStatusUrl);
  }

  @Override
  public void onPause() {
    super.onPause();
    Log.i(this.toString(), "PAUSE");
    bus.unregister(this);
    bus.unregister(adapter);
  }

  protected void getOrderStatus(String url) {
    CafeService service = CafeApplication.getObjectGraph().get(CafeService.class);
    service.getOrderStatus(url);
  }

  @Subscribe
  public void onOrderStatusEvent(OrderStatusEvent event) {
    if (event.getStatus() == 200) {
      adapter.clear();
      JSONArray jsonArray = new JSONArray();
      try {
        jsonArray = new JSONArray(event.getResponse());
      } catch (JSONException e) {
        e.printStackTrace();
      }
      ArrayList<StatusItem> items = StatusItem.fromJson(jsonArray);
      adapter.push(items);
      adapter.notifyDataSetChanged();
      progressBar.setVisibility(ProgressBar.INVISIBLE);
      tvLoading.setVisibility(ProgressBar.INVISIBLE);
    }

  }


}
