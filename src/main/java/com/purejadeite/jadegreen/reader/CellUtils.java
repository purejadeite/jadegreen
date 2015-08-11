package com.purejadeite.jadegreen.reader;

import java.math.BigDecimal;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.util.CellReference;

/**
 * Cellの値を取得する
 * 
 * @author mitsuhiroseino
 */
public class CellUtils {

	/**
	 * Trueとして扱う値
	 */
	private static final String VALUE_TRUE = "1";

	/**
	 * コンストラクター
	 */
	private CellUtils() {
	}

	public static String getStringValue(String value) {
		return StringUtils.defaultIfEmpty(value, null);
	}

	public static Date getDateValue(String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		Double dblVal = null;
		try {
			dblVal = getDoubleValue(value);
		} catch (Exception e) {
			throw new IllegalStateException("日付に変換できません。value=" + value, e);
		}
		// 1900年始まりのみ対応
		boolean use1904windowing = false;
		TimeZone timeZone = null;
		return DateUtil.getJavaDate(dblVal.doubleValue(), use1904windowing, timeZone);
	}

	public static String getStringDateValue(String value, String format) {
		Date date = getDateValue(value);
		if (date == null) {
			return null;
		} else {
			return DateFormatUtils.format(date, format);
		}
	}

	public static Integer getIntegerValue(String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		return Integer.valueOf(value);
	}

	public static Long getLongValue(String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		return Long.valueOf(value);
	}

	public static Short getShortValue(String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		return Short.valueOf(value);
	}

	public static Double getDoubleValue(String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		return Double.valueOf(value);
	}

	public static Float getFloatValue(String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		return Float.valueOf(value);
	}

	public static BigDecimal getBigDecimalValue(String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		return new BigDecimal(value);
	}

	public static Boolean getBooleanValue(String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		if (VALUE_TRUE.equals(value)) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	public static String getFirstColString(String range) {
		char[] cs = range.toCharArray();
		String col = "";
		for (char c : cs) {
			if (('0' <= c && c <= '9') || c == ':') {
				return col;
			} else {
				col += c;
			}
		}
		return col;
	}

	public static int toColIndex(String range) {
		return CellReference.convertColStringToIndex(range) + 1;
	}

}
