package com.purejadeite.jadegreen;

import java.math.BigDecimal;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.util.CellReference;

/**
 * Sxssf用のユーティリティ
 *
 * @author mitsuhiroseino
 */
public class SxssfUtils {

	/**
	 * Trueとして扱う値
	 */
	private static final String VALUE_TRUE = "1";

	/**
	 * コンストラクター
	 */
	private SxssfUtils() {
	}

	/**
	 * セルから取得した値が空文字の場合、nullに変換します
	 * @param value セルの値
	 * @return 文字列型の値
	 */
	public static String getString(String value) {
		return StringUtils.defaultIfEmpty(value, null);
	}

	/**
	 * セルから取得した値をjava.util.Dateに変換します。
	 * 日付の起点は1900年、タイムゾーンはデフォルトです。
	 * @param value セルの値
	 * @return 日付型の値
	 */
	public static Date getDate(String value) {
		// 1900年始まり
		boolean use1904windowing = false;
		TimeZone timeZone = null;
		return getDate(value, use1904windowing, timeZone);
	}

	/**
	 * セルから取得した値をjava.util.Dateに変換します。
	 * タイムゾーンはデフォルトです。
	 * @param value セルの値
	 * @param use1904windowing 日付の起点日を1904年(Mac版Office)とするか
	 * @return 日付型の値
	 */
	public static Date getDate(String value, boolean use1904windowing) {
		TimeZone timeZone = null;
		return getDate(value, use1904windowing, timeZone);
	}

	/**
	 * セルから取得した値をjava.util.Dateに変換します。
	 * @param value セルの値
	 * @param use1904windowing 日付の起点日を1904年(Mac版Office)とするか
	 * @param timeZone タイムゾーン
	 * @return 日付型の値
	 */
	public static Date getDate(String value, boolean use1904windowing, TimeZone timeZone) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		Double dblVal = getDouble(value);
		return DateUtil.getJavaDate(dblVal.doubleValue(), use1904windowing, timeZone);
	}

	/**
	 * セルから取得した値を、指定された日付フォーマットの形式に変換します。
	 * @param value セルの値
	 * @param format 日付フォーマット
	 * @return フォーマットされた日付文字列
	 */
	public static String getStringDate(String value, String format) {
		Date date = getDate(value);
		if (date == null) {
			return null;
		} else {
			return DateFormatUtils.format(date, format);
		}
	}

	/**
	 * セルから取得した値をIntegerに変換します。
	 * @param value セルの値
	 * @return Integer型の値
	 */
	public static Integer getInteger(String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		return Integer.valueOf(value);
	}

	/**
	 * セルから取得した値をLongに変換します。
	 * @param value セルの値
	 * @return Long型の値
	 */
	public static Long getLong(String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		return Long.valueOf(value);
	}

	/**
	 * セルから取得した値をShortに変換します。
	 * @param value セルの値
	 * @return Short型の値
	 */
	public static Short getShort(String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		return Short.valueOf(value);
	}

	/**
	 * セルから取得した値をDoubleに変換します。
	 * @param value セルの値
	 * @return Double型の値
	 */
	public static Double getDouble(String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		return Double.valueOf(value);
	}

	/**
	 * セルから取得した値をFloatに変換します。
	 * @param value セルの値
	 * @return Float型の値
	 */
	public static Float getFloat(String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		return Float.valueOf(value);
	}

	/**
	 * セルから取得した値をBigDecimalに変換します。
	 * @param value セルの値
	 * @return BigDecimal型の値
	 */
	public static BigDecimal getBigDecimal(String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		return new BigDecimal(value);
	}

	/**
	 * セルから取得した値をBooleanに変換します。
	 * "1"をtrue、それ以外をfalseとします。
	 * @param value セルの値
	 * @return Boolean型の値
	 */
	public static Boolean getBoolean(String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		if (VALUE_TRUE.equals(value)) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	/**
	 * <pre>
	 * A1形式のアドレス情報からカラムのみ取得します。
	 * 例:
	 *   "A1" -> "A"
	 *   "A1:B2" -> "A"
	 *   "A:B" -> "A"
	 * </pre>
	 * @param range A1形式のアドレス
	 * @return カラム文字列
	 */
	public static String getColString(String range) {
		char[] rangeChars = range.toCharArray();
		String col = "";
		for (char rangeChar : rangeChars) {
			if (('0' <= rangeChar && rangeChar <= '9') || rangeChar == ':') {
				return col;
			} else {
				col += rangeChar;
			}
		}
		return col;
	}

	/**
	 * <pre>
	 * A1形式のアドレス情報から列番号を取得します。
	 * 例:
	 *   "A5" -> 1
	 *   "C" -> 3
	 *   "AB3" -> 28
	 *   "B1:C6" -> 2
	 * </pre>
	 * @param range A1形式のアドレス
	 * @return カラム番号
	 */
	public static int toColIndex(String range) {
		return CellReference.convertColStringToIndex(getColString(range)) + 1;
	}

}
