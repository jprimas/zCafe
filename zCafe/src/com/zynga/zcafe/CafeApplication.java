package com.zynga.zcafe;

import javax.inject.Inject;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.urbanairship.AirshipConfigOptions;
import com.urbanairship.UAirship;
import com.urbanairship.push.PushManager;
import com.zynga.zcafe.events.Producers;
import com.zynga.zcafe.inject.modules.CafeModule;
import com.zynga.zcafe.models.Profile;

import dagger.ObjectGraph;

/**
 * 111
 * 
 * @author tlee
 * 
 */
public class CafeApplication extends Application {
  public static final String CONFIGS = "Configs";
  public static CafeApplication INSTANCE;

  private Context context;

  private Profile profile = null;

  private static ObjectGraph objectGraph;

  @Inject
  Producers producers;

  @Override
  public void onCreate() {
    super.onCreate();
    context = this;
    INSTANCE = this;
    init();
  }

  private void init() {
    // Creates objectGraph for DI.
    objectGraph = ObjectGraph.create(new CafeModule(this));
    objectGraph.inject(this);

    // Creates global configuration and initialize ImageLoader with this
    // configuration.
    DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory()
        .cacheOnDisc().displayer(new RoundedBitmapDisplayer(50)).build();
    ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
        .defaultDisplayImageOptions(defaultOptions).build();
    ImageLoader.getInstance().init(config);

    registerAirship();
  }

  public boolean isRegisteredUser() {
    boolean registered = false;
    SharedPreferences configs = getSharedPreferences(CONFIGS, 0);
    registered = configs.getBoolean("isRegistered", registered);
    return registered;
  }

  public Profile getProfile() {
    if (profile == null) {
      SharedPreferences configs = getConfigs();
      String udid = configs.getString("udid", "");
      String uaid = configs.getString("uaid", "");
      String userId = configs.getString("userId", "");
      String name = configs.getString("name", "");
      String device = configs.getString("device", "android");

      profile = new Profile.Builder().setName(name).setUdid(udid).setUaId(uaid)
          .setId(userId).setDevice(device).build();
    }
    return profile;
  }

  public SharedPreferences getConfigs() {
    SharedPreferences configs = getSharedPreferences(CONFIGS, 0);
    return configs;
  }

  private void registerAirship() {
    AirshipConfigOptions options = AirshipConfigOptions.loadDefaultOptions(this);
    UAirship.takeOff(this, options);
    PushManager.enablePush();
  }

  public static ObjectGraph getObjectGraph() {
    return objectGraph;
  }

  public Producers getProducers() {
    return this.producers;
  }

  public static CafeApplication getInstance() {
    return INSTANCE;
  }

  public Context getContext() {
    return context;
  }

}
