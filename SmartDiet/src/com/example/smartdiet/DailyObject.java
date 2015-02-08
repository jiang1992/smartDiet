package com.example.smartdiet;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

public class DailyObject{
	
	private String date;	// "YYYY-MM-DD" format
	private Map<String, Double> nutr_map = new ArrayMap<String, Double>();
	private int caloric_perc;
	
	
	/**
	 *  New daily object with today's date and empty attributes 
	 *  */
	public DailyObject(){
		date = Helpers.getTodaysDate();
		caloric_perc = 0;
		for(int i = 0; i<Food.KEYS.length; i++){
			nutr_map.put(Food.KEYS[i], 0.0);
		}
	}
	
	/**
	 * New daily object with specified date and empty attributes
	 * @param date
	 */
	public DailyObject(String date){
		this.date = date;
		caloric_perc = 0;
		for(int i = 0; i<Food.KEYS.length; i++){
			nutr_map.put(Food.KEYS[i], 0.0);
		}
	}
	
	public void setDate(String date){
		this.date = date;
	}
	
	public void setNutritionalProperty(String property, double value){
		  if (Arrays.asList(Food.KEYS).contains(property))
		  {
			  nutr_map.put(property, value);
		  }else{
			  Log.e("nutr_map", "Does not contain such property!");
		  }
	  }
	  
	  public void setNutritionalMap(Map <String, Double> map){
		  this.nutr_map = map;
	  }
	  
	  public void setNutritionalPercentage(int caloric_perc){
		  this.caloric_perc = caloric_perc;
	  }
	  
	  public double getNutritionalProperty(String property){
		  if (Arrays.asList(Food.KEYS).contains(property))
		  {
			  return nutr_map.get(property);
		  }else{
			  Log.e("nutr_map", "Does not contain such property!");
			  return -1;
		  }
	  }
	  
	  public Map<String, Double> getNutritionalMap(){
		  return nutr_map;
	  }
	  
	  public String getDate(){
		  return date;
	  }
	  
	  public int getNutritionalPercentage(){
		  return caloric_perc;
	  }
	  
	  /**
	   * Method to check if the date has changed
	   * @return
	   */
	  public static boolean dateHasChanged(){
		  String last_date = GlobalController.getSharedUser().getDailyObject().getDate();
		  String now_date = Helpers.getTodaysDate();
		  if(last_date.equals(now_date)){
			  return false;
		  }else{
			  return true;
		  }
	  }
}
