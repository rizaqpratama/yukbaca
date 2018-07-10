package com.yuk.baca.lang.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumProc {
	public ArrayList<HashMap<String, Integer>> listNominal = new ArrayList<HashMap<String, Integer>>();
	
	
	
	private String getBulan(String blnInAngka){
		
		
		return null;
	}
	public String getNatural(String ax) {
		StringBuffer sBuffer = new StringBuffer();
		// Pattern p = Pattern.compile("/^[1-9]\\d*(\\.\\d+)?$/");
		Pattern p = Pattern.compile("[0-9]+.[0-9]*|[0-9]*.[0-9]+|[0-9]+");
		Matcher m = p.matcher(ax);
		while (m.find()) {

			sBuffer.append(m.group());
		}
		String[] angkas = sBuffer.toString().split(" ");

		for (String string : angkas) {
			try {
				String az = parse(string);
				int pos = ax.indexOf(string);
				String depan = ax.substring(0, pos);

				String belakang = ax.substring(pos + string.length(),
						ax.length());
				/*
				 * System.out.println("--------------"); System.out.println(ax);
				 * System.out.println(string);
				 */
				ax = depan + az + belakang;

				/*
				 * System.out.println(depan); System.out.println(string);
				 * System.out.println(belakang); System.out.println(ax);
				 * System.out.println(az);
				 * System.out.println("---------------");
				 */
			} catch (Exception e) {
				// TODO: handle exception
			}
			// System.out.println(ax);
			// ax=depan+numProc.parse(string)+belakang;
		}
		return ax;
	}

	public String parse(String str) {
		String spoken = "";
		String[] xtr = str.split(",");
		xtr[0] = xtr[0].replace(".", "");
		long x = Long.parseLong(xtr[0]);
		// long x = 2147483647;
		System.out.println(getSpokenNumber(9));
		System.out.println((String.valueOf(10000).length() - 1) % 3);
		getScale(x);
		// System.out.println(x);
		// System.out.println(listNominal.toString());
		// sSystem.out.println(listNominal.size());
		// String spoken = "";
		for (int i = listNominal.size() - 1; i >= 0; i--) {
			HashMap<String, Integer> map = listNominal.get(i);

			int a = 0;
			try {
				a = map.get("ratusan");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				a = 0;
			}
			int b = 0;
			try {
				b = map.get("puluhan");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				b = 0;
			}
			int c = 0;
			try {
				c = map.get("satuan");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				c = 0;
			}
			// untuk ratusan
			if (a != 1) {
				spoken += getSpokenNumber(a);
			} else {
				spoken += "se";
			}
			if (a != 0 && a != 1) {
				spoken += " ratus ";
			} else if (a == 1) {
				spoken += "ratus ";
			}
			// untuk puluhan

			if (b == 1) {
				int belasan = Integer.parseInt(String.valueOf(b)
						+ String.valueOf(c));
				spoken += getSpokenBelasan(belasan);
			} else {
				if (b != 0) {
					spoken += getSpokenNumber(b);
					spoken += " puluh ";
				}

				spoken += getSpokenNumber(c);

			}
			switch (i) {
			case 0:

				break;
			case 1:
				spoken += " ribu ";
				break;
			case 2:
				spoken += " juta ";
				break;
			case 3:
				spoken += " milyar ";
				break;
			case 4:
				spoken += " triliun ";
				break;
			default:
				break;
			}

		}
		try {
			// System.out.println(xtr[1]);
			// ArrayList<Integer> newGuess = new ArrayList<Integer>();

			String temp = xtr[1];
			spoken += " koma";
			for (int i = 0; i < temp.length(); i++) {
				spoken += " " + getSpokenNumber(temp.charAt(i) - '0');

			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		return spoken;
	}

	public void getScale(long x) {
		int panjang = String.valueOf(x).length();
		ArrayList<Integer> newGuess = new ArrayList<Integer>();
		listNominal.clear();
		if (panjang > 0) {
			String temp = Long.toString(x);

			for (int i = 0; i < temp.length(); i++) {
				newGuess.add(temp.charAt(i) - '0');
			}
			int n = newGuess.size() - 1;
			while (n > -1) {
				HashMap<String, Integer> mapSatuan = new HashMap<String, Integer>();
				try {
					mapSatuan.put("satuan", newGuess.get(n));
					n = n - 1;
					mapSatuan.put("puluhan", newGuess.get(n));
					n = n - 1;
					mapSatuan.put("ratusan", newGuess.get(n));
					n = n - 1;
				} catch (ArrayIndexOutOfBoundsException e) {
					// TODO: handle exception
				}

				listNominal.add(mapSatuan);
			}

			int kelas = (int) Math.floor((panjang - 1) % 3);
			switch (kelas) {
			case 1:
				System.out.println("ribu");
				break;
			case 2:
				System.out.println("juta");
				break;
			case 3:
				System.out.println("milyar");
				break;
			default:
				break;
			}
		}
	}

	public String getSpokenBelasan(int a) {
		switch (a) {

		case 10:
			return "sepuluh";

		case 11:
			return "sebelas";

		case 12:
			return "dua belas";

		case 13:
			return "tiga belas";
		case 14:
			return "empat belas";
		case 15:
			return "lima belas";
		case 16:
			return "enam belas";
		case 17:
			return "tujuh belas";
		case 18:
			return "delapan belas";
		case 19:
			return "sembilan belas";

		default:
			return "";
			// break;

		}

	}

	public String getSpokenNumber(int a) {
		// String soken = "";
		switch (a) {
		case 1:
			return "satu";
			// break;
		case 2:
			return "dua";
			// break;
		case 3:
			return "tiga";
			// break;
		case 4:
			return "empat";
		case 5:
			return "lima";
		case 6:
			return "enam";
		case 7:
			return "tujuh";
		case 8:
			return "delapan";
		case 9:
			return "sembilan";

		default:
			return "";
			// break;

		}

	}
}
