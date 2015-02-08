package com.example.smartdiet;
import java.util.ArrayList;

import java.util.List;

import org.achartengine.*;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class ConsumeFragment extends Fragment implements OnItemSelectedListener{
	
	Spinner recent_consume_spinner;
	static List<String> recent_Consume_list = new ArrayList<String>();
	ArrayAdapter<String> dataAdapter;
	
	Button displayMealButton;
	Button consumeButton;
	int[] caloricIntake = {7,6,5,4,3,2,1}; // USED FOR PIE CHART
	String[] temp = {"caleb","hunter","hieu","ian","alistair","michael","jiang"};
	
	public static ProgressBar pb;
	public static TextView text_view;
	public static Resources res;
	public static Rect bounds;
	public static Context context;
	public static FoodDatabase food_db;
	int calories_per_day;
	int calories_today;
	int percentage;
	Profile user;
	
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		user = GlobalController.getSharedUser();
		//////////down below are spinner stuffs
        View ios = inflater.inflate(R.layout.consume_fragment, container, false);
        recent_consume_spinner = (Spinner)ios.findViewById(R.id.recent_consume_spinner);			
	    dataAdapter = new ArrayAdapter<String> (getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,recent_Consume_list);	                      
	    dataAdapter.setDropDownViewResource
	                     (android.R.layout.simple_spinner_dropdown_item);
	    recent_consume_spinner.setAdapter(dataAdapter);
	    //
	    
	    //
	    recent_consume_spinner.setOnItemSelectedListener(this /*new OnItemSelectedListener() {

	        public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {
	        	consumeButton.setEnabled(true); 
	        	System.out.println("XXXXXXXXXXXXXXXXX");
	        	
	        }
	        public void onNothingSelected(AdapterView<?> arg0) {  Toast.makeText(getActivity().getApplicationContext(), "YYYYY", 0).show();    }					

	    }*/);
        ///////////// Down below are button stuffs
	    displayMealButton = (Button)ios.findViewById(R.id.displayMealButton);
	    displayMealButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent(getActivity().getApplicationContext(), DisplayMeals.class);
        	    startActivity(intent);
        	    
        	  
            }
        });
        
	    /////////////Down below are progress bar and stuff
	    
		pb = (ProgressBar)ios.findViewById(R.id.progressBar1);
		
		calories_per_day = (int)user.getCaloriesPerDay();
		calories_today = user.getCaloriesToday();
		percentage = (int)((calories_today/calories_per_day)*100);
		pb.setProgress(percentage);
		
		try{
			updateProgress(percentage);
		}catch(NullPointerException e){
			// Do nothing
		}
		
		text_view = (TextView)ios.findViewById(R.id.tv);
		String text = Integer.toString(percentage) + "%";
		text_view.setText(text);
		res = getResources();
		bounds = pb.getProgressDrawable().getBounds();
		context = getActivity().getApplicationContext();
        
        /////////////////////////////////////////////////////
		
		consumeButton = (Button)ios.findViewById(R.id.button4);
		consumeButton.setEnabled(true);
		consumeButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				/*PieChart pie = new PieChart();
				Intent intent = pie.getIntent(getActivity().getApplicationContext(),caloricIntake, temp);
				startActivity(intent);	*/
				
				/*Food food = food_db.getMealByName((String)recent_consume_spinner.getSelectedItem());
        	    user.consumeFood(food);
        	    calories_today = user.getCaloriesToday();
        	    percentage = (int)((calories_today/calories_per_day)*100);
        	    updateProgress(percentage);*/
			}
		});
        return ios;
	}
	
	public static void updateProgress(int percentage) throws NullPointerException{
		if(percentage < 70 || percentage > 105){
			pb.setProgressDrawable(res.getDrawable(R.drawable.redprogressbar));
		}
		else if(percentage >= 70 && percentage < 95){
			pb.setProgressDrawable(res.getDrawable(R.drawable.yellowprogressbar));
		}else{
			pb.setProgressDrawable(res.getDrawable(R.drawable.greenprogressbar));
		}
		pb.getProgressDrawable().setBounds(bounds);
		pb.setProgress(percentage);
		String text = Integer.toString(percentage) + "%";
		text_view.setText(text);
	}
	
	public static void updateLastestFood(Food food){
		String foodName = food.getName();
		if(recent_Consume_list.contains(foodName)){}
		else{
			if(recent_Consume_list.size() >= 5){
				recent_Consume_list.remove(0);
			}
			recent_Consume_list.add(foodName);
		}
	}
	
	public void sendConsume(View view){

		   Intent intent = new Intent(getActivity().getApplicationContext(), DisplayMeals.class);
		    startActivity(intent);
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		 Toast.makeText(getActivity().getApplicationContext(), "XXXX", 0).show();    
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		 Toast.makeText(getActivity().getApplicationContext(), "00000", 0).show();    
		
	}
	

	
	
	
	

}
