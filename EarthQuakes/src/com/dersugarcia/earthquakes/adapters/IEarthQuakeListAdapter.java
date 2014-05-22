package com.dersugarcia.earthquakes.adapters;

import java.util.ArrayList;

import com.dersugarcia.earthquakes.model.EarthQuake;

public interface IEarthQuakeListAdapter {
	public void updateList(ArrayList<EarthQuake> newList);

	public void addEarthQuakes(ArrayList<EarthQuake> result);
}
