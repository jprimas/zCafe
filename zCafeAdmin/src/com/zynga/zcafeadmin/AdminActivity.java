package com.zynga.zcafeadmin;

import java.io.UnsupportedEncodingException;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings.Secure;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.urbanairship.UAirship;
import com.urbanairship.push.PushManager;
import com.zynga.zcafeadmin.fragments.FrequencyFragment;
import com.zynga.zcafeadmin.fragments.OrdersFragment;
import com.zynga.zcafeadmin.fragments.ReportsFragment;

public class AdminActivity extends FragmentActivity implements TabListener {
	
	IntentFilter apidUpdateFilter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin);
		setupNavigationTabs();
		
		apidUpdateFilter = new IntentFilter();
        apidUpdateFilter.addAction(UAirship.getPackageName()+IntentReceiver.APID_UPDATED_ACTION_SUFFIX);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		registerReceiver(apidUpdateReceiver, apidUpdateFilter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.admin, menu);
		return true;
	}
	
	private BroadcastReceiver apidUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        	String apid = PushManager.shared().getAPID();
        	Toast toast = Toast.makeText(context, apid, Toast.LENGTH_LONG);
        	toast.show();
        	registerApp(apid);
        }
    };
	
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	public void setupNavigationTabs(){
		ActionBar actionBar = getActionBar();
		actionBar.setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		Tab tabOrders = actionBar.newTab().setText("Orders")
				.setTag("OrdersFragment")
				//.setIcon(R.drawable.ic_menu)
				.setTabListener(this);
		Tab tabReports = actionBar.newTab().setText("Reports")
				.setTag("ReportsFragment")
				//.setIcon(R.drawable.ic_coffee)
				.setTabListener(this);
		Tab tabFrequency = actionBar.newTab().setText("Frequency")
				.setTag("FrequencyFragment")
				//.setIcon(R.drawable.ic_coffee)
				.setTabListener(this);
		
		actionBar.addTab(tabOrders);
		actionBar.addTab(tabReports);
		actionBar.addTab(tabFrequency);
		actionBar.selectTab(tabOrders);
	}

	public void registerApp(String apid){
		final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		boolean hasRegistered = pref.getBoolean("hasRegistered", false);
		if(!hasRegistered){
			String phoneId =  Secure.getString(getBaseContext().getContentResolver(), Secure.ANDROID_ID);
			JSONObject jsonParams = new JSONObject();
	    	StringEntity entity = null;
	        try {
				jsonParams.put("name", "admin");
				jsonParams.put("udid", phoneId);
				jsonParams.put("uaid", apid);
				jsonParams.put("device", "android");
				System.out.println(jsonParams.toString());
				entity = new StringEntity(jsonParams.toString());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
	    	AsyncHttpClient client = new AsyncHttpClient();
			client.post(this,
				"http://54.201.40.146/zcafe-api/register",
				entity,
				"application/json",
			    new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(String jsonString) {
					Editor edit = pref.edit();
					edit.putBoolean("hasRegistered", true);
					edit.commit();
				}
			});
		}
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		FragmentManager manager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction fts = manager.beginTransaction();
		if(tab.getTag().equals("OrdersFragment")){
			fts.replace(R.id.frame_container, new OrdersFragment());
		}else if(tab.getTag().equals("ReportsFragment")){
			fts.replace(R.id.frame_container, new ReportsFragment());
		}else{
			fts.replace(R.id.frame_container, new FrequencyFragment());
		}
		fts.commit();
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {  }

}
