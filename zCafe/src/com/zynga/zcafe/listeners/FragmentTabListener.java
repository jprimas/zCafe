package com.zynga.zcafe.listeners;

import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.zynga.zcafe.CafeApplication;
import com.zynga.zcafe.R;

public class FragmentTabListener<T extends Fragment> implements TabListener {
  private Fragment mFragment;
  private final FragmentActivity mActivity;
  private final String mTag;
  private final Class<?> mClass;
  private final int mfragmentContainerId;


  // This version defaults to replacing the entire activity content area
  // new FragmentTabListener<SomeFragment>(this, "first", SomeFragment.class))
  public FragmentTabListener(FragmentActivity activity, String tag, Class<T> clz) {
    mActivity = activity;
    mTag = tag;
    mClass = clz;
    mfragmentContainerId = android.R.id.content;
    CafeApplication.getObjectGraph().inject(this);
  }

  // This version supports specifying the container to replace with fragment
  // content
  // new FragmentTabListener<SomeFragment>(R.id.flContent, this, "first",
  // SomeFragment.class))
  public FragmentTabListener(int fragmentContainerId, FragmentActivity activity, String tag,
      Class<T> clz) {
    mActivity = activity;
    mTag = tag;
    mClass = clz;
    mfragmentContainerId = fragmentContainerId;
    CafeApplication.getObjectGraph().inject(this);
  }

  /* The following are each of the ActionBar.TabListener callbacks */

  @Override
  public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
    FragmentManager manager = mActivity.getSupportFragmentManager();
    FragmentTransaction sft = manager.beginTransaction();
    if (mFragment == null) {
      Log.i("MFRAGMENT", "NULL");
      Log.i(tab.getTag().toString(), mTag);
      mFragment = Fragment.instantiate(mActivity, mClass.getName());
      sft.replace(R.id.flFragmentContainer, mFragment, mTag);
    } else {
      Log.i("MFRAGMENT", "NOT-NULL");
      mFragment = manager.findFragmentByTag(mTag);
      Log.i(tab.getTag().toString(), mFragment.getTag());
      sft.attach(mFragment);
    }

    sft.commit();

    /*
     * // Check if the fragment is already initialized if (mFragment == null) {
     * // If not, instantiate and add it to the activity mFragment =
     * Fragment.instantiate(mActivity, mClass.getName());
     * sft.add(mfragmentContainerId, mFragment, mTag); } else { // If it exists,
     * simply attach it in order to show it sft.replace(mfragmentContainerId,
     * mFragment, mTag); } sft.commit();
     */
  }

  @Override
  public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
    FragmentTransaction sft = mActivity.getSupportFragmentManager().beginTransaction();
    if (mFragment != null) {
      // Detach the fragment, because another one is being attached
      sft.detach(mFragment);
    }
    sft.commit();
  }

  @Override
  public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {
    // User selected the already selected tab. Usually do nothing.
    FragmentManager manager = mActivity.getSupportFragmentManager();
    FragmentTransaction sft = manager.beginTransaction();
    mFragment = Fragment.instantiate(mActivity, mClass.getName());
    sft.replace(R.id.flFragmentContainer, mFragment, mTag);
    sft.commit();
  }
}
