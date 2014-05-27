package com.dersugarcia.earthquakes.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.net.Uri;

public class EarthQuakesContentProvider extends ContentProvider {


	public static final Uri CONTENT_URI = Uri
			.parse("content://com.dersugarcia.provider.earthquakes/elements");

	public EarthQuakesContentProvider() {
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// Implement this to handle requests to delete one or more rows.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public String getType(Uri uri) {
		// TODO: Implement this to handle requests for the MIME type of the data
		// at the given URI.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO: Implement this to handle requests to insert a new row.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public boolean onCreate() {
		// TODO: Implement this to initialize your content provider on startup.
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO: Implement this to handle query requests from clients.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO: Implement this to handle requests to update one or more rows.
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
	private class EQDBOpenHelper extends SQLiteOpenHelper {

		public static final String DATABASE_NAME = "earthquakes.db";
		public static final String DATABASE_TABLE = "earthquakes";
		public static final int DATABASE_VERSION = 1;

		public static final String IDCOLUMN = "_id";
		public static final String PLACECOLUMN = "place";
		public static final String TIMECOLUMN = "time";
		public static final String DETAILCOLUMN = "detail";
		public static final String MAGNITUDECOLUMN = "magnitude";
		public static final String LATITUDECOLUMN = "latitude";
		public static final String LONGITUDECOLUMN = "longitude";
		public static final String URLCOLUMN = "url";
		public static final String CREATEDATCOLUMN = "created_at";
		public static final String UPDATEDATCOLUMN = "updated_at";

		// SQL Statement to create a new database.
		private static final String DATABASE_CREATE = "create table "
				+ DATABASE_TABLE + " (" 
				+ IDCOLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ PLACECOLUMN + " TEXT, " 
				+ TIMECOLUMN + " NUMERIC UNIQUE, "
				+ DETAILCOLUMN + " TEXT, " 
				+ MAGNITUDECOLUMN + " REAL, " 
				+ LATITUDECOLUMN + " REAL, " 
				+ LONGITUDECOLUMN + " REAL, " 
				+ URLCOLUMN + " TEXT, " 
				+ CREATEDATCOLUMN + " NUMERIC, "
				+ UPDATEDATCOLUMN + " NUMERIC);";

		private EQDBOpenHelper(Context context, String name, CursorFactory factory,
				int version) {
			super(context, name, factory, version);
		}

		// Called when no db exists in disk
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE);
		}
		

		// Called when there is a database version mismatch
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// Simplest case is to drop the old table and create a new one.
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			// Create a new one.
			onCreate(db);
		}

	}
}
