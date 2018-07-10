package com.rnp.flite;

import java.io.File;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

/**
 * @author rizaqpratama bridge anatara file jni dengan java
 */
public class FliteAPI {
	static {
		try {
			System.loadLibrary("flite_hts");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// modelfile
	public String fp_ms_dur;
	public String fp_ms_mcp;
	public String fp_ms_lf0;
	public MediaPlayer player = new MediaPlayer();
	// tree
	public String fp_ts_dur;
	public String fp_ts_mcp;
	public String fp_ts_lf0;

	// window
	public String fp_ws_mcp1;
	public String fp_ws_mcp2;
	public String fp_ws_mcp3;
	public String fp_ws_lf01;
	public String fp_ws_lf02;
	public String fp_ws_lf03;

	/* int num_ws_mcp = 0, num_ws_lf0 = 0; */

	// global variance model
	public String fp_ms_gvm;
	public String fp_ms_gvl;

	// global variance tree
	public String fp_ts_gvm;
	public String fp_ts_gvl;

	// global var switch

	public String fp_gv_switch;

	// global paramater

	int sampling_rate = 16000;
	int fperiod = 80;
	double alpha = 0.42;
	double stage = 0.0;
	double beta = 0.0;
	int audio_buff_size = 1600;
	double uv_threshold = 0.5;
	boolean use_log_gain = false;
	double gv_weight_lf0 = 0.7;
	double gv_weight_mcp = 1.0;

	/**
	 * konstruktor default
	 */
	public FliteAPI() {
		// TODO Auto-generated constructor stub
		engineInit();
	}

	/**
	 * inisialisasi engine tanpa parameter
	 * 
	 * @return
	 */
	public boolean engineInit() {
		return inisialisasi(sampling_rate, fperiod, alpha, stage, beta,
				audio_buff_size, uv_threshold, use_log_gain, gv_weight_mcp,
				gv_weight_lf0);
	}

	/**
	 * 
	 * inisialisasi engine
	 * 
	 * @param sampling_rate
	 * @param fperiod
	 * @param alpha
	 * @param stage
	 * @param beta
	 * @param audio_buff_size
	 * @param uv_threshold
	 * @param use_log_gain
	 * @param gv_weight_mcp
	 * @param gv_weight_lf0
	 * @return
	 */
	public boolean engineInit(int sampling_rate, int fperiod, double alpha,
			double stage, double beta, int audio_buff_size,
			double uv_threshold, boolean use_log_gain, double gv_weight_mcp,
			double gv_weight_lf0) {
		this.sampling_rate = sampling_rate;
		this.fperiod = fperiod;
		this.alpha = alpha;
		this.stage = stage;
		this.beta = beta;
		this.audio_buff_size = audio_buff_size;
		this.uv_threshold = uv_threshold;
		this.use_log_gain = use_log_gain;
		this.gv_weight_mcp = gv_weight_mcp;
		this.gv_weight_lf0 = gv_weight_lf0;

		return inisialisasi(sampling_rate, fperiod, alpha, stage, beta,
				audio_buff_size, uv_threshold, use_log_gain, gv_weight_mcp,
				gv_weight_lf0);
	}

	/**
	 * 
	 * API untuk mesetiing parameter voice
	 * 
	 * @param fp_ms_durx
	 * @param fp_ts_durx
	 * @param fp_ms_mcpx
	 * @param fp_ts_mcpx
	 * @param fp_ws_mcpx
	 * @param fp_ms_lf0x
	 * @param fp_ts_lf0x
	 * @param fp_ms_gvmx
	 * @param fp_ts_gvmx
	 * @param fp_ms_gvlx
	 * @param fp_ts_gvlx
	 * @param fp_gv_switchx
	 * @return
	 */
	public boolean engineStart(String fp_ms_durx, String fp_ts_durx,
			String fp_ms_mcpx, String fp_ts_mcpx, String fp_ws_mcpx1,
			String fp_ws_mcpx2, String fp_ws_mcpx3, String fp_ms_lf0x,
			String fp_ts_lf0x, String fp_ws_lf0x1, String fp_ws_lf0x2,
			String fp_ws_lf0x3, String fp_ms_gvmx, String fp_ts_gvmx,
			String fp_ms_gvlx, String fp_ts_gvlx, String fp_gv_switchx) {
		this.fp_ms_dur = fp_ms_durx;
		this.fp_ts_dur = fp_ts_durx;
		this.fp_ms_mcp = fp_ms_mcpx;
		this.fp_ts_mcp = fp_ts_mcpx;
		this.fp_ws_mcp1 = fp_ws_mcpx1;
		this.fp_ws_mcp2 = fp_ws_mcpx2;
		this.fp_ws_mcp3 = fp_ws_mcpx3;

		this.fp_ws_lf01 = fp_ws_lf0x1;
		this.fp_ws_lf02 = fp_ws_lf0x2;
		this.fp_ws_lf03 = fp_ws_lf0x3;
		this.fp_ms_lf0 = fp_ms_lf0x;
		this.fp_ts_lf0 = fp_ts_lf0x;
		this.fp_ms_gvm = fp_ms_gvmx;
		this.fp_ts_gvm = fp_ts_gvmx;
		this.fp_ms_gvl = fp_ms_gvlx;
		this.fp_ts_gvl = fp_ts_gvlx;
		this.fp_gv_switch = fp_gv_switchx;
		return start(fp_ms_dur, fp_ts_dur, fp_ms_mcp, fp_ts_mcp, fp_ws_mcp1,
				fp_ws_mcp2, fp_ws_mcp3, fp_ms_lf0, fp_ts_lf0, fp_ws_lf01,
				fp_ws_lf02, fp_ws_lf03, fp_ms_gvm, fp_ts_gvm, fp_ms_gvl,
				fp_ts_gvl, fp_gv_switch);
	}

	/**
	 * 
	 * fungsi untuk mendapatkan file wav
	 * 
	 * @param file
	 * @param src
	 * @return
	 */
	public boolean engineGetSound(String file, String src, Context context) {
		// char[] x = src.toCharArray();
		ThreadStone stone = new ThreadStone(file, src, context);
		stone.start();

		return true;
	}

	public class ThreadStone extends Thread implements Runnable {
		private String file;
		private String src;
		private Context context;

		public ThreadStone(String file, String src, Context context) {
			// TODO Auto-generated constructor stub
			this.file = file;
			this.src = src;
			this.context = context;
		}

		public void run() {
			boolean c = getSound(file, src);
			if (c) {
				try {
					MediaPlayer mediaPlayer = MediaPlayer.create(context,
							Uri.fromFile(new File(file)));

					mediaPlayer.start();

				} catch (Exception e) { // TODO: handle exception
					Log.v("failed to create sound", "hahaha");
					e.printStackTrace();

				}
			}

		}
	}

	/* native method */
	public native boolean inisialisasi(int sampling_rate, int fperiod,
			double alpha, double stage, double beta, int audio_buff_size,
			double uv_threshold, boolean use_log_gain, double gv_weight_mcp,
			double gv_weight_lf0);

	public native boolean start(String fp_ms_dur, String fp_ts_dur,
			String fp_ms_mcp, String fp_ts_mcp, String fp_ws_mcp1,
			String fp_ws_mcp2, String fp_ws_mcp3, String fp_ms_lf0,
			String fp_ts_lf0, String fp_ws_lf01, String fp_ws_lf02,
			String fp_ws_lf03, String fp_ms_gvm, String fp_ts_gvm,
			String fp_ms_gvl, String fp_ts_gvl, String fp_gv_switch);

	public native boolean getSound(String file, String src);

}
