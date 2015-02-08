package com.example.smartdiet;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity 
{
	   private EditText  username=null;
	   private EditText  password=null;
	   private TextView attempts;
	   private Button login;
	   int counter = 3;
	   
	   protected void onCreate(Bundle savedInstanceState)
	   {
		      super.onCreate(savedInstanceState);
		      setContentView(R.layout.activity_login);
		      username = (EditText)findViewById(R.id.editText1);
		      password = (EditText)findViewById(R.id.editText2);
		      attempts = (TextView)findViewById(R.id.attemptsTextView);
		      attempts.setText(Integer.toString(counter));
		      login = (Button)findViewById(R.id.button1);
		   }

		   public void login(View view){
		      if(username.getText().toString().equals("admin"/*InitialRegistration.getUser()*/) && 
		      password.getText().toString().equals("admin"/*InitialRegistration.getPw()*/)){
		      Toast.makeText(getApplicationContext(), "Redirecting...", 
		      Toast.LENGTH_SHORT).show();
		      //This is where we will prompt registration
				Profile user = GlobalController.getSharedUser();		//Grabs the sharedUser instance
				user.setFirstTimeUser(user.loadProfile(MainActivity.context));
				if (user.isFirstTimeUser())								//check if firstTime user
				{
					//Registration
					System.out.println(user.toString());
					Intent intent = new Intent(this, RegistrationActivity.class);
					startActivity(intent);
				}
		      this.finish();
		   }	
		   else{
		      Toast.makeText(getApplicationContext(), "Wrong Credentials",
		      Toast.LENGTH_SHORT).show();
		      attempts.setBackgroundColor(Color.RED);	
		      counter--;
		      attempts.setText(Integer.toString(counter));
		      if(counter==0){
		         login.setEnabled(false);
		      }
		   }

		   }

		   @Override
		   public boolean onCreateOptionsMenu(Menu menu) {
		      // Inflate the menu; this adds items to the action bar if it is present.
		      getMenuInflater().inflate(R.menu.main, menu);
		      return true;
		   }
		   @Override
		   public void onBackPressed() {}

		}