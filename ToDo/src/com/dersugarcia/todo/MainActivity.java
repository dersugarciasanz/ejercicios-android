package com.dersugarcia.todo;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.todo.R;

public class MainActivity extends Activity {

	private ArrayList<String> list;
	private ArrayAdapter<String> adapter;
	private EditText editText;
	private ListView listView;
	private final String ARRAYLIST = "ARRAYLIST";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		list = new ArrayList<String>();
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
		editText = (EditText)findViewById(R.id.editText);
		listView = (ListView)findViewById(R.id.listView);
		
		listView.setAdapter(adapter);
		
		editText.setOnKeyListener(new View.OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(event.getAction() == KeyEvent.ACTION_DOWN) {
					if((keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER)) {
						list.add(0,editText.getText().toString());
						adapter.notifyDataSetChanged();
						editText.setText("");
						return true;
					}
				}
				return false;
			}
		});
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putStringArrayList(ARRAYLIST, list);
		super.onSaveInstanceState(outState);
	}
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		list.addAll(savedInstanceState.getStringArrayList(ARRAYLIST));
		
	}

}
