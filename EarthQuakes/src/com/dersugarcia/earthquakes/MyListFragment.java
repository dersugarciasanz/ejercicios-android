package com.dersugarcia.earthquakes;

import java.util.ArrayList;


import android.app.ListFragment;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyListFragment extends ListFragment implements IEarthQuakeListAdapter {
	

	private static final String TAG = "EARTHQUAKES";
	private ArrayList<EarthQuake> list;
	private EarthQuakeListAdapter adapter;
	private EarthQuakeDB eqdb;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		eqdb = EarthQuakeDB.getInstance(container.getContext());
		list = new ArrayList<EarthQuake>();
		adapter = new EarthQuakeListAdapter(inflater.getContext(), list);
		setListAdapter(adapter);
		
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	

	
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
		DownloadEarthQuakesTask d = new DownloadEarthQuakesTask(this, getActivity());
		d.execute("http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson");
	}
	
	public void updateList() {
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
