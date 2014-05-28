package com.dersugarcia.earthquakes.fragments;

import com.dersugarcia.earthquakes.R;
import com.dersugarcia.earthquakes.provider.EarthQuakesContentProvider;

import android.app.Fragment;
import android.app.LoaderManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;

public class DetailFragment extends Fragment implements LoaderCallbacks<Cursor> {
	private long id;
	private static int LOADER_ID = 2;
	
	public DetailFragment() {
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_detail,
				container, false);

		id = getArguments().getLong("id");
		Toast.makeText(inflater.getContext(), "Earthquake id: " + id, Toast.LENGTH_SHORT).show();
		
		LoaderManager lm = getLoaderManager();
		lm.initLoader(LOADER_ID, null, this);
		return rootView;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		getLoaderManager().restartLoader(LOADER_ID, null, this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		Uri rowAddress = ContentUris.withAppendedId(EarthQuakesContentProvider.CONTENT_URI, this.id);
		
		CursorLoader loader = new CursorLoader(getActivity(), rowAddress, EarthQuakesContentProvider.KEYS_ALL, null, null, null);
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		int place_idx = cursor.getColumnIndex(EarthQuakesContentProvider.Columns.KEY_PLACE);
		
		if(cursor.moveToFirst()) {
			String place = cursor.getString(place_idx);
			Toast.makeText(getActivity(), place, Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		
	}

}
