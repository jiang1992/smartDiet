package com.example.smartdiet;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import android.util.Log;

public class Food {
  
  protected String portionType;
  protected double servings;
  protected String name;
  protected String group;
  protected Map<String, Double> nutr_map = new HashMap<String, Double>();
  
  public final static String[] KEYS = {"calories", "fat", "cholesterol", "sodium", "potassium", "carbohydrates", "protein"};
  public final static String[] DB_KEYS = {"208", "204", "601", "307", "306", "205", "203"};
  
  //Initialize an 'empty' Food object for custom entries
  public Food()
  {
    portionType = "";
    servings = 0;
    name = "";
    group = "";
    
    for(int i=0; i<KEYS.length; i++){
    	nutr_map.put(KEYS[i], 0.0);
    }
  }
  
//Initialize a Food object with the given parameters
  public Food(String portionType, double servings, String name, String group, Map<String, Double> nutr)
  {
    this.portionType = portionType;
    this.servings = servings;
    this.name = name;
    this.group = group;
    this.nutr_map = nutr;
  }
  
//Individual setters
  
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public void setPortionType(String setPortionType)
  {
    this.portionType = setPortionType;
  }
  
  public void setServings(double servings)
  {
    this.servings = servings;
  }
  
  public void setGroup(String group)
  {
	  this.group = group;
  }
  
  public void setNutritionalProperty(String property, double value){
	  if (Arrays.asList(KEYS).contains(property))
	  {
		  nutr_map.put(property, value);
	  }else{
		  Log.e("nutr_map", "Does not contain such property!");
	  }
  }
  
  public void setNutritionalMap(Map <String, Double> map){
	  this.nutr_map = map;
  }
  
  
//Getters
  
  public String getPortionType()
  {
    return portionType;
  }
  
  public String getName()
  {
    return name;
  }
  
  public double getServings()
  {
    return servings;
  }
  
  public String getGroup()
  {
	  return group;
  }
  
  public double getNutritionalProperty(String property){
	  if (Arrays.asList(KEYS).contains(property))
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
  
//Other methods
  
//Servings inc/dec
  public void incServings(double increase)
  {
    servings += increase;
  }
  
  //returns -1 if attempting to remove
  //more servings then there are left
  public int decServings(double decrease)
  {
    if(servings < decrease) return -1;
    servings -= decrease;
    return 0;
  }

  /**
   * toString method
   * @return complete info about the food
   */
  public String toString()
  {
	  StringBuilder sb = new StringBuilder();
	  String name = "Name: " + getName();
	  String portionType = "Portion Type: " + getPortionType();
	  String servings = "Servings: " + getServings();
	  //calories
	  
	  sb.append(name + "\n");
	  sb.append(portionType + "\n");
	  sb.append(servings + "\n");
	  
	  return new String(sb);
  }
  
}//Food