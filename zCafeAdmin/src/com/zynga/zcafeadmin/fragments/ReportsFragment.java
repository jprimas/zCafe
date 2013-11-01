package com.zynga.zcafeadmin.fragments;

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
import com.zynga.zcafeadmin.R;

public class ReportsFragment extends Fragment {
	
	 private PieChart pie;
	 private Segment s1;
	 private Segment s2;
	 private Segment s3;
	 private Segment s4;
	 private TextView tvTotal;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_reports , parent, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		int totalSold = 10;
		tvTotal = (TextView) getActivity().findViewById(R.id.tvTotal);
		tvTotal.setText(String.valueOf(totalSold));
        
        pie = (PieChart) getActivity().findViewById(R.id.pieChart);
        pie.getBackgroundPaint().setAlpha(0);
        pie.getBorderPaint().setAlpha(0);;
        pie.getTitleWidget().getLabelPaint().setColor(Color.BLACK);
        pie.getTitleWidget().setSize(new SizeMetrics(20, SizeLayoutType.ABSOLUTE, 400, SizeLayoutType.ABSOLUTE));
        pie.getTitleWidget().getLabelPaint().setTextSize(20);
        pie.getPieWidget().setHeight(40);
 
        s1 = new Segment("Drip Coffee", 3);
        s2 = new Segment("Expresso", 4);
        s3 = new Segment("Cappaccino", 2);
        s4 = new Segment("Americano", 1);
 
        pie.addSeries(s1, new SegmentFormatter(Color.rgb(200, 150, 150), Color.BLACK,Color.BLACK, Color.BLACK));
        pie.addSeries(s2, new SegmentFormatter(Color.rgb(150, 200, 150), Color.BLACK,Color.BLACK, Color.BLACK));
        pie.addSeries(s3, new SegmentFormatter(Color.rgb(150, 150, 200), Color.BLACK,Color.BLACK, Color.BLACK));
        pie.addSeries(s4, new SegmentFormatter(Color.BLUE, Color.BLACK, Color.BLACK, Color.BLACK));

        
	}
	
}
