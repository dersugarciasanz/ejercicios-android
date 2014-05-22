package com.dersugarcia.earthquakes;

import java.util.ArrayList;

public interface IEarthQuakeListAdapter {
	public void updateList(ArrayList<EarthQuake> newList);

	public void addEarthQuakes(ArrayList<EarthQuake> result);
}
