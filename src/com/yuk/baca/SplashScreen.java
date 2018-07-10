package com.yuk.baca;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.yuk.baca.R;
import com.yuk.baca.database.DataAdapter;

public class SplashScreen extends MyAcitvity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_layout);
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(getApplication());
		boolean fr = preferences.getBoolean("fr", false);
		if(!fr){
			DataAdapter adapter= new DataAdapter(getApplication());
			adapter.insert("harapan");
			adapter.insert("supaya");
			adapter.insert("mengkhawatirkan");
			adapter.insert("mengapa");
			adapter.insert("memakai");
			adapter.insert("memakan");
			adapter.insert("terancam");
			adapter.insert("dilakukan");
			adapter.insert("diterapkan");
			adapter.insert("dipunyai");
			SharedPreferences.Editor editor= preferences.edit();
			editor.putBoolean("fr", true);
			editor.commit();
		}
		Thread timer = new Thread() {
			public void run() {
				try {
					// do some initialization stuff
					// ngopi2 file untuk data tesseractnya :p
					copyAssetData();
					// sleep a while, buat keren2 aja sih :v
					sleep(1000);

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				} finally {
					Intent i = new Intent(getApplicationContext(),
							MainActivity.class);
					startActivity(i);

				}
			}

		};
		timer.start();
	}

	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu) { // Inflate the
	 * menu; this adds items to the action bar if it is present.
	 * getMenuInflater().inflate(R.menu.activity_main, menu); return true; }
	 */

	private void copyAssetData() {
		// cek dulu ada gak sih fodernya...
		String[] paths = new String[] { DATA_PATH, DATA_PATH + "tessdata/",
				DATA_PATH + "hts_voice/" };
		for (String path : paths) {
			File dir = new File(path);
			if (!dir.exists()) {
				if (!dir.mkdirs()) {
					Log.v(TAG, "ERROR: gagal bikin direktori cuy..");
					return;
				} else {
					Log.v(TAG, "Berhasil bikin direktori, selamat!");
				}

			}
		}

		// kopi file ke direktori tadi..

		// 1 kopi tessdata
		kopiFile("tessdata/"+lang+".traineddata", DATA_PATH + "tessdata/" + lang
				+ ".traineddata");

		// 2 kopi voice hts yg terembed

		kopiFile("hts_voice/dur.pdf", DATA_PATH + "hts_voice/dur.pdf");
		kopiFile("hts_voice/gv-lf0.pdf", DATA_PATH + "hts_voice/gv-lf0.pdf");
		kopiFile("hts_voice/gv-mgc.pdf", DATA_PATH + "hts_voice/gv-mgc.pdf");
		kopiFile("hts_voice/gv-switch.inf", DATA_PATH
				+ "hts_voice/gv-switch.inf");
		kopiFile("hts_voice/lf0.pdf", DATA_PATH + "hts_voice/lf0.pdf");
		kopiFile("hts_voice/lf0.win1", DATA_PATH + "hts_voice/lf0.win1");
		kopiFile("hts_voice/lf0.win2", DATA_PATH + "hts_voice/lf0.win2");
		kopiFile("hts_voice/lf0.win3", DATA_PATH + "hts_voice/lf0.win3");
		kopiFile("hts_voice/mgc.pdf", DATA_PATH + "hts_voice/mgc.pdf");
		kopiFile("hts_voice/mgc.win1", DATA_PATH + "hts_voice/mgc.win1");
		kopiFile("hts_voice/mgc.win2", DATA_PATH + "hts_voice/mgc.win2");
		kopiFile("hts_voice/mgc.win3", DATA_PATH + "hts_voice/mgc.win3");
		kopiFile("hts_voice/tree-dur.inf", DATA_PATH + "hts_voice/tree-dur.inf");
		kopiFile("hts_voice/tree-gv-lf0.inf", DATA_PATH
				+ "hts_voice/tree-gv-lf0.inf");
		kopiFile("hts_voice/tree-gv-mgc.inf", DATA_PATH
				+ "hts_voice/tree-gv-mgc.inf");
		kopiFile("hts_voice/tree-lf0.inf", DATA_PATH + "hts_voice/tree-lf0.inf");
		kopiFile("hts_voice/tree-mgc.inf", DATA_PATH + "hts_voice/tree-mgc.inf");
	}

	private void kopiFile(String source, String target) {
		/* if(!(new File(DATA_PATH+"tessdata/"+lang+".traineddata")).exists()){ */
		if (!(new File(target)).exists()) {
			try {
				AssetManager assetManager = getAssets();
				InputStream inputStream = assetManager.open(source);
				OutputStream outputStream = new FileOutputStream(target);

				// copy the file
				byte[] buf = new byte[1024];
				int len;
				while ((len = inputStream.read(buf)) > 0) {
					outputStream.write(buf, 0, len);
				}
				inputStream.close();
				outputStream.close();
				Log.v(TAG, "Berhasil dikopi");
			} catch (IOException e) {
				// TODO: handle exception
				Log.v(TAG, "Gagal kopi file");
				e.printStackTrace();
				Log.v(TAG, e.getMessage());
			}

		}
	}
}
