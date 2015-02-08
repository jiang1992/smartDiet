package com.example.smartdiet;
import java.util.*;
import java.lang.*;

public class Security 
{
	private String log = "";//Grab from login information
    private String word = "";//Grab from login information
   //verify email address
	private boolean Verify(String address)
	{
      if(address.equals(log))
      {
         return true;
      }
      else
      {
         System.out.println("Email address does not match user profile");
         return false;
      }
	}
	//check password
   private boolean Check(String pass)
   {
      if(word.equals(pass))
      {
         return true;
      }
      else
      {
         System.out.println("Password incorrect!");
         return false;
      }
   }
	//evaluate password strength
   public static int Strength(String pass)
   {
      int score = 0;
      int i;
      //Check password length
      int length = pass.length();
      if(length < 6)
      {
         System.out.println("Password is too short");
         throw new IndexOutOfBoundsException();
      }
      if(length > 32)
      {
         System.out.println("Password is too long");
         throw new IndexOutOfBoundsException();
      }
      score += length;
      //Check if password contains a special character
      if(pass.contains("!") || pass.contains("@") || pass.contains("#") || pass.contains("$") || pass.contains("%"))
      {
         score += 5;
      }
      //Check for an uppercase letter
      for(i=0;i<length;i++)
      {
         if(Character.isUpperCase(pass.charAt(i)))
         {
            score += 3;
         }
      }
      //Check for a lowercase letter
      for(i=0;i<length;i++)
      {
         if(Character.isLowerCase(pass.charAt(i)))
         {
            score +=3;
         }
      }
      
      return score;
   }
}
