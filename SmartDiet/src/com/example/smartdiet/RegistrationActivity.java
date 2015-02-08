package com.example.smartdiet;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

public class RegistrationActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration_layout);
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
	
	public void onRadioButtonClicked(View view) {
	    // Is the button now checked?
	    boolean checked = ((RadioButton) view).isChecked();
    	RadioButton b = (RadioButton)view;
	    
	    // Check which radio button was clicked
	    switch(view.getId()) {
	        case R.id.boyRadioButton:
	            if (checked)
	            {
	            	
	            }
	            break;
	        case R.id.girlRadioButton:
	            if (checked)
	            {
	            	
	            }
	            break;
	    }
	}
	 @Override
	   public void onBackPressed() {}
	public void onFinishedClicked(View view) {
		boolean notFullList = false;
		Map<String, Integer> prop = new HashMap<String, Integer>();
		EditText dummyEdit = (EditText)findViewById(R.id.editText1);
		if (!dummyEdit.getText().toString().equals(""))
		{
			int age = Integer.parseInt(dummyEdit.getText().toString());
			prop.put(Profile.AGE, age);
		}
		else
			notFullList = true;
			dummyEdit = (EditText)findViewById(R.id.editText2);
		if (!dummyEdit.getText().toString().equals(""))
		{
			int height = Integer.parseInt(dummyEdit.getText().toString());
			prop.put(Profile.HEIGHT, height);
		}
		else
			notFullList = true;
		dummyEdit = (EditText)findViewById(R.id.editText3);
		if (!dummyEdit.getText().toString().equals(""))
		{
			int weight = Integer.parseInt(dummyEdit.getText().toString());
			prop.put(Profile.WEIGHT, weight);
		}
		else
			notFullList = true;
		if (findViewById(R.id.boyRadioButton).isActivated())
			prop.put(Profile.GENDER, 0);
		else
			prop.put(Profile.GENDER, 1);
		if (!notFullList) {
			GlobalController.getSharedUser().setProperties(prop);
			Intent intent = new Intent(this, Diets.class);
			startActivity(intent);
			this.finish();
		}
		GlobalController.getSharedUser().setFirstTimeUser(false);
		GlobalController.getSharedUser().saveProfile(MainActivity.context);
	}
}
