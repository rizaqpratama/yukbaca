/*
 * FliteAPI.c
 *
 *  Created on: May 23, 2013
 *      Author: rizaqpratama
 */
#include <string.h>
#include <jni.h>
#include <android/log.h>
#include "com_rnp_flite_FliteAPI.h"
#include "include/flite_hts_engine.h"

Flite_HTS_Engine engine;

JNIEXPORT jboolean JNICALL Java_com_rnp_flite_FliteAPI_inisialisasi(JNIEnv *env,
		jobject this, jint sampling_rate, jint fperiod, jdouble alpha,
		jdouble stage, jdouble beta, jint audio_buff_size, jdouble uv_threshold,
		jboolean use_log_gain, jdouble gv_weight_mcp, jdouble gv_weight_lf0) {

	Flite_HTS_Engine_initialize(&engine, sampling_rate, fperiod, alpha, stage,
			beta, audio_buff_size, uv_threshold, use_log_gain, gv_weight_mcp,
			gv_weight_lf0);
	return 0;
}

JNIEXPORT jboolean JNICALL Java_com_rnp_flite_FliteAPI_start(JNIEnv * env,
		jobject obj, jstring fp_ms_dur, jstring fp_ts_dur, jstring fp_ms_mcp,
		jstring fp_ts_mcp, jstring fp_ws_mcp1, jstring fp_ws_mcp2,
		jstring fp_ws_mcp3, jstring fp_ms_lf0, jstring fp_ts_lf0,
		jstring fp_ws_lf01, jstring fp_ws_lf02, jstring fp_ws_lf03,
		jstring fp_ms_gvm, jstring fp_ts_gvm, jstring fp_ms_gvl,
		jstring fp_ts_gvl, jstring fp_gv_switch) {

	const char *fp_ms_durr = (*env)->GetStringUTFChars(env, fp_ms_dur, 0);
	const char *fp_ts_durr = (*env)->GetStringUTFChars(env, fp_ts_dur, 0);
	const char *fp_ms_mcpr = (*env)->GetStringUTFChars(env, fp_ms_mcp, 0);
	const char *fp_ts_mcpr = (*env)->GetStringUTFChars(env, fp_ts_mcp, 0);
	const char *fp_ws_mcpr1 = (*env)->GetStringUTFChars(env, fp_ws_mcp1, 0);
	const char *fp_ws_mcpr2 = (*env)->GetStringUTFChars(env, fp_ws_mcp2, 0);
	const char *fp_ws_mcpr3 = (*env)->GetStringUTFChars(env, fp_ws_mcp3, 0);
	const char *fp_ms_lf0r = (*env)->GetStringUTFChars(env, fp_ms_lf0, 0);
	const char *fp_ts_lf0r = (*env)->GetStringUTFChars(env, fp_ts_lf0, 0);
	const char *fp_ws_lf0r1 = (*env)->GetStringUTFChars(env, fp_ws_lf01, 0);
	const char *fp_ws_lf0r2 = (*env)->GetStringUTFChars(env, fp_ws_lf02, 0);
	const char *fp_ws_lf0r3 = (*env)->GetStringUTFChars(env, fp_ws_lf03, 0);
	const char *fp_ms_gvmr = (*env)->GetStringUTFChars(env, fp_ms_gvm, 0);
	const char *fp_ts_gvmr = (*env)->GetStringUTFChars(env, fp_ts_gvm, 0);
	const char *fp_ms_gvlr = (*env)->GetStringUTFChars(env, fp_ms_gvl, 0);
	const char *fp_ts_gvlr = (*env)->GetStringUTFChars(env, fp_ts_gvl, 0);
	const char *fp_gv_switchr = (*env)->GetStringUTFChars(env, fp_gv_switch, 0);

	//file pointer untuk model
	FILE *fp_ms_lf0f = fopen(fp_ms_lf0r, "r");
	FILE *fp_ms_mcpf = fopen(fp_ms_mcpr, "r");
	FILE *fp_ms_durf = fopen(fp_ms_durr, "r");

	//file pointer untuk tree
	FILE *fp_ts_lf0f = fopen(fp_ts_lf0r, "r");
	FILE *fp_ts_mcpf = fopen(fp_ts_mcpr, "r");
	FILE *fp_ts_durf = fopen(fp_ts_durr, "r");

	//file pointer untuk global variance
	FILE *fp_ms_gvlf = fopen(fp_ms_gvlr, "r");
	FILE *fp_ms_gvmf = fopen(fp_ms_gvmr, "r");

	//file pointer untuk global variance tree
	FILE *fp_ts_gvlf = fopen(fp_ts_gvlr, "r");
	FILE *fp_ts_gvmf = fopen(fp_ts_gvmr, "r");

	FILE *fp_gv_switchf = fopen(fp_gv_switchr, "r");

	FILE **fp_ws_lf0 = (FILE **) calloc(3, sizeof(FILE));

	FILE **fp_ws_mcp = (FILE **) calloc(3, sizeof(FILE));

	//add the window mel-cepstral
	fp_ws_mcp[0] = fopen(fp_ws_mcpr1, "r");
	fp_ws_mcp[1] = fopen(fp_ws_mcpr2, "r");
	fp_ws_mcp[2] = fopen(fp_ws_mcpr3, "r");

	//add the window fo log f0
	fp_ws_lf0[0] = fopen(fp_ws_lf0r1, "r");
	fp_ws_lf0[1] = fopen(fp_ws_lf0r2, "r");
	fp_ws_lf0[2] = fopen(fp_ws_lf0r3, "r");

	int num_ws_mcp = 2;
	int num_ws_lf0 = 2;
	Flite_HTS_Engine_load(&engine, fp_ms_durf, fp_ts_durf, fp_ms_mcpf,
			fp_ts_mcpf, fp_ws_mcp, num_ws_mcp, fp_ms_lf0f, fp_ts_lf0f,
			fp_ws_lf0, num_ws_lf0, fp_ms_gvmf, fp_ts_gvmf, fp_ms_gvlf,
			fp_ts_gvlf, fp_gv_switchf);
	(*env)->ReleaseStringUTFChars(env,fp_ms_dur,fp_ms_durr);
	(*env)->ReleaseStringUTFChars(env,fp_ts_dur,fp_ts_durr);
	(*env)->ReleaseStringUTFChars(env,fp_ms_mcp,fp_ms_mcpr);
	(*env)->ReleaseStringUTFChars(env,fp_ts_mcp,fp_ts_mcpr);
	(*env)->ReleaseStringUTFChars(env,fp_ws_mcp1,fp_ws_mcpr1);
	(*env)->ReleaseStringUTFChars(env,fp_ws_mcp2,fp_ws_mcpr2);
	(*env)->ReleaseStringUTFChars(env,fp_ws_mcp3,fp_ws_mcpr3);
	(*env)->ReleaseStringUTFChars(env,fp_ts_lf0,fp_ts_lf0r);
	(*env)->ReleaseStringUTFChars(env,fp_ws_lf01,fp_ws_lf0r1);
	(*env)->ReleaseStringUTFChars(env,fp_ws_lf02,fp_ws_lf0r2);
	(*env)->ReleaseStringUTFChars(env,fp_ws_lf03,fp_ws_lf0r3);
	(*env)->ReleaseStringUTFChars(env,fp_ms_gvm,fp_ms_gvmr);
	(*env)->ReleaseStringUTFChars(env,fp_ts_gvm,fp_ts_gvmr);
	(*env)->ReleaseStringUTFChars(env,fp_ms_gvl,fp_ms_gvlr);
	(*env)->ReleaseStringUTFChars(env,fp_ts_gvl,fp_ts_gvlr);
	(*env)->ReleaseStringUTFChars(env,fp_gv_switch,fp_gv_switchr);
}

JNIEXPORT jboolean JNICALL Java_com_rnp_flite_FliteAPI_getSound
  (JNIEnv *env, jobject obj, jstring wavFile, jstring src ){
	const char *s=(*env)->GetStringUTFChars(env,wavFile,0);
	 char *x=(*env)->GetStringUTFChars(env,src,0);

	FILE *file_pinter= fopen(s,"w");
	Flite_HTS_Engine_synthesis(&engine,x,file_pinter);
	(*env)->ReleaseStringUTFChars(env,wavFile,s);
	(*env)->ReleaseStringUTFChars(env,src,x);
	return 1;
}

