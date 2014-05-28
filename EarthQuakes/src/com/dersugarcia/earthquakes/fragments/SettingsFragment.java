package com.dersugarcia.earthquakes.fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.dersugarcia.earthquakes.R;

public class SettingsFragment extends PreferenceFragment implements
		OnSharedPreferenceChangeListener {

	private static final String TAG = "EARTHQUAKES";
	private static final String ALARM_ACTION = "com.dersugarcia.earthquakes.DownloadEarthQuakesAction";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.userpreferences);
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
		sharedPreferences.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		if (key.equals(getString(R.string.autorefresh_switch_key))) {
			Toast.makeText(getActivity(), "Congrats! You pushed the switch!",
					Toast.LENGTH_SHORT).show();

			boolean autorefresh = sharedPreferences.getBoolean(key, false);

			if (autorefresh) {
				// Start
				setInexactRepeatingAlarm(sharedPreferences);
			} else {
				// Pause
				cancelRepeatingAlarm();
			}
		} else if (key.equals(getString(R.string.interval_list_key))) {
			Toast.makeText(getActivity(),
					"Congrats! You changed the interval!", Toast.LENGTH_SHORT)
					.show();
			setInexactRepeatingAlarm(sharedPreferences);
		} else if (key.equals(getString(R.string.magnitude_list_key))) {
			Toast.makeText(getActivity(),
					"Congrats! You changed the magnitude!", Toast.LENGTH_SHORT)
					.show();
		}
	}

	private void setInexactRepeatingAlarm(SharedPreferences sharedPreferences) {
		AlarmManager alarmManager = (AlarmManager) getActivity()
				.getSystemService(Context.ALARM_SERVICE);

		int alarmType = AlarmManager.RTC;
		 String timeStr = sharedPreferences.getString(
		 getResources().getString(R.string.interval_list_key), "1");
		 long timeOfWait = Long.valueOf(timeStr) * 60 * 1000;
		Intent intentToFire = new Intent(ALARM_ACTION);
		PendingIntent alarmIntent = PendingIntent.getBroadcast(getActivity(),
				0, intentToFire, 0);
		Log.d(TAG, "SettingsFragment => alarm set to " + timeOfWait
				+ " milliseconds");
		alarmManager.setInexactRepeating(alarmType, timeOfWait, timeOfWait,
				alarmIntent);
		
	}
	private void cancelRepeatingAlarm() {
		AlarmManager alarmManager = (AlarmManager) getActivity()
				.getSystemService(Context.ALARM_SERVICE);

		Intent intentToFire = new Intent(ALARM_ACTION);
		PendingIntent alarmIntent = PendingIntent.getBroadcast(getActivity(),
				0, intentToFire, 0);
		alarmManager.cancel(alarmIntent);
	}

}
