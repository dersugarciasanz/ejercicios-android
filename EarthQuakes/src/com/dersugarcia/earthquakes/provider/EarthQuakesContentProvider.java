package com.dersugarcia.earthquakes.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;

public class EarthQuakesContentProvider extends ContentProvider {
	private static final String AUTHORITY = "com.dersugarcia.provider.earthquakes";
	private static final int ALLROWS = 1;
	private static final int SINGLE_ROW = 2;

	public static final Uri CONTENT_URI = Uri
			.parse("content://" + AUTHORITY + "/elements");
	private static final UriMatcher uriMatcher;
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORITY, "elements", ALLROWS);
		uriMatcher.addURI(AUTHORITY, "elements/#", SINGLE_ROW);
	}
	
	
	public static final class Columns implements BaseColumns {
		// Column Names
		public static final String KEY_ID_STR = "id_str";
		public static final String KEY_PLACE = "place";
		public static final String KEY_TIME = "time";
		public static final String KEY_LOCATION_LAT = "latitude";
		public static final String KEY_LOCATION_LNG = "longitude";
		public static final String KEY_MAGNITUDE = "magnitude";
		public static final String KEY_URL = "url";
		public static final String KEY_CREATED_AT = "created_at";
		public static final String KEY_UPDATED_AT = "updated_at";
	}
	public static final String[] KEYS_ALL = { Columns._ID, Columns.KEY_ID_STR,
		Columns.KEY_PLACE, Columns.KEY_TIME, Columns.KEY_LOCATION_LAT,
		Columns.KEY_LOCATION_LNG, Columns.KEY_MAGNITUDE, Columns.KEY_URL };
	
	private EarthquakeDatabaseHelper dbHelper;

	@Override
	public boolean onCreate() {
		Context context = getContext();

		dbHelper = new EarthquakeDatabaseHelper(context,
				EarthquakeDatabaseHelper.DATABASE_NAME, null,
				EarthquakeDatabaseHelper.DATABASE_VERSION);

		return true;
		
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteDatabase database = dbHelper.getWritableDatabase();

		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		qb.setTables(EarthquakeDatabaseHelper.EARTHQUAKE_TABLE);

		// If this is a row query, limit the result set to the passed in row.
		switch (uriMatcher.match(uri)) {
		case SINGLE_ROW:
			qb.appendWhere(Columns._ID + "=" + uri.getPathSegments().get(1));
			break;
		default:
			break;
		}

		// If no sort order is specified, sort by date / time
		String orderBy;
		if (TextUtils.isEmpty(sortOrder)) {
			orderBy = Columns.KEY_TIME + " DESC";
		} else {
			orderBy = sortOrder;
		}

		// Apply the query to the underlying database.
		Cursor c = qb.query(database, projection, selection, selectionArgs,
				null, null, orderBy);

		// Register the contexts ContentResolver to be notified if
		// the cursor result set changes.
		c.setNotificationUri(getContext().getContentResolver(), uri);

		// Return a cursor to the query result.
		return c;
	}
	
	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
		case ALLROWS:
			return "vnd.android.cursor.dir/vnd.dersugarcia.provider.earthquakes";
		case SINGLE_ROW:
			return "vnd.android.cursor.item/vnd.dersugarcia.provider.earthquakes";
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
	}
	
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase database = dbHelper.getWritableDatabase();

		long now = System.currentTimeMillis();

		values.put(Columns.KEY_CREATED_AT, now);
		values.put(Columns.KEY_UPDATED_AT, now);

		long rowID = database.insert(EarthquakeDatabaseHelper.EARTHQUAKE_TABLE,
				null, values);

		if (rowID > -1) {
			// Construct and return the URI of the newly inserted row.
			Uri insertedId = ContentUris.withAppendedId(CONTENT_URI, rowID);

			// Notify any observers of the change in the data set.
			getContext().getContentResolver().notifyChange(insertedId, null);

			return insertedId;
		}

		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO: Implement this to handle requests to update one or more rows.
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// Implement this to handle requests to delete one or more rows.
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
	private class EarthquakeDatabaseHelper extends SQLiteOpenHelper {

		public static final String DATABASE_NAME = "earthquakes.db";
		public static final String EARTHQUAKE_TABLE = "earthquakes";
		public static final int DATABASE_VERSION = 1;

		// SQL Statement to create a new database.
		private static final String DATABASE_CREATE = "create table " + EARTHQUAKE_TABLE + " (" 
				+ Columns._ID + " integer primary key autoincrement, " 
				+ Columns.KEY_ID_STR + " TEXT UNIQUE, " 
				+ Columns.KEY_TIME + " INTEGER, "
				+ Columns.KEY_PLACE + " TEXT, " 
				+ Columns.KEY_LOCATION_LAT + " FLOAT, " 
				+ Columns.KEY_LOCATION_LNG + " FLOAT, "
				+ Columns.KEY_MAGNITUDE + " FLOAT, " 
				+ Columns.KEY_URL + " TEXT," 
				+ Columns.KEY_CREATED_AT + " INTEGER, "
				+ Columns.KEY_UPDATED_AT + " INTEGER" + ");";

		private EarthquakeDatabaseHelper(Context context, String name, CursorFactory factory,
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
			db.execSQL("DROP TABLE IF EXISTS " + EARTHQUAKE_TABLE);
			// Create a new one.
			onCreate(db);
		}

	}
}
