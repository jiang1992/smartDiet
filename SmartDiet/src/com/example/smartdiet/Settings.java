package com.example.smartdiet;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.widget.Toast;

public class Settings extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settingsactivity);
		// Show the Up button in the action bar.
		setupActionBar();
		Intent intent = getIntent();
	}

	//onClick methods
	public void setFemale(){
		Toast.makeText(getApplicationContext(), "Gender set to Female", Toast.LENGTH_SHORT);
	}

	public void setMale(){
		Toast.makeText(getApplicationContext(), "Gender set to Male", Toast.LENGTH_SHORT);
	}

	public void setThanks(){
		Toast.makeText(getApplicationContext(), "Thank you for rating our app!", Toast.LENGTH_SHORT);
	}

	public void saveButton(){
		EditText heightEdit = (EditText) findViewById(R.id.editText1);
		EditText weightEdit = (EditText) findViewById(R.id.editText2);
		EditText ageEdit    = (EditText) findViewById(R.id.editText3);

		Toast.makeText(getApplicationContext(), "New Settings Applied", Toast.LENGTH_SHORT);
		//TODO: Apply things to the saved settings
	}

	
	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
