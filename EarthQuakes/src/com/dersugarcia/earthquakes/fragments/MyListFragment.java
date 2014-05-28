package com.dersugarcia.earthquakes.fragments;

import android.app.ListFragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.dersugarcia.earthquakes.R;
import com.dersugarcia.earthquakes.activities.DetailActivity;
import com.dersugarcia.earthquakes.provider.EarthQuakesContentProvider;

public class MyListFragment extends ListFragment implements LoaderCallbacks<Cursor>  {
	
	private String[] from = { EarthQuakesContentProvider.Columns.KEY_TIME,
			EarthQuakesContentProvider.Columns.KEY_MAGNITUDE,
			EarthQuakesContentProvider.Columns.KEY_PLACE,
			EarthQuakesContentProvider.Columns._ID };
	private int[] to = { R.id.time, R.id.magnitude, R.id.place };
	private SimpleCursorAdapter adapter;
	public final static String ID = "_id";
	private int LOADER_ID = 1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		adapter = new SimpleCursorAdapter(getActivity(),
				R.layout.row, null, from, to, 0);
		adapter.setViewBinder(new EarthquakeViewBinder());
		setListAdapter(adapter);
		
		getLoaderManager().initLoader(LOADER_ID, null, this);
		
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		getLoaderManager().restartLoader(LOADER_ID, null, this);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		Intent i = new Intent(getActivity(), DetailActivity.class);
		i.putExtra(ID, id);
		
		startActivity(i);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		String minMag = prefs.getString(
				getResources().getString(R.string.magnitude_list_key), "0");

		String where = EarthQuakesContentProvider.Columns.KEY_MAGNITUDE + " >= ? ";
		String[] whereArgs = { minMag };
		String order = EarthQuakesContentProvider.Columns.KEY_TIME;

		CursorLoader loader = new CursorLoader(getActivity(),
				EarthQuakesContentProvider.CONTENT_URI, from, where, whereArgs,
				order);

		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		adapter.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		adapter.swapCursor(null);
	}
	
}
