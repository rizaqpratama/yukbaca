/*************************************************************************/
/*                                                                       */
/*                  Language Technologies Institute                      */
/*                     Carnegie Mellon University                        */
/*                         Copyright (c) 2000                            */
/*                        All Rights Reserved.                           */
/*                                                                       */
/*  Permission is hereby granted, free of charge, to use and distribute  */
/*  this software and its documentation without restriction, including   */
/*  without limitation the rights to use, copy, modify, merge, publish,  */
/*  distribute, sublicense, and/or sell copies of this work, and to      */
/*  permit persons to whom this work is furnished to do so, subject to   */
/*  the following conditions:                                            */
/*   1. The code must retain the above copyright notice, this list of    */
/*      conditions and the following disclaimer.                         */
/*   2. Any modifications must be clearly marked as such.                */
/*   3. Original authors' names are not deleted.                         */
/*   4. The authors' names are not used to endorse or promote products   */
/*      derived from this software without specific prior written        */
/*      permission.                                                      */
/*                                                                       */
/*  CARNEGIE MELLON UNIVERSITY AND THE CONTRIBUTORS TO THIS WORK         */
/*  DISCLAIM ALL WARRANTIES WITH REGARD TO THIS SOFTWARE, INCLUDING      */
/*  ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS, IN NO EVENT   */
/*  SHALL CARNEGIE MELLON UNIVERSITY NOR THE CONTRIBUTORS BE LIABLE      */
/*  FOR ANY SPECIAL, INDIRECT OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES    */
/*  WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN   */
/*  AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION,          */
/*  ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF       */
/*  THIS SOFTWARE.                                                       */
/*                                                                       */
/*************************************************************************/
/*             Author:  Alan W Black (awb@cs.cmu.edu)                    */
/*               Date:  September 2000                                   */
/*************************************************************************/
/*                                                                       */
/*  General synthesis control                                            */
/*                                                                       */
/*************************************************************************/

/* ----------------------------------------------------------------- */
/*           The HMM-Based Speech Synthesis System (HTS)             */
/*           flite+hts_engine developed by HTS Working Group         */
/*           http://hts-engine.sourceforge.net/                      */
/* ----------------------------------------------------------------- */
/*                                                                   */
/*  Copyright (c) 2005-2009  Nagoya Institute of Technology          */
/*                           Department of Computer Science          */
/*                                                                   */
/*                2005-2008  Tokyo Institute of Technology           */
/*                           Interdisciplinary Graduate School of    */
/*                           Science and Engineering                 */
/*                                                                   */
/* All rights reserved.                                              */
/*                                                                   */
/* Redistribution and use in source and binary forms, with or        */
/* without modification, are permitted provided that the following   */
/* conditions are met:                                               */
/*                                                                   */
/* - Redistributions of source code must retain the above copyright  */
/*   notice, this list of conditions and the following disclaimer.   */
/* - Redistributions in binary form must reproduce the above         */
/*   copyright notice, this list of conditions and the following     */
/*   disclaimer in the documentation and/or other materials provided */
/*   with the distribution.                                          */
/* - Neither the name of the HTS working group nor the names of its  */
/*   contributors may be used to endorse or promote products derived */
/*   from this software without specific prior written permission.   */
/*                                                                   */
/* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND            */
/* CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,       */
/* INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF          */
/* MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE          */
/* DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS */
/* BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,          */
/* EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED   */
/* TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,     */
/* DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON */
/* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,   */
/* OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY    */
/* OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE           */
/* POSSIBILITY OF SUCH DAMAGE.                                       */
/* ----------------------------------------------------------------- */

#include "../include/cst_hrg.h"
#include "../include/cst_cart.h"
#include "../include/cst_tokenstream.h"
#include "../include/cst_utt_utils.h"
#include "../include/cst_lexicon.h"
#ifndef FLITE_PLUS_HTS_ENGINE
#include "../include/cst_units.h"
#endif /* !FLITE_PLUS_HTS_ENIGINE */
#include "../include/cst_synth.h"
#include "../include/cst_phoneset.h"

CST_VAL_REGISTER_FUNCPTR(breakfunc,cst_breakfunc)

#ifndef SYNTH_MODULES_DEBUG
#define SYNTH_MODULES_DEBUG 0
#endif

#if SYNTH_MODULES_DEBUG > 0
#define DPRINTF(l,x) if (SYNTH_MODULES_DEBUG > l) cst_dbgmsg x
#else
#define DPRINTF(l,x)
#endif

static cst_utterance *tokentosegs(cst_utterance *u);

