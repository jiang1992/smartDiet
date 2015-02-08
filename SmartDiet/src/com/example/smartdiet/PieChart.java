package com.example.smartdiet;
import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.Toast;

public class PieChart {
	
	
	public Intent getIntent(Context context, int[] variables, String[] labels) {
		if(  variables.length != labels.length){
			Toast.makeText(context,  "Wrong inputs!", 0).show();      
			return null;
		}
		int[] values = { 1, 2 ,0,0,0,0,0,0,0,0,0,0,0};
		int i = 0;
		for(i = 0; i < variables.length;i++){
			values[i] = variables[i];
		}
		
		for(int j = i; j < values.length; j++){
			values[j] = 0;
		}		
		CategorySeries series = new CategorySeries("Pie Graph");
		int k = 0;
		for (int value : variables) {
			series.add(labels[k], value);
			k++;
		}
			
		int[] colors = new int[] { Color.BLUE, Color.GREEN, Color.MAGENTA, Color.YELLOW, Color.CYAN , Color.LTGRAY, Color.RED, Color.DKGRAY, Color.LTGRAY, Color.WHITE};
		int[] colorTemp = new int[variables.length];
		for(int j = 0; i < variables.length; j++){
			colorTemp[j] = colors[j];
		}
		k = 0;
		DefaultRenderer renderer = new DefaultRenderer();
		
		for (int color : colorTemp) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(colors[k]);
			k++;
			renderer.addSeriesRenderer(r);
		}
		renderer.setChartTitle("Pie Chart Demo bitch");
		renderer.setChartTitleTextSize(7);
		renderer.setZoomButtonsVisible(true);
		
		Intent intent = ChartFactory.getPieChartIntent(context, series, renderer, "Pie ");
		return intent;
	}
}

