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
		textView.setText(getIntent().getCharSequenceExtra(MainActivity.EDIT_TEXT));
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