static const cst_synth_module synth_method_text[] = {
    { "tokenizer_func", default_tokenization },
    { "textanalysis_func", default_textanalysis },
    { "pos_tagger_func", NULL },
    { "phrasing_func", default_phrasing },
    { "lexical_insertion_func", default_lexical_insertion },
    { "pause_insertion_func", default_pause_insertion },
    { "intonation_func", cart_intonation },
    { "postlex_func", NULL },
#ifndef FLITE_PLUS_HTS_ENGINE
    { "duration_model_func", cart_duration },
    { "f0_model_func", NULL },
    { "wave_synth_func", NULL },
    { "post_synth_hook_func", NULL },
#endif /* !FLITE_PLUS_HTS_ENIGINE */
    { NULL, NULL }
};

#ifndef FLITE_PLUS_HTS_ENGINE
static const cst_synth_module synth_method_text2segs[] = {
    { "tokenizer_func", default_tokenization },
    { "textanalysis_func", default_textanalysis },
    { "pos_tagger_func", NULL },
    { "phrasing_func", default_phrasing },
    { "lexical_insertion_func", default_lexical_insertion },
    { "pause_insertion_func", default_pause_insertion },
    { NULL, NULL }
};

static const cst_synth_module synth_method_tokens[] = {
    { "textanalysis_func", default_textanalysis },
    { "pos_tagger_func", NULL },
    { "phrasing_func", default_phrasing },
    { "lexical_insertion_func", default_lexical_insertion },
    { "pause_insertion_func", default_pause_insertion },
    { "intonation_func", cart_intonation },
    { "postlex_func", NULL },
    { "duration_model_func", cart_duration },
    { "f0_model_func", NULL },
    { "wave_synth_func", NULL },
    { "post_synth_hook_func", NULL },
    { NULL, NULL }
};

static const cst_synth_module synth_method_phones[] = {
    { "tokenizer_func", default_tokenization },
    { "textanalysis_func", tokentosegs },
    { "intonation_func", NULL },
    { "duration_model_func", cart_duration },
    { "f0_model_func", flat_prosody },
    { "wave_synth_func", NULL },
    { "post_synth_hook_func", NULL },
    { NULL, NULL }
};
#endif /* !FLITE_PLUS_HTS_ENGINE */

cst_utterance *apply_synth_module(cst_utterance *u,
				  const cst_synth_module *mod)
{
    const cst_val *v;

    v = feat_val(u->features, mod->hookname);
    if (v)
	return (*val_uttfunc(v))(u);
    if (mod->defhook)
	return (*mod->defhook)(u);
    return u;
}

cst_utterance *apply_synth_method(cst_utterance *u,
				  const cst_synth_module meth[])
{
    while (meth->hookname)
    {
	if ((u = apply_synth_module(u, meth)) == NULL)
	    return NULL;
	++meth;
    }

    return u;
}

cst_utterance *utt_init(cst_utterance *u, cst_voice *vox)
{
    feat_copy_into(vox->features,u->features);
    feat_copy_into(vox->ffunctions,u->ffunctions);
    if (vox->utt_init)
	vox->utt_init(u, vox);

    return u;
}

cst_utterance *utt_synth(cst_utterance *u)
{
    return apply_synth_method(u, synth_method_text);
}

#ifndef FLITE_PLUS_HTS_ENGINE
cst_utterance *utt_synth_tokens(cst_utterance *u)
{
    return apply_synth_method(u, synth_method_tokens);
}

cst_utterance *utt_synth_text2segs(cst_utterance *u)
{
    return apply_synth_method(u, synth_method_text2segs);
}

cst_utterance *utt_synth_phones(cst_utterance *u)
{
    return apply_synth_method(u, synth_method_phones);
}
#endif /* !FLITE_PLUS_HTS_ENGINE */

cst_utterance *default_tokenization(cst_utterance *u)
{
    const char *text,*token;
    cst_tokenstream *fd;
    cst_item *t;
    cst_relation *r;

    text = utt_input_text(u);
    r = utt_relation_create(u,"Token");
    fd = ts_open_string(text,
	get_param_string(u->features,"text_whitespace",NULL),
	get_param_string(u->features,"text_singlecharsymbols",NULL),
	get_param_string(u->features,"text_prepunctuation",NULL),
        get_param_string(u->features,"text_postpunctuation",NULL));
    
    while(!ts_eof(fd))
    {
	token = ts_get(fd);
	if (strlen(token) > 0)
	{
	    t = relation_append(r,NULL);
	    item_set_string(t,"name",token);
	    item_set_string(t,"whitespace",fd->whitespace);
	    item_set_string(t,"prepunctuation",fd->prepunctuation);
	    item_set_string(t,"punc",fd->postpunctuation);
	    item_set_int(t,"file_pos",fd->file_pos);
	    item_set_int(t,"line_number",fd->line_number);
	}
    }

    ts_close(fd);
    
    return u;
}

