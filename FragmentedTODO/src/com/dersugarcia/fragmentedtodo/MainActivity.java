package com.dersugarcia.fragmentedtodo;

import com.dersugarcia.fragmentedtodo.InputFragment.ITODO;
import com.dersugarcia.fragmentedtodo.R;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity implements ITODO {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.input_container, new InputFragment())
					.add(R.id.list_container, new MyListFragment(), "listTag").commit();
		}
	}

	@Override
	public void addItem(String item) {
		((MyListFragment) getFragmentManager().findFragmentByTag("listTag"))
				.addItem(item);
		;
	}

}
