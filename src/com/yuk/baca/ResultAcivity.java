package com.yuk.baca;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.yuk.baca.R;
import com.yuk.baca.database.DataAdapter;
import com.yuk.baca.lang.util.FonemMe;
import com.yuk.baca.lang.util.NumProc;
import com.yuk.baca.lang.util.Syllbreaker;

public class ResultAcivity extends MyAcitvity implements OnItemClickListener,
		OnClickListener {
	private String kalimat;
	private String[] katas;
	private ListView listKata;
	private TextView txtKalimat;
	private Button btn_simpan;
	private DataAdapter dbAdapter;

	private NumProc numProc;
	private ArrayList<HashMap<String, String>> fillList = new ArrayList<HashMap<String, String>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_result_acivity);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.custom_title_bar);
		
		//FonemMe me = new FonemMe();
		Syllbreaker me= new Syllbreaker();
		TextView t_time= (TextView)findViewById(R.id.txt_time);
		t_time.setText(String.valueOf(getIntent().getLongExtra("lama", 0)));
		listKata = (ListView) findViewById(R.id.listView1);
		txtKalimat = (TextView) findViewById(R.id.textKalimat);
		numProc = new NumProc();
		try {
			kalimat = getIntent().getStringExtra("kalimat");

			if (kalimat.trim().compareTo("") == 0) {
				AlertDialog.Builder dialog = new AlertDialog.Builder(this);
				dialog.setTitle("Tidak ada karakter dikenali!");
				dialog.setMessage("Apakah anda akan mengambil gambar lagi?");
				dialog.setPositiveButton("Baiklah",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								// ambil gambar lagi
								Intent i = new Intent(getApplication(),
										CamActivity.class);

								startActivity(i);
							}
						});
				dialog.setNegativeButton("Tidak",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								// kembali ke menu utama
								Intent intent = new Intent(getApplication(),
										MainActivity.class);
								startActivity(intent);

							}
						});
				dialog.show();
			}
			kalimat = numProc.getNatural(kalimat);
			txtKalimat.setText(kalimat);
			katas = kalimat.split(" ");
			for (String kata : katas) {
				HashMap<String, String> item = new HashMap<String, String>();
				item.put("kata", kata);
				item.put("suku_kata", me.sukukatastring(kata));
				fillList.add(item);
			}
			SimpleAdapter adapter = new SimpleAdapter(this, fillList,
					R.layout.listview_kata,
					new String[] { "kata", "suku_kata" }, new int[] {
							R.id.textKata, R.id.textSukuKata });
			listKata.setAdapter(adapter);
			listKata.setOnItemClickListener(this);
			btn_simpan = (Button) findViewById(R.id.btn_simpan);
			btn_simpan.setOnClickListener(this);
			dbAdapter = new DataAdapter(getApplication());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// header.setTypeface(ghFont);
		// subheader.setTypeface(ghFont);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_result_acivity, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		HashMap<String, String> selected = fillList.get(arg2);
		String kata = selected.get("kata");
		Log.v(TAG, "kata yg dpilih :" + kata);
		Intent sukuIntent = new Intent(this, SukuKata.class);
		sukuIntent.putExtra("kata", kata);
		startActivity(sukuIntent);

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.btn_simpan:
			if (kalimat != null) {
				for (String kata : katas) {
					dbAdapter.insert(kata);
				}
				Toast.makeText(getApplication(),
						"Berhasil disimpan ke database", Toast.LENGTH_SHORT)
						.show();
			} else {
				Toast.makeText(getApplication(), "Tidak ada teks disimpan",
						Toast.LENGTH_SHORT).show();
			}
			break;

		default:
			break;
		}
	}

}
