package com.zynga.zcafe.services;

import javax.inject.Inject;

import org.apache.http.Header;
import org.apache.http.HttpEntity;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zynga.zcafe.CafeApplication;
import com.zynga.zcafe.R;
import com.zynga.zcafe.events.CancelOrderEvent;
import com.zynga.zcafe.events.FavoriteEvent;
import com.zynga.zcafe.events.GetMessagesEvent;
import com.zynga.zcafe.events.MenuEvent;
import com.zynga.zcafe.events.OrderStatusEvent;
import com.zynga.zcafe.events.PostMessageEvent;
import com.zynga.zcafe.events.RegistrationEvent;
import com.zynga.zcafe.inject.modules.CafeModule.MainThreadBus;

public class CafeService {

  private static final AsyncHttpClient client = new AsyncHttpClient();

  @Inject
  MainThreadBus bus;

  @Inject
  CafeApplication app;

  @Inject
  public CafeService(MainThreadBus bus) {
    this.bus = bus;
    this.bus.register(this);
  }

  public void getMessages(String url) {
    JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {
      @Override
      public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        Log.i("GET MESSAGE", new String(responseBody));
        GetMessagesEvent event = new GetMessagesEvent(0, new String(responseBody));
        bus.post(event);
      }

      @Override
      public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        CafeApplication app = CafeApplication.getObjectGraph().get(CafeApplication.class);
        Toast.makeText(app.getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
        Log.d("GET MESSAGE ERROR", error.getMessage());
      }
    };
    Log.i("GET MESSAGES URL", url);
    client.get(url, handler);
  }

  public void postMessage(Context context, String url, HttpEntity entity) {
    Log.i("CONNECT-URL", url);
    Log.i("CONNECT", entity.toString());
    JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {
      @Override
      public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        String response = new String(responseBody);
        Log.i("CONNECT-POST-SUCCESS", response);
        PostMessageEvent event = new PostMessageEvent(0, response);
        bus.post(event);
      }

      @Override
      public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        PostMessageEvent event = new PostMessageEvent(1, error.getMessage());
        Log.i("CONNECT-POST-ERROR", error.getMessage());
        bus.post(event);
        CafeApplication app = CafeApplication.getObjectGraph().get(CafeApplication.class);
        Toast.makeText(app.getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
      }

    };

    client.post(context, url, entity, "application/json", handler);
  }

  public void registerUser(Context context, String url, HttpEntity entity) {
    JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {

      @Override
      public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        String response = new String(responseBody);
        Log.i("SUCCESS", response);
        RegistrationEvent event = new RegistrationEvent(response, 0);
        bus.post(event);
        CafeApplication app = CafeApplication.getObjectGraph().get(CafeApplication.class);
        String msg = app.getContext().getResources().getString(R.string.registration_success);
        Toast.makeText(app.getContext(), msg, Toast.LENGTH_LONG).show();
      }

      @Override
      public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        RegistrationEvent event = new RegistrationEvent(error.getMessage(), 1);
        bus.post(event);
        CafeApplication app = CafeApplication.getObjectGraph().get(CafeApplication.class);
        String msg = app.getContext().getResources().getString(R.string.registration_failure);
        Toast.makeText(app.getContext(), msg, Toast.LENGTH_LONG).show();
      }

    };

    client.post(context, url, entity, "application/json", handler);
  }

  public void getMenu(String url) {
    JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {
      @Override
      public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        Log.i("GET MENU1", "SUCCESS");
        MenuEvent event = new MenuEvent(statusCode, new String(responseBody));
        bus.post(event);
      }

      @Override
      public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        // Do not create string onFailure because no responseBody.
        // String response = new String(responseBody);
        CafeApplication app = CafeApplication.getObjectGraph().get(CafeApplication.class);
        Toast.makeText(app.getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
        Log.d("GET MENU1 ERROR", error.getMessage());
      }
    };
    Log.i("GET MENU LIST URL", url);
    client.get(url, handler);
  }

  public void getOrderStatus(String url) {
    JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {
      @Override
      public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        String response = new String(responseBody);
        OrderStatusEvent event = new OrderStatusEvent(statusCode, response);
        bus.post(event);
      }

      @Override
      public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        String response = new String(responseBody);
        CafeApplication app = CafeApplication.getObjectGraph().get(CafeApplication.class);
        Toast.makeText(app.getContext(), response, Toast.LENGTH_LONG).show();
        Log.d("ERROR1", response);
      }
    };
    Log.i("STATUS-URL", url);
    client.get(url, handler);
  }

  public void getFavorite(String url) {
    JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {
      @Override
      public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        String response = new String(responseBody);
        FavoriteEvent event = new FavoriteEvent(statusCode, response);
        bus.post(event);
      }

      @Override
      public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        String response = new String(responseBody);
        CafeApplication app = CafeApplication.getObjectGraph().get(CafeApplication.class);
        Toast.makeText(app.getContext(), response, Toast.LENGTH_LONG).show();
        Log.d("ERROR111", response);
      }
    };
    Log.i("STATUS-URL", url);
    client.get(url, handler);
  }

  public void postOrder(Context context, String url, HttpEntity entity) {
    JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {
      @Override
      public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
    	Log.i("POST-ORDER-SUCCESS", new String(responseBody));
        //bus.post(items);
        CafeApplication app = CafeApplication.getObjectGraph().get(CafeApplication.class);
        String msg = app.getContext().getResources().getString(R.string.order_submit_success);
        Toast.makeText(app.getContext(), msg, Toast.LENGTH_LONG).show();
      }

      @Override
      public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        // TODO: Needs to work on this as successfully post also triggers this
        // onFailure callback.
        // CafeApplication application =
        // CafeApplication.getObjectGraph().get(CafeApplication.class);
        // String msg =
        // application.getResources().getString(R.string.order_submit_failure);
        // String msg = e.getMessage();
        // Toast.makeText(application.getContext(), msg,
        // Toast.LENGTH_LONG).show();
      }
    };
    client.post(context, url, entity, "application/json", handler);
  }

  public void cancelOrder(Context context, String url, HttpEntity entity) {
    JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {
      @Override
      public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        // tlee: this doesn't get called at all on a successful post.
        // bus.post(items);
        CancelOrderEvent event = new CancelOrderEvent(statusCode, new String(responseBody));
        bus.post(event);
      }

      @Override
      public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        //Log.i("FAIL TO CANCEL", "ORDER");
      }
    };
    client.put(context, url, entity, "application/json", handler);
  }

  public void get(String url) {
    JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {
      @Override
      public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        Log.d("SUCCESS GET-MENU", "ITEMS");
        // bus.post(items);
      }

      @Override
      public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        String response = new String(responseBody);
        CafeApplication app = CafeApplication.getObjectGraph().get(CafeApplication.class);
        Toast.makeText(app.getContext(), response, Toast.LENGTH_LONG).show();
        Log.d("ERROR111", error.getMessage());
      }
    };

    client.get(url, handler);
  }

  public void post(Context context, String url, HttpEntity entity) {
    JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {
      @Override
      public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        // tlee: this doesn't get called at all on a successful post.
        // bus.post(items);
        CafeApplication app = CafeApplication.getObjectGraph().get(CafeApplication.class);
        String msg = app.getContext().getResources().getString(R.string.order_submit_success);
        Toast.makeText(app.getContext(), msg, Toast.LENGTH_LONG).show();
      }

      @Override
      public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        // TODO: Needs to work on this as successfully post also triggers this
        // onFailure callback.
        // CafeApplication application =
        // CafeApplication.getObjectGraph().get(CafeApplication.class);
        // String msg =
        // application.getResources().getString(R.string.order_submit_failure);
        // String msg = e.getMessage();
        // Toast.makeText(application.getContext(), msg,
        // Toast.LENGTH_LONG).show();
      }
    };

    client.post(context, url, entity, "application/json", handler);
  }

  public void put(String url, RequestParams params) {
    JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {
      @Override
      public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        Log.d("SUCCESS MENU", "ITEMS");
        // bus.post(items);
      }

      @Override
      public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        Log.d("ERROR", error.toString());
      }
    };

    client.put(url, params, handler);
  }
  
  public static AsyncHttpClient getClient() {
    return client;
  }


}