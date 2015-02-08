package com.example.smartdiet;

import android.view.View;

public class GlobalController {
	private static GlobalController instance = null;
	public View currentView;
	public static Profile user = null;
	public static Diet currentDiet = null; //track current diet (Hieu Tran)
	
	protected GlobalController() {
		//Exists only to defeat instantiation
	}
	
	public static GlobalController getInstance() {
		if (instance == null) {
			instance = new GlobalController();
		}
		return instance;
	}
	
	public static Profile getSharedUser() {
		if (user == null) {
			user = new Profile();
		}
		return user;
	}
	
	/* Start a new diet and make it current diet (Hieu) */
	public static Diet newDiet(String name){
		currentDiet = new Diet(name);
		return currentDiet;
	}
	
	/* Get current diet (Hieu) */ 
	public static Diet getCurrentDiet(){
		return currentDiet;
	}
	
	public void transitionToView(View view) {
		//Will get filled in when we come together
		currentView = view;
	}
	
}
