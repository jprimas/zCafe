package com.zynga.zcafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
	
	private String name;
	private String phoneId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Intent i = getIntent();
		name = i.getStringExtra("name");
		phoneId = i.getStringExtra("phoneId");
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

}
