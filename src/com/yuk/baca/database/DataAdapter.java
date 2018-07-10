package com.yuk.baca.database;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class DataAdapter {

	public static final String DATABASE_NAME = "dataKata";
	public static final String TABLE_NAME = "kata";
	public static final int DATABASE_VERSION = 1;
	public SQLiteDatabase db;
	private SQLiteStatement insertStmt;
	private SQLiteStatement deleteStmt;
	private static final String INSERT = "insert into " + TABLE_NAME
			+ "(kata)values (?)";
	private static final String DELETE = "delete from " + TABLE_NAME
			+ " where id_kata=(?)";

	public DataAdapter(Context context) {
		super();
		DataHelper dataHelper = new DataHelper(context);
		this.db = dataHelper.getWritableDatabase();
		this.insertStmt = this.db.compileStatement(INSERT);
		this.deleteStmt = this.db.compileStatement(DELETE);
		
	}

	public long insert(String kata) {
		this.insertStmt.bindString(1, kata);
		return this.insertStmt.executeInsert();
	}

	public void delete(int id_kata) {
		this.deleteStmt.bindLong(1, id_kata);
		this.deleteStmt.execute();
	}

	public void deletAll() {

		db.execSQL("DROP TABLE " + TABLE_NAME);

		db.execSQL("create table " + TABLE_NAME
				+ "(id_kata integer primary key, kata text)");

	}

	public ArrayList<HashMap<String, String>> selectAll() {
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		Cursor cursor = this.db.query(TABLE_NAME, new String[] { "kata" },
				null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("kata", cursor.getString(0));
				list.add(map);
			} while (cursor.moveToNext());

		}
		cursor.close();
		return list;
	}

	public String select(int id_kata) {
		String rt = "";
		Cursor cursor = this.db.query(TABLE_NAME, new String[] { "kata" },
				"id_kata ='" + id_kata + "'", null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				rt = cursor.getString(0);
			} while (cursor.moveToNext());

		}
		cursor.close();
		return rt;
	}

	public static class DataHelper extends SQLiteOpenHelper {

		public DataHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase arg0) {
			// TODO Auto-generated method stub
			arg0.execSQL("create table " + TABLE_NAME
					+ "(id_kata integer primary key, kata text)");
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			Log.v("warning", "database will be dropped");
			arg0.execSQL("drop table if exist " + TABLE_NAME);
			onCreate(arg0);

		}

	}
}
