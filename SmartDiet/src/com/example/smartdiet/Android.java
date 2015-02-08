package com.example.smartdiet;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Android extends Fragment {
	@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
		
			
		int[] values = { 1, 2, 3, 4, 5 };
		CategorySeries series = new CategorySeries("Pie BABY");
		int k = 0;
		for (int value : values) {
			series.add("Section " + ++k, value);
		}

		int[] colors = new int[] { Color.BLUE, Color.GREEN, Color.MAGENTA, Color.YELLOW, Color.CYAN };

		DefaultRenderer renderer = new DefaultRenderer();
		for (int color : colors) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(color);
			renderer.addSeriesRenderer(r);
		}
		renderer.setChartTitle("Pie Chart Demo BABY");
		renderer.setChartTitleTextSize(7);
		renderer.setZoomButtonsVisible(true);
		//ChartFactory.get
		View intent = ChartFactory.getPieChartView(getActivity().getApplicationContext(), series, renderer);
		return intent;
		
	}
}
