package com.zynga.zcafe.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zynga.zcafe.CafeApplication;
import com.zynga.zcafe.R;
import com.zynga.zcafe.services.CafeService;

public abstract class BaseListFragment extends Fragment {

  FragmentActivity activity;

  public BaseListFragment() {
    super();
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
    return inflater.inflate(R.layout.fragment_items_list, parent, false);
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    init();
  }

  @Override
  public void onResume() {
    super.onResume();
  }

  @Override
  public void onPause() {
    super.onPause();
  }

  protected abstract void init();


}
