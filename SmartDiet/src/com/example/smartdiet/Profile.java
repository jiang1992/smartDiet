package com.example.smartdiet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

import android.content.Context;
import android.util.Log;

public class Profile {
	
	public enum amtOfActivity {
		NONE,
		LITTLE,
		MODERATE,
		EXTENSIVE,
		ALWAYS
	}
	
	public enum genderType {
		BOY,
		GIRL
	}
	
	public final static String[] PROPERTY_KEYS = {"Gender", "Age", "Height", "Weight", "Body Type", "Amount of Activity"};
	public final static String GENDER = "Gender", AGE = "Age", HEIGHT = "Height", WEIGHT = "Weight", 
								BODY_TYPE= "Body Type", AMOUNT_OF_ACTIVITY = "Amount of Activity";
	
	private Map<String, Integer> properties = new HashMap<String, Integer>();
	private boolean firstTime;
	
	private DailyObject daily;
	
	public Profile() {
		this(0,0,0,0,0,0);
		firstTime = true;
	}
	
	public Profile(int gender, int age, int height, int weight, int bodyType, int amountOfActivity//, String dietType) {
		properties.put(GENDER, gender);
		properties.put(AGE, age);
		properties.put(HEIGHT, height);
		properties.put(WEIGHT, weight);
		properties.put(BODY_TYPE, bodyType);
		properties.put(AMOUNT_OF_ACTIVITY, amountOfActivity);
		firstTime = false;
		DailyObject daily = new DailyObject();
	}
	
	public boolean isFirstTimeUser() {
		return firstTime;
	}
	
	public int getHeight() {
		return properties.get("Height");
	}
	
	public int getGender() {
		return properties.get("Gender");
	}
	
	public int getAge() {
		return properties.get("Age");
	}
	
	public int getWeight() {
		return properties.get("Weight");
	}
	
	public int getBodyType() {
		return properties.get("Body Type");
	}
	
	public int getAmountofActivity() {
		return properties.get("Amount of Activity");
	}
	
	public int getCaloriesToday() {
		return (int)daily.getNutritionalProperty("calories");
	}
	
	public Map<String, Integer> getProperties() {
		return properties;
	}
	
	public float getBMI() {
		return Calculator.calculateBMI(properties.get("Weight"), properties.get("Height"));
	}
	
	public float getCaloriesPerDay() {
		return Calculator.calculateCaloriesPerDay(getWeight(), getHeight(), getAge(), getGender());
	}
	
	public DailyObject getDailyObject(){
		return daily;
	}
	
	public void setDailyObject(DailyObject daily){
		this.daily = daily;
	}
	
	public void setProperties(Map<String, Integer> prop) {
		properties = prop;
	}
	
	public void setFirstTimeUser(boolean val){
		firstTime = val;
	}
	
	public void setCaloriesToday(int calories) {
		daily.setNutritionalProperty("calories", (double)calories);
	}
	
	public void resetCaloriesToday() {
		daily.setNutritionalProperty("calories", 0);
	}
	
	public void consumeFood(Food food){
		if(food == null){}
		else{
			int calories = (int)(food.getNutritionalProperty("calories")*food.getServings());
			double calories_today = daily.getNutritionalProperty("calories");
			calories_today = calories_today + calories;
			daily.setNutritionalProperty("calories", calories_today);
			int percentage = (int)((calories_today/(double)(getCaloriesPerDay()))*100);
			daily.setNutritionalPercentage(percentage);
			//MainActivity.updateProgress(percentage);
			ConsumeFragment.updateProgress(percentage);
			//MainActivity.updateLastestFood(food);
			ConsumeFragment.updateLastestFood(food);
			saveProfile(MainActivity.context);
		}
	}
	
	/* Save profile on Internal Storage */
	public void saveProfile(Context context){
		String filename = "profile";
		FileWriter fw;
		
		File file = new File(context.getFilesDir(), filename);
		Log.i("Profile", context.getFilesDir().toString());
		String profile = toString();
		Log.i("Profile", profile);
		
		try{
			fw = new FileWriter(file, false);
			fw.write(profile);
			fw.close();
			Log.i("Profile", "Profile successfully saved");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Load profile from Internal Storage
	 * Return false if profile exists, true if it does not (for first time user) 
	 */
	public boolean loadProfile(Context context){
		String filename = "profile";
		String propname = "", propvalue = ""; //Property name and property value
		Profile user = GlobalController.getSharedUser();
		StringBuffer buf = new StringBuffer("");
		Map<String, Integer> propmap = new HashMap<String, Integer>();  //Property map for profile
		FileInputStream in;
		byte c;
		
		/* Load today's daily object */
		String date = Helpers.getTodaysDate();
		Log.i("Load Profile", "Today's date is " + date);
		daily = MainActivity.getDatabase().getDailyObject(date);
		
		
		if(daily == null){
			Log.i("dailyObject", "null");
			daily = new DailyObject();
		}else{
			Log.i("dailyObject", daily.toString());
		}
		
		try{
			in = context.openFileInput(filename);
			try{
				while( (c = (byte) in.read()) != -1){
					char ch = (char)c;
					if(ch == ':'){  //Colon encountered
						propname = new String(buf);
						Log.i("propname", propname);
						buf = new StringBuffer("");
					}
					else if(ch == '\n'){  //Newline encountered
						propvalue = new String(buf);
						propvalue = propvalue.substring(1);
						if(propvalue.equals("null")){
							propvalue = "2";
						}
						Log.i("propvalue", propvalue);
						
						//Parsing this line
						if(propname.equals("First Time")){
							boolean value = Boolean.parseBoolean(propvalue);
							user.setFirstTimeUser(value);
						}else{
							Integer value = Integer.parseInt(propvalue);
							propmap.put(propname, value);
						}
						
						propname = "";
						propvalue = "";
						buf = new StringBuffer("");
					}else{
						buf.append(ch);
					}
					
				}
				
				user.setProperties(propmap);
				
				Log.i("Load Profile", user.toString());
				int percentage = (int)((getCaloriesToday()/getCaloriesPerDay())*100);
				//MainActivity.updateProgress(percentage);
				
			}catch(IOException e){
				e.printStackTrace();
			}
		}catch(FileNotFoundException e){
			return true;
		}
		return false;
	}
	
	/**
	 * Wipe the old and create a new daily object
	 */
	public void newDailyObject(){
		/* Save the old daily object into the database */
		MainActivity.getDatabase().insertDailyObject(daily);
		DailyObject new_daily = new DailyObject();
		daily = new_daily;
	}
	
	public String toString() {
		String s = "";
		for (int i = 0; i < properties.size(); i++)
		{
			s += PROPERTY_KEYS[i] + ": " + properties.get(PROPERTY_KEYS[i]) + "\n"; 
		}
		s += "First Time: " + Boolean.toString(firstTime) + "\n";
		return s;
	}
}
