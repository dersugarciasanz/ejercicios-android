package com.dersugarcia.earthquakes.fragments;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.dersugarcia.earthquakes.R;

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
		} else if(key.equals(getString(R.string.magnitude_list_key))) {
			Toast.makeText(getActivity(), "Congrats! You changed the magnitude!", Toast.LENGTH_SHORT).show();
		}
	}

	
}
