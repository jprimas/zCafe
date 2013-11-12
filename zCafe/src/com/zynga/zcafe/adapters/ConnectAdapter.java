package com.zynga.zcafe.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zynga.zcafe.CafeApplication;
import com.zynga.zcafe.R;
import com.zynga.zcafe.models.Message;

public class ConnectAdapter extends ArrayAdapter<Message> {

  Context context;

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
    LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(
            Context.LAYOUT_INFLATER_SERVICE);
    if (view == null) {
      view = inflater.inflate(R.layout.connect_detail, null);
      holder = new ViewHolder();
      holder.layout = (LinearLayout)view.findViewById(R.id.llConnectDetailLayout);
      holder.ivPhotoUrl = (ImageView) view.findViewById(R.id.ivConnectDetailPhotoUrl);
      holder.tvMessage = (TextView) view.findViewById(R.id.tvConnectDetailMessage);
      holder.tvName = (TextView) view.findViewById(R.id.tvConnectDetailName);
      holder.tvDate = (TextView) view.findViewById(R.id.tvConnectDetailTime);
      view.setTag(holder);
    } else {
      holder = (ViewHolder) view.getTag();
    }

    final Message message = getItem(position);
    ImageLoader.getInstance().displayImage(message.getPhotoUrl(), holder.ivPhotoUrl);
    if (message.getName().equals(CafeApplication.getInstance().getProfile().getName())) {
      holder.layout.setBackgroundResource(R.drawable.connect_list_msg_receive);
    }

    holder.tvMessage.setText(message.getMessage());
    holder.tvName.setText(message.getName());
    holder.tvDate.setText(message.getDate());

    return view;
  }

  static class ViewHolder {
    LinearLayout layout;
    ImageView ivPhotoUrl;
    TextView tvName;
    TextView tvDate;
    TextView tvMessage;
  }

}
