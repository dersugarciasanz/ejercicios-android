package com.dersugarcia.sharedpreferences;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Switch;

public class SettingsActivity extends Activity implements OnCheckedChangeListener, OnItemSelectedListener {
	private static final String MY_PREFS = "MY_PREFS";
	private static final String INTERVAL_INDEX = "INTERVAL_INDEX";
	private static final String AUTOREFRESH_CHECKED = "AUTOREFRESH_CHECKED";
	private SharedPreferences mySharedPreferences;
	private Switch autorefresh;
	private Spinner spinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		mySharedPreferences = getSharedPreferences(MY_PREFS, Activity.MODE_PRIVATE);
		spinner = (Spinner) findViewById(R.id.spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.interval_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		autorefresh = (Switch) findViewById(R.id.autorefresh_switch);
		loadSharedPreferences();
		autorefresh.setOnCheckedChangeListener(this);
		spinner.setOnItemSelectedListener(this);
	}

	private void loadSharedPreferences() {
		boolean checked = mySharedPreferences.getBoolean(AUTOREFRESH_CHECKED, false);
		int position = mySharedPreferences.getInt(INTERVAL_INDEX, 0);
		autorefresh.setChecked(checked);
		spinner.setSelection(position);
	}
	
//**************************Listeners*****************************************
	@Override
	public void onCheckedChanged(CompoundButton buttonView,
			boolean isChecked) {
		SharedPreferences.Editor editor = mySharedPreferences.edit();
		    editor.putBoolean(AUTOREFRESH_CHECKED, isChecked);
		    editor.apply();
	}
	
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		SharedPreferences.Editor editor = mySharedPreferences.edit();
	    editor.putInt(INTERVAL_INDEX, arg2);
	    editor.apply();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
