package com.dersugarcia.layouts;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class FormActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_form);
	}
	
	public void close(View v) {
		finish();
	}
}
