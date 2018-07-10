package com.yuk.baca;

import com.yuk.baca.lang.util.Syllbreaker;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PecahSukuKata extends Activity {
	EditText editText;
	TextView textView;
	Button button;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pecah_suku_kata);
		editText=(EditText)findViewById(R.id.txt_src);
		textView= (TextView)findViewById(R.id.textView1);
		button=(Button)findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String src= editText.getText().toString();
				Syllbreaker syllbreaker= new Syllbreaker();
				String hasil = syllbreaker.sukukatastring(src);
				textView.setText(hasil);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_pecah_suku_kata, menu);
		return true;
	}

}
