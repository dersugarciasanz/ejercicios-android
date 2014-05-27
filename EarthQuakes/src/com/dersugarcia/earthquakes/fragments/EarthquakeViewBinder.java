package com.dersugarcia.earthquakes.fragments;

import java.text.SimpleDateFormat;
import java.util.Locale;

import com.dersugarcia.earthquakes.provider.EarthQuakesContentProvider;

import android.database.Cursor;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class EarthquakeViewBinder implements SimpleCursorAdapter.ViewBinder {

	@Override
	public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
		int time = cursor.getColumnIndex(EarthQuakesContentProvider.Columns.KEY_TIME);
		
        if (time == columnIndex) {
        	SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss aaa", Locale.ENGLISH);
            String dateString = sdf.format(cursor.getLong(time));
            
            ((TextView) view).setText(dateString);
            
            return true;
        }
        
        return false;
	}

}
