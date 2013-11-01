package com.zynga.zcafeadmin.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zynga.zcafeadmin.R;

public class OrdersFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_orders , parent, false);
	}
	
}
