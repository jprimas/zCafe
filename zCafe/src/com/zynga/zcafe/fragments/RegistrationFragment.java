package com.zynga.zcafe.fragments;

import java.io.UnsupportedEncodingException;

import javax.inject.Inject;

import org.apache.http.entity.StringEntity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.urbanairship.UAirship;
import com.urbanairship.push.PushManager;
import com.zynga.zcafe.CafeApplication;
import com.zynga.zcafe.IntentReceiver;
import com.zynga.zcafe.R;
import com.zynga.zcafe.activities.CafeActivity;
import com.zynga.zcafe.events.RegistrationEvent;
import com.zynga.zcafe.events.UaIdEvent;
import com.zynga.zcafe.inject.modules.CafeModule.MainThreadBus;
import com.zynga.zcafe.models.Profile;
import com.zynga.zcafe.services.CafeService;

public class RegistrationFragment extends Fragment {

  FragmentActivity activity;
  CafeApplication app;
  IntentFilter apidUpdateFilter;

  @Inject
  MainThreadBus mainBus;

  @Inject
  CafeService service;

  EditText etFullName;
  Button bRegister;

  public RegistrationFragment() {
    super();
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    this.activity = (FragmentActivity) activity;
    CafeApplication.getObjectGraph().inject(this);
    this.app = CafeApplication.getObjectGraph().get(CafeApplication.class);
    mainBus = CafeApplication.getObjectGraph().get(MainThreadBus.class);
  }

  @Override
  public void onCreate(Bundle state) {
    super.onCreate(state);

    // Airship
    apidUpdateFilter = new IntentFilter();
    apidUpdateFilter.addAction(UAirship.getPackageName()
        + IntentReceiver.APID_UPDATED_ACTION_SUFFIX);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.registration, parent, false);
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    init();
    registerListeners();
  }

  @Override
  public void onResume() {
    super.onResume();
    mainBus.register(this);
    this.activity.registerReceiver(apidUpdateReceiver, apidUpdateFilter);
  }

  private BroadcastReceiver apidUpdateReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
      registerUser();
    }
  };

  @Override
  public void onPause() {
    super.onPause();
    mainBus.unregister(this);
    this.activity.unregisterReceiver(apidUpdateReceiver);
  }

  private void init() {
    etFullName = (EditText) getView().findViewById(R.id.etFullName);
    bRegister = (Button) getView().findViewById(R.id.bRegister);
  }

  private void registerListeners() {
    bRegister.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Profile profile = createAndStoreProfile();
        if (profile != null) {
          if (!(profile.getUaId().isEmpty() || profile.getUdId().isEmpty() || profile.getName()
              .isEmpty())) {
            registerUser();
          }
        }
      }
    });
  }

  private void registerUser() {
    Profile profile = createAndStoreProfile();
    Log.i("PROFILE NAME", profile.getName());
    Log.i("PROFILE UADI", profile.getUaId());
    Log.i("PROFILE UDID", profile.getUdId());
    Log.i("PROFILE DEVICE", profile.getDevice());
    Log.i("PROFILE JSON", profile.getProfileJson().toString());
    StringEntity entity = null;
    try {
      Log.i("REGISTERATION", profile.getProfileJson().toString());
      entity = new StringEntity(profile.getProfileJson().toString());
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    String url = getView().getResources().getString(R.string.api_url)
        + getView().getResources().getString(R.string.register_post_url);
    Log.i("UAID", url);
    service.registerUser(app.getContext(), url, entity);
  }

  private Profile createAndStoreProfile() {
    SharedPreferences configs = app.getConfigs();
    SharedPreferences.Editor editor = configs.edit();
    boolean isProfileComplete = true;
    // Fullname
    String name = etFullName.getText().toString();
    if (name.isEmpty()) {
      Toast.makeText(getView().getContext(),
          getView().getResources().getString(R.string.warning_registration_empty_name),
          Toast.LENGTH_LONG).show();
      isProfileComplete = false;
    } else {
      editor.putString("name", name);
    }

    // udid
    String udid = configs.getString("udid", "");
    if (udid.isEmpty()) {
      udid = Secure.getString(getView().getContext().getContentResolver(), Secure.ANDROID_ID);
    }
    editor.putString("udid", udid);

    String uaid = PushManager.shared().getAPID();
    editor.putString("uaid", uaid);
    // Log.i("UAID", uaid);

    // uaid
    /*
     * String uaid = configs.getString("uaid", ""); if (uaid.isEmpty()) { uaid =
     * PushManager.shared().getAPID(); ; if (uaid.isEmpty()) { Thread background
     * = new Thread(new Runnable() {
     * 
     * @Override public void run() { Looper.prepare(); Handler handler = new
     * Handler(); handler.postDelayed(new Runnable() {
     * 
     * @Override public void run() { String uaid =
     * PushManager.shared().getAPID(); if (uaid.isEmpty()) { Handler handler =
     * new Handler(Looper.getMainLooper()); handler.postDelayed(new Runnable() {
     * 
     * @Override public void run() { Toast.makeText( getView().getContext(),
     * getView().getResources().getString(
     * R.string.warning_registration_empty_uaid), Toast.LENGTH_LONG).show(); }
     * }, 1000); } else { UaIdEvent event = new UaIdEvent(uaid);
     * mainBus.post(event); } } }, 5000); Looper.loop(); } });
     * background.start(); }
     * 
     * }
     */

    if (!isProfileComplete) {
      return null;
    }

    // Creates new profile
    String device = getView().getResources().getString(R.string.registration_device);
    editor.putString("device", device);
    editor.commit();
    Profile newProfile = new Profile.Builder().setName(name).setUdid(udid).setUaId(uaid)
            .setDevice(device).build();
    return newProfile;
  }

  @Subscribe
  public void onUaIdUpdated(UaIdEvent event) {
    Log.i("UAID-EVENT", "UPDATED");
    SharedPreferences configs = app.getConfigs();
    SharedPreferences.Editor editor = configs.edit();
    editor.putString("uaid", event.getUaId());
    editor.commit();
  }

  @Subscribe
  public void onRegistrationEvent(RegistrationEvent event) {
    Log.i("SUBSCRIBE", "EVENT");
    SharedPreferences configs = app.getConfigs();
    SharedPreferences.Editor editor = configs.edit();
    if (event.getStatus() == 0) {
      editor.putBoolean("isRegistered", true);
      Log.i("SUBSCRIBE SUCCESS", event.getResponse());
    } else {
      Log.i("FAIL", event.getResponse());
      editor.putBoolean("isRegistered", true);
    }
    editor.commit();

    if (event.getStatus() == 0) {
      Intent intent = new Intent(getActivity(), CafeActivity.class);
      startActivity(intent);
    }
  }

}
