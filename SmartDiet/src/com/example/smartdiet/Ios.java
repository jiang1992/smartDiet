package com.example.smartdiet;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Ios extends Fragment {
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {

			int[] x = { 1, 2, 3, 4, 5, 6, 7 }; 
			int[] y =  { 100, 34, 45, 57, 77, 89, 100}; 
			TimeSeries series = new TimeSeries("WEEKLY HISTORY"); 
			for( int i = 0; i < x.length; i++)
			{
				series.add(x[i], y[i]);
			}
			
			
			
			XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
			dataset.addSeries(series);
				
			XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer(); // Holds a collection of XYSeriesRenderer and customizes the graph
			XYSeriesRenderer renderer = new XYSeriesRenderer(); // This will be used to customize line 1
			
			mRenderer.addSeriesRenderer(renderer);
			mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00));
			//mRenderer.addSeriesRenderer(renderer2);
			
			// Customization time for line 1!
			renderer.setColor(Color.BLACK);
			renderer.setPointStyle(PointStyle.SQUARE);
			renderer.setFillPoints(true);
			
		    View view = ChartFactory.getLineChartView(getActivity().getApplicationContext(), dataset,mRenderer);
		   // ChartFactory.getB
			//Intent intent = ChartFactory.getLineChartIntent(getActivity().getC, dataset, mRenderer, "Line Graph Title");
		//	View intent = ChartFactory.getLineChartView(getActivity().getApplicationContext(), dataset, mRenderer);
			return view;
	        
		 
		 
	 }
}
