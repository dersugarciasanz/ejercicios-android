package com.dersugarcia.earthquakes.fragments;

import java.util.ArrayList;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dersugarcia.earthquakes.R;
import com.dersugarcia.earthquakes.activities.DetailActivity;
import com.dersugarcia.earthquakes.adapters.EarthQuakeListAdapter;
import com.dersugarcia.earthquakes.adapters.IEarthQuakeListAdapter;
import com.dersugarcia.earthquakes.asynctasks.DownloadEarthQuakesTask;
import com.dersugarcia.earthquakes.asynctasks.QueryEarthQuakesTask;
import com.dersugarcia.earthquakes.model.EarthQuake;

public class MyListFragment extends ListFragment implements IEarthQuakeListAdapter {
	
	private ArrayList<EarthQuake> list;
	private EarthQuakeListAdapter adapter;
	private double lastMagnitude;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		list = new ArrayList<EarthQuake>();
		adapter = new EarthQuakeListAdapter(inflater.getContext(), list);
		setListAdapter(adapter);
		
		lastMagnitude = Double.valueOf(PreferenceManager.getDefaultSharedPreferences(getActivity()).getString(getString(R.string.magnitude_list_key), "0"));
		
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
	
	public void getEarthQuakes() {
		DownloadEarthQuakesTask d = new DownloadEarthQuakesTask(this, getActivity());
		d.execute("http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson");
	}
	
	public void queryEarthQuakes() {
		QueryEarthQuakesTask q = new QueryEarthQuakesTask(this, getActivity());
		q.execute();
	}
	
	@Override
	public void addEarthQuakes(ArrayList<EarthQuake> newList) {
		for (EarthQuake earthQuake: newList) {
			
			if(earthQuake.getMagnitude() > lastMagnitude) {
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
		super.onResume();
		
		double newMagnitude = Double.valueOf(PreferenceManager.getDefaultSharedPreferences(getActivity()).getString(getString(R.string.magnitude_list_key), "0"));
		
		if(lastMagnitude != newMagnitude) {
			lastMagnitude = newMagnitude;
			queryEarthQuakes();
		}
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		EarthQuake eq = list.get(position);
		int eqId = eq.getId();
		Intent i = new Intent(getActivity(), DetailActivity.class);
		i.putExtra("id", eqId);
		startActivity(i);
	}
	
}
