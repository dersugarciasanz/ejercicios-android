package com.dersugarcia.lifecycle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ActivityC extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_c);
		Log.d("LIFECYCLE", "ActivityC.onCreate()");
	}
	
	@Override
	protected void onStart() {
		super.onRestart();
		Log.d("LIFECYCLE", "ActivityC.onStart()");
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		Log.d("LIFECYCLE", "ActivityC.onPause()");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Log.d("LIFECYCLE", "ActivityC.onResume()");
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		Log.d("LIFECYCLE", "ActivityC.onRestart()");
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		Log.d("LIFECYCLE", "ActivityC.onStop()");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d("LIFECYCLE", "ActivityC.onDestroy()");
	}
	
	public void startA(View v) {
		Intent intent = new Intent(this,MainActivity.class);
		startActivity(intent);
		Log.d("LIFECYCLE", "ActivityC.startA()");
	}
	public void startB(View v) {
		Intent intent = new Intent(this,ActivityB.class);
		startActivity(intent);
		Log.d("LIFECYCLE", "ActivityC.startB()");
	}
	public void close(View v) {
		finish();
		Log.d("LIFECYCLE", "ActivityC.close()");
	}

}
