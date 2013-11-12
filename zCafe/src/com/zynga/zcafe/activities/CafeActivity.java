package com.zynga.zcafe.activities;

import java.io.UnsupportedEncodingException;

import javax.inject.Inject;

import org.apache.http.entity.StringEntity;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.squareup.otto.Produce;
import com.zynga.zcafe.CafeApplication;
import com.zynga.zcafe.R;
import com.zynga.zcafe.fragments.ConnectFragment;
import com.zynga.zcafe.fragments.FavoriteListFragment;
import com.zynga.zcafe.fragments.MenuListFragment;
import com.zynga.zcafe.fragments.StatusListFragment;
import com.zynga.zcafe.inject.modules.CafeModule.MainThreadBus;
import com.zynga.zcafe.listeners.FragmentTabListener;
import com.zynga.zcafe.models.Profile;
import com.zynga.zcafe.services.CafeService;

/**
 * The main activity.
 */
public class CafeActivity extends FragmentActivity {

  public static final String PREFS = "CafePreferences";

  @Inject
  MainThreadBus bus;

  @Inject
  CafeApplication app;

  @Inject
  CafeService service;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_cafe);
    CafeApplication.getObjectGraph().inject(this);
    if (!CafeApplication.getObjectGraph().get(CafeApplication.class).isRegisteredUser()) {
      Intent intent = new Intent(this, RegistrationActivity.class);
      startActivity(intent);
    } else {
      setupTabs();
    }
  }

  private void setupTabs() {
    ActionBar actionBar = getActionBar();
    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
    actionBar.setDisplayShowTitleEnabled(true);

    Tab tabMenu = actionBar
        .newTab()
        .setIcon(R.drawable.ic_coffee)
        .setTag(getString(R.string.menu_fragment))
        .setTabListener(
            new FragmentTabListener<MenuListFragment>(this, getString(R.string.menu_fragment),
                MenuListFragment.class));
 
    Tab tabStatus = actionBar
        .newTab()
        .setIcon(R.drawable.ic_dashboard)
        .setTag(getString(R.string.status_fragment))
        .setTabListener(
            new FragmentTabListener<StatusListFragment>(this, getString(R.string.status_fragment),
                StatusListFragment.class));
      
    Tab tabFav = actionBar
        .newTab()
        .setIcon(R.drawable.ic_heart_on)
        .setTag(getString(R.string.favorite_fragment))
        .setTabListener(
            new FragmentTabListener<FavoriteListFragment>(this,
                getString(R.string.favorite_fragment), FavoriteListFragment.class));

    Tab tabConnect = actionBar
        .newTab()
        .setIcon(R.drawable.ic_new_group)
        .setTag(getString(R.string.connect_fragment))
        .setTabListener(
            new FragmentTabListener<ConnectFragment>(this, getString(R.string.connect_fragment),
                ConnectFragment.class));

    actionBar.addTab(tabMenu);
    actionBar.addTab(tabStatus);
    actionBar.addTab(tabFav);
    actionBar.addTab(tabConnect);
    actionBar.selectTab(tabConnect);
    bus.post(tabMenu);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.cafe, menu);
    return true;
  }

  @Override
  protected void onResume() {
    super.onResume();
    bus.register(this);
  }

  
  private void registerUser() {
    Profile profile = app.getProfile();
    StringEntity entity = null;
    try {
      entity = new StringEntity(profile.getProfileJson().toString());
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    String url = getResources().getString(R.string.api_url)
        + getResources().getString(R.string.register_post_url);
    service.registerUser(getBaseContext(), url, entity);
  }


  @Override
  protected void onPause() {
    super.onPause();
    bus.unregister(this);
  }

  @Produce
  public Tab getSelectedTab() {
    return getActionBar().getSelectedTab();
  }
  
  public void onProfileView(MenuItem mi) {
		Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
		startActivity(intent);
  }
}