cst_val *default_tokentowords(cst_item *i)
{
    return cons_val(string_val(item_feat_string(i,"name")), NULL);
}

cst_utterance *default_textanalysis(cst_utterance *u)
{
    cst_item *t,*word;
    cst_relation *word_rel;
    cst_val *words;
    const cst_val *w;
    const cst_val *ttwv;

    word_rel = utt_relation_create(u,"Word");
    ttwv = feat_val(u->features, "tokentowords_func");

    for (t=relation_head(utt_relation(u,"Token")); t; t=item_next(t))
    {
	if (ttwv)
	    words = (cst_val *)(*val_itemfunc(ttwv))(t);
	else
	    words = default_tokentowords(t);

	for (w=words; w; w=val_cdr(w))
	{
	    word = item_add_daughter(t,NULL);
	    if (cst_val_consp(val_car(w)))
	    {   /* Has extra features */
		item_set_string(word,"name",val_string(val_car(val_car(w))));
		feat_copy_into(val_features(val_cdr(val_car(w))),
			       item_feats(word));
	    }
	    else
		item_set_string(word,"name",val_string(val_car(w)));
	    relation_append(word_rel,word);
	}
	delete_val(words);
    }

    return u;
}

cst_utterance *default_phrasing(cst_utterance *u)
{
    cst_relation *r;
    cst_item *w, *p;
    const cst_val *v;
    cst_cart *phrasing_cart;

    r = utt_relation_create(u,"Phrase");
    phrasing_cart = val_cart(feat_val(u->features,"phrasing_cart"));

    for (p=NULL,w=relation_head(utt_relation(u,"Word")); w; w=item_next(w))
    {
	if (p == NULL)
	{
	    p = relation_append(r,NULL);
	    item_set_string(p,"name","BB");
	}
	item_add_daughter(p,w);
	v = cart_interpret(w,phrasing_cart);
	if (cst_streq(val_string(v),"BB"))
	    p = NULL;
    }
    
    return u;
}

cst_utterance *default_pause_insertion(cst_utterance *u)
{
    /* Add initial silences and silence at each phrase break */
    const char *silence;
    const cst_item *w;
    cst_item *p, *s;

    silence = val_string(feat_val(u->features,"silence"));

    /* Insert initial silence */
    s = relation_head(utt_relation(u,"Segment"));
    if (s == NULL)
	s = relation_append(utt_relation(u,"Segment"),NULL);
    else
	s = item_prepend(s,NULL);
    item_set_string(s,"name",silence);

    for (p=relation_head(utt_relation(u,"Phrase")); p; p=item_next(p))
    {
	for (w = item_last_daughter(p); w; w=item_prev(w))
	{
	    s = path_to_item(w,"R:SylStructure.daughtern.daughtern.R:Segment");
	    if (s)
	    {
		s = item_append(s,NULL);
		item_set_string(s,"name",silence);
		break;
	    }
	}
    }

    return u;
}

cst_utterance *cart_intonation(cst_utterance *u)
{
    cst_cart *accents, *tones;
    cst_item *s;
    const cst_val *v;

    accents = val_cart(feat_val(u->features,"int_cart_accents"));
    tones = val_cart(feat_val(u->features,"int_cart_tones"));
    
    for (s=relation_head(utt_relation(u,"Syllable")); s; s=item_next(s))
    {
	v = cart_interpret(s,accents);
	if (!cst_streq("NONE",val_string(v)))
	    item_set_string(s,"accent",val_string(v));
	v = cart_interpret(s,tones);
	if (!cst_streq("NONE",val_string(v)))
	    item_set_string(s,"endtone",val_string(v));
	DPRINTF(0,("word %s gpos %s stress %s ssyl_in %s ssyl_out %s accent %s endtone %s\n",
		   ffeature_string(s,"R:SylStructure.parent.name"),
		   ffeature_string(s,"R:SylStructure.parent.gpos"),
		   ffeature_string(s,"stress"),
		   ffeature_string(s,"ssyl_in"),
		   ffeature_string(s,"ssyl_out"),
		   ffeature_string(s,"accent"),
		   ffeature_string(s,"endtone")));

    }

    return u;
}

CST_VAL_REGISTER_TYPE_NODEL(dur_stats,dur_stats)

