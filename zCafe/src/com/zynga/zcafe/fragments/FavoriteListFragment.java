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

import com.squareup.otto.Subscribe;
import com.zynga.zcafe.CafeApplication;
import com.zynga.zcafe.R;
import com.zynga.zcafe.adapters.MenuAdapter;
import com.zynga.zcafe.events.FavoriteEvent;
import com.zynga.zcafe.inject.modules.CafeModule.MainThreadBus;
import com.zynga.zcafe.models.MenuItem;
import com.zynga.zcafe.services.CafeService;

public class FavoriteListFragment extends BaseListFragment {

  @Inject
  MainThreadBus bus;

  @Inject
  CafeApplication app;

  @Inject
  CafeService service;

  MenuAdapter adapter;
  ListView lvItems;

  @Inject
  public FavoriteListFragment() {
    super();
  }

  @Override
  protected void init() {
    ArrayList<MenuItem> items = new ArrayList<MenuItem>();
    FragmentManager manager = this.activity.getSupportFragmentManager();
    adapter = new MenuAdapter(this, manager, app.getApplicationContext(), items);
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
  }

  @Override
  public void onResume() {
    super.onResume();
    MenuAdapter.isLoading = 0;
    Log.i(this.toString(), "RESUME");
    bus.register(this);
    String favoriteUrl = app.getResources().getString(R.string.api_url)
        + app.getResources().getString(R.string.menu_get_url) + "/" + app.getProfile().getUdId();
    Log.i("FAVEVENT-URL1", favoriteUrl);
    getFavorite(favoriteUrl);
  }

  @Override
  public void onPause() {
    super.onPause();
    Log.i(this.toString(), "PAUSE");
    bus.unregister(this);
  }

  protected void getFavorite(String url) {
    CafeService service = CafeApplication.getObjectGraph().get(CafeService.class);
    service.getFavorite(url);
  }

  @Subscribe
  public void onFavoriteUpdated(FavoriteEvent event) {
    Log.i("FAVEVENT-CODE", String.valueOf(event.getStatus()));
    Log.i("FAVEVENT-RESPONSE", event.getResponse());
    if (event.getStatus() == 200) {
      adapter.clear();
      JSONArray jsonArray = new JSONArray();
      try {
        jsonArray = new JSONArray(event.getResponse());
      } catch (JSONException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      ArrayList<MenuItem> menuItems = MenuItem.fromJson(jsonArray);
      adapter.addAll(menuItems);
      adapter.notifyDataSetChanged();
    }
  }

}
