package com.zynga.zcafe.activities;

import javax.inject.Inject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;

import com.zynga.zcafe.CafeApplication;
import com.zynga.zcafe.R;
import com.zynga.zcafe.fragments.RegistrationFragment;
import com.zynga.zcafe.inject.modules.CafeModule.MainThreadBus;

/**
 * 111111
 * 
 * @author tlee
 * 
 */
public class RegistrationActivity extends FragmentActivity {

  @Inject
  MainThreadBus bus;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_registration);
    CafeApplication.getObjectGraph().inject(this);
    attachRegistrationFragment();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.registration, menu);
    return true;
  }

  @Override
  protected void onResume() {
    super.onResume();
    bus.register(this);
  }

  @Override
  protected void onPause() {
    super.onPause();
    bus.unregister(this);
  }

  private Fragment attachRegistrationFragment() {
    Fragment fragment = new RegistrationFragment();
    FragmentTransaction sft = getSupportFragmentManager().beginTransaction();
    sft.replace(R.id.flRegistrationContainer, fragment,
        getResources().getString(R.string.registration_fragment));
    sft.commit();
    return fragment;
  }

}
