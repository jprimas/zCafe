package com.zynga.zcafe.fragments;

import java.io.UnsupportedEncodingException;

import javax.inject.Inject;

import org.apache.http.entity.StringEntity;

import android.app.ActionBar.Tab;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.otto.Subscribe;
import com.zynga.zcafe.CafeApplication;
import com.zynga.zcafe.R;
import com.zynga.zcafe.inject.modules.CafeModule.MainThreadBus;
import com.zynga.zcafe.models.MenuItem;
import com.zynga.zcafe.models.OrderItem;
import com.zynga.zcafe.models.Profile;
import com.zynga.zcafe.services.CafeService;

public class OrderFormFragment extends Fragment {

  @Inject
  MainThreadBus bus;

  @Inject
  CafeApplication app;

  @Inject
  CafeService service;
  FragmentActivity activity;
  MenuItem menuItem;
  TextView tvOrderFormTitle;
  ImageView ivOrderFormPhoto;
  TextView tvOrderFormDescription;
  EditText etOrderFormNote;
  Button bOrderFormOrderButton;
  Fragment fragment;
  Tab tab;

  @Inject
  public OrderFormFragment() {
    super();
    fragment = this;
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    this.activity = (FragmentActivity) activity;
    CafeApplication.getObjectGraph().inject(this);
  }

  @Override
  public void onCreate(Bundle state) {
    super.onCreate(state);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.order_form, parent, false);
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    init();
  }

  private void init() {
    tvOrderFormTitle = (TextView) getView().findViewById(R.id.tvOrderFormTitle);
    ivOrderFormPhoto = (ImageView) getView().findViewById(R.id.ivOrderFormPhoto);
    tvOrderFormDescription = (TextView) getView().findViewById(R.id.tvOrderFormDescription);
    etOrderFormNote = (EditText) getView().findViewById(R.id.etOrderFormNote);
    bOrderFormOrderButton = (Button) getView().findViewById(R.id.bOrderFormOrderButton);
    bOrderFormOrderButton.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        StringEntity entity = compileJsonEntity();
        String url = getView().getResources().getString(R.string.api_url) +
            getView().getResources().getString(R.string.order_post_url);
        service.postOrder(activity, url, entity);
        FragmentManager manager = activity.getSupportFragmentManager();
        FragmentTransaction sft = manager.beginTransaction();
        sft.detach(fragment);
        sft.commit();
        tab.select();
      }

    });
  }

  private StringEntity compileJsonEntity() {
    Profile profile = ((CafeApplication) app.getContext()).getProfile();
    OrderItem orderItem = new OrderItem(profile);
    orderItem.setMenuId(menuItem.getId());
    orderItem.setCount("1");
    orderItem.setNotes(etOrderFormNote.getText().toString().trim());

    StringEntity entity = null;
    try {
      entity = new StringEntity(orderItem.getOrderItemJson().toString());
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return entity;
  }

  @Override
  public void onResume() {
    super.onResume();
    // Log.i(this.toString(), "RESUME");
    bus.register(this);
  }

  @Override
  public void onPause() {
    super.onPause();
    Log.i(this.toString(), "PAUSE");
    bus.unregister(this);
  }

  @Subscribe
  public void onMenuItemPost(MenuItem item) {
    Log.i(this.toString(), "MENU_ITEM");
    this.menuItem = item;
    tvOrderFormTitle.setText(item.getTitle());
    ImageLoader.getInstance().displayImage(item.getImageUrl(), ivOrderFormPhoto);
    tvOrderFormDescription.setText(item.getDescription());
  }

  @Subscribe
  public void onTabSelected(Tab tab) {
    this.tab = tab;
  }

}
