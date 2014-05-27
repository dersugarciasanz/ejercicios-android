package com.dersugarcia.earthquakes.asynctasks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dersugarcia.earthquakes.model.EarthQuake;
import com.dersugarcia.earthquakes.provider.EarthQuakesContentProvider;
import com.dersugarcia.earthquakes.util.ResourceParser;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class DownloadEarthQuakesTask extends
		AsyncTask<String, Void, Void> {

	private static final String TAG = "EARTHQUAKES";
	private Context mContext;
	
	
	public DownloadEarthQuakesTask(Context context) {
		mContext = context;
	}
	
	@Override
	protected Void doInBackground(String... urls) {
		
		JSONObject json =  ResourceParser.getJSONObject(urls[0]);
		try {
			JSONArray array = json.getJSONArray("features");
			for(int i=array.length()-1; i>=0 ; i--) {
				JSONObject eq = array.getJSONObject(i);
				JSONObject props =  eq.getJSONObject("properties");
				JSONArray coordinates =  eq.getJSONObject("geometry").getJSONArray("coordinates");
				EarthQuake e = new EarthQuake(eq.getString("id"), props.getString("place")
						, props.getLong("time"), props.getString("detail"), props.getDouble("mag"), coordinates.getDouble(1),  coordinates.getDouble(0), props.getString("url"));
				insertEarthQuake(e);
				
				Log.d(TAG, "Recuperado terremoto número: " + e.getTime());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	private void insertEarthQuake(EarthQuake q) {
		ContentResolver cr = mContext.getContentResolver();
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
