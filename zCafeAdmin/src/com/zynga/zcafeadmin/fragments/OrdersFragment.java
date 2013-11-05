package com.zynga.zcafeadmin.fragments;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.urbanairship.push.PushManager;
import com.zynga.zcafeadmin.OrdersAdapter;
import com.zynga.zcafeadmin.R;
import com.zynga.zcafeadmin.models.ZyngaCoffee;

public class OrdersFragment extends Fragment {
	
	private ArrayList<ZyngaCoffee> pendingCoffees;
	private ListView lvOrders;
	private OrdersAdapter adapter;
	private Handler mHandler;
	boolean viewActive;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_orders , parent, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		pendingCoffees = new ArrayList<ZyngaCoffee>();
		lvOrders = (ListView) getActivity().findViewById(R.id.lvOrders);
		adapter = new OrdersAdapter(getActivity(), pendingCoffees);
		lvOrders.setAdapter(adapter);
		
		mHandler = new Handler();
		
		updateOrders();
		createUpdateThread();
		
		String apid = PushManager.shared().getAPID();
		System.out.println(">>>>>>>>>>" + apid);
		
	}
	
	@Override
	public void onStop() {
		super.onStop();
		viewActive = false;
	}
	
	private void updateOrders(){
		AsyncHttpClient client = new AsyncHttpClient();
		client.get("https://yipbb.corp.zynga.com/zcafe-api/admin/orders/Pending", new
		    AsyncHttpResponseHandler() {
		        @Override
		        public void onSuccess(String jsonString) {
		            try {
		            	JSONArray jsonObj = new JSONArray(jsonString);
						pendingCoffees = ZyngaCoffee.toZyngaCoffeeObjectArray(jsonObj);
						adapter.clear();
						adapter.addAll(pendingCoffees);
					} catch (JSONException e) {
						e.printStackTrace();
					}
		        }
		    }
		);
	}
	
	private void createUpdateThread(){
		new Thread(new Runnable() {
	        @Override
	        public void run() {
	        	viewActive = true;
	            while (viewActive) {
	                try {
	                    Thread.sleep(100000);
	                    mHandler.post(new Runnable() {

	                        @Override
	                        public void run() {
	                            updateOrders();
	                            System.out.println("Updated");
	                        }
	                    });
	                } catch (Exception e) {
	                    System.err.println("Error: Update Thread failed.");
	                }
	            }
	        }
	    }).start();
	}
}
