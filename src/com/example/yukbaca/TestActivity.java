package com.example.yukbaca;

import java.io.File;
import java.io.IOException;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.rnp.flite.FliteAPI;
import com.yuk.baca.MyAcitvity;
import com.yuk.baca.R;
import com.yuk.baca.lang.util.FonemMe;

public class TestActivity extends MyAcitvity implements OnClickListener {
	private EditText editText;
	private FliteAPI api;
	private File audioWav;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		Log.v(TAG,dur);
		Log.v(TAG,tree_dur);
		Log.v(TAG,mgc);
		Log.v(TAG,tree_mgc);
		Log.v(TAG,mgc_win1);
		Log.v(TAG,mgc_win2);
		Log.v(TAG,mgc_win3);
		Log.v(TAG,lf0);
		Log.v(TAG,tree_lf0);
		Log.v(TAG,lf0_win1);
		Log.v(TAG,lf0_win2);
		Log.v(TAG,lf0_win3);
		Log.v(TAG,gv_mgc);
		Log.v(TAG,tree_gv_mgc);
		Log.v(TAG,gv_lf0);
		Log.v(TAG,tree_gv_lf0);
		Log.v(TAG,gv_switch);
		api= new FliteAPI();
		//FliteAPI api= new FliteAPI();
		api.engineStart(dur, tree_dur, mgc, tree_mgc, mgc_win1, mgc_win2,
				mgc_win3, lf0, tree_lf0, lf0_win1, lf0_win2, lf0_win3, gv_mgc,
				tree_gv_mgc, gv_lf0, tree_gv_lf0, gv_switch);
		audioWav= new File(DATA_PATH+"sound.wav");
		try {
			audioWav.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		editText= (EditText)findViewById(R.id.editText1);
		Button button= (Button)findViewById(R.id.button1);
		button.setOnClickListener(this);
		System.out.println(audioWav.getPath());
		String src="aa. paa. kaa. barg";
		try{
			api.engineGetSound(audioWav.getAbsolutePath(),src, getApplicationContext());
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_test, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		String aa= editText.getText().toString();
		FonemMe fonemMe= new FonemMe();
		//aa=fonemMe.sukukatastring(aa);
		api.engineGetSound(audioWav.getAbsolutePath(), aa, getApplicationContext());
	}

}
