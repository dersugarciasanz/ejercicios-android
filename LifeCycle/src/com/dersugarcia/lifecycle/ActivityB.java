package com.dersugarcia.lifecycle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ActivityB extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_b);
		Log.d("LIFECYCLE", "ActivityB.onCreate()");
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		Log.d("LIFECYCLE", "ActivityB.onPause()");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Log.d("LIFECYCLE", "ActivityB.onResume()");
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		Log.d("LIFECYCLE", "ActivityB.onRestart()");
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		Log.d("LIFECYCLE", "ActivityB.onStop()");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d("LIFECYCLE", "ActivityB.onDestroy()");
	}
	
	public void startA(View v) {
		Intent intent = new Intent(this,MainActivity.class);
		startActivity(intent);
		Log.d("LIFECYCLE", "ActivityB.startA()");
	}
	public void startC(View v) {
		Intent intent = new Intent(this,ActivityC.class);
		startActivity(intent);
		Log.d("LIFECYCLE", "ActivityB.startC()");
	}
	public void close(View v) {
		finish();
		Log.d("LIFECYCLE", "ActivityB.close()");
	}

}
