package com.jizan.utils;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

public class StringUtil {

	private StringUtil() {
	}

	/**
	 * 清理字符串：如果是Null，则替换为""，并清除所有空格
	 * 
	 * @param s
	 * @return
	 */
	public static String clean(String s) {
		return s != null ? s.trim() : "";
	}

	public static String trim(String s) {
		return s != null ? s.trim() : null;
	}

	public static String deleteWhitespace(String s) {
		StringBuffer stringbuffer = new StringBuffer();
		int i = s.length();
		for (int j = 0; j < i; j++)
			if (!Character.isWhitespace(s.charAt(j)))
				stringbuffer.append(s.charAt(j));

		return stringbuffer.toString();
	}

	public static boolean isNotEmpty(String s) {
		return s != null && s.length() > 0;
	}

	public static boolean isEmpty(String s) {
		return s == null || s.trim().length() == 0;
	}

	public static boolean equals(String s, String s1) {
		return s != null ? s.equals(s1) : s1 == null;
	}

	public static boolean equalsIgnoreCase(String s, String s1) {
		return s != null ? s.equalsIgnoreCase(s1) : s1 == null;
	}

	public static int indexOfAny(String s, String as[]) {
		if (s == null || as == null)
			return -1;
		int i = as.length;
		int j = 0x7fffffff;
		// boolean flag = false;
		for (int l = 0; l < i; l++) {
			int k = s.indexOf(as[l]);
			if (k != -1 && k < j)
				j = k;
		}

		return j != 0x7fffffff ? j : -1;
	}

	/**
	 * @param s
	 * @param as
	 * @return
	 */
	public static int lastIndexOfAny(String s, String as[]) {
		if (s == null || as == null)
			return -1;
		int i = as.length;
		int j = -1;
		// boolean flag = false;
		for (int l = 0; l < i; l++) {
			int k = s.lastIndexOf(as[l]);
			if (k > j)
				j = k;
		}

		return j;
	}

	public static String substring(String s, int i) {
		if (s == null)
			return null;
		if (i < 0)
			i = s.length() + i;
		if (i < 0)
			i = 0;
		if (i > s.length())
			return "";
		else
			return s.substring(i);
	}

	public static String substring(String s, int i, int j) {
		if (s == null)
			return null;
		if (j < 0)
			j = s.length() + j;
		if (i < 0)
			i = s.length() + i;
		if (j > s.length())
			j = s.length();
		if (i > j)
			return "";
		if (i < 0)
			i = 0;
		if (j < 0)
			j = 0;
		return s.substring(i, j);
	}

	public static String left(String s, int i) {
		if (i < 0)
			throw new IllegalArgumentException("Requested String length " + i + " is less than zero");
		if (s == null || s.length() <= i)
			return s;
		else
			return s.substring(0, i);
	}

	public static String leftWithEllipsis(String s, int i) {
		if (i < 0)
			throw new IllegalArgumentException("Requested String length " + i + " is less than zero");
		if (s == null || s.length() <= i)
			return s;
		if (s.length() > i)
			return s.substring(0, i) + "...";
		else
			return s.substring(0, i);
	}

	public static String right(String s, int i) {
		if (i < 0)
			throw new IllegalArgumentException("Requested String length " + i + " is less than zero");
		if (s == null || s.length() <= i)
			return s;
		else
			return s.substring(s.length() - i);
	}

	public static String mid(String s, int i, int j) {
		if (i < 0 || s != null && i > s.length())
			throw new StringIndexOutOfBoundsException("String index " + i + " is out of bounds");
		if (j < 0)
			throw new IllegalArgumentException("Requested String length " + j + " is less than zero");
		if (s == null)
			return null;
		if (s.length() <= i + j)
			return s.substring(i);
		else
			return s.substring(i, i + j);
	}

	public static String[] split(String s) {
		return split(s, null, -1);
	}

	public static String[] splitWithSpaceElement(String s, String s1) {
		int i = s.indexOf(s1);
		int j = 0;
		for (; i != -1; i = s.indexOf(s1, i + 1))
			j++;

		String as[] = new String[j + 1];
		i = s.indexOf(s1);
		j = 0;
		int k = -1;
		while (i != -1) {
			as[j] = s.substring(k + 1, i);
			k = i;
			i = s.indexOf(s1, i + 1);
			j++;
		}
		if (k + 1 == s.length())
			as[as.length - 1] = "";
		else
			as[as.length - 1] = s.substring(k + 1);
		return as;
	}

