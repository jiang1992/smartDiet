package com.example.smartdiet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**  
 * DOA class for food database. Author: Hieu Tran 
 * */
public class FoodDatabase {
	
	private SQLiteDatabase db;
	private FoodDatabaseHelper foodDB_open;
	
	public FoodDatabase(Context context){
		foodDB_open = new FoodDatabaseHelper(context);
		foodDB_open.createDataBase();
		foodDB_open.openDataBase();
	}
	
	/** 
	 * Open the food database 
	 * */
	public boolean open(){
		try{
			db = foodDB_open.getWritableDatabase();
			return true;
		}catch(SQLException e){
			return false;
		}
	}
	
	/** 
	 * Close the food database */
	public void close(){
		foodDB_open.close();
	}
	
	
	/** 
	 * Get all of the foods from the database */
	public List<String> getAllMeals(){
		List<String> food_list = new ArrayList<String>();
		
		Cursor c = db.rawQuery("SELECT name FROM public_meals_data", null);
		
		System.out.println("Getting foods " + String.valueOf(c.getCount()));
		c.moveToFirst();
		int i = 1;
		while(!c.isAfterLast()){
			System.out.println("getting food # " + String.valueOf(i));
			String food = c.getString(0);
			food_list.add(food);
			c.moveToNext();
			i++;
		}
		
		return food_list;
	}
	
	public List<String> getSuggestedMeals(){
		List<String> food_list = new ArrayList<String>();
		Profile user = GlobalController.getSharedUser();
		float maxCal = user.getCaloriesPerDay();
		int curCal = user.getCaloriesToday(); 
		
		float remainCal = maxCal - curCal;
		
		if(remainCal <= 0)
		{
			return getAllMeals();
		}
		
		Cursor c = db.rawQuery("SELECT name FROM public_meals_data WHERE calories < " + String.valueOf(remainCal), null);
		
		System.out.println("Getting foods " + String.valueOf(c.getCount()));
		c.moveToFirst();
		int i = 1;
		while(!c.isAfterLast()){
			System.out.println("getting food # " + String.valueOf(i));
			String food = c.getString(0);
			food_list.add(food);
			c.moveToNext();
			i++;
		}
		
		return food_list;
	}
	
	/** 
	 * Get all of the foods from a specified group 
	 * */
	public List<String> getFoodsByGroup(String group)
	{
		// TODO
		return null;
	}
	
	/* Get all of the foods within the specified range of calories */
	public List<String> getFoodsByCalories(int min_cal, int max_cal){
		/*List<String> food_list = new ArrayList<String>();
		
		Cursor c = db.rawQuery("select " + FoodDatabaseHelper.COLUMN_NAME + " from " + FoodDatabaseHelper.FOODS + 
					" where " + FoodDatabaseHelper.COLUMN_CALORIES + " >= ? AND " +
					FoodDatabaseHelper.COLUMN_CALORIES + " <= ? ;", new String[] { String.valueOf(min_cal), String.valueOf(max_cal) });
		
		c.moveToFirst();
		while(!c.isAfterLast()){
			String food = c.getString(0);
			food_list.add(food);
			c.moveToNext();
		}
		
		return food_list;*/
		return null;
	}
	
	/**
	 * Get nutritional information for food with specified id
	 * DO NOT use this method to select nutritional info for meals
	 */
	public Map<String, Double> getNutritionalInfo(String ndb_no){
		Map<String, Double> nutr_map = new HashMap<String, Double>();
		
		int length = Food.KEYS.length;
		String sql = "SELECT Nutr_Val FROM public_nut_data WHERE NDB_No = ? AND Nutr_No = ? ;";
		for(int i = 0; i<length; i++){
			String nutr_no = Food.DB_KEYS[i];
			Cursor c = db.rawQuery(sql, new String[]{ndb_no, nutr_no});
			c.moveToFirst();
			double value = c.getDouble(0);
			nutr_map.put(Food.KEYS[i], value);
		}
		
		return nutr_map;
	}
	
