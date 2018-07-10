package com.example.yukbaca;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.yuk.baca.MyAcitvity;
import com.yuk.baca.R;
import com.yuk.baca.lang.util.Syllbreaker;

public class TestFonemMe extends MyAcitvity {
	
	private TextView textView,tv;
	// private Button btnPlay;
	private Syllbreaker me = new Syllbreaker();
	

	private String kata;
	private ArrayList<String> sukus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

		setContentView(R.layout.activity_test_fonem_me);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.custom_title_bar);
		kata = "perubahan";
		textView=(TextView)findViewById(R.id.textView1);
		textView.setText(kata);
		tv=(TextView)findViewById(R.id.textView2);
		
		ArrayList<HashMap<String, Long>> data= new ArrayList<HashMap<String,Long>>();
		for(int i=0;i<30;i++){
			HashMap<String, Long>hashMap = new HashMap<String, Long>();
			Long t= System.nanoTime();
			sukus = me.pecah(kata);
			Long s= System.nanoTime()-t;
			hashMap.put("waktu", s);
			data.add(hashMap);
		}
		tv.setText(sukus.toString());
		SimpleAdapter adapter= new SimpleAdapter(getApplication(), data,android.R.layout.simple_list_item_1, new String[]{"waktu"}, new int[]{android.R.id.text1});
		ListView listView= (ListView)findViewById(R.id.listView1);
		listView.setAdapter(adapter);
		// btn_play.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_suku_kata, menu);
		return true;
	}

}
