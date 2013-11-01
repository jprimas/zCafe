package com.zynga.zcafeadmin;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;

import com.zynga.zcafeadmin.fragments.OrdersFragment;
import com.zynga.zcafeadmin.fragments.ReportsFragment;

public class AdminActivity extends FragmentActivity implements TabListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin);
		setupNavigationTabs();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.admin, menu);
		return true;
	}
	
	public void setupNavigationTabs(){
		ActionBar actionBar = getActionBar();
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
		
		actionBar.addTab(tabOrders);
		actionBar.addTab(tabReports);
		actionBar.selectTab(tabOrders);
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		FragmentManager manager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction fts = manager.beginTransaction();
		if(tab.getTag().equals("OrdersFragment")){
			fts.replace(R.id.frame_container, new OrdersFragment());
		}else{
			fts.replace(R.id.frame_container, new ReportsFragment());
		}
		fts.commit();
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {  }

}