	public static String[] split(String s, String s1) {
		return split(s, s1, -1);
	}

	public static String[] split(String s, String s1, int i) {
		StringTokenizer stringtokenizer = null;
		if (s1 == null)
			stringtokenizer = new StringTokenizer(s);
		else
			stringtokenizer = new StringTokenizer(s, s1);
		int j = stringtokenizer.countTokens();
		if (i > 0 && j > i)
			j = i;
		String as[] = new String[j];
		int k = 0;
		// boolean flag = false;
		int j1 = 0;
		do {
			if (!stringtokenizer.hasMoreTokens())
				break;
			if (i > 0 && k == j - 1) {
				String s2 = stringtokenizer.nextToken();
				int l = s.indexOf(s2, j1);
				as[k] = s.substring(l);
				break;
			}
			as[k] = stringtokenizer.nextToken();
			int i1 = s.indexOf(as[k], j1);
			j1 = i1 + as[k].length();
			k++;
		} while (true);
		return as;
	}

	public static String concatenate(Object aobj[]) {
		return join(aobj, "");
	}

	public static String join(Object aobj[], String s) {
		if (s == null)
			s = "";
		int i = aobj.length;
		int j = i != 0 ? (aobj[0].toString().length() + s.length()) * i : 0;
		StringBuffer stringbuffer = new StringBuffer(j);
		for (int k = 0; k < i; k++) {
			if (k > 0)
				stringbuffer.append(s);
			stringbuffer.append(aobj[k]);
		}
		return stringbuffer.toString();
	}

	public static String join(JSONArray aobj, String s) {
		if (s == null)
			s = "";
		int i = aobj.size();
		int j = i != 0 ? (aobj.get(0).toString().length() + s.length()) * i : 0;
		StringBuffer stringbuffer = new StringBuffer(j);
		for (int k = 0; k < i; k++) {
			if (k > 0)
				stringbuffer.append(s);
			stringbuffer.append(aobj.get(k));
		}
		return stringbuffer.toString();
	}

	public static String join(ArrayList<String> aobj, String s) {
		if (s == null)
			s = "";
		int i = aobj.size();
		int j = i != 0 ? (aobj.get(0).toString().length() + s.length()) * i : 0;
		StringBuffer stringbuffer = new StringBuffer(j);
		for (int k = 0; k < i; k++) {
			if (k > 0)
				stringbuffer.append(s);
			stringbuffer.append(aobj.get(k));
		}
		return stringbuffer.toString();
	}
	/**
	 * @param obj
	 * @param wrapper
	 *            包裹字符，例如：{str}的wrapper为{“{”，“}”}
	 * @param s
	 * @return
	 */
	public static String join(Object obj[], String wrapperLeft, String wrapperRight, String s) {
		Object[] newobj = new Object[obj.length];
		for (int n = 0; n < obj.length; n++) {
			newobj[n] = wrapperLeft + obj[n] + wrapperRight;
		}
		if (s == null)
			s = "";
		int i = newobj.length;
		int j = i != 0 ? (newobj[0].toString().length() + s.length()) * i : 0;
		StringBuffer stringbuffer = new StringBuffer(j);
		for (int k = 0; k < i; k++) {
			if (k > 0)
				stringbuffer.append(s);
			stringbuffer.append(newobj[k]);
		}
		return stringbuffer.toString();
	}

	public static String join(Iterator<?> iterator, String s) {
		if (s == null)
			s = "";
		StringBuffer stringbuffer = new StringBuffer(256);
		do {
			if (!iterator.hasNext())
				break;
			stringbuffer.append(iterator.next());
			if (iterator.hasNext())
				stringbuffer.append(s);
		} while (true);
		return stringbuffer.toString();
	}

	public static String replaceOnce(String s, String s1, String s2) {
		return replace(s, s1, s2, 1);
	}

	public static String replace(String s, String s1, String s2) {
		return replace(s, s1, s2, -1);
	}

	public static String replace(String s, String s1, String s2, int i) {
		if (s == null)
			return null;
		if (s1 == null || s1.length() == 0)
			return s;
		StringBuffer stringbuffer = new StringBuffer(s.length());
		int j = 0;
		// boolean flag = false;
		do {
			int k;
			if ((k = s.indexOf(s1, j)) == -1)
				break;
			stringbuffer.append(s.substring(j, k)).append(s2);
			j = k + s1.length();
		} while (--i != 0);
		stringbuffer.append(s.substring(j));
		return stringbuffer.toString();
	}

