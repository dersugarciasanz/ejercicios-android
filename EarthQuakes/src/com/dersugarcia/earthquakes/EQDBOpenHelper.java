package com.dersugarcia.earthquakes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class EQDBOpenHelper extends SQLiteOpenHelper {

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

	public EQDBOpenHelper(Context context, String name, CursorFactory factory,
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
