package com.dersugarcia.fragmentedtodo;

import java.util.ArrayList;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class MyListFragment extends ListFragment {
	
	private ArrayList<String> list;
	private ArrayAdapter<String> adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		list = new ArrayList<String>();
		adapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1, list);
		setListAdapter(adapter);
		
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	public void addItem(String item) {
		list.add(0,item);
		adapter.notifyDataSetChanged();
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if(savedInstanceState != null) {
			list.addAll(savedInstanceState.getStringArrayList("ARRAYLIST"));
			adapter.notifyDataSetChanged();
		}
	}
	
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putStringArrayList("ARRAYLIST", list);
		super.onSaveInstanceState(outState);
	}
	
}
