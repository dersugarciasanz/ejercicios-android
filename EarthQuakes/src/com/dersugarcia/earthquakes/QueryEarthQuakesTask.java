package com.dersugarcia.earthquakes;

import java.util.ArrayList;

import android.content.Context;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

public class QueryEarthQuakesTask extends
		AsyncTask<Void, Void, ArrayList<EarthQuake>> {

	private IEarthQuakeListAdapter fragment;
	private EarthQuakeDB eqdb;
	private Context context;
	
	public QueryEarthQuakesTask(IEarthQuakeListAdapter fragment, Context context) {
		this.fragment = fragment;
		this.context = context;
		this.eqdb = EarthQuakeDB.getInstance(context);
	}
	
	@Override
	protected ArrayList<EarthQuake> doInBackground(Void... params) {
		String mag = PreferenceManager.getDefaultSharedPreferences(context).getString(context.getString(R.string.magnitude_list_key), "0");
		ArrayList<EarthQuake> list = (ArrayList<EarthQuake>) eqdb.filterByMagnitude(Double.parseDouble(mag));
		
		return list;
	}
	
	@Override
	protected void onPostExecute(ArrayList<EarthQuake> result) {
		fragment.updateList(result);
	}

}
