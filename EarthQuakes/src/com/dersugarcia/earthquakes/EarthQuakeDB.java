package com.dersugarcia.earthquakes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class EarthQuakeDB {

	private static EarthQuakeDB instance;
	private EQDBOpenHelper dbHelper;
	private SQLiteDatabase db;

	private EarthQuakeDB(Context context) {
		dbHelper = new EQDBOpenHelper(context, EQDBOpenHelper.DATABASE_NAME,
				null, EQDBOpenHelper.DATABASE_VERSION);
	}
	
	public static EarthQuakeDB getInstance(Context context) {
		if (instance == null) {
			instance = new EarthQuakeDB(context);
		}
		return instance;
	}
	
	public void openDB() {
		if(db == null || !db.isOpen()) {
			db = dbHelper.getWritableDatabase();
		}
	}
	
	public void closeDB() {
		db.close();
	}

	public Cursor query(String filter, String filterArgs[]) {
		// Specify the result column projection. Return the minimum set
		// of columns required to satisfy your requirements.
		String[] result_columns = new String[] { EQDBOpenHelper.IDCOLUMN,
				EQDBOpenHelper.PLACECOLUMN, EQDBOpenHelper.TIMECOLUMN,
				EQDBOpenHelper.DETAILCOLUMN, EQDBOpenHelper.MAGNITUDECOLUMN,
				EQDBOpenHelper.LATITUDECOLUMN, EQDBOpenHelper.LONGITUDECOLUMN,
				EQDBOpenHelper.URLCOLUMN, EQDBOpenHelper.CREATEDATCOLUMN,
				EQDBOpenHelper.UPDATEDATCOLUMN };
		// Specify the where clause that will limit our results.
		String where = filter;
		// Replace these with valid SQL statements as necessary.
		String whereArgs[] = filterArgs;
		String groupBy = null;
		String having = null;
		String order = EQDBOpenHelper.TIMECOLUMN + " DESC";

		openDB();
		Cursor cursor = db.query(EQDBOpenHelper.DATABASE_TABLE, result_columns,
				where, whereArgs, groupBy, having, order);
		return cursor;

	}
	
	public List<EarthQuake> filterByMagnitude(double mag) {
		
		List<EarthQuake> list = new ArrayList<EarthQuake>();
		EarthQuake eq;
		
		String filter = EQDBOpenHelper.MAGNITUDECOLUMN + ">= ?";
		String filterArgs[] = {String.valueOf(mag)};
		
		Cursor cursor = query(filter, filterArgs);
		
		
		int idIndex, placeIndex, timeIndex, detailIndex, magnitudeIndex, latitudeIndex, longitudeIndex, urlIndex, createdAtIndex, updatedAtIndex;
		
		idIndex = cursor.getColumnIndexOrThrow(EQDBOpenHelper.IDCOLUMN);
		placeIndex = cursor.getColumnIndexOrThrow(EQDBOpenHelper.PLACECOLUMN);
		timeIndex = cursor.getColumnIndexOrThrow(EQDBOpenHelper.TIMECOLUMN);
		detailIndex = cursor.getColumnIndexOrThrow(EQDBOpenHelper.DETAILCOLUMN);
		magnitudeIndex = cursor.getColumnIndexOrThrow(EQDBOpenHelper.MAGNITUDECOLUMN);
		latitudeIndex = cursor.getColumnIndexOrThrow(EQDBOpenHelper.LATITUDECOLUMN);
		longitudeIndex = cursor.getColumnIndexOrThrow(EQDBOpenHelper.LONGITUDECOLUMN);
		urlIndex = cursor.getColumnIndexOrThrow(EQDBOpenHelper.URLCOLUMN);
		createdAtIndex = cursor.getColumnIndexOrThrow(EQDBOpenHelper.CREATEDATCOLUMN);
		updatedAtIndex = cursor.getColumnIndexOrThrow(EQDBOpenHelper.UPDATEDATCOLUMN);
		
		// Iterate over the cursors rows. 
		while (cursor.moveToNext()) {
			int id = cursor.getInt(idIndex);
			String place = cursor.getString(placeIndex);
			long time = cursor.getLong(timeIndex);
			String detail = cursor.getString(detailIndex);
			double magnitude = cursor.getDouble(magnitudeIndex);
			double latitude = cursor.getDouble(latitudeIndex);
			double longitude = cursor.getDouble(longitudeIndex);
			String url = cursor.getString(urlIndex);
			long created_at  = cursor.getLong(createdAtIndex);
			long updated_at = cursor.getLong(updatedAtIndex);
			
			eq = new EarthQuake(id, place, time, detail, magnitude, latitude, longitude, url, created_at, updated_at);
			list.add(eq);
		}
		cursor.close();
		db.close();
		return list;
	}
	

	public long insert(EarthQuake e) {
		// Create a new row of values to insert.
		ContentValues newValues = new ContentValues();
		Date d = new Date();
		long createdAt = d.getTime();

		// Assign values for each row.
		newValues.put(EQDBOpenHelper.PLACECOLUMN, e.getPlace());
		newValues.put(EQDBOpenHelper.TIMECOLUMN, e.getTime());
		newValues.put(EQDBOpenHelper.DETAILCOLUMN, e.getDetail());
		newValues.put(EQDBOpenHelper.MAGNITUDECOLUMN, e.getMagnitude());
		newValues.put(EQDBOpenHelper.LATITUDECOLUMN, e.getLatitude());
		newValues.put(EQDBOpenHelper.LONGITUDECOLUMN, e.getLongitude());
		newValues.put(EQDBOpenHelper.URLCOLUMN, e.getUrl());
		newValues.put(EQDBOpenHelper.CREATEDATCOLUMN, createdAt);
		newValues.put(EQDBOpenHelper.UPDATEDATCOLUMN, createdAt);

		// Insert the row into your table
		openDB();
		long rowId = db.insert(EQDBOpenHelper.DATABASE_TABLE, null, newValues);
		closeDB();
		return rowId;
	}

	public void delete(int id) {
		// Specify a where clause that determines which row(s) to delete.
		// Specify where arguments as necessary.
		String where = EQDBOpenHelper.IDCOLUMN + " = ?";
		String whereArgs[] = { String.valueOf(id) };
		// Delete the rows that match the where clause.
		openDB();
		db.delete(EQDBOpenHelper.DATABASE_TABLE, where, whereArgs);
		closeDB();
	}

}
