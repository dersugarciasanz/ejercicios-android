package com.dersugarcia.earthquakes.activities;

import com.dersugarcia.earthquakes.R;
import com.dersugarcia.earthquakes.fragments.MyListFragment;
import com.dersugarcia.earthquakes.model.EarthQuake;
import com.dersugarcia.earthquakes.provider.EarthQuakesContentProvider;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class DetailActivity extends Activity {


	private long id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		Intent i = getIntent();
		id = i.getLongExtra(MyListFragment.ID, 0);
		Toast.makeText(this, "Earthquake id: " + id, Toast.LENGTH_SHORT).show();
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent i = new Intent(this, SettingsActivity.class);
			startActivity(i);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment implements LoaderCallbacks<Cursor>  {
		private static int LOADER_ID = 2;
		private String[] from = { EarthQuakesContentProvider.Columns.KEY_TIME,
				EarthQuakesContentProvider.Columns.KEY_MAGNITUDE,
				EarthQuakesContentProvider.Columns.KEY_PLACE,
				EarthQuakesContentProvider.Columns._ID };
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_detail,
					container, false);
			getLoaderManager().initLoader(LOADER_ID, null, this);
			return rootView;
		}

		@Override
		public CursorLoader onCreateLoader(int id, Bundle bundle) {
			long rowId = ((DetailActivity)getActivity()).getId();
			String where = EarthQuakesContentProvider.Columns._ID + " = ? ";
			String[] whereArgs = { String.valueOf(rowId) };
			
			Uri uri = ContentUris.withAppendedId(EarthQuakesContentProvider.CONTENT_URI, rowId);
			CursorLoader loader = new CursorLoader(getActivity(),
					uri, from, where, whereArgs,
					null);

			return loader;
		}


		@Override
		public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onLoaderReset(Loader<Cursor> arg0) {
			// TODO Auto-generated method stub
			
		}
	}

	public long getId() {
		return id;
	}

}
