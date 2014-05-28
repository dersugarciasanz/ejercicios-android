package com.dersugarcia.earthquakes.asynctasks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dersugarcia.earthquakes.R;
import com.dersugarcia.earthquakes.model.EarthQuake;
import com.dersugarcia.earthquakes.provider.EarthQuakesContentProvider;
import com.dersugarcia.earthquakes.util.ResourceParser;

import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class EarthQuakeService extends Service {

	
	private String url;
	private static final String TAG = "EARTHQUAKES";
	
	@Override
	public void onCreate() {
		Log.d(TAG, "Servicio => onCreate()");
		url = getString(R.string.quake_feed);
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "Servicio => onStartCommand()");
		downloadEarthQuakes();
		
		return Service.START_NOT_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void downloadEarthQuakes() {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				JSONObject json =  ResourceParser.getJSONObject(url);
				try {
					JSONArray array = json.getJSONArray("features");
					for(int i=array.length()-1; i>=0 ; i--) {
						JSONObject eq = array.getJSONObject(i);
						JSONObject props =  eq.getJSONObject("properties");
						JSONArray coordinates =  eq.getJSONObject("geometry").getJSONArray("coordinates");
						EarthQuake e = new EarthQuake(eq.getString("id"), props.getString("place")
								, props.getLong("time"), props.getString("detail"), props.getDouble("mag"), coordinates.getDouble(1),  coordinates.getDouble(0), props.getString("url"));
						insertEarthQuake(e);
						
						Log.d(TAG, "Servicio => Recuperado terremoto número: " + e.getIdStr());
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
			
		});
		t.start();
	}
	
	private void insertEarthQuake(EarthQuake q) {
		ContentResolver cr = getContentResolver();
		ContentValues newValues = new ContentValues();

		newValues.put(EarthQuakesContentProvider.Columns.KEY_TIME, q.getTime());
		newValues.put(EarthQuakesContentProvider.Columns.KEY_ID_STR, q.getIdStr());
		newValues.put(EarthQuakesContentProvider.Columns.KEY_PLACE, q.getPlace());
		newValues.put(EarthQuakesContentProvider.Columns.KEY_LOCATION_LAT, q.getLatitude());
		newValues.put(EarthQuakesContentProvider.Columns.KEY_LOCATION_LNG, q.getLongitude());
		newValues.put(EarthQuakesContentProvider.Columns.KEY_MAGNITUDE, q.getMagnitude());
		newValues.put(EarthQuakesContentProvider.Columns.KEY_URL, q.getUrl());

		cr.insert(EarthQuakesContentProvider.CONTENT_URI, newValues);
	}

}