#ifndef FLITE_PLUS_HTS_ENGINE
const dur_stat *phone_dur_stat(const dur_stats *ds,const char *ph)
{
    int i;
    for (i=0; ds[i]; i++)
	if (cst_streq(ph,ds[i]->phone))
	return ds[i];

    return ds[0];
}

cst_utterance *cart_duration(cst_utterance *u)
{
    cst_cart *dur_tree;
    cst_item *s;
    float zdur, dur_stretch, local_dur_stretch, dur;
    float end;
    dur_stats *ds;
    const dur_stat *dur_stat;

    end = 0;

    dur_tree = val_cart(feat_val(u->features,"dur_cart"));
    dur_stretch = get_param_float(u->features,"duration_stretch", 1.0);
    ds = val_dur_stats(feat_val(u->features,"dur_stats"));
    
    for (s=relation_head(utt_relation(u,"Segment")); s; s=item_next(s))
    {
	zdur = val_float(cart_interpret(s,dur_tree));
	dur_stat = phone_dur_stat(ds,item_name(s));

	local_dur_stretch = ffeature_float(s, "R:SylStructure.parent.parent."
					   "R:Token.parent.local_duration_stretch");
	if (local_dur_stretch)
	    local_dur_stretch *= dur_stretch;
	else
	    local_dur_stretch = dur_stretch;

	dur = local_dur_stretch * ((zdur*dur_stat->stddev)+dur_stat->mean);
	DPRINTF(0,("phone %s accent %s stress %s pdur %f stretch %f mean %f std %f dur %f\n",
		   item_name(s),
		   ffeature_string(s,"R:SylStructure.parent.accented"),
		   ffeature_string(s,"R:SylStructure.parent.stress"),
		   zdur, local_dur_stretch, dur_stat->mean,
		   dur_stat->stddev, dur));
	end += dur;
	item_set_float(s,"end",end);
    }
    return u;
}
#endif /* !FLITE_PLUS_HTS_ENIGINE */

cst_utterance *default_lexical_insertion(cst_utterance *u)
{
    cst_item *word;
    cst_relation *sylstructure,*seg,*syl;
    cst_lexicon *lex, *ulex = NULL;
    const cst_val *p;
    char *phone_name;
    char *stress = "0";
    cst_val *phones;
    cst_item *ssword, *sssyl, *segitem, *sylitem, *seg_in_syl;


    lex = val_lexicon(feat_val(u->features,"lexicon"));
    if (feat_present(u->features, "user_lexicon"))
	ulex = val_lexicon(feat_val(u->features, "user_lexicon"));

    syl = utt_relation_create(u,"Syllable");
    sylstructure = utt_relation_create(u,"SylStructure");
    seg = utt_relation_create(u,"Segment");

    for (word=relation_head(utt_relation(u,"Word")); 
	 word; word=item_next(word))
    {
	ssword = relation_append(sylstructure,word);
	phones = NULL;

	/* FIXME: need to make sure that textanalysis won't split
           tokens with explicit pronunciation (or that it will
           propagate such to words, then we can remove the path here) */
	if (item_feat_present(item_parent(item_as(word, "Token")), "phones"))
	    phones = (cst_val *) item_feat(item_parent(item_as(word, "Token")), "phones");
	else
	{
	    if (ulex)
		phones = lex_lookup(ulex,item_feat_string(word, "name"),0);
	    if (phones == NULL)
		phones = lex_lookup(lex,item_feat_string(word,"name"),0);
	}

	for (sssyl=NULL,sylitem=NULL,p=phones; p; p=val_cdr(p))
	{
	    if (sylitem == NULL)
	    {
		sylitem = relation_append(syl,NULL);
		sssyl = item_add_daughter(ssword,sylitem);
		stress = "0";
	    }
	    segitem = relation_append(seg,NULL);
	    phone_name = cst_strdup(val_string(val_car(p)));
	    if (phone_name[strlen(phone_name)-1] == '1')
	    {
		stress = "1";
		phone_name[strlen(phone_name)-1] = '\0';
	    }
	    else if (phone_name[strlen(phone_name)-1] == '0')
	    {
		stress = "0";
		phone_name[strlen(phone_name)-1] = '\0';
	    }
	    item_set_string(segitem,"name",phone_name);
	    seg_in_syl = item_add_daughter(sssyl,segitem);
	    if ((lex->syl_boundary)(seg_in_syl,val_cdr(p)))
	    {
		sylitem = NULL;
		if (sssyl)
		    item_set_string(sssyl,"stress",stress);
	    }
	    cst_free(phone_name);
	}
	if (!item_feat_present(item_parent(item_as(word, "Token")), "phones"))
	    delete_val(phones);
    }

    return u;
}

