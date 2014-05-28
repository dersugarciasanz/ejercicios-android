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
import android.widget.TextView;
import android.widget.Toast;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;

public class DetailFragment extends Fragment implements LoaderCallbacks<Cursor> {
	private long id;
	private static int LOADER_ID = 2;
	private TextView placeTextView, timeTextView, magnitudeTextView, latitudeTextView, longitudeTextView, urlTextView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_detail,
				container, false);
		
		saveViews(rootView);
		
		
		id = getArguments().getLong("id");
		Toast.makeText(inflater.getContext(), "Earthquake id: " + id, Toast.LENGTH_SHORT).show();
		
		LoaderManager lm = getLoaderManager();
		lm.initLoader(LOADER_ID, null, this);
		return rootView;
	}
	
	private void saveViews(View rootView) {
		placeTextView = (TextView) rootView.findViewById(R.id.detail_place);
		timeTextView = (TextView) rootView.findViewById(R.id.detail_time);
		magnitudeTextView = (TextView) rootView.findViewById(R.id.detail_magnitude);
		latitudeTextView = (TextView) rootView.findViewById(R.id.detail_latitude);
		longitudeTextView = (TextView) rootView.findViewById(R.id.detail_longitude);
		urlTextView = (TextView) rootView.findViewById(R.id.detail_url);
		
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
		
		
		if(cursor.moveToFirst()) {
			draw(cursor);
		}
	}
	
	public void draw(Cursor cursor) {
		int place_idx = cursor.getColumnIndex(EarthQuakesContentProvider.Columns.KEY_PLACE);
		int magnitude_idx = cursor.getColumnIndex(EarthQuakesContentProvider.Columns.KEY_MAGNITUDE);
		int latitude_idx = cursor.getColumnIndex(EarthQuakesContentProvider.Columns.KEY_LOCATION_LAT);
		int longitude_idx = cursor.getColumnIndex(EarthQuakesContentProvider.Columns.KEY_LOCATION_LNG);
		int time_idx = cursor.getColumnIndex(EarthQuakesContentProvider.Columns.KEY_TIME);
		int url_idx = cursor.getColumnIndex(EarthQuakesContentProvider.Columns.KEY_URL);
		
		
		String place = cursor.getString(place_idx);
		String magnitude = cursor.getString(magnitude_idx);
		String latitude = cursor.getString(latitude_idx);
		String longitude = cursor.getString(longitude_idx);
		String time = cursor.getString(time_idx);
		String url = cursor.getString(url_idx);
		
		placeTextView.setText(place);
		magnitudeTextView.setText(magnitude);
		latitudeTextView.setText(latitude);
		longitudeTextView.setText(longitude);
		timeTextView.setText(time);
		urlTextView.setText(url);

	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		
	}

}
