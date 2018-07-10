package com.yuk.baca.lang.util;

import java.util.ArrayList;
import java.util.Arrays;

public class Syllbreaker {
	public String source;
	ArrayList<String> vowel = new ArrayList<String>(Arrays.asList("a", "i",
			"u", "e", "o"));
	ArrayList<String> src;

	public String sukukatastring(String source) {
		String sukustring = "";
		ArrayList<String> sukus = this.pecah(source);
		for (String string : sukus) {
			sukustring += string + " ";
		}
		return sukustring;
	}

	public ArrayList<String> pecah(String src) {

		 this.source = src;
		ArrayList<String> a = uno();

		ArrayList<String> b = des(a);

		ArrayList<String> c = tres(b);
		return c;
		// return unreplacer(arya);
	}

	public Syllbreaker() {
		// defaul constructor
		

	}

	public ArrayList<String> uno(){
		char[] sr_array = source.toCharArray();
		src = new ArrayList<String>();
		ArrayList<String> hasil = new ArrayList<String>();
		for (char c : sr_array) {
			src.add(String.valueOf(c));
		}
		// int i=0;
		for (int i = 0; i < src.size(); i++) {

			if (vowel.contains(src.get(i))) {
				// detect vokal
				// System.out.println("detect vokal");
				hasil.add(src.get(i));

			} else if (src.get(i).compareTo("n") == 0) {
				// detect n
				// System.out.println("detect n");
				// do next
				if (i + 1 < src.size()) {

					if (src.get(i + 1).compareTo("g") == 0) {
						// detect ng or ny
						// System.out.println("detect ng or ny");
						if (i + 2 < src.size()) {
							if (vowel.contains(src.get(i + 2))) {
								hasil.add("ng" + src.get(i + 2));
								i++;
							} else {
								hasil.add("ng");
							}
						} else {

							hasil.add("ng");
						}
						i++;

					} else if (src.get(i + 1).compareTo("y") == 0) {
						if (i + 2 < src.size()) {
							if (vowel.contains(src.get(i + 2))) {
								hasil.add("ny" + src.get(i + 2));
								i++;
							} else {
								hasil.add("ny");
							}
						} else {

							hasil.add("ny");
						}
						i++;

					} else if (vowel.contains(src.get(i + 1))) {
						// detect kv ('n'v)
						// System.out.println("detect kv ('n'v)");
						hasil.add("n" + src.get(i + 1));
						i++;
					} else {
						hasil.add("n");
					}
				} else {
					hasil.add("n");
				}
			} else if (src.get(i).compareTo("k") == 0) {
				// detect k
				// System.out.println("detect k");
				// do next thing
				if (i + 1 < src.size()) {
					if (src.get(i + 1).compareTo("h") == 0) {
						// detect kh
						// System.out.println("detect kh");
						if (i + 2 < src.size()) {
							if (vowel.contains(src.get(i + 2))) {
								hasil.add("kh" + src.get(i + 2));
								i++;
							} else {
								hasil.add("kh");
							}
						} else {

							hasil.add("kh");
						}
						i++;

					} else if (vowel.contains(src.get(i + 1))) {
						// detect kv ('k'v)
						// System.out.println("detect kv ('k'v)");
						hasil.add("k" + src.get(i + 1));
						i++;
					} else {
						hasil.add("k");
					}
				} else {
					hasil.add("k");
				}
			} else if (src.get(i).compareTo("s") == 0) {
				// detect s
			//	System.out.println("detect s");
				// do next thing
				if (i + 1 < src.size()) {
					if (src.get(i + 1).compareTo("y") == 0) {
						// detect sy
						if (i + 2 < src.size()) {
							if (vowel.contains(src.get(i + 2))) {
								hasil.add("sy" + src.get(i + 2));
								i++;
							} else {
								hasil.add("sy");
							}
						} else {

							hasil.add("sy");
						}
						i++;

					} else if (vowel.contains(src.get(i + 1))) {
						// detect kv ('s'v)
						// System.out.println("detect kv ('s'v)");
						hasil.add("s" + src.get(i + 1));
						i++;
					} else {
						hasil.add("s");
					}
				} else {
					hasil.add("s");
				}

			} else if (src.get(i).compareTo(" ") == 0) {
				// detect space
				// System.out.println("detect space");
				hasil.add(" ");
			} else {
				// detect konsonan
				// System.out.println("detect konsonan");
				// do next thing
				if (i + 1 < src.size()) {
					if (vowel.contains(src.get(i + 1))) {
						// detect kv
						// System.out.println("detect kv");
						hasil.add(src.get(i) + src.get(i + 1));
						i++;
					} else {
						// System.out.println("k");
						hasil.add(src.get(i));
					}
				} else {
					hasil.add(src.get(i));
				}
			}

		}
		return hasil;
	}

