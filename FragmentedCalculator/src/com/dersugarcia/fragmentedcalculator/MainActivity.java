package com.dersugarcia.fragmentedcalculator;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.dersugarcia.fragmentedcalculator.BasicKeypadFragment.IBasicKeypad;

public class MainActivity extends Activity implements IBasicKeypad {
//	private TextView output;
	private Calculator calculator;
	private DisplayFragment display;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		display = (DisplayFragment) getFragmentManager().findFragmentById(R.id.display_fragment);
		calculator = Calculator.getInstance();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		this.updateDisplay();
	}

	@Override
	public void numberClick(char c) {
		calculator.inputNumber(c);
		updateDisplay();
	}

	@Override
	public void operationClick(char c) {
		calculator.inputOperation(c);
		updateDisplay();
	}

	@Override
	public void deleteClick() {
		calculator.inputDelete();
		updateDisplay();
	}

	@Override
	public void equalsClick() {
		calculator.inputEquals();
		updateDisplay();
	}
	
	private void updateDisplay() {
		display.updateDisplay(calculator.getDisplay());
		Log.d("FRAG", calculator.getDisplay());
	}
}
