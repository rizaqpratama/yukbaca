package com.yuk.baca;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yukbaca.TestFonemMe;
import com.example.yukbaca.TestParseNum;
import com.yuk.baca.R;

/**
 * @author rizaqpratama
 *         <p>
 *         main Activity for menu and etc
 * 
 *         </p>
 */
public class MainActivity extends MyAcitvity implements OnClickListener {
	private ImageButton btn_camera, btn_lib, btn_setting, btn_info;
	private TextView t1, t2, t3, t4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

		setContentView(R.layout.activity_main);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.custom_title_bar);
		t1 = (TextView) findViewById(R.id.txt_ambil_gambar);
		t1.setTypeface(ghFont);
		t2 = (TextView) findViewById(R.id.txt_ambil_lib);
		t2.setTypeface(ghFont);
		t3 = (TextView) findViewById(R.id.txt_pengaturan);
		t3.setTypeface(ghFont);
		t4 = (TextView) findViewById(R.id.txt_tentang_aplikasi);
		t4.setTypeface(ghFont);

		btn_camera = (ImageButton) findViewById(R.id.img_btn_cam);
		btn_camera.setOnClickListener(this);
		btn_lib = (ImageButton) findViewById(R.id.img_btn_lib);
		btn_lib.setOnClickListener(this);
		btn_setting = (ImageButton) findViewById(R.id.img_btn_setting);
		btn_setting.setOnClickListener(this);
		btn_info = (ImageButton) findViewById(R.id.img_btn_about);
		btn_info.setOnClickListener(this);

		// FonemMe me= new FonemMe();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.fonem:
			Intent intent = new Intent(getApplication(), TestFonemMe.class);
			startActivity(intent);
			break;
		case R.id.parse_num:
			Intent intent2= new Intent(getApplication(), TestParseNum.class);
			startActivity(intent2);
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.img_btn_cam:
			Intent camIntent = new Intent(this, CamActivity.class);
			startActivity(camIntent);

			break;
		case R.id.img_btn_about:
			Intent intent = new Intent(this, AboutActivuty.class);
			startActivity(intent);
			break;
		case R.id.img_btn_lib:
			Intent intent2 = new Intent(this, LibraryActivity.class);
			startActivity(intent2);
			break;
		case R.id.img_btn_setting:
			Intent intent3 = new Intent(this, Setting.class);
			startActivity(intent3);
			break;

		default:
			Toast.makeText(this, "Sorry not implemented yet :p",
					Toast.LENGTH_SHORT).show();
			break;
		}

	}

}
