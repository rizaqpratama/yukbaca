package com.yuk.baca;

import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yuk.baca.R;
import com.rnp.flite.FliteAPI;
import com.yuk.baca.adapter.sukugridadapter;
import com.yuk.baca.lang.util.FonemMe;
import com.yuk.baca.lang.util.Syllbreaker;

public class SukuKata extends MyAcitvity implements OnClickListener,
		OnItemClickListener {
	private GridView gridView;
	private TextView textView;
	// private Button btnPlay;
	//private FonemMe me = new FonemMe();
	private Syllbreaker me= new Syllbreaker();
	private sukugridadapter sukugridadapter;
	private ImageButton btn_play;
	private FliteAPI api;
	private String soundPath;
	private String kata;
	private ArrayList<String> sukus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		soundPath = audioWav.getAbsolutePath();
		setContentView(R.layout.activity_suku_kata);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.custom_title_bar);
		kata = getIntent().getStringExtra("kata");
		api = new FliteAPI();
		api.engineStart(dur, tree_dur, mgc, tree_mgc, mgc_win1, mgc_win2,
				mgc_win3, lf0, tree_lf0, lf0_win1, lf0_win2, lf0_win3, gv_mgc,
				tree_gv_mgc, gv_lf0, tree_gv_lf0, gv_switch);
		Log.v("TAG", "kata yg ditangkap " + kata);
		sukus = me.pecah(kata);
		sukugridadapter = new sukugridadapter(this, sukus);
		gridView = (GridView) findViewById(R.id.gridView1);
		gridView.setAdapter(sukugridadapter);
		gridView.setOnItemClickListener(this);
		textView = (TextView) findViewById(R.id.textKata);
		textView.setText(kata);
		btn_play=(ImageButton)findViewById(R.id.imageButton1);
		btn_play.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_suku_kata, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.imageButton1:
			FonemMe me= new FonemMe();
			String haha= me.sukukatastring(kata);
			api.engineGetSound(soundPath, haha, getApplication());
			break;

		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		String suku = sukus.get(arg2);
		Log.v(TAG+" suara yg diproduksi",suku);
		api.engineGetSound(soundPath, suku, getApplication());

	}
}
