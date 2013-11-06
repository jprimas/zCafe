package com.zynga.zcafeadmin.fragments;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidplot.pie.PieChart;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;
import com.androidplot.ui.SizeLayoutType;
import com.androidplot.ui.SizeMetrics;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zynga.zcafeadmin.R;
import com.zynga.zcafeadmin.models.ReportData;
import com.zynga.zcafeadmin.models.ZyngaCoffee;

public class ReportsFragment extends Fragment {
	
	 private PieChart pie;
	 private TextView tvTotal;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_reports , parent, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		getActivity().findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
		 
		tvTotal = (TextView) getActivity().findViewById(R.id.tvTotal);
        
        pie = (PieChart) getActivity().findViewById(R.id.pieChart);
        pie.getBackgroundPaint().setAlpha(0);
        pie.getBorderPaint().setAlpha(0);
        pie.getTitleWidget().getLabelPaint().setColor(Color.BLACK);
        pie.getTitleWidget().setSize(new SizeMetrics(20, SizeLayoutType.ABSOLUTE, 400, SizeLayoutType.ABSOLUTE));
        pie.getTitleWidget().getLabelPaint().setTextSize(20);
        pie.getPieWidget().setHeight(40);
        
        getData();
        
	}
	
	private void getData(){
		AsyncHttpClient client = new AsyncHttpClient();
		client.get("https://yipbb.corp.zynga.com/zcafe-api/admin/dailyReport", new
		    AsyncHttpResponseHandler() {
		        @Override
		        public void onSuccess(String jsonString) {
		            try {
		            	JSONObject jsonObj = new JSONObject(jsonString);
						ReportData data = ReportData.parseReportData(jsonObj);
						System.out.println("SUCCESS");
						updateDate(data);
					} catch (JSONException e) {
						e.printStackTrace();
					}
		        }
		    }
		);
	}
	
	public void updateDate(ReportData data){
		int totalSold = data.getTotalCount();
		tvTotal.setText(String.valueOf(totalSold));
		
		String[] names = data.getNames();
		int[] counts = data.getCounts();
		for(int i = 0; i < names.length; i++){
			Segment s = new Segment(names[i], counts[i]);
			pie.addSeries(s, new SegmentFormatter(Color.rgb((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255)), Color.BLACK,Color.BLACK, Color.BLACK));
		}
		if(names.length > 1 && getActivity().findViewById(R.id.progressBar).getVisibility() == View.VISIBLE){
			getActivity().findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
		}
		pie.redraw();
	}
	
}

/*
 * if(dripCoffeeCount > 0){
        	s1 = new Segment("Drip Coffee", dripCoffeeCount);
        	pie.addSeries(s1, new SegmentFormatter(Color.rgb(200, 150, 150), Color.BLACK,Color.BLACK, Color.BLACK));
        }
        
        if(expressoCount > 0){
        	s2 = new Segment("Expresso", expressoCount);
        	pie.addSeries(s2, new SegmentFormatter(Color.rgb(150, 200, 150), Color.BLACK,Color.BLACK, Color.BLACK));
        }
        
        if(cappaccinoCount > 0){
        	s3 = new Segment("Cappaccino", cappaccinoCount);
        	pie.addSeries(s3, new SegmentFormatter(Color.rgb(150, 150, 200), Color.BLACK,Color.BLACK, Color.BLACK));
        }
        
        if(americanoCount > 0){
        	s4 = new Segment("Americano", americanoCount);
        	pie.addSeries(s4, new SegmentFormatter(Color.BLUE, Color.BLACK, Color.BLACK, Color.BLACK));
        }
        
        if(latteCount > 0){
            s5 = new Segment("Latte", latteCount);
            pie.addSeries(s5, new SegmentFormatter(Color.rgb(215, 170, 90), Color.BLACK, Color.BLACK, Color.BLACK));
        }
        */
