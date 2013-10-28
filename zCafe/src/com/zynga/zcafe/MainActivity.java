package com.zynga.zcafe;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;

import com.zynga.zcafe.fragments.MenuFragment;
import com.zynga.zcafe.fragments.OrdersFragment;

public class MainActivity extends FragmentActivity implements TabListener {
	
	private String name;
	private String phoneId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Intent i = getIntent();
		name = i.getStringExtra("name");
		phoneId = i.getStringExtra("phoneId");
		setupNavigationTabs();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) { 
	        case R.id.changeName:
	        	Intent i = new Intent(this, RegistrationActivity.class);
	        	i.putExtra("forChange", true);
	            startActivity(i);
	            break;
	        default:
	        	break;
        }
        return true;
    }
	
	public void setupNavigationTabs(){
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		Tab tabMenu = actionBar.newTab().setText("Menu")
				.setTag("MenuFragment")
				.setIcon(R.drawable.ic_menu)
				.setTabListener(this);
		Tab tabOrders = actionBar.newTab().setText("Current Orders")
				.setTag("OrdersFragment")
				.setIcon(R.drawable.ic_coffee)
				.setTabListener(this);
		
		actionBar.addTab(tabMenu);
		actionBar.addTab(tabOrders);
		actionBar.selectTab(tabMenu);
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		FragmentManager manager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction fts = manager.beginTransaction();
		if(tab.getTag().equals("MenuFragment")){
			fts.replace(R.id.frame_container, new MenuFragment());
		}else{
			fts.replace(R.id.frame_container, new OrdersFragment());
		}
		fts.commit();
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

}
