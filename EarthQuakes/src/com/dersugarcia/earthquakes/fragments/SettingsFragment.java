package com.dersugarcia.earthquakes.fragments;

import com.dersugarcia.earthquakes.R;
import com.dersugarcia.earthquakes.R.string;
import com.dersugarcia.earthquakes.R.xml;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class SettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.userpreferences);
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
		sharedPreferences.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		if (key.equals(getString(R.string.autorefresh_switch_key))) {
			Toast.makeText(getActivity(), "Congrats! You pushed the switch!", Toast.LENGTH_SHORT).show();
			
			boolean autorefresh = sharedPreferences.getBoolean(key, false);
			
			if (autorefresh) {
				// Start
			} else {
				// Pause
			}
		} else if (key.equals(getString(R.string.interval_list_key))) {
			Toast.makeText(getActivity(), "Congrats! You changed the interval!", Toast.LENGTH_SHORT).show();
		}
	}

	
}