	public ArrayList<String> des(ArrayList<String> src) {
		ArrayList<String> hasil = new ArrayList<String>();
		for (int i = 0; i < src.size(); i++) {
			if (!vowel.contains(src.get(i).substring(0,1)) && vowel.contains(src.get(i).substring(src.get(i).length()-1))) {
				// detect kv
				
				if (i + 1 < src.size()) {
					if (!vowel.contains(src.get(i + 1))
							&& src.get(i + 1).length() == 1 || (!vowel
									.contains(src.get(i + 1)
											.substring(0, 1)) && !vowel
									.contains(src.get(i + 1).substring(1)) && src.get(i+1).length()==2)) {
						hasil.add(src.get(i) + src.get(i + 1));
						i++;
					} else {
						hasil.add(src.get(i));
					}
				} else {
					hasil.add(src.get(i));
				}
			} else {
				if (vowel.contains(src.get(i))) {
					// detect v
					if (i + 1 < src.size()) {
						if (!vowel.contains(src.get(i + 1))
								&& (src.get(i + 1).length() == 1 || (!vowel
										.contains(src.get(i + 1)
												.substring(0, 1)) && !vowel
										.contains(src.get(i + 1).substring(1))&& src.get(i+1).length()==2))) {
							hasil.add(src.get(i) + src.get(i + 1));
							i++;
						} else {
							hasil.add(src.get(i));
						}
					} else {
						hasil.add(src.get(i));
					}
				} else {
					// detect k
					if (i + 1 < src.size()) {
						if (src.get(i + 1).length() == 2) {
							// detect kv

							if (i + 2 < src.size()) {
								if (!vowel.contains(src.get(i + 2))
										&& (src.get(i + 2).length() == 1 || (!vowel
												.contains(src.get(i + 2)
														.substring(0, 1)) && !vowel
												.contains(src.get(i + 2).substring(1))&& src.get(i+2).length()==2))) {
									// detect k
									hasil.add(src.get(i) + src.get(i + 1)
											+ src.get(i + 2));
									i++;
								} else {
									hasil.add(src.get(i) + src.get(i + 1));
								}
							} else {
								hasil.add(src.get(i) + src.get(i + 1));
							}
							i++;
						} else if (!vowel.contains(src.get(i + 1)) &&
								(src.get(i + 1).length() == 1 || (!vowel
										.contains(src.get(i + 1)
												.substring(0, 1)) && !vowel
										.contains(src.get(i + 1).substring(1))&& src.get(i+1).length()==2))) {
							// detect k
							// cek masih punya nafas gak :p

							if (i + 2 < src.size()) {
								// masih ada karakter lagi
								// cek apakah dia kv atau bukan
								if (src.get(i + 2).length() == 2) {
									// detect kv
									if (i + 3 < src.size()) {
										if (!vowel.contains(src.get(i + 3))
												&& (src.get(i + 3).length() == 1 || (!vowel
														.contains(src.get(i + 3)
																.substring(0, 1)) && !vowel
														.contains(src.get(i + 3).substring(1))&& src.get(i+3).length()==2))) {
											// detect k
											hasil.add(src.get(i)
													+ src.get(i + 1)
													+ src.get(i + 2)
													+ src.get(i + 3));
											i++;
										} else {
											hasil.add(src.get(i)
													+ src.get(i + 1)
													+ src.get(i + 2));
										}
									} else {
										hasil.add(src.get(i) + src.get(i + 1)
												+ src.get(i + 2));
									}
									i++;
								} else {
									hasil.add(src.get(i));
									hasil.add(src.get(i + 1));

								}

							} else {
								// tidak ada karakter lagi
								// deteksi akhir k dan k
								hasil.add(src.get(i));
								hasil.add(src.get(i + 1));
							}
							i++;
						}
					} else {
						hasil.add(src.get(i));
					}
				}
			}
		}
		return hasil;
	}

