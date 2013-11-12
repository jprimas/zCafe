package com.zynga.zcafe.inject.modules;

import javax.inject.Singleton;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;
import com.zynga.zcafe.CafeApplication;
import com.zynga.zcafe.activities.CafeActivity;
import com.zynga.zcafe.activities.ProfileActivity;
import com.zynga.zcafe.activities.RegistrationActivity;
import com.zynga.zcafe.adapters.ConnectAdapter;
import com.zynga.zcafe.adapters.MenuAdapter;
import com.zynga.zcafe.adapters.PopupEditTextAdapter;
import com.zynga.zcafe.adapters.StatusAdapter;
import com.zynga.zcafe.events.Producers;
import com.zynga.zcafe.fragments.BaseListFragment;
import com.zynga.zcafe.fragments.ConnectFragment;
import com.zynga.zcafe.fragments.FavoriteListFragment;
import com.zynga.zcafe.fragments.MenuListFragment;
import com.zynga.zcafe.fragments.OrderFormFragment;
import com.zynga.zcafe.fragments.RegistrationFragment;
import com.zynga.zcafe.fragments.StatusListFragment;
import com.zynga.zcafe.inject.modules.CafeModule.MainThreadBus;
import com.zynga.zcafe.listeners.FragmentTabListener;
import com.zynga.zcafe.services.CafeService;

import dagger.Module;
import dagger.Provides;

// Modules for @Inject

@Module(
    injects = {
        CafeService.class, CafeApplication.class, CafeActivity.class, RegistrationActivity.class,
        BaseListFragment.class, OrderFormFragment.class, FavoriteListFragment.class,
        MenuListFragment.class, StatusListFragment.class, ConnectFragment.class, Producers.class,
        PopupEditTextAdapter.class, MenuAdapter.class, FragmentTabListener.class,
        StatusAdapter.class, ConnectAdapter.class,
        RegistrationFragment.class, MainThreadBus.class, ProfileActivity.class
 })
public class CafeModule {

  CafeApplication app;

  public CafeModule(CafeApplication app) {
    this.app = app;
  }

  @Provides
  @Singleton
  CafeApplication provideApplication() {
    return this.app;
  }
  

  @Provides
  InputMethodManager provideImm() {
    InputMethodManager imm = (InputMethodManager) app
        .getSystemService(Context.INPUT_METHOD_SERVICE);
    return imm;
  }

  @Provides
  @Singleton
  Producers provideProducers() {
    return Producers.getInstance(provideMainThreadBus());
  }

  @Provides
  @Singleton
  CafeService provideCafeService() {
    return new CafeService(provideMainThreadBus());
  }

  public final static class BusProvider {
    private static final Bus BUS = new Bus(ThreadEnforcer.ANY);

    public static Bus getInstance() {
      return BUS;
    }

    private BusProvider() {
    }
  }

  @Provides
  @Singleton
  MainThreadBus provideMainThreadBus() {
    return MainThreadBus.getInstance();
  }

  public final static class MainThreadBus extends Bus {
    private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());
    private static final MainThreadBus BUS = new MainThreadBus();

    @Override
    public void post(final Object event) {
      if (Looper.myLooper() == Looper.getMainLooper()) {
        Log.i("MAIN", "THREAD");
        super.post(event);
      } else {
        mainThreadHandler.post(new Runnable() {
          @Override
          public void run() {
            Log.i("CHILD", "THREAD");
            MainThreadBus.super.post(event);
          }
        });
      }
    }

    public static MainThreadBus getInstance() {
      return BUS;
    }
  }


  /*
   * @Provides View provideMenuItemLayout() { LayoutInflater inflater =
   * (LayoutInflater) CafeApplication.getInstance().getSystemService(
   * Context.LAYOUT_INFLATER_SERVICE); return
   * inflater.inflate(R.layout.menu_item_detail, null); }
   */
}
