package com.zynga.zcafe.adapters;

import static com.nineoldandroids.view.ViewPropertyAnimator.animate;
import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zynga.zcafe.CafeApplication;
import com.zynga.zcafe.R;
import com.zynga.zcafe.fragments.MenuListFragment;
import com.zynga.zcafe.fragments.OrderFormFragment;
import com.zynga.zcafe.models.MenuItem;

public class MenuAdapter extends ArrayAdapter<MenuItem> {


	Context context;
	FragmentManager fragmentManager;
	Fragment fragment;
	public static int isLoading = 0;

	public MenuAdapter(Fragment fragment, FragmentManager fragmentManager, Context context,
			ArrayList<com.zynga.zcafe.models.MenuItem> items) {
		super(context, 0, items);
		this.context = context;
		this.fragmentManager = fragmentManager;
		this.fragment = fragment;
		CafeApplication.getObjectGraph().inject(this);
	}

	public void push(ArrayList<MenuItem> items) {
		clear();
		addAll(items);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.menu_item_detail, null);
			holder = new ViewHolder();
			holder.ivPhoto = (ImageView) view.findViewById(R.id.ivItemPhoto);
			holder.tvTitle = (TextView) view.findViewById(R.id.tvItemTitle);
			holder.bOrder = (Button) view.findViewById(R.id.bItemOrder);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		MenuItem item = getItem(position);
		ImageLoader.getInstance().displayImage(item.getThumbImageUrl(), holder.ivPhoto);
		holder.tvTitle.setText(item.getTitle());
		holder.bOrder.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentTransaction ft = fragmentManager.beginTransaction();
				ft.replace(R.id.flFragmentContainer, new OrderFormFragment(),
						v.getResources().getString(R.string.order_form_fragment));
				ft.commit();
				MenuItem menuItem = getItem(position);
				CafeApplication app = CafeApplication.getObjectGraph().get(CafeApplication.class);
				app.getProducers().setMenuItem(menuItem);
			}
		});
		if(isLoading == 0 || isLoading == 2 || isLoading == 4){
			isLoading += 1;
			float x = view.getX();
			view.setAlpha(0);
			view.setX(x-300);
			animate(view).translationX(x).alpha(1).setDuration(750);
		}else if(isLoading == 1 || isLoading == 3){
			isLoading += 1;
			float x = view.getX();
			view.setAlpha(0);
			view.setX(x+300);
			animate(view).translationX(-x).alpha(1).setDuration(750);
		}

		return view;
	}

	static class ViewHolder {
		ImageView ivPhoto;
		TextView tvTitle;
		Button bOrder;
	}


}
