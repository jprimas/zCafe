package com.zynga.zcafeadmin.fragments;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zynga.zcafeadmin.R;
import com.zynga.zcafeadmin.models.ZyngaCoffee;

public class OrdersFragment extends Fragment {
	
	ZyngaCoffee[] pendingCoffees;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_orders , parent, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		AsyncHttpClient client = new AsyncHttpClient();
		client.get("http://10.101.66.58:8080/zcafe-api/orders/Pending", new
		    AsyncHttpResponseHandler() {
		        @Override
		        public void onSuccess(String jsonString) {
		        	JSONObject jsonObj = new JSONObject(jsonString);
		            pendingCoffees = ZyngaCoffee.toZyngaCoffeeObject(jsonObj);
		        }
		    }
		);
		
	}
}
