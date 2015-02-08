package com.example.smartdiet;

import java.util.*;

import android.util.Log;

//NOTE: I can see where some problems may arise due to how Java
//handles object passing, depending on how we implement
//the rest of the app. May need to make an internal copy function here or
//in Food (probably the latter) to make everything work.

public class Meal extends Food{
  
  protected Map<String,Food> ingredients; //Hash map keyed by Food name
//Also has Food's variables, used as combined total
  
  public Meal(String mealName)
  {
    super();
    ingredients = new HashMap<String,Food>();
    this.name = mealName;
  }
  
//Add one or more serving(s) of Food to the Meal, and add Food's nutritional values to Meal
  public void addFood(Food ing)
  {
	Log.i("addFodd", ing.toString());
    String ingName = ing.getName();
    
    //Insert the food to the Map if it is not already present
    if(!ingredients.containsKey(ingName))
    {
      ingredients.put(ingName, ing);
    }
    else //update the number of servings in the map
    {
      Food servUpdate = (Food) ingredients.get(ingName);
      servUpdate.setServings(servUpdate.getServings() + ing.getServings());
    }
    
    //Add nutritional values
    
    //get the number of servings, which acts as a multiplyer for other values
    double ingServ = ing.getServings();
    
    double calories = getNutritionalProperty("calories");
    calories += Math.round(ing.getNutritionalProperty("calories") * ingServ);
    setNutritionalProperty("calories", calories);
  }
  
  //TODO: Modify to remove all servings
  
//Remove all serving of Food from the meal, and remove nut. values
//Returns -1 if the given ingredient was not found, or 0 if it was
  public int removeFood(String ingName)
  {
    Food checker = (Food) ingredients.get(ingName);
    if(checker == null) return -1;
    else 
    {
      //Get servings
      double ingServ = checker.getServings();
      //Subtract nut. values
      double calories = getNutritionalProperty("calories");
      calories -= Math.round(checker.getNutritionalProperty("calories") * ingServ);
      setNutritionalProperty("calories", calories);
      
      ingredients.remove(ingName);
      return 0;
    }
  }
  
//Returns an array containing all foods in the Map
//This array does not change when new foods are added or
//removed
  public Food[] getFoods()
  {
    //Collection<>.toArray will create a new array of type Food
    //if need be, but needs this as 'prototype'
    Food[] foods = new Food[1];
    
    Collection<Food> ingColl = ingredients.values();
    foods = ingColl.toArray(foods);
    
    return foods;
  }
}