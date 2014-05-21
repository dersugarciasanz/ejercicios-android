package com.dersugarcia.earthquakes;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListFragment;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class MyListFragment extends ListFragment {
	

	private static final String TAG = "EARTHQUAKES";
	private ArrayList<EarthQuake> list;
	private ArrayAdapter<EarthQuake> adapter;
	private EarthQuakeDB eqdb;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		eqdb = new EarthQuakeDB(container.getContext());
		list = new ArrayList<EarthQuake>();
		adapter = new ArrayAdapter<EarthQuake>(inflater.getContext(), android.R.layout.simple_list_item_1, list);
		setListAdapter(adapter);
		
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
//	public void addItem(EarthQuake item) {
//		list.add(0,item);
//		adapter.notifyDataSetChanged();
//	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if(savedInstanceState != null) {
			list.addAll((ArrayList<EarthQuake>) savedInstanceState.getSerializable("ARRAYLIST"));
			adapter.notifyDataSetChanged();
		} else {
			getEarthQuakes();
		}
	}
	
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putSerializable("ARRAYLIST", list);
		super.onSaveInstanceState(outState);
	}
	
	private void getEarthQuakes() {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				JSONObject json =  ResourceParser.getJSONObject("http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson");
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
				} finally {
					updateList();
				}
				
			}
			
		});
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void updateList() {
		String mag = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString(getString(R.string.magnitude_list_key), "0");
		Log.d(TAG, mag);
		list.clear();
		list.addAll(eqdb.filterByMagnitude(Double.parseDouble(mag)));
		adapter.notifyDataSetChanged();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		updateList();
	}
	
}
