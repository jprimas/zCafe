package com.zynga.zcafe.fragments;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import org.apache.http.entity.StringEntity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings.Secure;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.squareup.otto.Subscribe;
import com.urbanairship.UAirship;
import com.urbanairship.push.PushManager;
import com.zynga.zcafe.CafeApplication;
import com.zynga.zcafe.IntentReceiver;
import com.zynga.zcafe.R;
import com.zynga.zcafe.events.RegistrationEvent;
import com.zynga.zcafe.events.UaIdEvent;
import com.zynga.zcafe.inject.modules.CafeModule.MainThreadBus;
import com.zynga.zcafe.models.Profile;
import com.zynga.zcafe.services.CafeService;

public class RegistrationFragment extends Fragment {

  FragmentActivity activity;
  CafeApplication app;
  IntentFilter apidUpdateFilter;

  @Inject
  MainThreadBus mainBus;

  @Inject
  CafeService service;

  EditText etFullName;
  Button bRegister;

  private ImageView ivCamera;
  public static final int MEDIA_TYPE_IMAGE = 1;
  private Uri fileUri;
  private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
  private ImageView ivProfile;
  private static final String IMAGE_DIRECTORY_NAME = "zCAFE";

  public RegistrationFragment() {
    super();
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    this.activity = (FragmentActivity) activity;
    CafeApplication.getObjectGraph().inject(this);
    this.app = CafeApplication.getObjectGraph().get(CafeApplication.class);
    mainBus = CafeApplication.getObjectGraph().get(MainThreadBus.class);
  }

  @Override
  public void onCreate(Bundle state) {
    super.onCreate(state);

    // Airship
    apidUpdateFilter = new IntentFilter();
    apidUpdateFilter.addAction(UAirship.getPackageName()
        + IntentReceiver.APID_UPDATED_ACTION_SUFFIX);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.registration, parent, false);
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    init();
    registerListeners();
  }

  @Override
  public void onResume() {
    super.onResume();
    mainBus.register(this);
    this.activity.registerReceiver(apidUpdateReceiver, apidUpdateFilter);
  }

  private BroadcastReceiver apidUpdateReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
      registerUser();
    }
  };

  @Override
  public void onPause() {
    super.onPause();
    mainBus.unregister(this);
    this.activity.unregisterReceiver(apidUpdateReceiver);
  }

  private void init() {
    etFullName = (EditText) getView().findViewById(R.id.etFullName);
    bRegister = (Button) getView().findViewById(R.id.bRegister);

    ivCamera = (ImageView)getView().findViewById(R.id.ivCamera);
    ivProfile = (ImageView)getView().findViewById(R.id.ivProfile);
  }

  private void registerListeners() {
    bRegister.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Profile profile = createAndStoreProfile();
        if (profile != null) {
          if (!((profile.getUaId() == null || profile.getUaId().isEmpty()) || (profile.getUdId() == null || profile.getUdId().isEmpty()) || profile.getName().isEmpty())) {
            registerUser();
          }
        }
      }
    });

    ivCamera.setOnClickListener(new OnClickListener() {
    
    @Override
    public void onClick(View v) {
      captureImage();
    }
  });
  }

  private void registerUser() {
	  System.out.println("registering");
    Profile profile = app.getProfile();
    StringEntity entity = null;
    try {
      entity = new StringEntity(profile.getProfileJson().toString());
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    String url = getView().getResources().getString(R.string.api_url)
        + getView().getResources().getString(R.string.register_post_url);
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
      Toast.makeText(getView().getContext(),
          getView().getResources().getString(R.string.warning_registration_empty_name),
          Toast.LENGTH_LONG).show();
      isProfileComplete = false;
    } else {
      editor.putString("name", name);
    }

    // udid
    String udid = configs.getString("udid", "");
    if (udid.isEmpty()) {
      udid = Secure.getString(getView().getContext().getContentResolver(), Secure.ANDROID_ID);
    }
    editor.putString("udid", udid);

    String uaid = PushManager.shared().getAPID();
    editor.putString("uaid", uaid);
    // Log.i("UAID", uaid);

    // uaid
    /*
     * String uaid = configs.getString("uaid", ""); if (uaid.isEmpty()) { uaid =
     * PushManager.shared().getAPID(); ; if (uaid.isEmpty()) { Thread background
     * = new Thread(new Runnable() {
     * 
     * @Override public void run() { Looper.prepare(); Handler handler = new
     * Handler(); handler.postDelayed(new Runnable() {
     * 
     * @Override public void run() { String uaid =
     * PushManager.shared().getAPID(); if (uaid.isEmpty()) { Handler handler =
     * new Handler(Looper.getMainLooper()); handler.postDelayed(new Runnable() {
     * 
     * @Override public void run() { Toast.makeText( getView().getContext(),
     * getView().getResources().getString(
     * R.string.warning_registration_empty_uaid), Toast.LENGTH_LONG).show(); }
     * }, 1000); } else { UaIdEvent event = new UaIdEvent(uaid);
     * mainBus.post(event); } } }, 5000); Looper.loop(); } });
     * background.start(); }
     * 
     * }
     */

    if (!isProfileComplete) {
      return null;
    }

    // Creates new profile
    String device = getView().getResources().getString(R.string.registration_device);
    editor.commit();
    Profile newProfile = new Profile("0", name, udid, uaid, device);
    return newProfile;
  }

  @Subscribe
  public void onUaIdUpdated(UaIdEvent event) {
    Log.i("UAID-EVENT", "UPDATED");
    SharedPreferences configs = app.getConfigs();
    SharedPreferences.Editor editor = configs.edit();
    editor.putString("uaid", event.getUaId());
    editor.commit();
  }

  @Subscribe
  public void onRegistrationEvent(RegistrationEvent event) {
    Log.i("SUBSCRIBE", "EVENT");
    SharedPreferences configs = app.getConfigs();
    SharedPreferences.Editor editor = configs.edit();
    if (event.getStatus() == 0) {
      editor.putBoolean("isRegistered", true);
      Log.i("SUBSCRIBE SUCCESS", event.getResponse());
    } else {
      Log.i("FAIL", event.getResponse());
      editor.putBoolean("isRegistered", true);
    }
    editor.commit();

    if (event.getStatus() == 0) {
      activity.finish();
    }
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
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    Log.d("DEBUG", "On activity result fragment");
    // if the result is capturing Image
      if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
          if (resultCode == getActivity().RESULT_OK) {
              // successfully captured the image
              // display it in image view
              previewCapturedImage();
          } else if (resultCode == getActivity().RESULT_CANCELED) {
              // user cancelled Image capture
              Toast.makeText(getActivity().getApplicationContext(),
                      "User cancelled image capture", Toast.LENGTH_SHORT)
                      .show();
          } else {
              // failed to capture image
              Toast.makeText(getActivity().getApplicationContext(),
                      "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                      .show();
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
          
          String url = getView().getResources().getString(R.string.api_url)
                  + getView().getResources().getString(R.string.upload_profile_image)+"/test.json";
          
          Log.i("upload image", url);
          service.uploadProfilePic(params, url, null);
       
          SharedPreferences configs = app.getConfigs();
          SharedPreferences.Editor editor = configs.edit();
          
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
//      super.onRestoreInstanceState(savedInstanceState);
//   
//      // get the file url
//      fileUri = savedInstanceState.getParcelable("file_uri");
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

}
