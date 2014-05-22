package com.dersugarcia.earthquakes.fragments;

import java.util.ArrayList;

import com.dersugarcia.earthquakes.DownloadEarthQuakesTask;
import com.dersugarcia.earthquakes.EarthQuake;
import com.dersugarcia.earthquakes.EarthQuakeListAdapter;
import com.dersugarcia.earthquakes.IEarthQuakeListAdapter;
import com.dersugarcia.earthquakes.QueryEarthQuakesTask;
import com.dersugarcia.earthquakes.R;
import com.dersugarcia.earthquakes.R.string;

import android.app.ListFragment;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyListFragment extends ListFragment implements IEarthQuakeListAdapter {
	

	private ArrayList<EarthQuake> list;
	private EarthQuakeListAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		list = new ArrayList<EarthQuake>();
		adapter = new EarthQuakeListAdapter(inflater.getContext(), list);
		setListAdapter(adapter);
		
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	

	
	@SuppressWarnings("unchecked")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		Log.d("EARTHQUAKE", "onActivityCreated()");
		
		if(savedInstanceState != null) {
			list.addAll((ArrayList<EarthQuake>) savedInstanceState.getSerializable("ARRAYLIST"));
			adapter.notifyDataSetChanged();
		} else {
			queryEarthQuakes();
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
	
	private void queryEarthQuakes() {
		QueryEarthQuakesTask q = new QueryEarthQuakesTask(this, getActivity());
		q.execute();
	}
	
	@Override
	public void addEarthQuakes(ArrayList<EarthQuake> newList) {
		String magStr = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString(getString(R.string.magnitude_list_key), "0");
		double mag = Double.parseDouble(magStr);
		for (EarthQuake earthQuake: newList) {
			
			if(earthQuake.getMagnitude() > mag) {
				list.add(0, earthQuake);
			}
		}
		adapter.notifyDataSetChanged();
	}
	
	@Override
	public void updateList(ArrayList<EarthQuake> newList) {
		list.clear();
		list.addAll(newList);
		adapter.notifyDataSetChanged();
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
}
