package com.zynga.zcafeadmin;

import static com.nineoldandroids.view.ViewPropertyAnimator.animate;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.zynga.zcafeadmin.models.ZyngaCoffee;

public class OrdersAdapter extends ArrayAdapter<ZyngaCoffee>{

	private Context context;
	private ViewGroup parent;
	private ZyngaCoffee coffee;
	private String notes;
	private View currentView;
	private static boolean canDelete = true;

	public OrdersAdapter(Context context, List<ZyngaCoffee> orders){
		super(context, 0, orders);
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		View view = convertView;
		this.parent = parent;
		if(view == null){
			LayoutInflater inflator = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflator.inflate(R.layout.order_item, null);
		}
		coffee = getItem(position);
		notes = coffee.getNotes();
		TextView tvName = (TextView) view.findViewById(R.id.tvName);
		TextView tvBody = (TextView) view.findViewById(R.id.tvBody);
		TextView tvCount = (TextView) view.findViewById(R.id.tvCount);
		Button btComplete = (Button) view.findViewById(R.id.btComplete);
		btComplete.setTag(position);

		tvName.setText(Html.fromHtml(coffee.getItem().getTitle() + "<small>&nbsp;&nbsp;<small>for</small>&nbsp;&nbsp;</small>" + coffee.getUserName()));
		tvBody.setText("Notes: " + coffee.getNotes());
		tvCount.setText("Count: " + coffee.getCount());

		final View itemView = view;
		btComplete.setOnClickListener(new Button.OnClickListener() {
			public void onClick(final View v) {
				if(canDelete){
					canDelete = false;
					final Integer index = (Integer) v.getTag();
					JSONObject jsonParams = new JSONObject();
					StringEntity entity = null;
					try {
						jsonParams.put("orderId", getItem(index).getId());
						entity = new StringEntity(jsonParams.toString());
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					}
					AsyncHttpClient client = new AsyncHttpClient();
					client.put(context,
							"https://yipbb.corp.zynga.com/zcafe-api/admin/completeOrder.json",
							entity,
							"application/json",
							new AsyncHttpResponseHandler() {

						@Override
						public void onSuccess(String arg0) {
							currentView = itemView;
							animateDeletion(index);
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
			}
		});


		return view;
	}

	private void animateDeletion(final int index){
		//FragmentViewHome homeFragment = (FragmentViewHome) .getSupportFragmentManager().findFragmentByTag("homelist");
		System.out.println(index);
		final float x = currentView.getX();
		animate(currentView).alpha(.001f).setDuration(700).
		//animate(currentView).translationX(500).setDuration(700).
		setListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				System.out.println(getItem(index).getNotes());
				remove(getItem(index));
				currentView.setAlpha(1);
				canDelete = true;
				//currentView.setX(x);
			};
		});

	}

}
