package com.dersugarcia.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	TextView output;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		output = (TextView)findViewById(R.id.textView);
	}
	
	public void buttonClick(View v) {
		Button b = (Button)v;
		String text = (String) b.getText();
		Log.d("CALCULATOR", text);
		
		if(Character.isDigit(text.charAt(0))) {
			numberClick(text);
		} else {
			operationClick(text);
		}
		
	}

	private void numberClick(String num) {
		Log.d("CALCULATOR", "numberClick");
		output.append(num);
	}
	
	private void operationClick(String op) {
		
	}
	
}
