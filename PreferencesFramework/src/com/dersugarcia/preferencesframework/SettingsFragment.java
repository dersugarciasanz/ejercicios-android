package com.dersugarcia.preferencesframework;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

public class SettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {
	
	public static final String KEY_PREF_AUTOREFRESH = "settings_autorefresh";
	public static final String KEY_PREF_INTERVAL = "settings_interval";
	
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
		if (key.equals(KEY_PREF_AUTOREFRESH)) {
			updateMenu(sharedPreferences);
		}
	}
	private void updateMenu(SharedPreferences sharedPreferences) {
		boolean enabled = sharedPreferences.getBoolean(KEY_PREF_AUTOREFRESH, true);
		findPreference(KEY_PREF_INTERVAL).setEnabled(enabled);
	}
	
}
