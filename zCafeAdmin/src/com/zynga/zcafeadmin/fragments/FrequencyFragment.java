package com.zynga.zcafeadmin.fragments;

import java.text.DecimalFormat;
import java.util.Arrays;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        freqeuncyPlot.getBackgroundPaint().setAlpha(0);
        freqeuncyPlot.getBorderPaint().setAlpha(0);
        freqeuncyPlot.setTicksPerRangeLabel(2);
        freqeuncyPlot.getTitleWidget().getLabelPaint().setColor(Color.BLACK);
        freqeuncyPlot.getRangeLabelWidget().getLabelPaint().setColor(Color.BLACK);
        freqeuncyPlot.getDomainLabelWidget().getLabelPaint().setColor(Color.BLACK);
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

        Number[] coffees1 = {2, 15, 30, 33,  22, 16,   0,   0,    0,   0,   5,  17,  41,  33,  24,   2,    0,    0,   0};
        Number[] xValues = {8, 8.5, 9, 9.5, 10, 10.5, 11, 11.5, 12, 12.5, 13, 13.5, 14, 14.5, 15, 15.5, 16, 16.5, 17};


        XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(xValues),          	// SimpleXYSeries takes a List so turn our array into a List
                Arrays.asList(coffees1), 			// Y_VALS_ONLY means use the element index as the x value
                "Day1");                            // Set the display title of the series

        LineAndPointFormatter series1Format = new LineAndPointFormatter(
                Color.GRAY,                   		// line color
                Color.BLACK,                   		// point color
                null,                               // fill color (none)
                null);                           	// text color


        freqeuncyPlot.addSeries(series1, series1Format);
	}
	
}
