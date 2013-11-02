package com.zynga.zcafeadmin.fragments;

import java.text.DecimalFormat;
import java.util.Arrays;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidplot.ui.AnchorPosition;
import com.androidplot.ui.SizeLayoutType;
import com.androidplot.ui.SizeMetrics;
import com.androidplot.ui.XLayoutStyle;
import com.androidplot.ui.YLayoutStyle;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYStepMode;
import com.zynga.zcafeadmin.R;

public class FrequencyFragment extends Fragment {
	
	private XYPlot freqeuncyPlot;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_frequency , parent, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

        freqeuncyPlot = (XYPlot) getActivity().findViewById(R.id.frequencyPlot);
        freqeuncyPlot.getLayoutManager().remove(freqeuncyPlot.getLegendWidget());
        freqeuncyPlot.getLayoutManager().remove(freqeuncyPlot.getTitleWidget());
        freqeuncyPlot.getGraphWidget().setHeight(25);
        freqeuncyPlot.getGraphWidget().position(
                10, XLayoutStyle.ABSOLUTE_FROM_LEFT,
                0, YLayoutStyle.ABSOLUTE_FROM_TOP,
                AnchorPosition.LEFT_TOP);
        freqeuncyPlot.getBackgroundPaint().setAlpha(0);
        freqeuncyPlot.getBorderPaint().setAlpha(0);
        freqeuncyPlot.setTicksPerRangeLabel(2);
        freqeuncyPlot.getTitleWidget().getLabelPaint().setColor(Color.BLACK);
        freqeuncyPlot.getRangeLabelWidget().getLabelPaint().setColor(Color.BLACK);
        freqeuncyPlot.getRangeLabelWidget().setSize(new SizeMetrics(200, SizeLayoutType.ABSOLUTE, 20, SizeLayoutType.ABSOLUTE));
        freqeuncyPlot.getRangeLabelWidget().getLabelPaint().setTextSize(20);
        freqeuncyPlot.getDomainLabelWidget().getLabelPaint().setColor(Color.BLACK);
        freqeuncyPlot.getDomainLabelWidget().setSize(new SizeMetrics(20, SizeLayoutType.ABSOLUTE, 400, SizeLayoutType.ABSOLUTE));
        freqeuncyPlot.getDomainLabelWidget().getLabelPaint().setTextSize(20);
        freqeuncyPlot.setDomainStep(XYStepMode.INCREMENT_BY_VAL,1);
        freqeuncyPlot.setDomainValueFormat(new DecimalFormat("#"));
        freqeuncyPlot.setRangeValueFormat(new DecimalFormat("#"));
        freqeuncyPlot.getGraphWidget().getDomainLabelPaint().setColor(Color.BLACK);
        freqeuncyPlot.getGraphWidget().getRangeLabelPaint().setColor(Color.BLACK);
        
        freqeuncyPlot.getGraphWidget().getBackgroundPaint().setAlpha(0);
        freqeuncyPlot.getGraphWidget().getGridBackgroundPaint().setAlpha(0);
        freqeuncyPlot.getGraphWidget().getDomainSubGridLinePaint().setColor(Color.BLACK);
        freqeuncyPlot.getGraphWidget().getRangeGridLinePaint().setColor(Color.rgb(150,150,150));
        freqeuncyPlot.getGraphWidget().getDomainGridLinePaint().setColor(Color.rgb(150,150,150));
        freqeuncyPlot.getGraphWidget().getDomainOriginLinePaint().setColor(Color.rgb(150,150,150));
        freqeuncyPlot.getGraphWidget().getRangeOriginLinePaint().setColor(Color.rgb(150,150,150));

        Number[] coffees1 = {2, 15, 30, 33,  22, 16,   0,   0,    0,   0,   5,  17,  41,  33,  24,   2,  0,   0,   0};
        Number[] coffees2 = {0, 20, 35, 50,  32, 16,   0,   0,    0,   0,   1,  3,  18,  19,  18,   6,  0,   0,   0};
        Number[] coffees3 = {1, 5,  10, 30,  15, 3,    0,   0,    0,   0,   15,  16,  18,  3,  3,   1,  0,   0,   0};
        //Number[] coffees4 = {7, 1, 27, 28,   29, 27,   0,   0,    0,   0,   0,  7,  23,  12,  5,   21,  0,   0,   0};
        //Number[] coffees5 = {0, 2, 15, 8,    19, 21,   0,   0,    0,   0,   0,  30,  45,  10,  1,   1,  0,   0,   0};
        //Number[] xValues ={8, 8.5, 9, 9.5, 10, 10.5, 11, 11.5, 12, 12.5, 13, 13.5, 14, 14.5, 15, 15.5, 16, 16.5, 17};
        
        addLine(coffees1, Color.rgb(255, 180, 180), Color.rgb(255, 200, 200));
        addLine(coffees2, Color.rgb(255, 100, 100), Color.rgb(255, 120, 120));
        addLine(coffees3, Color.rgb(255, 0, 0), Color.rgb(255, 0, 0));
        //addLine(coffees4, Color.rgb(150, 150, 150), Color.BLACK);
        //addLine(coffees5, Color.rgb(200, 200, 200), Color.BLACK);

        
        
	}
	
	private void addLine(Number[] yValues, int lineColor, int pointColor){
		
		Number[] xValues =  {8, 8.5, 9, 9.5, 10, 10.5, 11, 11.5, 12, 12.5, 13, 13.5, 14, 14.5, 15, 15.5, 16, 16.5, 17};
		
		XYSeries series = new SimpleXYSeries(
                Arrays.asList(xValues),          	// SimpleXYSeries takes a List so turn our array into a List
                Arrays.asList(yValues), 			// Y_VALS_ONLY means use the element index as the x value
                "");                            // Set the display title of the series

        LineAndPointFormatter seriesFormat = new LineAndPointFormatter(
        		lineColor,                   		// line color
        		pointColor,                   		// point color
                null,                               // fill color (none)
                null);                           	// text color


        freqeuncyPlot.addSeries(series, seriesFormat);
	}
	
}
