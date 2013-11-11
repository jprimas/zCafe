package com.zynga.zcafe.views;

import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView.Validator;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.zynga.zcafe.R;
import com.zynga.zcafe.adapters.PopupEditTextAdapter;
import com.zynga.zcafe.models.Friend;

public class PopupEditText extends EditText {
	
	
	PopupEditTextAdapter adapter;
	PopupWindow popup;
  ListView lvItems;
  LayoutInflater inflater;
  Context context;

	private boolean blockCompletion = false;
	private int lastAtSignIndex = 0;

	
	public PopupEditText(Context context, AttributeSet attrs) {
	  super(context, attrs);
	  this.context = getContext();
	  init();
	}
	
	private void init() {
    inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    lvItems = (ListView)inflater.inflate(R.layout.popup_list, null);
    popup = new PopupWindow(lvItems, LayoutParams.MATCH_PARENT, 200, true);
    adapter = new PopupEditTextAdapter(getContext(), Friend.buildFriendsMock());
    lvItems.setAdapter(adapter);
    setRawInputType(EditorInfo.TYPE_TEXT_FLAG_AUTO_COMPLETE);
    setFocusable(true);
    lvItems.setClickable(true);
    registerListeners();
	}
	
  private void registerListeners() {
    addTextChangedListener(new EditTextWatcher());
    lvItems.setOnItemClickListener(new OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
        Friend friend = (Friend) lvItems.getItemAtPosition(position);
        Editable account = new SpannableStringBuilder(friend.getAccount());
        if (!blockCompletion) {
          beginBatchEdit();
          getText().append(account);
        }
        endBatchEdit();
        postDelayed(new Runnable() {
          @Override
          public void run() {
            popup.dismiss();
          }
        }, 200);
      }
      
    });
    
  }
  
  @Override
  public void onBeginBatchEdit() {
    blockCompletion = true;
  }
  
  @Override
  public void onEndBatchEdit() {
    blockCompletion = false;
  }

  public boolean isPopupShowing() {
    return popup.isShowing();
  }
	
	private class EditTextWatcher implements TextWatcher {
	  @Override
	  public void afterTextChanged(Editable s) {
	        
	  }

	  @Override
	  public void beforeTextChanged(CharSequence s, int start, int count, int after) {

	  }

    @Override
	  public void onTextChanged(CharSequence s, int start, int before, int count) {
      int position = s.length() - 1;
      if (!isPopupShowing()) {
        if (s.length() == 1 && s.toString().equals("@")) {
          showPopup();
          lastAtSignIndex = 0;
        } else if (s.length() > 1) {
          if (String.valueOf(s.subSequence(position - 1, s.length())).equals(" @")) {
            showPopup();
            lastAtSignIndex = position;
          }
        }
      }
	  }
	    
	}
	
	private void showPopup() {
	  Drawable background = getResources().getDrawable(R.drawable.popup);
	  popup.setBackgroundDrawable(background);
	  popup.setFocusable(true);
	  popup.setHeight(200);
	  popup.setInputMethodMode(PopupWindow.INPUT_METHOD_NOT_NEEDED);
	  popup.setOutsideTouchable(true);
	  post(new Runnable() {

      @Override
      public void run() {
        popup.showAtLocation(PopupEditText.this, Gravity.CENTER_HORIZONTAL, 0,
                0 - PopupEditText.this.getHeight());
      }
	    
	  });

    lvItems.requestFocusFromTouch();
	}
	

	

}