	public static String overlayString(String s, String s1, int i, int j) {
		return (new StringBuffer(((i + s1.length() + s.length()) - j) + 1)).append(s.substring(0, i)).append(s1)
				.append(s.substring(j)).toString();
	}

	public static String center(String s, int i) {
		return center(s, i, " ");
	}

	public static String center(String s, int i, String s1) {
		int j = s.length();
		int k = i - j;
		if (k < 1) {
			return s;
		} else {
			s = leftPad(s, j + k / 2, s1);
			s = rightPad(s, i, s1);
			return s;
		}
	}

	public static String chomp(String s) {
		return chomp(s, "\n");
	}

	public static String chomp(String s, String s1) {
		int i = s.lastIndexOf(s1);
		if (i != -1)
			return s.substring(0, i);
		else
			return s;
	}

	public static String chompLast(String s) {
		return chompLast(s, "\n");
	}

	public static String chompLast(String s, String s1) {
		if (s.length() == 0)
			return s;
		String s2 = s.substring(s.length() - s1.length());
		if (s1.equals(s2))
			return s.substring(0, s.length() - s1.length());
		else
			return s;
	}

	public static String getChomp(String s, String s1) {
		int i = s.lastIndexOf(s1);
		if (i == s.length() - s1.length())
			return s1;
		if (i != -1)
			return s.substring(i);
		else
			return "";
	}

	public static String prechomp(String s, String s1) {
		int i = s.indexOf(s1);
		if (i != -1)
			return s.substring(i + s1.length());
		else
			return s;
	}

	public static String getPrechomp(String s, String s1) {
		int i = s.indexOf(s1);
		if (i != -1)
			return s.substring(0, i + s1.length());
		else
			return "";
	}

	public static String chop(String s) {
		if ("".equals(s))
			return "";
		if (s.length() == 1)
			return "";
		int i = s.length() - 1;
		String s1 = s.substring(0, i);
		char c = s.charAt(i);
		if (c == '\n' && s1.charAt(i - 1) == '\r')
			return s1.substring(0, i - 1);
		else
			return s1;
	}

	public static String chopNewline(String s) {
		int i = s.length() - 1;
		char c = s.charAt(i);
		if (c == '\n') {
			if (s.charAt(i - 1) == '\r')
				i--;
		} else {
			i++;
		}
		return s.substring(0, i);
	}

	public static String escape(String s) {
		int i = s.length();
		StringBuffer stringbuffer = new StringBuffer(2 * i);
		for (int j = 0; j < i; j++) {
			char c = s.charAt(j);
			if (c > '\u0FFF') {
				stringbuffer.append("\\u" + Integer.toHexString(c));
				continue;
			}
			if (c > '\377') {
				stringbuffer.append("\\u0" + Integer.toHexString(c));
				continue;
			}
			if (c > '\177') {
				stringbuffer.append("\\u00" + Integer.toHexString(c));
				continue;
			}
			if (c < ' ') {
				switch (c) {
				case 8: // '\b'
					stringbuffer.append('\\');
					stringbuffer.append('b');
					continue;

				case 10: // '\n'
					stringbuffer.append('\\');
					stringbuffer.append('n');
					continue;

				case 9: // '\t'
					stringbuffer.append('\\');
					stringbuffer.append('t');
					continue;

				case 12: // '\f'
					stringbuffer.append('\\');
					stringbuffer.append('f');
					continue;

				case 13: // '\r'
					stringbuffer.append('\\');
					stringbuffer.append('r');
					continue;
				}
				if (c > '\017')
					stringbuffer.append("\\u00" + Integer.toHexString(c));
				else
					stringbuffer.append("\\u000" + Integer.toHexString(c));
				continue;
			}
			switch (c) {
			case 39: // '\''
				stringbuffer.append('\\');
				stringbuffer.append('\'');
				break;

			case 34: // '"'
				stringbuffer.append('\\');
				stringbuffer.append('"');
				break;

			case 92: // '\\'
				stringbuffer.append('\\');
				stringbuffer.append('\\');
				break;

			default:
				stringbuffer.append(c);
				break;
			}
		}

		return stringbuffer.toString();
	}

	public static String repeat(String s, int i) {
		StringBuffer stringbuffer = new StringBuffer(i * s.length());
		for (int j = 0; j < i; j++)
			stringbuffer.append(s);

		return stringbuffer.toString();
	}

