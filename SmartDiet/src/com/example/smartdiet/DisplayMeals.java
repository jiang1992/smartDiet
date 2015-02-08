package com.example.smartdiet;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActionBar;
import android.app.ListActivity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;

import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

public class DisplayMeals extends ListActivity {
	
	public static FoodDatabase food_db;
	private static List<String> food_list = new ArrayList<String>();
	private static ArrayAdapter<String> list_adapter;
	
	private EditText et;
	private int textlength;
	private List<String> array_sorted = new LinkedList<String>();
	
	private ListView list; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_meals);
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		
		System.out.println("display_meals");
		food_db = MainActivity.getDatabase();
		food_list = food_db.getAllMeals();
		list = (ListView) findViewById(android.R.id.list);
		list_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, food_list);
		list.setAdapter(list_adapter);
		System.out.println("food_list size" + String.valueOf(food_list.size()));
		
		for(String food : food_list) {
			
            System.out.println(food);
            System.out.println(GlobalController.getSharedUser().getAge());
        }
		
		
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				
				String food_name = ((TextView)view).getText().toString();
				Log.i("DisplayMeals", food_name);
				Food food = food_db.getMealByName(food_name);
				food.setServings(1);
				Profile user = GlobalController.getSharedUser();
				user.consumeFood(food);
				Toast.makeText(getApplicationContext(), "Redirecting...", 0).show();    
				finish();
			}
			
		});
		et = (EditText) findViewById(R.id.editText1);
		et.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				array_sorted.clear();
				textlength = et.getText().length();
				if( textlength == 0){
					list.setAdapter(new ArrayAdapter<String>(DisplayMeals.this,android.R.layout.simple_list_item_1, food_list));
				}
				else{
				for (int i = 0; i < food_list.size(); i++){
					int spacePos = food_list.get(i).indexOf(" ");
					//int subStringLen = food_list.get(i).length() - spacePos - 1;
					String segments[] =  food_list.get(i).split("-|\\ ");
					
					for(int j = 0; j < segments.length; j++){
						if (textlength <=  food_list.get(i).length() && textlength <= segments[j].length()){
							if(et.getText().toString().equalsIgnoreCase((String)segments[j].subSequence(0,textlength))){
								array_sorted.add( food_list.get(i)); break;
					        }
						}
				    }
				}
				list.setAdapter(new ArrayAdapter<String>(DisplayMeals.this,android.R.layout.simple_list_item_1, array_sorted));
			}
			}	
		});	
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        default:
        	Log.i("DisplayMeals", item.toString());
        }
        return super.onOptionsItemSelected(item);
	}

}
