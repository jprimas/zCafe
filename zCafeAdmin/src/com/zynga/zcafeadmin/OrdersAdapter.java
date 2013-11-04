package com.zynga.zcafeadmin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zynga.zcafeadmin.models.ZyngaCoffee;

public class OrdersAdapter extends ArrayAdapter<ZyngaCoffee>{
	
	private Context context;
	ZyngaCoffee coffee;
	
	public OrdersAdapter(Context context, List<ZyngaCoffee> orders){
		super(context, 0, orders);
		this.context = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		View view = convertView;
		if(view == null){
			LayoutInflater inflator = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflator.inflate(R.layout.order_item, null);
		}
		coffee = getItem(position);
		TextView tvName = (TextView) view.findViewById(R.id.tvName);
		TextView tvBody = (TextView) view.findViewById(R.id.tvBody);
		Button btComplete = (Button) view.findViewById(R.id.btComplete);
		
		tvName.setText(coffee.getItem().getTitle());
		tvBody.setText(coffee.getUserName());
		
		final OrdersAdapter a = this;
		final int pos = position;
		btComplete.setOnClickListener(new Button.OnClickListener() {
		    public void onClick(View v) {
		    	JSONObject jsonParams = new JSONObject();
		    	StringEntity entity = null;
		        try {
					jsonParams.put("orderId", coffee.getId());
					entity = new StringEntity(jsonParams.toString());
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
		    	AsyncHttpClient client = new AsyncHttpClient();
				client.put(context,
					"https://yipbb.corp.zynga.com/zcafe-api/admin/completeOrder",
					entity,
					"application/json",
					new AsyncHttpResponseHandler() {
				        
						@Override
						public void onSuccess(String arg0) {
							System.out.println("removing");
				            a.remove(a.getItem(pos));
						};
				        
				        @Override
				        public void onFailure(Throwable arg0, String arg1) {
				        	super.onFailure(arg0, arg1);
				        	System.out.println("failed: ");
				        	arg0.printStackTrace();
				        	
				        }
				    }
				);
		    }
		});
		
		return view;
	}

}