	public ArrayList<String> tres(ArrayList<String> src) {
		ArrayList<String> hasil = new ArrayList<String>();
		for (int i = 0; i < src.size(); i++) {
			if (src.get(i).length() == 1 && vowel.contains(src.get(i))) {
				hasil.add(src.get(i));
			} else if (i + 1 < src.size()) {

				if (src.get(i).length() == 2
						&& vowel.contains(src.get(i).substring(0, 1))
						&& !vowel.contains(src.get(i).substring(1))) {
					// detect vk
					if (!vowel.contains(src.get(i + 1))
							&& src.get(i + 1).length() == 1) {
						// detect k
						hasil.add(src.get(i) + src.get(i + 1));
						i++;
					} else {
						hasil.add(src.get(i));
					}

				} else if (src.get(i).length() == 2
						&& !vowel.contains(src.get(i).substring(0, 1))
						&& vowel.contains(src.get(i).substring(1))) {
					// detect kv
					if (vowel.contains(src.get(i + 1))
							&& src.get(i + 1).length() == 1) {
						// detect v
						hasil.add(src.get(i) + src.get(i + 1));
						i++;
					} else {
						hasil.add(src.get(i));
					}
				} else if (src.get(i).length() == 3
						&& !vowel.contains(src.get(i).substring(0, 1))
						&& vowel.contains(src.get(i).substring(1, 2))
						&& !vowel.contains(src.get(i).substring(2))) {
					// detect kvk
					if (!vowel.contains(src.get(i + 1))
							&& (src.get(i + 1).length() == 1 || (!vowel
									.contains(src.get(i + 1)
											.substring(0, 1)) && !vowel
									.contains(src.get(i + 1).substring(1))&& src.get(i+1).length()==2))) {
						// detect k
						hasil.add(src.get(i) + src.get(i + 1));
						i++;
					} else {
						hasil.add(src.get(i));
					}
				} else if (src.get(i).length() == 3
						&& !vowel.contains(src.get(i).substring(0, 1))
						&& !vowel.contains(src.get(i).substring(1, 2))
						&& vowel.contains(src.get(i).substring(2))) {
					// detect kkv
					if (vowel.contains(src.get(i + 1))
							&& src.get(i + 1).length() == 1) {
						// detect v
						hasil.add(src.get(i) + src.get(i + 1));
						i++;
					} else {
						hasil.add(src.get(i));
					}
				} else if (src.get(i).length() == 3
						&& !vowel.contains(src.get(i).substring(0, 1))
						&& !vowel.contains(src.get(i).substring(1, 2))
						&& vowel.contains(src.get(i).substring(2, 3))
						&& !vowel.contains(src.get(i).substring(3))) {
					// detect kkvk
					if (!vowel.contains(src.get(i + 1))
							&& (src.get(i + 1).length() == 1 || (!vowel
									.contains(src.get(i + 1)
											.substring(0, 1)) && !vowel
									.contains(src.get(i + 1).substring(1))&& src.get(i+1).length()==2))) {
						// detect k
						hasil.add(src.get(i) + src.get(i + 1));
						i++;
					} else {
						hasil.add(src.get(i));
					}
				} else if (src.get(i).length() == 3
						&& !vowel.contains(src.get(i).substring(0, 1))
						&& !vowel.contains(src.get(i).substring(1, 2))
						&& !vowel.contains(src.get(i).substring(2, 3))
						&& vowel.contains(src.get(i).substring(3))) {
					// detect kkkv
					if (vowel.contains(src.get(i + 1))
							&& src.get(i + 1).length() == 1) {
						// detect v
						hasil.add(src.get(i) + src.get(i + 1));
						i++;
					} else {
						hasil.add(src.get(i));
					}
				} else {
					hasil.add(src.get(i));
				}

			} else {
				hasil.add(src.get(i));
			}
		}
		return hasil;
	}
}
