package com.yuk.baca;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Bitmap.Config;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.yuk.baca.R;
import com.googlecode.tesseract.android.TessBaseAPI;

@SuppressLint("DefaultLocale")
public class CamActivity extends MyAcitvity {

	private static final int CAMERA_REQ_CODE = 212;
	private static final int CROP_REQ_CODE = 122;
	private static final int CROP_CODE = 100;
	private Uri picUri;
	private boolean enableBW;
	String _path;
	private SharedPreferences preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cam);
		capture();
	}
	
	
	private void capture(){
		_path = DATA_PATH + "/ocr.jpg";
		preferences = PreferenceManager
				.getDefaultSharedPreferences(getApplication());
		enableBW = preferences.getBoolean("add_bw", false);
		
		File file = new File(_path);
		Uri imgUri = Uri.fromFile(file);
		try {
			Intent intentCam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intentCam.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
			startActivityForResult(intentCam, CAMERA_REQ_CODE);

		} catch (ActivityNotFoundException exception) {
			// TODO: handle exception
			Toast.makeText(this, "Gagal mengambil gambar", Toast.LENGTH_SHORT)
					.show();
		}

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_cam, menu);
		return true;

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i(TAG, "resultCode: " + resultCode);
		switch (requestCode) {
		case CROP_CODE:

			break;

		case CAMERA_REQ_CODE:

			doOcr(data);
			break;

		default:
			break;
		}

	}

	// to be implemented

	private Bitmap doBW(Bitmap b) {

		int h = b.getHeight();
		int w = b.getWidth();
		Bitmap c = Bitmap.createBitmap(w, h, Config.ARGB_8888);

		int[][] gray = new int[h][w];

		int thr = 128;
		int[][] pixel = new int[h][w];
		for (int i = 0; i < b.getHeight() - 1; i++) {
			for (int j = 0; j < b.getWidth() - 1; j++) {
				pixel[i][j] = b.getPixel(j, i);
				gray[i][j] = (int) Math.round(0.3 * Color.red(pixel[i][j])
						+ 0.59 * Color.green(pixel[i][j]) + 0.11
						* Color.blue(pixel[i][j]));
				if (gray[i][j] < thr) {
					c.setPixel(j, i, Color.BLACK);
				} else {
					c.setPixel(j, i, Color.WHITE);
				}

			}
		}

		return c;
	}

	/**
	 * @param data
	 */
	private void doOcr(Intent data) {
		/*
		 * Bundle extra = data.getExtras(); if (extra != null) { Bitmap bitmap =
		 * extra.getParcelable("data");
		 */
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 4;
		Long time = System.currentTimeMillis();
		Bitmap bitmap = BitmapFactory.decodeFile(_path, options);
		try {
			// auto rotate agar mudah diproses
			ExifInterface exifInterface = new ExifInterface(_path);
			int exifOrientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);

			int rotate = 0;
			switch (exifOrientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				rotate = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				rotate = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				rotate = 270;
				break;

			default:
				break;
			}

			if (rotate != 0) {
				int w = bitmap.getWidth();
				int h = bitmap.getHeight();

				Matrix matrix = new Matrix();
				matrix.preRotate(rotate);
				bitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, false);

			}
			// - end auto rotate

			// convert ke ARGB_8888
			bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
			Log.v(TAG+" enable BW",String.valueOf(enableBW));
			if (enableBW) {
				bitmap = doBW(bitmap);
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "gagal mengubah orientasi");
		}

		// inisialisasi engine ocr
		TessBaseAPI api = new TessBaseAPI();
		api.setDebug(true);
		api.init(DATA_PATH, lang);
		api.setImage(bitmap);

		// ambil teks dari gambar
		// may be add some cropping before here...

		String recString = api.getUTF8Text();
		// filtering non karakter normal
		recString = recString.replaceAll("[^a-zA-Z0-9]+", " ");
		recString = recString.toLowerCase();
		long endTime= System.currentTimeMillis()-time;
		api.end();
		Intent resIntent = new Intent(this, ResultAcivity.class);
		Log.v(TAG, "recognized TEXT :" + recString);
		resIntent.putExtra("kalimat", recString);
		resIntent.putExtra("lama", endTime);
		startActivity(resIntent);
		Log.v(TAG + "waktu selesai",
				String.valueOf(endTime));

		/* } */
	}

	private void performCrop() {
		try {
			Intent crop = new Intent("com.android.camera.action.CROP");
			crop.setDataAndType(picUri, "image/*");
			crop.putExtra("crop", "true");
			crop.putExtra("aspectX", 1);
			crop.putExtra("aspectY", 1);
			crop.putExtra("outputX", 256);
			crop.putExtra("outputY", 256);
			crop.putExtra("return-data", true);
			startActivityForResult(crop, CROP_CODE);
		} catch (ActivityNotFoundException e) {
			// TODO: handle exception
			Toast.makeText(getApplication(), "Not implemented yet",
					Toast.LENGTH_SHORT).show();
		}
	}
}
