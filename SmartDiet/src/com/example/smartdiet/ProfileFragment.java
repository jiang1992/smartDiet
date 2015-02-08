package com.example.smartdiet;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileFragment extends Fragment {
	Profile user;
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
		 	user = GlobalController.getSharedUser();
		 	if(user == null){
		 		 Toast.makeText(getActivity().getApplicationContext(),  "THE GLOBAL CONTROLLER IS NULL, so profile properties will be all 0", 0).show();      
		 	}
		 	else{
		 		Toast.makeText(getActivity().getApplicationContext(),  "NOT ZERO`!", 0).show();      
		 	}
		 	View rootView = inflater.inflate(R.layout.profile_fragment,
					container, false);
			TextView dummyTextView;
			dummyTextView = (TextView) rootView
					.findViewById(R.id.textView2);
			Typeface font = Typeface.createFromAsset(this.getActivity().getAssets(), "fonts/bankgothicbold.ttf");
			dummyTextView.setTypeface(font);
			dummyTextView.setText("" + GlobalController.getSharedUser().getAge());
			dummyTextView = (TextView) rootView
					.findViewById(R.id.textView6);
			dummyTextView.setTypeface(font);
			dummyTextView.setText("" + GlobalController.getSharedUser().getHeight());
			dummyTextView = (TextView) rootView
					.findViewById(R.id.textView8);
			dummyTextView.setTypeface(font);
			dummyTextView.setText("" + GlobalController.getSharedUser().getWeight());
			dummyTextView = (TextView) rootView
					.findViewById(R.id.textView10);
			dummyTextView.setTypeface(font);
			dummyTextView.setText("" + GlobalController.getSharedUser().getBMI());
			return rootView;
	 }
}
