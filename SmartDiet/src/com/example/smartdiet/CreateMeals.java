package com.example.smartdiet;

import android.os.Bundle;
import android.app.Activity;
import android.app.ActionBar;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class CreateMeals extends Activity {

	private TextView calorieCount;
	private EditText foodName, foodServings, mealName;
	private ListView foodList;
	private Spinner portionTypes;
	private ArrayList<String> portionTypesSource;
	private ArrayAdapter<String> portionTypesContents;
	private ArrayList<Food> foodListSource;
	private ArrayAdapter<Food> foodListContents;
	private Meal mealBuilder;
	public static FoodDatabase food_db;
	private Button searchB, addB, finishB;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_create_meals);	
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);	

		mealBuilder = new Meal("null");
		
		calorieCount = (TextView) findViewById(R.id.mealCalorieCount);
		calorieCount.setText("0");
		
		//Disable the foodServings on launch
		foodServings = (EditText) findViewById(R.id.servingCountText);
		foodServings.setEnabled(false);
		foodServings.setClickable(false);
		foodServings.setFocusable(false);
		foodServings.setFocusableInTouchMode(false);
		
		foodName = (EditText) findViewById(R.id.foodNameText);
		mealName = (EditText) findViewById(R.id.mealNameEnter);
		foodList = (ListView) findViewById(R.id.listView1);
		
		foodListSource = new ArrayList<Food>();
		foodListContents = new ArrayAdapter<Food>(this, android.R.layout.simple_list_item_1, foodListSource);
		foodList.setAdapter(foodListContents);
		
		portionTypes = (Spinner) findViewById(R.id.servingTypeSpinner);
		
		portionTypesSource = new ArrayList<String>();
		portionTypesContents = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, portionTypesSource);
		portionTypesContents.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		portionTypes.setAdapter(portionTypesContents);
		
		food_db = MainActivity.getDatabase();
		
		searchB = (Button) findViewById(R.id.createSearchB);
		searchB.setOnClickListener(new OnClickListener(){
			
			public void onClick (View view){
			//Get food name
			String foodSearched = foodName.getText().toString();
			
			portionTypesSource.clear();
			try{
				//Get portion types from food_db and apply to portionTypesSource
				portionTypesSource.addAll(food_db.getPortionTypes(foodSearched));
				portionTypesContents.notifyDataSetChanged();
			}catch(Exception e){
				Log.i("createSearchButton","Error in database method getPortionTypes.");
				return;
			}
			
			//Activate servings entry box
			foodServings.setEnabled(true);
			foodServings.setClickable(true);
			foodServings.setFocusable(true);
			foodServings.setFocusableInTouchMode(true);
			
			Toast.makeText(getApplicationContext(), "Food found, please enter number of servings and select serving type.", Toast.LENGTH_LONG).show();
			}
		});
		
		addB = (Button) findViewById(R.id.createAddB);
		addB.setOnClickListener(new OnClickListener(){
			
			public void onClick(View view){
				//Get data
				double servings = 0;
				String portion = "";
				String foodSearched = "";
				try{			
					foodSearched = foodName.getText().toString();
					servings = Double.parseDouble(foodServings.getText().toString());
					portion = ((String)portionTypes.getSelectedItem()).toString();
				}catch(NumberFormatException e){
					//servings is not a number, abort
					return;
				}
				//Make sure something was entered, otherwise aout
				if(foodSearched.equals("") || portion.equals("") || servings == 0){
					return;
				}
				
				//Get Food from food_db using data
				Food foodRetrieved = food_db.getFood(foodSearched, portion, servings);
				
				//Add to the Meal/ListView
				mealBuilder.addFood(foodRetrieved);
				foodListSource.add(foodRetrieved);
				foodListContents.notifyDataSetChanged();
				
				//Clear text boxes
				foodName.setText("");
				foodServings.setText("");
				foodServings.setEnabled(false);
				foodServings.setClickable(false);
				foodServings.setFocusable(false);
				foodServings.setFocusableInTouchMode(false);
				
				calorieCount.setText(((Double)mealBuilder.getNutritionalProperty("calories")).toString());
				//Clear Spinner
				portionTypesSource.clear();
				portionTypesContents.notifyDataSetChanged();
				Toast.makeText(getApplicationContext(), "Food added to Meal", Toast.LENGTH_SHORT).show();
			}
		});
		
		finishB = (Button) findViewById(R.id.createFinishB);
		finishB.setOnClickListener(new OnClickListener(){
			
			public void onClick (View view)
			{
				//Add final details to mealBuilder
				mealBuilder.setName(mealName.getText().toString());
				
				//Ensure there is something in mealBuilder
				if(mealName.getText().toString().equals("") || mealBuilder.getNutritionalProperty("calories") == 0)
				{
					return;
				}
				
				//Add meal to food_db
				Log.i("addMeal", mealBuilder.toString());
				food_db.addMeal(mealBuilder);
				
				
				//Clear text boxes
				foodName.setText("");
				foodServings.setText("");
				foodServings.setEnabled(false);
				foodServings.setClickable(false);
				foodServings.setFocusable(false);
				foodServings.setFocusableInTouchMode(false);
				
				//Clear Spinner
				portionTypesSource.clear();
				portionTypesContents.notifyDataSetChanged();
				
				//Clear foodList
				foodListSource.clear();
				foodListContents.notifyDataSetChanged();
				Toast.makeText(getApplicationContext(), "Meal added to database.", Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
	}

}
