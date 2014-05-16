package com.dersugarcia.internet;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.app.DownloadManager.Request;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final String TAG = "INTERNET";
	private ArrayList<Photo> photos;
	private long myDownloadReference;
	private DownloadManager downloadManager;
	String serviceString = Context.DOWNLOAD_SERVICE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button p = (Button) findViewById(R.id.button_photos);
		Button z = (Button) findViewById(R.id.button_zip);
		p.setOnClickListener(new PhotosClickListener());
		z.setOnClickListener(new ZipClickListener());
		photos = new ArrayList<Photo>();
		downloadManager = (DownloadManager) getSystemService(serviceString);
		
		IntentFilter filter = new IntentFilter(
				DownloadManager.ACTION_DOWNLOAD_COMPLETE);
		BroadcastReceiver receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				long reference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
				if (myDownloadReference == reference) {
					
					
					Query successfulDownloadQuery = new Query(); 
					successfulDownloadQuery.setFilterById(myDownloadReference);
					Cursor successfulDownloads = downloadManager.query(successfulDownloadQuery);
					int sizeIdx = successfulDownloads.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
					int localUriIdx = successfulDownloads.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);
					while (successfulDownloads.moveToNext()) {
						int size = successfulDownloads.getInt(sizeIdx);
						String name = successfulDownloads.getString(localUriIdx);
						Toast.makeText(MainActivity.this,
								"Download Complete: " + name + "\n Size: " + size,
								Toast.LENGTH_LONG).show();

					}
					successfulDownloads.close();
					
//					Uri uri = downloadManager.getUriForDownloadedFile(reference);
//					Toast.makeText(MainActivity.this,
//							"Download Complete: " + uri.getPath(),
//							Toast.LENGTH_LONG).show();
				}
			}
		};
		registerReceiver(receiver, filter);
	}

	private class PhotosClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					String myFeed = getString(R.string.my_feed);
					JSONObject json = ResourceParser.getJSONObject(myFeed);
					processJSON(json);
				}
			});
			t.start();
		}
	}

	private class ZipClickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			downloadZip();
		}

	}

	public void processJSON(JSONObject json) {
		try {
			Log.d(TAG, json.toString());
			JSONArray array = (JSONArray) json.get("photos");
			for (int i = 0; i < array.length(); i++) {
				JSONObject photo = (JSONObject) array.get(i);
				int id = (Integer) photo.get("id");
				String url = (String) photo.get("image_url");
				Log.d(TAG, url);
				photos.add(new Photo(id, url));
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void downloadZip() {
		downloadManager = (DownloadManager) getSystemService(serviceString);
		Uri uri = Uri
				.parse("http://developer.android.com/shareables/icon_templates-v4.0.zip");
		DownloadManager.Request request = new Request(uri);
		request.setDestinationInExternalFilesDir(this, serviceString,
				uri.getLastPathSegment());
		request.setNotificationVisibility(Request.VISIBILITY_HIDDEN);
		myDownloadReference = downloadManager.enqueue(request);

	}

}