	public static String rightPad(String s, int i) {
		return rightPad(s, i, " ");
	}

	public static String rightPad(String s, int i, String s1) {
		i = (i - s.length()) / s1.length();
		if (i > 0)
			s = s + repeat(s1, i);
		return s;
	}

	public static String leftPad(String s, int i) {
		return leftPad(s, i, " ");
	}

	public static String leftPad(String s, int i, String s1) {
		i = (i - s.length()) / s1.length();
		if (i > 0)
			s = repeat(s1, i) + s;
		return s;
	}

	public static String strip(String s) {
		return strip(s, null);
	}

	public static String strip(String s, String s1) {
		s = stripStart(s, s1);
		return stripEnd(s, s1);
	}

	public static String[] stripAll(String as[]) {
		return stripAll(as, null);
	}

	public static String[] stripAll(String as[], String s) {
		if (as == null || as.length == 0)
			return as;
		int i = as.length;
		String as1[] = new String[i];
		for (int j = 0; j < i; j++)
			as1[j] = strip(as[j], s);

		return as1;
	}

	public static String stripEnd(String s, String s1) {
		if (s == null)
			return null;
		int i = s.length();
		if (s1 == null)
			for (; i != 0 && Character.isWhitespace(s.charAt(i - 1)); i--)
				;
		else
			for (; i != 0 && s1.indexOf(s.charAt(i - 1)) != -1; i--)
				;
		return s.substring(0, i);
	}

	public static String stripStart(String s, String s1) {
		if (s == null)
			return null;
		int i = 0;
		int j = s.length();
		if (s1 == null)
			for (; i != j && Character.isWhitespace(s.charAt(i)); i++)
				;
		else
			for (; i != j && s1.indexOf(s.charAt(i)) != -1; i++)
				;
		return s.substring(i);
	}

	public static String upperCase(String s) {
		if (s == null)
			return null;
		else
			return s.toUpperCase();
	}

	public static String lowerCase(String s) {
		if (s == null)
			return null;
		else
			return s.toLowerCase();
	}

