package com.dersugarcia.earthquakes;

import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	private static final String TAG = "EARTHQUAKES";
	private Button createButton, insertButton, selectButton, updateButton, deleteButton, jsonButton;
	private EQDBOpenHelper dbHelper;
	private EarthQuakeDB eqdb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		dbHelper = new EQDBOpenHelper(this, EQDBOpenHelper.DATABASE_NAME, null, EQDBOpenHelper.DATABASE_VERSION);
		eqdb = new EarthQuakeDB(this);
		createButton = (Button) findViewById(R.id.create_button);
		insertButton = (Button) findViewById(R.id.insert_button);
		selectButton = (Button) findViewById(R.id.select_button);
		deleteButton = (Button) findViewById(R.id.delete_button);
		updateButton = (Button) findViewById(R.id.update_button);
		jsonButton = (Button) findViewById(R.id.json_button);
		createButton.setOnClickListener(this);
		insertButton.setOnClickListener(this);
		selectButton.setOnClickListener(this);
		deleteButton.setOnClickListener(this);
		updateButton.setOnClickListener(this);
		jsonButton.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		Button b = (Button)v;
		switch(b.getId()) {
		case (R.id.create_button):
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			Log.d(TAG,db.getPath());
			break;
		case (R.id.select_button):
			int size = eqdb.filterByMagnitude(1.0f).size();
		Log.d(TAG, "Se han recuperado " + size + " filas.");
			break;
		case (R.id.insert_button):
			eqdb.insert(new EarthQuake("Errenteria", new Date().getTime(), "Polígono Egiburuberri", 4.3, 43.296798, -1.895617
					, "https://www.google.es/maps/place/43%C2%B017'48.5%22N+1%C2%B053'44.2%22W/@43.296798,-1.895617,3331m/data=!3m2!1e3!4b1!4m2!3m1!1s0x0:0x0?hl=en"
					));
			break;
		case (R.id.update_button):
			break;
		case (R.id.delete_button):
			eqdb.delete(7);
			break;
		case (R.id.json_button):
			
			getEarthQuakes();
			break;
		default:
			break;
		
		}
		
	}
	
	private void getEarthQuakes() {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				JSONObject json =  ResourceParser.getJSONObject("http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson");
				try {
					JSONArray array = json.getJSONArray("features");
					for(int i=0; i< array.length(); i++) {
						JSONObject eq = array.getJSONObject(i);
						JSONObject props =  eq.getJSONObject("properties");
						JSONArray coordinates =  eq.getJSONObject("geometry").getJSONArray("coordinates");
						EarthQuake e = new EarthQuake(props.getString("place")
								, props.getLong("time"), props.getString("detail"), props.getDouble("mag"), coordinates.getDouble(1),  coordinates.getDouble(0), props.getString("url"));
						Log.d(TAG, "Recuperado terremoto número: " + e.getTime());
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		t.start();
	}
}
