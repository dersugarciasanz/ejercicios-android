package com.dersugarcia.earthquakes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class DownloadEarthQuakesTask extends
		AsyncTask<String, Void, Void> {

	private static final String TAG = "EARTHQUAKES";
	private IEarthQuakeListAdapter fragment;
	private EarthQuakeDB eqdb;
	
	public DownloadEarthQuakesTask(IEarthQuakeListAdapter fragment, Context context) {
		this.fragment = fragment;
		this.eqdb = EarthQuakeDB.getInstance(context);
	}
	
	@Override
	protected void onPostExecute(Void result) {
		fragment.updateList();
	}

	@Override
	protected Void doInBackground(String... urls) {
		JSONObject json =  ResourceParser.getJSONObject(urls[0]);
		try {
			JSONArray array = json.getJSONArray("features");
			for(int i=0; i< array.length(); i++) {
				JSONObject eq = array.getJSONObject(i);
				JSONObject props =  eq.getJSONObject("properties");
				JSONArray coordinates =  eq.getJSONObject("geometry").getJSONArray("coordinates");
				EarthQuake e = new EarthQuake(props.getString("place")
						, props.getLong("time"), props.getString("detail"), props.getDouble("mag"), coordinates.getDouble(1),  coordinates.getDouble(0), props.getString("url"));
				eqdb.insert(e);
				
				Log.d(TAG, "Recuperado terremoto número: " + e.getTime());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
