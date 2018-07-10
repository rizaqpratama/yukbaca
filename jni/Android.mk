LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_LDLIBS += -llog
LOCAL_CFLAGS    := 	-DFLITE_PLUS_HTS_ENGINE \
					-DNO_UNION_INITIALIZATION
LOCAL_MODULE    := flite_hts
LOCAL_SRC_FILES :=  FliteAPI.c \
					src/cmu_lex.c \
					src/cmu_lex_data.c \
					src/cmu_lex_entries.c \
					src/cmu_lts_model.c \
					src/cmu_lts_rules.c \
					src/cmu_us_kal.c \
					src/cst_alloc.c \
					src/cst_cart.c \
					src/cst_error.c \
					src/cst_features.c \
					src/cst_ffeature.c \
					src/cst_file_stdio.c \
					src/cst_item.c \
					src/cst_lexicon.c \
					src/cst_lts.c \
					src/cst_phoneset.c \
					src/cst_regex.c \
					src/cst_relation.c \
					src/cst_string.c \
					src/cst_synth.c \
					src/cst_tokenstream.c \
					src/cst_utt_utils.c \
					src/cst_utterance.c \
					src/cst_val.c \
					src/cst_val_const.c \
					src/cst_val_user.c \
					src/cst_voice.c \
					src/flite.c \
					src/flite_hts_engine.c \
					src/regexp.c \
					src/us_aswd.c \
					src/us_expand.c \
					src/us_ffeatures.c \
					src/us_gpos.c \
					src/us_int_accent_cart.c \
					src/us_int_tone_cart.c \
					src/us_nums_cart.c \
					src/us_phoneset.c \
					src/us_phrasing_cart.c \
					src/us_postlex.c \
					src/us_text.c \
					src/usenglish.c \
					src/HTS_engine.c \
					src/HTS_audio.c \
					src/HTS_gstream.c \
					src/HTS_label.c \
					src/HTS_misc.c \
					src/HTS_model.c \
					src/HTS_pstream.c \
					src/HTS_sstream.c \
					src/HTS_vocoder.c
LOCAL_EXPORT_C_FILES := include \
						$(LOCAL_PATH)
include $(BUILD_SHARED_LIBRARY)