#ifndef FLITE_PLUS_HTS_ENGINE
/* Dummy F0 modelling for phones, copied directly from us_f0_model.c */
cst_utterance *flat_prosody(cst_utterance *u)
{
    /* F0 target model */
    cst_item *s,*t;
    cst_relation *targ_rel;
    float mean, stddev;

    targ_rel = utt_relation_create(u,"Target");
    mean = get_param_float(u->features,"target_f0_mean", 100.0);
    mean *= get_param_float(u->features,"f0_shift", 1.0);
    stddev = get_param_float(u->features,"target_f0_stddev", 12.0);

    s=relation_head(utt_relation(u,"Segment"));
    t = relation_append(targ_rel,NULL);
    item_set_float(t,"pos",0.0);
    item_set_float(t,"f0",mean+stddev);

    s=relation_tail(utt_relation(u,"Segment"));
    t = relation_append(targ_rel,NULL);

    item_set_float(t,"pos",item_feat_float(s,"end"));
    item_set_float(t,"f0",mean-stddev);

    return u;
}

static cst_utterance *tokentosegs(cst_utterance *u)
{
    cst_item *t;
    cst_relation *seg, *syl, *sylstructure, *word;
    cst_item *sylitem, *sylstructureitem, *worditem, *sssyl;
    cst_phoneset *ps;

    ps = val_phoneset(utt_feat_val(u, "phoneset"));
    /* Just copy tokens into the Segment relation */
    seg = utt_relation_create(u, "Segment");
    syl = utt_relation_create(u, "Syllable");
    word = utt_relation_create(u, "Word");
    sylstructure = utt_relation_create(u, "SylStructure");
    sssyl = sylitem = worditem = sylstructureitem = 0;
    for (t = relation_head(utt_relation(u, "Token")); t; t = item_next(t)) 
    {
	cst_item *segitem = relation_append(seg, NULL);
	char const *pname = item_feat_string(t, "name");
	char *name = cst_strdup(pname);

	if (worditem == 0)
	{
	    worditem = relation_append(word,NULL);
	    item_set_string(worditem, "name", "phonestring");
	    sylstructureitem = relation_append(sylstructure,worditem);
	}
	if (sylitem == 0)
	{
	    sylitem = relation_append(syl,NULL);
	    sssyl = item_add_daughter(sylstructureitem,sylitem);
	}
	
	if (name[strlen(name)-1] == '1')
	{
	    item_set_string(sssyl,"stress","1");
	    name[strlen(name)-1] = '\0';
	}
	else if (name[strlen(name)-1] == '0')
	{
	    item_set_string(sssyl,"stress","0");
	    name[strlen(name)-1] = '\0';
	}

	if (cst_streq(name,"-"))
	{
	    sylitem = 0;  /* syllable break */
	}
	else if (phone_id(ps, name) == -1) 
	{
	    cst_errmsg("Phone `%s' not in phoneset\n", pname);
	    cst_error();
	}
	else
	{
	    item_add_daughter(sssyl,segitem);
	    item_set_string(segitem, "name", name);
	}

	cst_free(name);
    }

    return u;
}
#endif /* !FLITE_PLUS_HTS_ENIGINE */

int default_utt_break(cst_tokenstream *ts,
		      const char *token,
		      cst_relation *tokens)
{
    /* This is the default utt break functions, languages may override this */
    /* This will be ok for some latin based languages */
    const char *postpunct = item_feat_string(relation_tail(tokens), "punc");
    const char *ltoken = item_name(relation_tail(tokens));

    if (cst_strchr(ts->whitespace,'\n') != cst_strrchr(ts->whitespace,'\n'))
	 /* contains two new lines */
	 return TRUE;
    else if (strchr(postpunct,':') ||
	     strchr(postpunct,'?') ||
	     strchr(postpunct,'!'))
	return TRUE;
    else if (strchr(postpunct,'.') &&
	     (strlen(ts->whitespace) > 1) &&
	     strchr("ABCDEFGHIJKLMNOPQRSTUVWXYZ",token[0]))
	return TRUE;
    else if (strchr(postpunct,'.') &&
	     /* next word starts with a capital */
	     strchr("ABCDEFGHIJKLMNOPQRSTUVWXYZ",token[0]) &&
	     /* last word isn't an abbreviation */
	     !(strchr("ABCDEFGHIJKLMNOPQRSTUVWXYZ",ltoken[strlen(ltoken)-1])||
	       ((strlen(ltoken) < 4) &&
		strchr("ABCDEFGHIJKLMNOPQRSTUVWXYZ",ltoken[0]))))
	return TRUE;
    else
	return FALSE;
}
