package com.example.smartdiet;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.NavUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

import java.util.ArrayList;




import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;

import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;

public class VeryAwesomeCalendar extends Activity {
    /** Called when the activity is first created. */
	private TextView dateDisplay;
	private CalendarView calendarView1;
	private int mDay;
	private int mMonth;
	private int mYear;
	PopupWindow popupMessage;
	LinearLayout layoutOfPopup;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.very_awesome_calendar);
        
        ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
        findView();
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        calendarView1=(CalendarView) findViewById(R.id.calendarView1);
       
        calendarView1.setOnDateChangeListener(new OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year, int month,
                    int dayOfMonth) {
                String date = year + "-" + month + "-" + dayOfMonth;
                update(date);       
                Intent intent = new Intent(getBaseContext(), DailyDisplay.class);
                startActivity(intent);
             //    Toast.makeText(getApplicationContext(), date, 0).show();      
            }
        });  
    }    
    private void update(String date){
    	dateDisplay.setText(date);
    	
    }
    private void findView(){
    	
    	dateDisplay = (TextView) findViewById(R.id.dateDisplay);
    	calendarView1 = (CalendarView)findViewById(R.id.calendarView1);
    	calendarView1.setOnDateChangeListener( new OnDateChangeListener(){

			@Override
			public void onSelectedDayChange(CalendarView view, int year,
					int month, int dayOfMonth) {
				mYear = year;
				mMonth = month;
				mDay = dayOfMonth;
				// TODO Auto-generated method stub
				
			}
    		
    		
    	});
    }
    

}