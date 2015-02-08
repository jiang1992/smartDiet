package com.example.smartdiet;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.Context;
import android.content.Intent;

import java.util.List;
import java.util.ArrayList;

public class QuickEnter extends ListActivity {
	
	//public static FoodDatabase food_db;
	//private static List<String> food_list = new ArrayList<String>();
	//private static ArrayAdapter<String> list_adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_meals);
		
		/* On launch open the database */
		System.out.println("display_meals");
	//	food_db = MainActivity.getDatabase();
	//	ListView list = (ListView) findViewById(android.R.id.list);
	//	list_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, food_list);
	//	list.setAdapter(list_adapter);
	//	food_list = food_db.getAllFoods();
	//	System.out.println("food_list size" + String.valueOf(food_list.size()));
	//	for(String food : food_list) {
    //        System.out.println(food);
    //    }
	//	list_adapter.notifyDataSetChanged();
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
  //      switch (item.getItemId()) {
 //       case android.R.id.home:
   //         NavUtils.navigateUpFromSameTask(this);
  //          return true;
    //    }
        return super.onOptionsItemSelected(item);
	}

}
