package com.example.smartdiet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
/*
public class SectionsPagerAdapter extends FragmentPagerAdapter {

	public SectionsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		// getItem is called to instantiate the fragment for the given page.
		// Return a DummySectionFragment (defined as a static inner class
		// below) with the page number as its lone argument.
		Fragment fragment = new DummySectionFragment();
		Bundle args = new Bundle();
		args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position+1);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public int getCount() {
		// Show 4 total pages.
		return 3;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		Locale l = Locale.getDefault();
		switch (position) {
		case 0:
			return getString(R.string.title_section1).toUpperCase(l);
		case 1:
			return getString(R.string.title_section4).toUpperCase(l);
		case 2:
			return getString(R.string.title_section5).toUpperCase(l);
		}
		return null;
	}
}


public static class DummySectionFragment extends Fragment {

	public static final String ARG_SECTION_NUMBER = "section_number";

	public DummySectionFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(getArguments().getInt(ARG_SECTION_NUMBER) == 1){
			View rootView = inflater.inflate(R.layout.consume_fragment,
					container, false);
			TextView dummyTextView = (TextView) rootView
					.findViewById(R.id.section_label);

			dummyTextView.setText("Home is where the code is");		
			return rootView;
		}else if(getArguments().getInt(ARG_SECTION_NUMBER) == 3){
			
				View rootView = inflater.inflate(R.layout.settings_fragment,
						container, false);
				TextView dummyTextView = (TextView) rootView
						.findViewById(R.id.section_label);
				return rootView;
		}else if(getArguments().getInt(ARG_SECTION_NUMBER) == 2){
			
			View rootView = inflater.inflate(R.layout.profile_fragment,
					container, false);
			TextView dummyTextView;
			dummyTextView = (TextView) rootView
					.findViewById(R.id.textView2);
			dummyTextView.setText("" + GlobalController.getSharedUser().getAge());
			dummyTextView = (TextView) rootView
					.findViewById(R.id.textView4);
			dummyTextView.setText("" + GlobalController.getSharedUser().getGender());
			dummyTextView = (TextView) rootView
					.findViewById(R.id.textView6);
			dummyTextView.setText("" + GlobalController.getSharedUser().getHeight());
			dummyTextView = (TextView) rootView
					.findViewById(R.id.textView8);
			dummyTextView.setText("" + GlobalController.getSharedUser().getWeight());
			dummyTextView = (TextView) rootView
					.findViewById(R.id.textView10);
			dummyTextView.setText("" + GlobalController.getSharedUser().getBMI());
			return rootView;
	}
		else{
		View rootView = inflater.inflate(R.layout.settings_fragment,
				container, false);
		TextView dummyTextView = (TextView) rootView
				.findViewById(R.id.section_label);
		dummyTextView.setText("Settings on Settings on Settings coming soon");
		return rootView;
		}
	}
}
*/

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
	

	//SectionsPagerAdapter mSectionsPagerAdapter;
	MainActivityAdapter main_activity_adapter;
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	 ViewPager mViewPager;

	 boolean click = true;
	 
	 static List<String> recent_Consume_list = new ArrayList<String>();
	 Spinner recent_consume_spinner;
	 Food selectedFood;
	 Profile user;
	/*
	 * The database
	 */
	public static FoodDatabase food_db; 
	public static Spinner sp;
	public static ProgressBar pb;
	public static TextView text_view;
	public static Resources res;
	public static Rect bounds;
	public static Context context;
	RelativeLayout rl;
	List<String> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/* Today's date */
		Calendar today = Calendar.getInstance();
		Log.i("Today's date", Integer.toString(today.get(Calendar.DATE)) + "-" + Integer.toString(today.get(Calendar.MONTH)) + "-"
				+ Integer.toString(today.get(Calendar.YEAR)));
				
		/* Load the database */
		this.deleteDatabase("usda");
		food_db = new FoodDatabase(this);
		food_db.open();
		
		GlobalController.getSharedUser().loadProfile(this);
		if(GlobalController.getSharedUser().isFirstTimeUser()){
			Intent inte = new Intent(this, InitialRegistration.class);
			startActivity(inte);
		}
		
		context = this;		
		setContentView(R.layout.activity_main);			
		main_activity_adapter = new MainActivityAdapter(getSupportFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(main_activity_adapter);	
	}
	

	public static FoodDatabase getDatabase(){
		return food_db;
	}
	/*
	public void PieChartHandler(View view){
		PieChart pie = new PieChart();
		Intent intent = pie.getIntent(this);
		startActivity(intent);
	}
	*/
	
	public void sendCreate(View view){
		Intent intent = new Intent(this, CreateMeals.class);
		startActivity(intent);
	}
	
	public void startCalendar(){
		Intent intent = new Intent(this, VeryAwesomeCalendar.class);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_search:
	            startCalendar();
	            return true;
	        case R.id.action_settings:
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

//Things got deleted
}
//  Jiang's push
