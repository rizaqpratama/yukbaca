package com.yuk.baca;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;

/**
 * @author rizaqpratama
 * 
 */

public class MyAcitvity extends Activity {

	File audioWav= new File(DATA_PATH+"v.wav");
	/**
	 * just a TAG :p 
	 */
	public static final String TAG = "YukBaca";
	
	/**
	 * Simpen2 file di alamat ini yaaa 
	 */
	public static final String DATA_PATH = Environment
			.getExternalStorageDirectory().toString() + "/yukbacaData/";
	
	/**
	 * force to use english :( 
	 */
	public static final String lang = "eng";

	/**
	 * konstanta file duration untuk fp_ts_dur
	 */
	public static final String tree_dur = DATA_PATH + "hts_voice/tree-dur.inf";

	/**
	 * konstanta file tree lf0 untuk fp_ts_lf0
	 */
	public static final String tree_lf0 = DATA_PATH + "hts_voice/tree-lf0.inf";

	/**
	 * konstanta file tree mgc untuk fp_ts_mcp
	 */
	public static final String tree_mgc = DATA_PATH + "hts_voice/tree-mgc.inf";

	/**
	 * konstanta file pdf dur untuk fp_ms_dur
	 */
	public static final String dur = DATA_PATH + "hts_voice/dur.pdf";

	/**
	 * konstanta file pdf lf0 untuk fp_ms_lf0
	 */
	public static final String lf0 = DATA_PATH + "hts_voice/lf0.pdf";

	/**
	 * konstanta file pdf mgc untuk fp_ms_mcp
	 */
	public static final String mgc = DATA_PATH + "hts_voice/mgc.pdf";

	/**
	 * konstanta file delta window lf0 fp_ws_lf0[0]
	 */
	public static final String lf0_win1 = DATA_PATH + "hts_voice/lf0.win1";
	/**
	 * konstanta file delta window lf0 fp_ws_lf0[1]
	 */
	public static final String lf0_win2 = DATA_PATH + "hts_voice/lf0.win2";
	/**
	 * konstanta file delta window lf0 fp_ws_lf0[2]
	 */
	public static final String lf0_win3 = DATA_PATH + "hts_voice/lf0.win3";
	/**
	 * konstanta file delta window mel-cepstrum fp_ws_mcp[0]
	 */
	public static final String mgc_win1 = DATA_PATH + "hts_voice/mgc.win1";
	/**
	 * konstanta file delta window mel-cepstrum fp_ws_mcp[1]
	 */
	public static final String mgc_win2 = DATA_PATH + "hts_voice/mgc.win2";
	/**
	 * konstanta file delta window mel-cepstrum fp_ws_mcp[2]
	 */
	public static final String mgc_win3 = DATA_PATH + "hts_voice/mgc.win3";
	/**
	 * konstanta file global variance lf0 fp_ms_gvl
	 */
	public static final String gv_lf0 = DATA_PATH + "hts_voice/gv-lf0.pdf";

	/**
	 * konstanta file global variance mcp fp_ms_gvm
	 */
	public static final String gv_mgc = DATA_PATH + "hts_voice/gv_mgc.pdf";
	
	/**
	 *konstanta file tree global variance lf0 fp_ts_gvl 
	 */
	public static final String tree_gv_lf0 = DATA_PATH
			+ "hts_voice/tree_gv_lf0.inf";
	
	/**
	 *konstanta file tree global variance mcp fp_ts_gvm 
	 */
	public static final String tree_gv_mgc = DATA_PATH
			+ "hts_voice/tree_gv_mgc.inf";
	
	/**
	 *konstanta file switch global variance  fp_gv_switch 
	 */
	public static final String gv_switch = DATA_PATH
			+ "hts_voice/gv-switch.inf";
	public Typeface ghFont;
	// public Typeface ghFont= Typeface.createFromAsset(mgr, path)(getAssets(),
	// "gh.ttf");
	@Override
	protected void onCreate(Bundle bundle){
		super.onCreate(bundle);
		ghFont= Typeface.createFromAsset(getAssets(), "gh.ttf");
		try {
			audioWav.createNewFile();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
