package com.example.smartdiet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class InitialRegistration extends Activity
{
	private static EditText username;
	private static EditText password;
	public boolean passG = false;
	
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.initial_registration);
		username = (EditText)findViewById(R.id.uname);
		password = (EditText)findViewById(R.id.pword);
	}
	@Override
	public void onBackPressed() {}
	public void switch1(View view)
	{
		Intent intent = new Intent(this, RegistrationActivity.class);
		startActivity(intent);
		this.finish();
	}
	
	public void switch2(View view)
	{
		Intent intent = new Intent(this, Login.class);
		startActivity(intent);
		this.finish();
	}
	
	/* Save User name and password to Internal Storage */
	public void saveCredentials(Context context)
	{
		String filename = "Credentials.txt";
		FileWriter writer;
		
		File file = new File(filename);
		
		try
		{
			writer = new FileWriter(file, false);
			//Save username to file in internal storage
			writer.write(username.toString());
			writer.write('\n');
			//save password to file in internal storage
			writer.write(password.toString());
			//Null terminate the file
			writer.write('\0');
			writer.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public boolean loadCredentials(Context context){
		String filename = "Credentials.txt";
		StringBuffer buf = new StringBuffer("");
		FileInputStream input;
		String user = "";
		String pass = "";
		byte c;
		
		try
		{
			//Load the saved file containing the username and password
			input = context.openFileInput(filename);
			try
			{
				while((c = (char) input.read()) != -1)
				{
					char ch = (char)c;
					if(ch == '\n')
					{  //Newline encountered
						user.append(buf.toString());
						buf = new StringBuffer("");
					}
					else if(ch == '\0')
					{  //EOF
						pass.append(buf.toString());
					}
				}
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		catch(FileNotFoundException e)
		{
			return true;
		}
		return false;
	}
	
	
	public void initialRegistration(View view)
	{
		while(!passG)
		{
			if(Security.Strength(password.toString()) > 6 &&  Security.Strength(password.toString()) < 32)
			{
				passG = true;
				this.finish();
			}
			else
			{
				Toast.makeText(getApplicationContext(),"Enter new password",Toast.LENGTH_SHORT).show();
			}
		}
		Intent intent = new Intent(this, Login.class);
		startActivity(intent);
		this.finish();
	}
	
	public static String getUser()
	{
		return username.toString();
	}
	
	public static String getPw()
	{
		return password.toString();
	}
	
	@Override
	   public boolean onCreateOptionsMenu(Menu menu) {
	      // Inflate the menu; this adds items to the action bar if it is present.
	      getMenuInflater().inflate(R.menu.main, menu);
	      return true;
	   }
}
