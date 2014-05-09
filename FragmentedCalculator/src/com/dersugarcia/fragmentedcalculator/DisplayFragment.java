package com.dersugarcia.fragmentedcalculator;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DisplayFragment extends Fragment {
	
	TextView output;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v =  inflater.inflate(R.layout.display_fragment, container, false);
		output = (TextView) v.findViewById(R.id.textView);
		return v;
	}
	
	public void updateDisplay(String display) {
		output.setText(display);
	}
}
