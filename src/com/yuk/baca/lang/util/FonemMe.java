package com.yuk.baca.lang.util;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author rizaqpratama
 * 
 */
public class FonemMe {

	// list karakter vokal
	ArrayList<String> vokal = new ArrayList<String>(Arrays.asList("a", "i",
			"u", "e", "o"));
	// list awalan kata
	// awalan kata yang umunya mengalami perubahan bunyi karena pertemuan dengan
	// huruf pertama dari kata induk
	// contoh be + api = berapi
	ArrayList<String> awalan = new ArrayList<String>(Arrays.asList("be", "me",
			"pe"));

	// map suku kata dengan karakter simbol penggantinya
	ArrayList<Pair<String, String>> kdift = new ArrayList<Pair<String, String>>(
			Arrays.asList(new Pair<String, String>("kh", "$"),
					new Pair<String, String>("ng", "%"),
					new Pair<String, String>("ny", "^"),
					new Pair<String, String>("sy", "&"),
					new Pair<String, String>("tr", "*"),
					new Pair<String, String>("g", "(")));

	/**
	 * @param String
	 *            n : kata-kata yang akan di filter
	 * @param m
	 *            : Pair antara suku kata yang akan di filter dengan simbol
	 *            penggantinya
	 * @return
	 */
	private String replacer(String n, ArrayList<Pair<String, String>> m) {
		// TODO Auto-generated method stub

		for (int i = 0; i < m.size(); i++) {
			Pair<String, String> c = m.get(i);
			n = n.replace(c.getLeft(), c.getRight());
		}
		return n;

	}

	private String unreplacer(String n, ArrayList<Pair<String, String>> m) {

		for (int i = 0; i < m.size(); i++) {
			Pair<String, String> c = m.get(i);
			n = n.replace(c.getRight(), c.getLeft());
		}

		return n;
	}

	private ArrayList<String> preprocess(String kata) {
		// TODO Auto-generated method stub
		ArrayList<String> hasil = new ArrayList<String>();
		String tmp = "";
		boolean inKonsonan = false;
		// int numKonsonan = 0;
		for (char c : kata.toCharArray()) {
			if (!vokal.contains(String.valueOf(c))) {
				if (!inKonsonan) {
					inKonsonan = true;
				}
				// numKonsonan++;
				tmp += c;
			} else {
				if (inKonsonan) {
					inKonsonan = false;
					if (tmp.length() == 1) {
						hasil.add(tmp + c);
					} else {
						hasil.add(tmp.substring(0, 1));
						hasil.add(tmp.substring(1) + c);
					}
					tmp = "";

				} else {
					hasil.add(String.valueOf(c));
				}
			}

		}
		if (tmp.length() > 0) {
			hasil.add(tmp);
		}
		return hasil;

	}

	private ArrayList<String> kaidah1(ArrayList<String> listSuku) {
		// TODO Auto-generated method stub
		ArrayList<String> hasil = new ArrayList<String>();

		int i = 0;

		for (String suku : listSuku) {

			if ((suku.length() == 1) && i > 0) {

				if (vokal.contains(suku)) {

					hasil.add(suku);

				} else if (vokal.contains(String.valueOf(listSuku.get(i - 1)
						.charAt(listSuku.get(i - 1).length() - 1)))) {
					if ((i < listSuku.size() - 1)
							&& (listSuku.get(i + 1).length() == 1)
							&& vokal.contains(String.valueOf(listSuku
									.get(i + 1).charAt(0)))) {
						hasil.add(suku);

					} else {

						hasil.set(hasil.size() - 1, hasil.get(hasil.size() - 1)
								+ suku);

					}
				}
			} else {
				hasil.add(suku);

			}
			i++;

		}
		return hasil;
	}

	private ArrayList<String> kaidah2(ArrayList<String> listSuku) {
		// TODO Auto-generated method stub

		ArrayList<String> dift = new ArrayList<String>(Arrays.asList("$", "%",
				"^", "&", "*", "("));

		if (listSuku.size() > 1) {
			if (awalan.contains(listSuku.get(0))
					&& !vokal.contains(String
							.valueOf(listSuku.get(1).charAt(0)))
					&& !dift.contains(String.valueOf(listSuku.get(1).charAt(0)))
					&& (listSuku.get(1).length() > 2)) {
				listSuku.set(0,
						listSuku.get(0) + listSuku.get(1).substring(0, 1));
				listSuku.set(1, listSuku.get(1).substring(1));
			}
			if (listSuku.get(0).length() == 1) {
				if (!vokal.contains(String.valueOf(listSuku.get(0).charAt(0)))) {
					// System.out.println("3"+ listSuku.toString());
					listSuku.add(0, listSuku.get(0) + listSuku.get(1));
					// .out.println("4"+ listSuku.toString());
					listSuku.remove(1);
					// System.out.println("5"+ listSuku.toString());
					listSuku.remove(1);

				}

			}

		}
		// System.out.println(listSuku.toString());
		return listSuku;

	}

	private ArrayList<String> kaidah3(ArrayList<String> listSuku) {
		// TODO Auto-generated method stub
		ArrayList<String> hasil = new ArrayList<String>();

		int i = 0;
		for (String suku : listSuku) {
			if ((suku.length() == 1) && (i > 0)) {
				if (vokal.contains(String.valueOf(listSuku.get(i - 1).charAt(
						listSuku.get(i - 1).length() - 1)))) {
					hasil.set(hasil.size() - 1, hasil.get(hasil.size() - 1)
							+ suku);
				} else {
					hasil.add(suku);
				}

			} else {
				hasil.add(suku);
			}
			i++;

		}
		return hasil;
	}

	public String sukukatastring(String source) {
		String sukustring = "";
		ArrayList<String> sukus = this.pecah(source);
		for (String string : sukus) {
			sukustring += string + " ";
		}
		return sukustring;
	}

	public ArrayList<String> pecah(String source) {
		// TODO Auto-generated method stub
		ArrayList<String> sukus = new ArrayList<String>();
		ArrayList<String> suku = new ArrayList<String>();

		suku = this.preprocess(this.replacer(source, kdift));
		// System.out.println(suku.toString());
		suku = this.kaidah1(suku);
		// System.out.println("1. " + suku.toString());
		suku = this.kaidah2(suku);
		// System.out.println("2. " + suku.toString());
		suku = this.kaidah3(suku);
		// System.out.println("3. " + suku.toString());
		for (String s : suku) {
			sukus.add(unreplacer(s, kdift));
		}
		return sukus;

	}

}