package com.dersugarcia.earthquakes.asynctasks;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dersugarcia.earthquakes.adapters.IEarthQuakeListAdapter;
import com.dersugarcia.earthquakes.databases.EarthQuakeDB;
import com.dersugarcia.earthquakes.model.EarthQuake;
import com.dersugarcia.earthquakes.util.ResourceParser;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class DownloadEarthQuakesTask extends
		AsyncTask<String, Void, ArrayList<EarthQuake>> {

	private static final String TAG = "EARTHQUAKES";
	private IEarthQuakeListAdapter fragment;
	private EarthQuakeDB eqdb;
	
	public DownloadEarthQuakesTask(IEarthQuakeListAdapter fragment, Context context) {
		this.fragment = fragment;
		this.eqdb = EarthQuakeDB.getInstance(context);
	}
	
	@Override
	protected ArrayList<EarthQuake> doInBackground(String... urls) {
		ArrayList<EarthQuake> result = new ArrayList<EarthQuake>();
		
		JSONObject json =  ResourceParser.getJSONObject(urls[0]);
		try {
			JSONArray array = json.getJSONArray("features");
			for(int i=array.length()-1; i>=0 ; i--) {
				JSONObject eq = array.getJSONObject(i);
				JSONObject props =  eq.getJSONObject("properties");
				JSONArray coordinates =  eq.getJSONObject("geometry").getJSONArray("coordinates");
				EarthQuake e = new EarthQuake(props.getString("place")
						, props.getLong("time"), props.getString("detail"), props.getDouble("mag"), coordinates.getDouble(1),  coordinates.getDouble(0), props.getString("url"));
				long id = eqdb.insert(e);
				
				if(id != -1) {
					e.setId((int) id);
					result.add(e);
				}
				
				Log.d(TAG, "Recuperado terremoto número: " + e.getTime());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@Override
	protected void onPostExecute(ArrayList<EarthQuake> result) {
		
		fragment.addEarthQuakes(result);
	}

}
