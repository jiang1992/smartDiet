package com.example.smartdiet;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CustomMeals extends Activity {
	
	public static FoodDatabase food_db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		food_db = MainActivity.getDatabase();
		final Meal meal;
		
		EditText mealName = (EditText) findViewById(R.id.CustMealNameEdit);
		meal = new Meal(mealName.getText().toString());
		
		final EditText calories = (EditText) findViewById(R.id.CustCaloriesEdit);
		final EditText carbs = (EditText)	findViewById(R.id.CustCarbsEdit);
		final EditText fats = (EditText)	findViewById(R.id.CustFatsEdit);
		final EditText proteins = (EditText) findViewById(R.id.CustProteinsEdit);
		
		meal.setNutritionalProperty("calories", Double.parseDouble((calories.getText().toString())));
		meal.setNutritionalProperty("carbohydrates", Double.parseDouble((carbs.getText().toString())));
		meal.setNutritionalProperty("fat", Double.parseDouble((fats.getText().toString())));
		meal.setNutritionalProperty("protein", Double.parseDouble((proteins.getText().toString())));

		Button finishB = (Button) findViewById(R.id.CustFinishButton);
		finishB.setOnClickListener(new OnClickListener(){
			
			public void onClick (View view)
			{
					food_db.addMeal(meal);
					Toast.makeText(getApplicationContext(), "Meal Added to Database", Toast.LENGTH_SHORT).show();
	
					calories.setText("");
					carbs.setText("");
					fats.setText("");
					proteins.setText("");
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
