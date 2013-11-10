package com.zynga.zcafe.activities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import org.apache.http.entity.StringEntity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings.Secure;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.urbanairship.push.PushManager;
import com.zynga.zcafe.CafeApplication;
import com.zynga.zcafe.R;
import com.zynga.zcafe.inject.modules.CafeModule.MainThreadBus;
import com.zynga.zcafe.models.Profile;
import com.zynga.zcafe.services.CafeService;

public class ProfileActivity extends FragmentActivity {

	@Inject
	CafeService service;
	@Inject
	CafeApplication app;
	@Inject
	MainThreadBus bus;
	
	private EditText etFullName;
	private Button bRegister;
	private ImageView ivProfile;
	private ImageView ivCamera;
	
	public static final int MEDIA_TYPE_IMAGE = 1;
	private Uri fileUri;
	private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
	private static final String IMAGE_DIRECTORY_NAME = "zCAFE";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		CafeApplication.getObjectGraph().inject(this);
		
		setViews();
		app = CafeApplication.getObjectGraph().get(CafeApplication.class);
		
		SharedPreferences configs = app.getConfigs();
		String name = configs.getString("name", "");
		String filePath = configs.getString("profilePath", "");
		etFullName.setText(name);
		
		
		ivProfile.setVisibility(View.VISIBLE);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        final Bitmap bitmap = BitmapFactory.decodeFile(filePath,options);
        ivProfile.setImageBitmap(bitmap);
        
