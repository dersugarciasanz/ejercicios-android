package com.dersugarcia.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView output;
	private Calculator calculator;

//	private final String DISPLAY = "DISPLAY";
//	private final String OP = "OP";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		output = (TextView) findViewById(R.id.textView);
		calculator = Calculator.getInstance();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
//		Save variables before destroying the activity
//		outState.putString(DISPLAY, calculator.getDisplay());
//		outState.putChar(OP, calculator.getOperation());

		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
//		Restore saved variables
//		calculator.setOperation(savedInstanceState.getChar(OP));
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		this.updateDisplay();
	}

	public void numberClick(View v) {
		Log.d("CALCULATOR", "numberClick");
		Button b = (Button) v;
		String text = (String) b.getText();

		calculator.inputNumber(text.charAt(0));
		this.updateDisplay();
	}

	public void operationClick(View v) {
		Log.d("CALCULATOR", "operationClick");

		Button b = (Button) v;
		String text = (String) b.getText();

		calculator.inputOperation(text.charAt(0));
		this.updateDisplay();
	}

	public void equalsClick(View v) {
		Log.d("CALCULATOR", "equalsClick");

		calculator.inputEquals();
		this.updateDisplay();
	}

	public void deleteClick(View v) {
		Log.d("CALCULATOR", "equalsClick");

		calculator.inputDelete();
		this.updateDisplay();
	}

	private void updateDisplay() {
		output.setText(calculator.getDisplay());
	}

}
