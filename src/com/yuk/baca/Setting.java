package com.yuk.baca;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.yuk.baca.database.DataAdapter;

public class Setting extends PreferenceActivity implements OnClickListener {
	private Button resetDbButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.layout.activity_setting);
		setContentView(R.layout.setting_layout_wrap);
		resetDbButton = (Button) findViewById(R.id.btn_rst_db);
		resetDbButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_rst_db:
			DataAdapter adapter = new DataAdapter(getApplication());
			adapter.deletAll();
			Toast.makeText(getApplication(), "Database telah direset",
					Toast.LENGTH_SHORT).show();
			break;

		default:
			Toast.makeText(getApplication(), "Not implemented yet",
					Toast.LENGTH_SHORT).show();

			break;
		}
	}

	
}
