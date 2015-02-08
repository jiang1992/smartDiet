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

public class Diets extends Activity
{
	//String names for diets
	String WeightLoss = "weightloss";
	String Fitness = "fitness";
	String Maintenance = "maintenance";
	String Custom = "custom";
	Profile user = GlobalController.getSharedUser();
	//Doubles for Weightdiet percentages proteins, carbs, fats
	double pr1 = .25;, cr1 = .55;, ft1 = .20;
	//Doubles for Fitnessdiet
	double pr2, cr2, ft2;
	//Doubles for Maintenancediet
	double pr3, cr3, ft3;
	//Doubles for Custom
	double pr4, cr4, ft4;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.diets);
	}
	@Override
	public void onBackPressed() {}
	public void change1(View view)
	{ 
		//Intent intent = new Intent(this, RegistrationActivity.class);
		startActivity(intent);
		this.finish();
	}
	
	public void change2(View view)
	{
		//Intent intent = new Intent(this, Login.class);
		startActivity(intent);
		this.finish();
	}
	
	public void change3(View view)
	{
		//Intent intent = new Intent(this, Login.class);
		startActivity(intent);
		this.finish();
	}
	
	public void change4(View view)
	{
		//Intent intent = new Intent(this, Login.class);
		startActivity(intent);
		this.finish();
	}
	
	public Diets(WeightLoss, int calories)
	{
		calories -= 750;
		user.newDiet(weightLoss);
	}
	
	public Diets(Fitness, int calories)
	{
		calories += 200;
		user.newDiet(Fitness);
	}
	
	public Diets(Maintenance, int calories)
	{
		user.newDiet(Maintenance);
	}
	
	public Diets(Custom, int calories)
	{
		user.newDiet(Custom);
	}
	public String getDietType()
	{
		return "";
	}
}
