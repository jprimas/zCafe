package com.zynga.zcafe.adapters;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.inject.Inject;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.otto.Subscribe;
import com.zynga.zcafe.CafeApplication;
import com.zynga.zcafe.R;
import com.zynga.zcafe.events.CancelOrderEvent;
import com.zynga.zcafe.inject.modules.CafeModule.MainThreadBus;
import com.zynga.zcafe.models.Profile;
import com.zynga.zcafe.models.StatusItem;
import com.zynga.zcafe.services.CafeService;

public class StatusAdapter extends ArrayAdapter<StatusItem> {

  @Inject
  MainThreadBus bus;

  @Inject
  CafeService service;

  Context context;
  FragmentManager fragmentManager;
  Fragment fragment;

  static int currentCancelPosition;
  static View currentView;

  public StatusAdapter(Fragment fragment, FragmentManager fragmentManager, Context context,
      ArrayList<StatusItem> items) {
    super(context, 0, items);
    this.context = context;
    this.fragmentManager = fragmentManager;
    this.fragment = fragment;
    CafeApplication.getObjectGraph().inject(this);
  }

  public void push(ArrayList<StatusItem> items) {
    clear();
    for (StatusItem item : items) {
      if (!item.getStatus().equals("Cancelled")) {
        add(item);
      }
    }
  }

  @Override
  public View getView(final int position, final View convertView, final ViewGroup parent) {
    ViewHolder holder;
    View view = convertView;
    if (view == null) {
      LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(
          Context.LAYOUT_INFLATER_SERVICE);
      view = inflater.inflate(R.layout.order_status_detail, null);
      holder = new ViewHolder();
      holder.ivOrderStatusMenuPhoto = (ImageView) view.findViewById(R.id.ivOrderStatusMenuPhoto);
      holder.tvOrderStatusMenuTitle = (TextView) view.findViewById(R.id.tvOrderStatusMenuTitle);
      holder.tvOrderStatusNumber = (TextView) view.findViewById(R.id.tvOrderStatusNumber);
      holder.tvOrderStatus = (TextView) view.findViewById(R.id.tvOrderStatus);
      holder.tvOrderStatusUserName = (TextView) view.findViewById(R.id.tvOrderStatusUserName);
      holder.tvOrderStatusQueueCount = (TextView) view.findViewById(R.id.tvOrderStatusQueueCount);
      holder.tvOrderStatusOrderDate = (TextView) view.findViewById(R.id.tvOrderStatusOrderDate);
      holder.bCancelOrder = (Button) view.findViewById(R.id.bCancelOrder);
      view.setTag(holder);
    } else {
      holder = (ViewHolder) view.getTag();
    }

    final StatusItem item = getItem(position);
    ImageLoader.getInstance().displayImage(item.getThumbImageUrl(), holder.ivOrderStatusMenuPhoto);
    holder.tvOrderStatusMenuTitle.setText(item.getMenuTitle());
    holder.tvOrderStatusNumber.setText(item.getOrderId());
    holder.tvOrderStatus.setText(item.getStatus());
    holder.tvOrderStatusUserName.setText(item.getUserName());
    holder.tvOrderStatusQueueCount.setText(item.getQueueCount());
    holder.tvOrderStatusOrderDate.setText(item.getOrderDate());
    Log.i("ITEM_STATUS", item.getStatus());
    if (item.getStatus().equals("Pending")) {
      holder.bCancelOrder.setVisibility(View.VISIBLE);
      holder.bCancelOrder.setOnClickListener(new OnClickListener() {

        @Override
        public void onClick(View view) {
          Log.i("CANCEL1", "ORDER");
          String url = view.getResources().getString(R.string.api_url)
              + view.getResources().getString(R.string.cancel_order_url);
          cancelOrder(url, item);
          currentCancelPosition = position;
          currentView = convertView;
        }

      });
    } else {
      holder.bCancelOrder.setVisibility(View.INVISIBLE);
    }

    return view;
  }

  private void cancelOrder(String url, StatusItem item) {
    CafeApplication app = CafeApplication.getInstance();
    Profile profile = app.getProfile();
    JSONObject json = new JSONObject();
    try {
      json.put("userName", item.getUserName());
      json.put("udid", profile.getUdId());
      json.put("orderId", item.getOrderId());
    } catch (JSONException e) {
      e.printStackTrace();
    }

    StringEntity entity = null;
    try {
      entity = new StringEntity(json.toString());
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    service.cancelOrder(this.context, url, entity);
  }

  static class ViewHolder {
    ImageView ivOrderStatusMenuPhoto;
    TextView tvOrderStatusMenuTitle;
    TextView tvOrderStatusNumber;
    TextView tvOrderStatus;
    TextView tvOrderStatusUserName;
    TextView tvOrderStatusQueueCount;
    TextView tvOrderStatusOrderDate;
    Button bCancelOrder;
  }

  @Subscribe
  public void onCancelOrderEvent(CancelOrderEvent event) {
    Log.i("ONCANCELEVENT", "TRUE");
    if (event.getStatus() == 200) {
      Log.i("ONCANCELEVENT-200", "TRUE");

      runItemRemovalAnimation();

      CafeApplication app = CafeApplication.getObjectGraph().get(CafeApplication.class);
      String msg = app.getContext().getResources().getString(R.string.order_cancel_success);
      Toast.makeText(app.getContext(), msg, Toast.LENGTH_LONG).show();
    }
  }

  private void runItemRemovalAnimation() {
    Animation animation = new AlphaAnimation(1.0f, 0.0f);
    animation.setDuration(2000);
    animation.setAnimationListener(new Animation.AnimationListener() {

      @Override
      public void onAnimationEnd(Animation animation) {
        StatusItem item = getItem(currentCancelPosition);
        remove(item);
        notifyDataSetChanged();
      }

      @Override
      public void onAnimationRepeat(Animation animation) {

      }

      @Override
      public void onAnimationStart(Animation animation) {

      }
    });

    currentView.startAnimation(animation);
  }

}
