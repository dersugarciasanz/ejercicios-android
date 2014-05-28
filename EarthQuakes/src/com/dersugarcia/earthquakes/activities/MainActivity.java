package com.dersugarcia.earthquakes.activities;



import com.dersugarcia.earthquakes.R;
import com.dersugarcia.earthquakes.asynctasks.EarthQuakeService;
import com.dersugarcia.earthquakes.fragments.MyListFragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		PreferenceManager.setDefaultValues(this, R.xml.userpreferences, false);
		
		
		serviceStart();
		
		if (savedInstanceState == null) {
			
			getFragmentManager().beginTransaction()
					.add(R.id.list_container, new MyListFragment(), "listTag").commit();
		}
	}
	
	private void serviceStart() {
		Intent intent = new Intent(this, EarthQuakeService.class);
		startService(intent);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
		} else if(id == R.id.action_refresh) {
			serviceStart();
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		stopServices();
	}

	private void stopServices() {
		Intent intent = new Intent(this, EarthQuakeService.class);
		stopService(intent);
	}

}
