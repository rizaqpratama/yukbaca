package com.yuk.baca;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.yuk.baca.R;
import com.yuk.baca.database.DataAdapter;

public class LibraryActivity extends MyAcitvity implements OnItemClickListener {
	private DataAdapter adapter;
	private ListView listView;
	ArrayList<HashMap<String, String>> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_library);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.custom_title_bar);
		adapter = new DataAdapter(getApplication());
		listView = (ListView) findViewById(R.id.listView1);
		list = adapter.selectAll();
		SimpleAdapter adapter = new SimpleAdapter(getApplication(), list,
				android.R.layout.simple_list_item_1, new String[] { "kata" },
				new int[] { android.R.id.text1 });
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		TextView textView = (TextView) findViewById(R.id.text_pembuka);
		textView.setTypeface(ghFont);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_library, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

		HashMap<String, String> selected = list.get(arg2);
		String kata = selected.get("kata");
		Intent intent = new Intent(getApplication(), SukuKata.class);
		intent.putExtra("kata", kata);
		startActivity(intent);

	}
}
