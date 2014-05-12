package com.dersugarcia.fragmentedtodo;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.EditText;

public class InputFragment extends Fragment {

	private ITODO activity;
	private EditText input;
	
	public interface ITODO {
		public void addItem(String item);
		
	}
	
	private class EnterTouchListener implements OnKeyListener  {
		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			if(event.getAction() == KeyEvent.ACTION_DOWN) {
				if((keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER)) {
					String item = input.getText().toString();
					activity.addItem(item);
					input.setText("");
					return true;
				}
			}
			return false;
		}
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			this.activity = (ITODO) activity;
		} catch(ClassCastException e) {
			Log.d("FRAG","The activity " + activity.getClass().getSimpleName() +" must implement the ITODO interface.");
		}
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v =  inflater.inflate(R.layout.input_fragment, container, false);
		input = (EditText) v.findViewById(R.id.editText);
		v.findViewById(R.id.editText).setOnKeyListener(new EnterTouchListener());
		return v;
	}

}
