package com.zynga.zcafe.fragments;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.inject.Inject;

import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.squareup.otto.Subscribe;
import com.zynga.zcafe.CafeApplication;
import com.zynga.zcafe.R;
import com.zynga.zcafe.adapters.ConnectAdapter;
import com.zynga.zcafe.events.GetMessagesEvent;
import com.zynga.zcafe.events.PostMessageEvent;
import com.zynga.zcafe.inject.modules.CafeModule.MainThreadBus;
import com.zynga.zcafe.models.Message;
import com.zynga.zcafe.models.Profile;
import com.zynga.zcafe.services.CafeService;

public class ConnectFragment extends BaseListFragment {

  @Inject
  MainThreadBus bus;

  @Inject
  CafeApplication app;

  @Inject
  CafeService service;

  ConnectAdapter adapter;
  ListView lvItems;
  EditText etMessage;
  Button bSend;

  @Inject
  public ConnectFragment() {
    super();
  }

  @Override
  protected void init() {
    ArrayList<Message> messages = new ArrayList<Message>();
    FragmentManager manager = activity.getSupportFragmentManager();
    adapter = new ConnectAdapter(this, manager, getView().getContext(), messages);
    lvItems = (ListView) getView().findViewById(R.id.lvFragmentItemsList);
    lvItems.setAdapter(adapter);
    lvItems.setClickable(false);
    etMessage = (EditText) getView().findViewById(R.id.etConnectChatField);
    bSend = (Button) getView().findViewById(R.id.bConnectSend);
    registerListeners();
  }

  private void registerListeners() {
    bSend.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        if (etMessage.getText().toString().isEmpty()) {
          return;
        }
        Profile profile = CafeApplication.getInstance().getProfile();
        StringEntity entity = compileJsonEntity(profile);
        String url = getResources().getString(R.string.api_url)
            + getResources().getString(R.string.connects_post_url);
        service.postMessage(v.getContext(), url, entity);
      }

    });
  }

  private StringEntity compileJsonEntity(Profile profile) {
    Message message = Message.build(profile.getName(), profile.getUdId(), etMessage.getText()
        .toString());

    StringEntity entity = null;
    try {
      entity = new StringEntity(message.toJsonObject().toString());
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return entity;
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    Log.i("ONATTACH", "ATTACH");
  }

  @Override
  public void onCreate(Bundle state) {
    super.onCreate(state);
    Log.i("ONCREATE", "CREATE");
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
    Log.i("ONCREATEVIEW", "CREATEVIEW");
    return inflater.inflate(R.layout.fragment_connect, parent, false);
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
  }

  @Override
  public void onResume() {
    super.onResume();
    Log.i(this.toString(), "RESUME");
    bus.register(this);
    getMessages();
  }

  @Override
  public void onPause() {
    super.onPause();
    Log.i(this.toString(), "PAUSE");
    bus.unregister(this);
  }

  private void getMessages() {
    String url = getView().getResources().getString(R.string.api_url)
        + getView().getResources().getString(R.string.test_connects_get_url);
    service.getMessages(url);
  }

  @Subscribe
  public void getMessagesEvent(GetMessagesEvent event) {
    if (event.getStatus() == 0) {
      showMessages(event);
    }
  }

  private void showMessages(GetMessagesEvent event) {
    adapter.clear();
    JSONArray array = new JSONArray();
    try {
      array = new JSONArray(event.getResponse());
    } catch (JSONException e) {
      e.printStackTrace();
    }
    ArrayList<Message> messages = Message.fromJson(array);
    adapter.push(messages);
    adapter.notifyDataSetChanged();
  }

  @Subscribe
  public void postMessageEvent(PostMessageEvent event) {
    if (event.getStatus() == 0) {
      Log.i("POST MESSAGE", "SUCCESS");
    }
  }

}
