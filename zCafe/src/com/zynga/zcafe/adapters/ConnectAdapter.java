package com.zynga.zcafe.adapters;

import java.util.ArrayList;

import javax.inject.Inject;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zynga.zcafe.R;
import com.zynga.zcafe.inject.modules.CafeModule.MainThreadBus;
import com.zynga.zcafe.models.Message;
import com.zynga.zcafe.services.CafeService;

public class ConnectAdapter extends ArrayAdapter<Message> {

  @Inject
  MainThreadBus bus;

  @Inject
  CafeService service;

  Context context;
  FragmentManager fragmentManager;
  Fragment fragment;

  public ConnectAdapter(Fragment fragment, FragmentManager fragmentManager, Context context,
      ArrayList<Message> items) {
    super(context, 0, items);
    this.context = context;
  }

  public void push(ArrayList<Message> messages) {
    clear();
    addAll(messages);
  }

  @Override
  public View getView(final int position, final View convertView, final ViewGroup parent) {
    ViewHolder holder;
    View view = convertView;
    if (view == null) {
      LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(
          Context.LAYOUT_INFLATER_SERVICE);
      view = inflater.inflate(R.layout.connect_detail, null);
      holder = new ViewHolder();
      holder.ivPhotoUrl = (ImageView) view.findViewById(R.id.ivConnectDetailPhotoUrl);
      holder.tvMessage = (TextView) view.findViewById(R.id.tvConnectDetailMessage);
      holder.tvName = (TextView) view.findViewById(R.id.tvConnectDetailName);
      holder.tvDate = (TextView) view.findViewById(R.id.tvConnectDetailTime);
      view.setTag(holder);
    } else {
      holder = (ViewHolder) view.getTag();
    }

    final Message message = getItem(position);
    ImageLoader.getInstance().displayImage("", holder.ivPhotoUrl);
    holder.tvMessage.setText(message.getMessage());
    holder.tvName.setText(message.getName());
    holder.tvDate.setText(message.getDate());

    return view;
  }

  static class ViewHolder {
    ImageView ivPhotoUrl;
    TextView tvName;
    TextView tvDate;
    TextView tvMessage;
  }

}
