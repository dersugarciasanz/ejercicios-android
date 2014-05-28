package com.dersugarcia.earthquakes;

import com.dersugarcia.earthquakes.asynctasks.EarthQuakeService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class EarthQuakeReceiver extends BroadcastReceiver {
	public EarthQuakeReceiver() {
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent service = new Intent(context, EarthQuakeService.class);
		context.startService(service);
	}
}
