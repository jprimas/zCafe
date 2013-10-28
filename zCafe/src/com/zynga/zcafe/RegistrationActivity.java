package com.zynga.zcafe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationActivity extends Activity {
	private EditText tvName;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		tvName = (EditText) findViewById(R.id.tvName);
		
		Intent i = getIntent();
		boolean forChange = i.getBooleanExtra("forChange", false);
		
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		String name = pref.getString("name", "");
		String phoneId = pref.getString("name", "");
		//Skip the register view if you already registered and user is not trying to edit it
		if(!forChange && !name.equals("") && !phoneId.equals("")){
			Intent newIntent = new Intent(this, MainActivity.class);
			newIntent.putExtra("name", name);
			newIntent.putExtra("phoneId", phoneId);
			startActivity(newIntent);
		}
		
		tvName.setText(name);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registration, menu);
		return true;
	}
	
	public void onRegisterPress(View v){
		String name = tvName.getText().toString();
		if(name == null || name.equals("")){
			Toast toast = Toast.makeText(getApplicationContext(), "Please enter your name to continue.", Toast.LENGTH_LONG);
			toast.show();
		} else {
			//Get the unique phone Id. Could be null in some very, very rare cases.
			final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
			String phoneId = tm.getDeviceId();
			if(phoneId == null){
				//I try to make it so that it will never be null. Still not 100% though
				phoneId = tm.getSimSerialNumber();
			}
			//Save name to shared preferences
			SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
			Editor edit = pref.edit();
			edit.putString("name", name);
			edit.putString("phoneId", phoneId);
			edit.commit();
			Intent i = new Intent(this, MainActivity.class);
			i.putExtra("name", name);
			i.putExtra("phoneId", phoneId);
			startActivity(i);
		}
	}

}
