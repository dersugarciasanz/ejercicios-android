package com.dersugarcia.earthquakes.activities;

import com.dersugarcia.earthquakes.R;
import com.dersugarcia.earthquakes.fragments.DetailFragment;
import com.dersugarcia.earthquakes.fragments.MyListFragment;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class DetailActivity extends Activity {


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		Intent i = getIntent();
		long id = i.getLongExtra(MyListFragment.ID, 0);
		
		if (savedInstanceState == null) {
			DetailFragment detail = new DetailFragment();
			savedInstanceState = new Bundle(); 
			savedInstanceState.putLong("id", id);
			detail.setArguments(savedInstanceState);
			getFragmentManager().beginTransaction()
					.add(R.id.container, detail).commit();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail, menu);
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
		}
		return super.onOptionsItemSelected(item);
	}

	
	

}
