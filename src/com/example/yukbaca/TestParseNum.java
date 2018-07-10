package com.example.yukbaca;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.yuk.baca.R;
import com.yuk.baca.lang.util.NumProc;

public class TestParseNum extends Activity {
	private NumProc numProc;
	private TextView result, lama;
	private Button button, reset;
	private EditText editText;
	private String file;
	private PrintWriter writer;
	private ListView listView;
	private SimpleAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_parse_num);
		numProc = new NumProc();
		result = (TextView) findViewById(R.id.textView1);
		button = (Button) findViewById(R.id.button1);
		reset = (Button) findViewById(R.id.button2);
		editText = (EditText) findViewById(R.id.editText1);
		editText.setText("umur saya 23 tahun");
		lama = (TextView) findViewById(R.id.textView2);
		listView = (ListView) findViewById(R.id.listView1);
		
		
			ArrayList<HashMap<String,Long>>list= new ArrayList<HashMap<String,Long>>();
			for (int i = 0; i < 30; i++) {
				Long t = System.nanoTime();
				HashMap<String, Long>hashMap= new HashMap<String, Long>();
				String strx = numProc.getNatural(editText.getText().toString());
				Long x = System.nanoTime() - t;
				hashMap.put("lama", x);
				list.add(hashMap);
			
				
			}
			adapter= new SimpleAdapter(getApplication(), list, android.R.layout.simple_list_item_1, new String[]{"lama"}, new int[]{android.R.id.text1});
			listView.setAdapter(adapter);
			
			
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_test_parse_num, menu);
		return true;
	}

}