	public static String uncapitalise(String s) {
		if (s == null)
			return null;
		if (s.length() == 0)
			return "";
		else
			return (new StringBuffer(s.length())).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1))
					.toString();
	}

	public static String capitalise(String s) {
		if (s == null)
			return null;
		if (s.length() == 0)
			return "";
		else
			return (new StringBuffer(s.length())).append(Character.toTitleCase(s.charAt(0))).append(s.substring(1))
					.toString();
	}

	public static String swapCase(String s) {
		if (s == null)
			return null;
		int i = s.length();
		StringBuffer stringbuffer = new StringBuffer(i);
		boolean flag = false;
		// boolean flag1 = false;
		// boolean flag2 = false;
		for (int j = 0; j < i; j++) {
			char c = s.charAt(j);
			char c1;
			if (Character.isUpperCase(c))
				c1 = Character.toLowerCase(c);
			else if (Character.isTitleCase(c))
				c1 = Character.toLowerCase(c);
			else if (Character.isLowerCase(c)) {
				if (flag)
					c1 = Character.toTitleCase(c);
				else
					c1 = Character.toUpperCase(c);
			} else {
				c1 = c;
			}
			stringbuffer.append(c1);
			flag = Character.isWhitespace(c);
		}

		return stringbuffer.toString();
	}

	public static String capitaliseAllWords(String s) {
		if (s == null)
			return null;
		int i = s.length();
		StringBuffer stringbuffer = new StringBuffer(i);
		boolean flag = true;
		for (int j = 0; j < i; j++) {
			char c = s.charAt(j);
			if (Character.isWhitespace(c)) {
				stringbuffer.append(c);
				flag = true;
				continue;
			}
			if (flag) {
				stringbuffer.append(Character.toTitleCase(c));
				flag = false;
			} else {
				stringbuffer.append(c);
			}
		}

		return stringbuffer.toString();
	}

	public static String getNestedString(String s, String s1) {
		return getNestedString(s, s1, s1);
	}

	public static String getNestedString(String s, String s1, String s2) {
		if (s == null)
			return null;
		int i = s.indexOf(s1);
		if (i != -1) {
			int j = s.indexOf(s2, i + s1.length());
			if (j != -1)
				return s.substring(i + s1.length(), j);
		}
		return null;
	}

	public static int countMatches(String s, String s1) {
		if (s == null)
			return 0;
		int i = 0;
		for (int j = 0; (j = s.indexOf(s1, j)) != -1; j += s1.length())
			i++;

		return i;
	}

	public static boolean isAlpha(String s) {
		if (s == null)
			return false;
		int i = s.length();
		for (int j = 0; j < i; j++)
			if (!Character.isLetter(s.charAt(j)))
				return false;

		return true;
	}

	public static boolean isAlphaSpace(String s) {
		if (s == null)
			return false;
		int i = s.length();
		for (int j = 0; j < i; j++)
			if (!Character.isLetter(s.charAt(j)) && s.charAt(j) != ' ')
				return false;

		return true;
	}

	public static boolean isAlphanumeric(String s) {
		if (s == null)
			return false;
		int i = s.length();
		for (int j = 0; j < i; j++)
			if (!Character.isLetterOrDigit(s.charAt(j)))
				return false;

		return true;
	}

	public static boolean isAlphanumericSpace(String s) {
		if (s == null)
			return false;
		int i = s.length();
		for (int j = 0; j < i; j++)
			if (!Character.isLetterOrDigit(s.charAt(j)) && s.charAt(j) != ' ')
				return false;

		return true;
	}

	public static boolean isNumeric(String s) {
		if (s == null)
			return false;
		int i = s.length();
		for (int j = 0; j < i; j++)
			if (!Character.isDigit(s.charAt(j)))
				return false;
		return true;
	}

	public static boolean isNumericSpace(String s) {
		if (s == null)
			return false;
		int i = s.length();
		for (int j = 0; j < i; j++)
			if (!Character.isDigit(s.charAt(j)) && s.charAt(j) != ' ')
				return false;

		return true;
	}

	public static String defaultString(String s) {
		return defaultString(s, "");
	}

	public static String defaultString(String s, String s1) {
		return s != null ? s : s1;
	}

	public static String reverse(String s) {
		if (s == null)
			return null;
		else
			return (new StringBuffer(s)).reverse().toString();
	}

	public static String reverseDelimitedString(String s, String s1) {
		String as[] = split(s, s1);
		reverseArray(as);
		return join(as, s1);
	}

	private static void reverseArray(Object aobj[]) {
		int i = 0;
		for (int j = aobj.length - 1; j > i; i++) {
			Object obj = aobj[j];
			aobj[j] = aobj[i];
			aobj[i] = obj;
			j--;
		}

	}

	public static boolean containsOnly(String s, char ac[]) {
		if (s == null || ac == null)
			return false;
		int i = s.length();
		int j = ac.length;
		for (int k = 0; k < i; k++) {
			boolean flag = false;
			int l = 0;
			do {
				if (l >= j)
					break;
				if (ac[l] == s.charAt(k)) {
					flag = true;
					break;
				}
				l++;
			} while (true);
			if (!flag)
				return false;
		}

		return true;
	}

	public static String encodeStringArray(String as[]) {
		if (as != null) {
			StringBuffer stringbuffer = new StringBuffer();
			for (int i = 0; i < as.length; i++) {
				stringbuffer.append(as[i]);
				stringbuffer.append(";");
			}

			return stringbuffer.toString();
		} else {
			return "";
		}
	}

	public static String[] decodeStringArray(String s) {
		if (s == null)
			return null;
		StringTokenizer stringtokenizer = new StringTokenizer(s, ";");
		ArrayList<Object> arraylist = new ArrayList<Object>();
		for (; stringtokenizer.hasMoreElements(); arraylist.add(stringtokenizer.nextElement()))
			;
		String as[] = new String[arraylist.size()];
		for (int i = 0; i < arraylist.size(); i++)
			as[i] = (String) arraylist.get(i);

		return as;
	}

	public static int lengthOf(String s) {
		if (null == s)
			return -1;
		int i = s.length();
		int j = 0;
		for (int k = 0; k < i; k++)
			j += s.charAt(k) <= '\377' ? 1 : 2;

		return j;
	}

	public static int getMaxLenOfStrList(String as[]) {
		List<String> list = Arrays.asList(as);
		int i = getMaxLenOfStrList(list);
		return i;
	}

	public static int getMaxLenOfStrList(List<String> list) {
		class _cls1StrComparator implements Comparator<Object> {

			public boolean equals(Object obj) {
				return false;
			}

			public int compare(Object obj, Object obj1) {
				if (null == obj || null == obj1)
					return obj != obj1 ? null != obj1 ? -1 : 1 : 0;
				else
					return StringUtil.lengthOf((String) obj) - StringUtil.lengthOf((String) obj1);
			}

			_cls1StrComparator() {
			}
		}

		_cls1StrComparator _lcls1strcomparator = new _cls1StrComparator();
		String s = (String) Collections.max(list, _lcls1strcomparator);
		_lcls1strcomparator = null;
		return lengthOf(s);
	}

	public static int indexOf(char c, String s) {
		int i = s.length();
		for (int j = 0; j < i; j++)
			if (s.charAt(j) == c)
				return j;

		return -1;
	}

	public static String quote(String s, char c) {
		if (isEmpty(s))
			return s;
		StringBuffer stringbuffer = new StringBuffer();
		stringbuffer.append(c);
		for (int i = 0; i < s.length(); i++)
			if (s.charAt(i) == c) {
				stringbuffer.append(c);
				stringbuffer.append(c);
			} else {
				stringbuffer.append(s.charAt(i));
			}

		stringbuffer.append(c);
		return stringbuffer.toString();
	}

	public static String dequote(String s, char c) {
		if (isEmpty(s))
			return s;
		if (s.charAt(0) != c)
			return null;
		StringBuffer stringbuffer = new StringBuffer(s.length());
		int i = 1;
		do {
			if (i >= s.length())
				break;
			char c1 = s.charAt(i++);
			if (c1 == c) {
				if (i >= s.length())
					break;
				char c2 = s.charAt(i++);
				if (c2 != c)
					break;
				stringbuffer.append(c);
			} else {
				stringbuffer.append(s.charAt(i));
			}
		} while (true);
		return stringbuffer.toString();
	}

	/**
	 * 截取字符串设定长度
	 * 
	 * @param str
	 * @param cutCount
	 *            设定长度，字节数
	 * @return
	 */
	public static String getSubStr(String str, int cutCount) {
		if (str == null)
			return "";

		String resultStr = "";
		char[] ch = str.toCharArray();
		int count = ch.length;
		int strBLen = str.getBytes().length;
		int temp = 0;
		for (int i = 0; i < count; i++) {
			resultStr += ch[i];
			temp = resultStr.getBytes().length;
			if (temp >= cutCount && temp < strBLen) {
				resultStr += "...";
				break;
			}
		}
		return resultStr;
	}

	/**
	 * 获得Clob
	 * 
	 * @param s
	 *            字符串
	 * @return java.sql.Clob
	 */
	public static Blob Str2Blob(String s) {
		Blob c = null;
		try {
			if (s != null) {
				c = new SerialBlob(s.getBytes());
			}
		} catch (SerialException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}

	public static String Blob2Str(Blob blob) {

		String result = "";
		try {
			if (blob != null) {
				ByteArrayInputStream msgContent = (ByteArrayInputStream) blob.getBinaryStream();
				byte[] byte_data = new byte[msgContent.available()];
				msgContent.read(byte_data, 0, byte_data.length);
				result = new String(byte_data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取指定长度随机简体中文
	 * 
	 * @param len
	 *            int
	 * @return String
	 */
	public static String getRandomJianHan(int len) {
		String ret = "";
		for (int i = 0; i < len; i++) {
			String str = null;
			int hightPos, lowPos; // 定义高低位
			Random random = new Random();
			hightPos = (176 + Math.abs(random.nextInt(39))); // 获取高位值
			lowPos = (161 + Math.abs(random.nextInt(93))); // 获取低位值
			byte[] b = new byte[2];
			b[0] = (new Integer(hightPos).byteValue());
			b[1] = (new Integer(lowPos).byteValue());
			try {
				str = new String(b, "GBK"); // 转成中文
			} catch (UnsupportedEncodingException ex) {
				ex.printStackTrace();
			}
			ret += str;
		}
		return ret;
	}
	
	/** 
     * 检测邮箱地址是否合法 
     * @param email 
     * @return true合法 false不合法 
     */  
    public boolean isEmail(String email){  
          if (null==email || "".equals(email)) return false;    
          //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配  
          Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配  
          Matcher m = p.matcher(email);  
          return m.matches();  
         } 

	/*public static void main(String[] args) {
		System.out.println(getRandomJianHan(30));
		String aa = null;
		String[] bbb = { "1", "2" };
		System.out.println(join(bbb, "+", "-", ","));
		System.out.println(clean(aa));
		System.out.println(capitalise("wangchao"));
		System.out.println(Blob2Str(Str2Blob("sdfsdfsadfsad")));
	}*/
}