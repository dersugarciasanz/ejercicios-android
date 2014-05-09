package com.dersugarcia.fragmentedcalculator;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;


public class BasicKeypadFragment extends Fragment {
	
	private IBasicKeypad activity;
	private int[] numbers = {R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9, R.id.buttonPeriod};
	private int[] operations = {R.id.buttonAdd, R.id.buttonSub, R.id.buttonMul, R.id.buttonDiv};

	public interface IBasicKeypad {
		public void numberClick(char c);
		public void operationClick(char c);
		public void deleteClick();
		public void equalsClick();
	}
	
	private class NumberClickListener implements OnClickListener  {
		@Override
		public void onClick(View v) {
			Button b = (Button) v;
			String text = (String) b.getText();
			activity.numberClick(text.charAt(0));
		}
	}
	
	private class OperationClickListener implements OnClickListener  {
		@Override
		public void onClick(View v) {
			Button b = (Button) v;
			String text = (String) b.getText();
			activity.operationClick(text.charAt(0));
		}
	}
	
	private class DeleteClickListener implements OnClickListener  {
		@Override
		public void onClick(View v) {
			activity.deleteClick();
		}
	}
	
	private class EqualsClickListener implements OnClickListener  {
		@Override
		public void onClick(View v) {
			activity.equalsClick();
		}
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			this.activity = (IBasicKeypad) activity;
		} catch(ClassCastException e) {
			Log.d("FRAG","No se ha podido hacer casting");
		}
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.basic_keypad_fragment, container, false);
		Button b;
		NumberClickListener numberClickListener = new NumberClickListener();
		OperationClickListener operationClickListener = new OperationClickListener();
		for(int id=0; id<numbers.length; id++) {
			b = (Button)v.findViewById(numbers[id]);
			b.setOnClickListener(numberClickListener);
		}
		for(int id=0; id<operations.length; id++) {
			b = (Button)v.findViewById(operations[id]);
			b.setOnClickListener(operationClickListener);
		}
		v.findViewById(R.id.buttonEquals).setOnClickListener(new EqualsClickListener());
		v.findViewById(R.id.buttonDel).setOnClickListener(new DeleteClickListener());
		
		
		return v;
	}
	
	
}
