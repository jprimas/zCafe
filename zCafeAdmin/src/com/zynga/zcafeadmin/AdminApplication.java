package com.zynga.zcafeadmin;

import java.io.UnsupportedEncodingException;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.urbanairship.AirshipConfigOptions;
import com.urbanairship.UAirship;
import com.urbanairship.push.PushManager;

public class AdminApplication extends Application {

	@Override
	public void onCreate(){

		AirshipConfigOptions options = AirshipConfigOptions.loadDefaultOptions(this);
		UAirship.takeOff(this, options);
		PushManager.enablePush();
		PushManager.shared().setIntentReceiver(IntentReceiver.class);

		String apid = PushManager.shared().getAPID();
	}

	
}
