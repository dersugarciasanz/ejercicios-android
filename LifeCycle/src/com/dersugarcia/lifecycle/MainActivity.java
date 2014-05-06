package com.dersugarcia.lifecycle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Log.d("LIFECYCLE", "ActivityA.onCreate()");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d("LIFECYCLE", "ActivityA.onPause()");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Log.d("LIFECYCLE", "ActivityA.onResume()");
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		Log.d("LIFECYCLE", "ActivityA.onRestart()");
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		Log.d("LIFECYCLE", "ActivityA.onStop()");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d("LIFECYCLE", "ActivityA.onDestroy()");
	}
	
	public void startB(View v) {
		Intent intent = new Intent(this,ActivityB.class);
		startActivity(intent);
		Log.d("LIFECYCLE", "ActivityA.startB()");
	}
	public void startC(View v) {
		Intent intent = new Intent(this,ActivityC.class);
		startActivity(intent);
		Log.d("LIFECYCLE", "ActivityA.startC()");
	}
	public void close(View v) {
		finish();
		Log.d("LIFECYCLE", "ActivityA.close()");
	}
	
}
