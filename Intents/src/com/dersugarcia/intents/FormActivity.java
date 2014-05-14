package com.dersugarcia.intents;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class FormActivity extends Activity {

	public static final String EDIT_TEXT = "EDIT_TEXT";
	public static final String TEXT_VIEW = "TEXT_VIEW";
	private EditText editText;
	private TextView textView;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_form);
		
		textView = (TextView) findViewById(R.id.textview_form);
		editText = (EditText) findViewById(R.id.edittext_form);
		
		findViewById(R.id.button_ok).setOnClickListener(new OKClickListener());
		findViewById(R.id.button_back).setOnClickListener(new BackClickListener());
		
		if(savedInstanceState == null) {
			textView.setText(getIntent().getCharSequenceExtra(MainActivity.EDIT_TEXT));
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
//		Save variables before destroying the activity
		outState.putCharSequence(TEXT_VIEW, textView.getText());
		super.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
//		Restore saved variables
		textView.setText(savedInstanceState.getCharSequence(TEXT_VIEW));
	}
	
	private class OKClickListener implements OnClickListener  {
		@Override
		public void onClick(View v) {
			String text = editText.getText().toString();
			if (text.length() != 0) {
				Intent result = new Intent();
				result.putExtra(EDIT_TEXT, text);
				setResult(RESULT_OK, result);
				finish();
			}
		}
	}
	
	private class BackClickListener implements OnClickListener  {
		@Override
		public void onClick(View v) {
				Intent result = new Intent(FormActivity.this,MainActivity.class);
				setResult(RESULT_CANCELED, result);
				finish();
		}
	}
	
}