        ivCamera.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
              captureImage();
            }
        });
        
        bRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
              Profile profile = createAndStoreProfile();
              if (profile != null) {
                if (!(profile.getUaId().isEmpty() || profile.getUdId().isEmpty() || profile.getName()
                    .isEmpty())) {
                  registerUser();
                  finish();
                }
              }
            }
         });
	}
	
	private void registerUser() {
	    Profile profile = app.getProfile();
	    StringEntity entity = null;
	    try {
	      entity = new StringEntity(profile.getProfileJson().toString());
	    } catch (UnsupportedEncodingException e) {
	      e.printStackTrace();
	    }
	    String url = getResources().getString(R.string.api_url)
	        + getResources().getString(R.string.register_post_url);
	    Log.i("UAID", url);
	    service.registerUser(app.getContext(), url, entity);
	}
	
	private Profile createAndStoreProfile() {
	    SharedPreferences configs = app.getConfigs();
	    SharedPreferences.Editor editor = configs.edit();
	    boolean isProfileComplete = true;
	    // Fullname
	    String name = etFullName.getText().toString();
	    if (name.isEmpty()) {
	      Toast.makeText(getApplicationContext(),getResources().getString(R.string.warning_registration_empty_name),
	          Toast.LENGTH_LONG).show();
	      isProfileComplete = false;
	    } else {
	      editor.putString("name", name);
	    }

	    // udid
	    String udid = configs.getString("udid", "");
	    if (udid.isEmpty()) {
	      udid = Secure.getString(getApplicationContext().getContentResolver(), Secure.ANDROID_ID);
	    }
	    editor.putString("udid", udid);

	    String uaid = PushManager.shared().getAPID();
	    editor.putString("uaid", uaid);
	    // Log.i("UAID", uaid);

	    if (!isProfileComplete) {
	      return null;
	    }

	    // Creates new profile
	    String device = getResources().getString(R.string.registration_device);
	    editor.commit();
	    Profile newProfile = new Profile("0", name, udid, uaid, device);
	    return newProfile;
	}
	 

	private void setViews() {
		etFullName = (EditText) findViewById(R.id.etFullName);
		ivProfile = (ImageView) findViewById(R.id.ivProfile);
		ivCamera = (ImageView) findViewById(R.id.ivCamera);
		bRegister = (Button) findViewById(R.id.bRegister);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}
	
	@Override
	protected void onResume() {
	    super.onResume();
	    bus.register(this);
	}
	
	@Override
	protected void onPause() {
	    super.onPause();
	    bus.unregister(this);
	}

	/*
	   * Capturing Camera Image will lauch camera app requrest image capture
	   */
	  private void captureImage() {
	      Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	   
	      fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
	   
	      intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
	   
	      // start the image capture Intent
	      startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
	  }
	  
	  /**
	   * Receiving activity result method will be called after closing the camera
	   * */
	  @Override
	  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    Log.d("DEBUG", "On activity result fragment");
	    // if the result is capturing Image
	      if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
	          if (resultCode == RESULT_OK) {
	              // successfully captured the image
	              // display it in image view
	              previewCapturedImage();
	          } else if (resultCode == RESULT_CANCELED) {
	              // user cancelled Image capture
	              Toast.makeText(getApplicationContext(),
	                      "User cancelled image capture", Toast.LENGTH_SHORT)
	                      .show();
	          } else {
	              // failed to capture image
	              Toast.makeText(getApplicationContext(),
	                      "Sorry! Failed to capture image", Toast.LENGTH_SHORT).show();
	          }
	      }
	  }
	  
	  /*
	   * Display image from a path to ImageView
	   */
	  private void previewCapturedImage() {
	      try {
	          
	          ivProfile.setVisibility(View.VISIBLE);

	          // bimatp factory
	          BitmapFactory.Options options = new BitmapFactory.Options();

	          // downsizing image as it throws OutOfMemory Exception for larger
	          // images
	          options.inSampleSize = 8;

	          final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
	                  options);

	          ivProfile.setImageBitmap(bitmap);
	          
	          File myFile = new File(fileUri.getPath());
	          RequestParams params = new RequestParams();
	          try {
	              params.put("file", myFile);
	          } catch(FileNotFoundException e) {
	            
	          }
	          
	          SharedPreferences configs = app.getConfigs();
	          SharedPreferences.Editor editor = configs.edit();
	          
	          String udid = configs.getString("udid", "");
	          String url = getResources().getString(R.string.api_url)
	                  + getResources().getString(R.string.upload_profile_image)+"/"+udid+".json";
	          
	          Log.i("upload image", url);
	          service.uploadProfilePic(params, url, null);
	       
	          
	          
	          editor.putString("profilePath", fileUri.getPath());
	          
	          editor.commit();
	          
	      } catch (NullPointerException e) {
	          e.printStackTrace();
	      }
	  }
	  
	  
	  /**
	   * Here we store the file url as it will be null after returning from camera
	   * app
	   */
	  @Override
	  public void onSaveInstanceState(Bundle outState) {
	      super.onSaveInstanceState(outState);
	   
	      // save file url in bundle as it will be null on scren orientation
	      // changes
	      outState.putParcelable("file_uri", fileUri);
	  }
	   
	  /*
	   * Here we restore the fileUri again
	   */
	//  @Override
	//  public void onRestoreInstanceState(Bundle savedInstanceState) {
//	      super.onRestoreInstanceState(savedInstanceState);
	//   
//	      // get the file url
//	      fileUri = savedInstanceState.getParcelable("file_uri");
	//  }
	  
	  /**
	   * Creating file uri to store image/video
	   */
	  public Uri getOutputMediaFileUri(int type) {
	      return Uri.fromFile(getOutputMediaFile(type));
	  }
	   
	  /*
	   * returning image / video
	   */
	  private static File getOutputMediaFile(int type) {
	   
	      // External sdcard location
	      File mediaStorageDir = new File(
	              Environment
	                      .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
	              IMAGE_DIRECTORY_NAME);
	   
	      // Create the storage directory if it does not exist
	      if (!mediaStorageDir.exists()) {
	          if (!mediaStorageDir.mkdirs()) {
	              Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
	                      + IMAGE_DIRECTORY_NAME + " directory");
	              return null;
	          }
	      }
	   
	      // Create a media file name
	      String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
	              Locale.getDefault()).format(new Date());
	      File mediaFile;
	      if (type == MEDIA_TYPE_IMAGE) {
	          mediaFile = new File(mediaStorageDir.getPath() + File.separator
	                  + "IMG_" + timeStamp + ".jpg");
	      } else {
	          return null;
	      }
	   
	      return mediaFile;
	  }
	  
	  public void loadHome(MenuItem m) {
		  finish();
	  }
}
