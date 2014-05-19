package com.dersugarcia.preferencesframework;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

public class SettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.userpreferences);
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
		updateMenu(sharedPreferences);
		sharedPreferences.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		if (key.equals(getResources().getString(R.string.autorefresh_switch_key))) {
			updateMenu(sharedPreferences);
		}
	}
	private void updateMenu(SharedPreferences sharedPreferences) {
//		boolean enabled = sharedPreferences.getBoolean(getResources().getString(R.string.autorefresh_switch_key), true);
//		findPreference(getResources().getString(R.string.interval_list_key)).setEnabled(enabled);
	}
	
}