	/**
	 *  Get food by unique name 
	 *  */
	public Food getFoodByName(String name){
		try{
			Cursor c = db.rawQuery("SELECT * FROM public_food_des WHERE Long_Desc = ? ;" , new String[] { name });
			c.moveToFirst();
			String ndb_no = c.getString(0);
			
			Food food = new Food();
			food.setServings(1);
			food.setName(name);
			
			Log.i("getFoodByName", "nbd_no is " + ndb_no);
			
			/**
			 * Get all of the nutritional information
			 */
			Map<String, Double> nutr_map = getNutritionalInfo(ndb_no);
			Log.i("getFoodByName", nutr_map.toString());
			food.setNutritionalMap(nutr_map);
			
			return food;
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Get meal by unique name
	 */
	public Meal getMealByName(String name){
		Meal meal = new Meal("");
		meal.setServings(1);
		try{
			Cursor c = db.rawQuery("SELECT calories, fat, cholesterol, sodium, potassium, carbohydrates, protein FROM public_meals_data WHERE name = ? ;" , new String[] { name });
			c.moveToFirst();
			
			/**
			 * Get all of the nutritional information
			 */
			Map<String, Double> nutr_map = new HashMap<String, Double>();
			for(int i = 0;i<Food.KEYS.length; i++){
				nutr_map.put(Food.KEYS[i], c.getDouble(i));
			}
			
			meal.setNutritionalMap(nutr_map);
			meal.setName(name);
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
		
		return meal;
	}
	
	/**
	 * Get either ingredient food or meal by the unique name
	 */
	public Food getFood(String name){
		Food food = new Food();
		if(name.contains(",")){ /* It's an ingredient food */
			food = getFoodByName(name);
		}else{	/* It's a meal */
			food = getMealByName(name);
		}
		return food;
	}
	
	/**
	 * Get all the food that contain the string
	 * @param name - string to search foods by
	 * @return array of foods
	 */
	public ArrayList<String> searchFoods(String name){
		
		ArrayList<String> foods = new ArrayList<String>();
		/* Search the food array */
		String sql = ("SELECT Long_Desc FROM public_food_des WHERE Long_Desc LIKE ? ;");
		String searchName = "%" + name + "%";
		Cursor c = db.rawQuery(sql, new String[] { searchName });
		c.moveToFirst();
		while(!c.isAfterLast()){
			String foodName = c.getString(0);
			foods.add(foodName);
			c.moveToNext();
		}
		
		/*Search the meals array */
		sql = ("SELECT name FROM public_meals_data WHERE name LIKE ? ;");
		c = db.rawQuery(sql, new String[] { searchName });
		c.moveToFirst();
		while(!c.isAfterLast()){
			String foodName = c.getString(0);
			foods.add(foodName);
			c.moveToNext();
		}
		
		return foods;
	}
	
	/**
	 * Get portion types for food
	 * 
	 */
	public ArrayList<String> getPortionTypes(String name){
		ArrayList<String> portionTypes = new ArrayList<String>();
		Log.i("get_portion_types", name);
		if(!name.contains(",")){	//It's a meal, the only portion type is "meal"
			portionTypes.add("meal");
		}else{
			
			Cursor c = db.rawQuery("SELECT NDB_No FROM public_food_des WHERE Long_Desc = ? ;", new String[] { name });
			c.moveToFirst();
			String ndb_no = c.getString(0);
			
			String sql = "SELECT Msre_Desc FROM public_weight WHERE NDB_No = ? ;";
			c = db.rawQuery(sql, new String[] { ndb_no });
			
			c.moveToFirst();
			while(!c.isAfterLast()){
				String type = c.getString(0);
				portionTypes.add(type);
				c.moveToNext();
			}
			
			portionTypes.add("100g");
		}
		return portionTypes;
	}
	
	/** 
	 * Get Food with serving and portionType 
	 * */
	public Food getFood(String name, String portionType, double servings){
		
		Food food = getFood(name);
		food.setPortionType(portionType);
		
		if(portionType.equals("meal")){  //It's a meal
			
			Map<String, Double> nutr_map = food.getNutritionalMap();
			Map<String, Double> temp_map = new HashMap<String, Double>();
			Iterator<Entry<String, Double>> it = nutr_map.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry<String, Double> pairs = (Map.Entry<String, Double>) it.next();
				String key = pairs.getKey();
				double value = pairs.getValue();
				value = value*servings;
				value = Helpers.round(value, 2);
				temp_map.put(key, value);
			}
			
			nutr_map = temp_map;
			
		}else if(portionType.equals("100g")){
			// Do nothing, everything is up-to-scale
		}
		else{
			double factor = 1;
			Cursor c = db.rawQuery("SELECT NDB_No FROM public_food_des WHERE Long_Desc = ? ;", new String[] { name });
			c.moveToFirst();
			String ndb_no = c.getString(0);
			
			String sql = "SELECT Gm_Wgt FROM public_weight WHERE NDB_No = ? AND Msre_Desc = ? ;";
			c = db.rawQuery(sql, new String[] { ndb_no, portionType });
			c.moveToFirst();
			double weight = c.getDouble(0);
			factor = factor*(weight/100.0)*servings;
			
			Map<String, Double> nutr_map = food.getNutritionalMap();
			Map<String, Double> temp_map = new HashMap<String, Double>();
			Iterator<Entry<String, Double>> it = nutr_map.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry<String, Double> pairs = (Map.Entry<String, Double>) it.next();
				String key = pairs.getKey();
				double value = pairs.getValue();
				value = value*factor;
				value = Helpers.round(value, 2);
				temp_map.put(key, value);
			}
			
			nutr_map = temp_map;
			food.setNutritionalMap(nutr_map);
		}
		
		return food;
	}
	
	/**
	 *  Add Meal to the Database 
	 *  */
	public void addMeal(Meal meal){
		ContentValues values = new ContentValues();
		values.put("name", meal.getName());
		values.put("calories", meal.getNutritionalProperty("calories"));
		values.put("fat", meal.getNutritionalProperty("fat"));
		values.put("cholesterol", meal.getNutritionalProperty("cholesterol"));
		values.put("sodium", meal.getNutritionalProperty("sodium"));
		values.put("potassium", meal.getNutritionalProperty("potassium"));
		values.put("carbohydrates", meal.getNutritionalProperty("carbihydrates"));
		values.put("protein", meal.getNutritionalProperty("protein"));
		
		db.insert("public_meals_data", null, values);
	}
	
	/**
	 * 
	 * @param date in "YYYY-MM-DD" format
	 * @return dailyObject
	 */
	public DailyObject getDailyObject(String date){
		DailyObject daily = new DailyObject();
		if(Helpers.isValidDate(date)){
			Cursor c = db.rawQuery("SELECT * FROM public_daily_data WHERE date = ? ;", new String[] { date });
			c.moveToFirst();
			if(!c.isAfterLast()){
				daily.setDate(c.getString(0));
				daily.setNutritionalPercentage(c.getInt(1));
				daily.setNutritionalProperty("fat", c.getDouble(2));
				daily.setNutritionalProperty("cholesterol", c.getDouble(3));
				daily.setNutritionalProperty("sodium", c.getDouble(4));
				daily.setNutritionalProperty("potassium", c.getDouble(5));
				daily.setNutritionalProperty("carbohydrates", c.getDouble(6));
				daily.setNutritionalProperty("protein", c.getDouble(7));
				daily.setNutritionalProperty("calories", c.getDouble(8));
			}else{
				return null;
			}
		}else{
			return null;
		}
		
		return daily;
	}
	
	/**
	 * Insert daily object into the database
	 */
	public void insertDailyObject(DailyObject daily){
		ContentValues values = new ContentValues();
		values.put("date", daily.getDate());
		values.put("caloric_perc", daily.getNutritionalPercentage());
		for(int i = 0; i<Food.KEYS.length; i++){
			values.put(Food.KEYS[i], daily.getNutritionalProperty(Food.KEYS[i]));
		}
		
		db.insert("public_daily_data", null, values);
	}

}
