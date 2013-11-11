package com.zynga.zcafe.adapters;

import java.util.ArrayList;

import javax.inject.Inject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zynga.zcafe.R;
import com.zynga.zcafe.inject.modules.CafeModule.MainThreadBus;
import com.zynga.zcafe.models.Friend;
import com.zynga.zcafe.services.CafeService;

public class PopupEditTextAdapter extends ArrayAdapter<Friend>{
  

  @Inject
  MainThreadBus bus;

  @Inject
  CafeService service;

  Context context;
  
  public PopupEditTextAdapter(Context context, ArrayList<Friend> items) {
    super(context, 0, items);
    this.context = context;
  }
  
  @Override
  public View getView(final int position, final View convertView, final ViewGroup parent) {
    ViewHolder holder;
    View view = convertView;
    if (view == null) {
      LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      view = inflater.inflate(R.layout.connect_popup_detail, null);
      holder = new ViewHolder();
      holder.ivPhotoUrl = (ImageView) view.findViewById(R.id.ivConnectPopupPhotoUrl);
      holder.tvName = (TextView)view.findViewById(R.id.tvConnectPopupName);
      view.setTag(holder);
    } else {
      holder = (ViewHolder)view.getTag();
    }
    
    Friend friend = getItem(position);
    ImageLoader.getInstance().displayImage(friend.getPhotoUrl(), holder.ivPhotoUrl);
    holder.tvName.setText(friend.getName());
    return view;
  }
  
  static class ViewHolder {
    ImageView ivPhotoUrl;
    TextView tvName;
  }
}
