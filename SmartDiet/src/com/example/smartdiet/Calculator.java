package com.example.smartdiet;

public class Calculator {
	
	/*
	 * Straight forward calculation
	 * BMI = (weight(lbs)/height(in)^2)*703		<--- multiplier constant for BMI
	 */
	public static float calculateBMI(int weight, int height) {
		float BMI = 0;
		BMI = (float) ((float)weight/Math.pow(height, 2));
		BMI *= 703;
		return BMI;
	}
	
	/*
	 * This is based on the BMR
	 * For a man: BMR = 10 * weight(kg) + 6.25 * height(cm) - 5 * age(y) + 5
	 * For a woman: BMR = 10 * weight(kg) + 6.25 * height(cm) - 5 * age(y) - 161
	 */
	public static float calculateCaloriesPerDay(int weight, int height, int age, int gender) {
		float cal = 0;
		float heightCM = (float) (height*2.54);
		float weightKG = (float)(weight*.453592);
		if (gender == 0) {
			cal = (float) (10 * weightKG + 6.25*heightCM - 5*age + 5);
		}
		else {
			cal = (float)(10*weightKG + 6.25*heightCM-5*age-161);
		}
		
		//This is just regular BMR for now, will change once I get multipliers
		return cal;
	}
}
