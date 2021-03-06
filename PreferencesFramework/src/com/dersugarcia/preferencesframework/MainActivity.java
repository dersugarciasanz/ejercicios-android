package com.dersugarcia.preferencesframework;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {

	
	TextView autorefresh, interval, magnitude;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		autorefresh = (TextView) findViewById(R.id.textView_autorefresh);
		interval = (TextView) findViewById(R.id.textView_interval);
		magnitude = (TextView) findViewById(R.id.textView_magnitude);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		boolean autorefreshIsChecked = prefs.getBoolean(getString(R.string.autorefresh_switch_key), false);
		autorefresh.setText("Autorefresh: " + String.valueOf(autorefreshIsChecked));
		String intervalSelection = prefs.getString(getString(R.string.interval_list_key), "1");
		interval.setText("Interval: " + intervalSelection);
		String magnitudeSelection = prefs.getString(getString(R.string.magnitude_list_key), "1");
		magnitude.setText("Magnitude: " + magnitudeSelection);
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent i = new Intent(this, SettingsActivity.class);
			startActivity(i);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}



}
