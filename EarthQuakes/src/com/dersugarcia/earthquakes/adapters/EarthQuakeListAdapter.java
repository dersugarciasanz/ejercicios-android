package com.dersugarcia.earthquakes.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dersugarcia.earthquakes.R;
import com.dersugarcia.earthquakes.model.EarthQuake;

public class EarthQuakeListAdapter extends BaseAdapter {

	private ArrayList<EarthQuake> data;
	private static LayoutInflater inflater = null;
	
	public EarthQuakeListAdapter(Context context, ArrayList<EarthQuake> data) {
		this.data = data;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.row, null);
        TextView mag = (TextView) vi.findViewById(R.id.magnitude);
        TextView place = (TextView) vi.findViewById(R.id.place);
        TextView time = (TextView) vi.findViewById(R.id.time);
        mag.setText(String.valueOf(data.get(position).getMagnitude()));
        place.setText(data.get(position).getPlace());
        time.setText(DateFormat.format("yyyy-MM-dd hh:mm:ss", data.get(position).getTime()));
        return vi;
	}

}
