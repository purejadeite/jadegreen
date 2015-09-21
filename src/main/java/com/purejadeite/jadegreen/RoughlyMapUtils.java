package com.purejadeite.jadegreen;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * 値がObjectのMap用ユーティリティ
 *
 * @author mitsuhiroseino
 *
 */
public class RoughlyMapUtils {

	/**
	 * <pre>
	 * Mapから値をMapで取得します。
	 * 値がMapでない場合はnullを返します。
	 * </pre>
	 * @param map 対象のMap
	 * @param key キー
	 * @return Map形式の値
	 */
	@SuppressWarnings("unchecked")
	public static <K, V extends Object> Map<K, V> getMap(Map<K, ? extends Object> map, K key) {
		Object value = map.get(key);
		if (value instanceof Map) {
			return (Map<K, V>) value;
		}
		return null;
	}

	/**
	 * <pre>
	 * Mapから値をListで取得します。
	 * 値がListでない場合はnullを返します。
	 * </pre>
	 * @param map 対象のMap
	 * @param key キー
	 * @return List形式の値
	 */
	@SuppressWarnings("unchecked")
	public static <K, V extends Object> List<V> getList(Map<K, ? extends Object> map, K key) {
		Object value = map.get(key);
		if (value instanceof List) {
			return (List<V>) value;
		}
		return null;
	}

	/**
	 * <pre>
	 * Mapから値をStringで取得します。
	 * 値がStringでない場合はnullを返します。
	 * </pre>
	 * @param map 対象のMap
	 * @param key キー
	 * @return String形式の値
	 */
	public static <K> String getString(Map<K, ? extends Object> map, K key) {
		Object value = map.get(key);
		if (value instanceof CharSequence) {
			return value.toString();
		}
		return null;
	}

	/**
	 * <pre>
	 * Mapから値をBooleanで取得します。
	 * 値がBooleanの場合はその値を、
	 * Stringの場合はBoolean.valueOfで取得できる値を、
	 * Numberの場合は0をfalse、それ以外をtrueとした値を、
	 * それ以外はnullを返します。
	 * </pre>
	 * @param map 対象のMap
	 * @param key キー
	 * @return Boolean形式の値
	 */
	public static <K> Boolean getBoolean(Map<K, ? extends Object> map, K key) {
		Object value = map.get(key);
		if (value instanceof Boolean) {
			return (Boolean) value;
		} else if (value instanceof CharSequence) {
			return Boolean.valueOf(value.toString());
		} else if (value instanceof Number) {
			boolean val = ((Number) value).intValue() == 0 ? false : true;
			return Boolean.valueOf(val);
		}
		return null;
	}

	/**
	 * <pre>
	 * Mapから値をbooleanで取得します。
	 * getBooleanで取得できた値がnullの場合はfalseを、
	 * それ以外の場合は取得できた値をbooleanに変換した値を返します。
	 * </pre>
	 * @param map 対象のMap
	 * @param key キー
	 * @return boolean形式の値
	 */
	public static <K> boolean getBooleanValue(Map<K, ? extends Object> map, K key) {
		return getBooleanValue(map, key, false);
	}

	/**
	 * <pre>
	 * Mapから値をNumberで取得します。
	 * Numberの場合はその値を、
	 * CharSequenceの場合はNumberFormatでパースした値を、
	 * パースに失敗した場合および上記以外はnullを返します。
	 * </pre>
	 * @param map 対象のMap
	 * @param key キー
	 * @return Number形式の値
	 */
	public static <K> Number getNumber(Map<K, ? extends Object> map, K key) {
		Object value = map.get(key);
		if (value instanceof Number) {
			return (Number) value;
		} else if (value instanceof CharSequence) {
			String str = value.toString();
			try {
				return NumberFormat.getInstance().parse(str);
			} catch (ParseException e) {
				return null;
			}
		}
		return null;
	}

	/**
	 * <pre>
	 * Mapから値をByteで取得します。
	 * 値がByteでない場合はnullを返します。
	 * </pre>
	 * @param map 対象のMap
	 * @param key キー
	 * @return Byte形式の値
	 */
	public static <K> Byte getByte(Map<K, ? extends Object> map, K key) {
		Number value = getNumber(map, key);
		if (value == null) {
			return null;
		} else if (value instanceof Byte) {
			return (Byte) value;
		}
		return Byte.valueOf(value.byteValue());
	}

	/**
	 * <pre>
	 * Mapから値をbyteで取得します。
	 * getByteで取得できた値がnullの場合は0を、
	 * それ以外の場合は取得できた値をbyteに変換した値を返します。
	 * </pre>
	 * @param map 対象のMap
	 * @param key キー
	 * @return Byte形式の値
	 */
	public static <K> byte getByteValue(Map<K, ? extends Object> map, K key) {
		return getByteValue(map, key, (byte)0);
	}

	public static <K> Integer getInteger(Map<K, ? extends Object> map, K key) {
		Number value = getNumber(map, key);
		if (value == null) {
			return null;
		} else if (value instanceof Integer) {
			return (Integer) value;
		}
		return Integer.valueOf(value.intValue());
	}

	public static <K> int getIntValue(Map<K, ? extends Object> map, K key) {
		return getIntValue(map, key, 0);
	}

	public static <K> Short getShort(Map<K, ? extends Object> map, K key) {
		Number value = getNumber(map, key);
		if (value == null) {
			return null;
		} else if (value instanceof Short) {
			return (Short) value;
		}
		return Short.valueOf(value.shortValue());
	}

	public static <K> short getShortValue(Map<K, ? extends Object> map, K key) {
		return 	getShortValue(map, key, (short)0);
	}

	public static <K> Long getLong(Map<K, ? extends Object> map, K key) {
		Number value = getNumber(map, key);
		if (value == null) {
			return null;
		} else if (value instanceof Long) {
			return (Long) value;
		}
		return Long.valueOf(value.longValue());
	}

	public static <K> long getLongValue(Map<K, ? extends Object> map, K key) {
		return getLongValue(map, key, 0L);
	}

	public static <K> Float getFloat(Map<K, ? extends Object> map, K key) {
		Number value = getNumber(map, key);
		if (value == null) {
			return null;
		} else if (value instanceof Float) {
			return (Float) value;
		}
		return Float.valueOf(value.floatValue());
	}

	public static <K> float getFloatValue(Map<K, ? extends Object> map, K key) {
		return getFloatValue(map, key, 0F);
	}

	public static <K> Double getDouble(Map<K, ? extends Object> map, K key) {
		Number value = getNumber(map, key);
		if (value == null) {
			return null;
		} else if (value instanceof Double) {
			return (Double) value;
		}
		return Double.valueOf(value.doubleValue());
	}

	public static <K> double getDoubleValue(Map<K, ? extends Object> map, K key) {
		return getDoubleValue(map, key, 0D);
	}

	public static <K> BigDecimal getBigDecimal(Map<K, ? extends Object> map, K key) {
		Object value = map.get(key);
		if (value == null) {
			return null;
		} else if (value instanceof BigDecimal) {
			return (BigDecimal) value;
		}
		try {
			return new BigDecimal(value.toString());
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static <K> BigInteger getBigInteger(Map<K, ? extends Object> map, K key) {
		Object value = map.get(key);
		if (value == null) {
			return null;
		} else if (value instanceof BigInteger) {
			return (BigInteger) value;
		}
		try {
			return new BigInteger(value.toString());
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static <K> Date getDate(Map<K, ? extends Object> map, K key) {
		Object value = map.get(key);
		if (value == null) {
			return null;
		} else if (value instanceof Date) {
			return (Date) value;
		} else if (value instanceof CharSequence) {
			try {
				return DateFormat.getInstance().parse(value.toString());
			} catch (ParseException e) {
				return null;
			}
		}
		return null;
	}

	public static <K> java.sql.Date getSqlDate(Map<K, ? extends Object> map, K key) {
		Object value = map.get(key);
		if (value == null) {
			return null;
		} else if (value instanceof java.sql.Date) {
			return (java.sql.Date) value;
		} else if (value instanceof CharSequence) {
			try {
				return java.sql.Date.valueOf(value.toString());
			} catch (IllegalArgumentException e) {
				return null;
			}
		}
		return null;
	}

	public static <K> Timestamp getTimestamp(Map<K, ? extends Object> map, K key) {
		Object value = map.get(key);
		if (value == null) {
			return null;
		} else if (value instanceof Timestamp) {
			return (Timestamp) value;
		} else if (value instanceof CharSequence) {
			try {
				return Timestamp.valueOf(value.toString());
			} catch (IllegalArgumentException e) {
				return null;
			}
		}
		return null;
	}

	public static <K, V extends Object> Map<K, V> getMap(Map<K, ? extends Object> map, K key, Map<K, V> dflt) {
		Map<K, V> value = getMap(map, key);
		return value == null ? dflt : value;
	}

	public static <K, V extends Object> List<V> getList(Map<K, ? extends Object> map, K key, List<V> dflt) {
		List<V> value = getList(map, key);
		return value == null ? dflt : value;
	}

	public static <K> String getString(Map<K, ? extends Object> map, K key, String dflt) {
		String value = getString(map, key);
		return value == null ? dflt : value;
	}

	public static <K> Boolean getBoolean(Map<K, ? extends Object> map, K key, Boolean dflt) {
		Boolean value = getBoolean(map, key);
		return value == null ? dflt : value;
	}

	public static <K> boolean getBooleanValue(Map<K, ? extends Object> map, K key, boolean dflt) {
		Boolean value = getBoolean(map, key);
		if (value == null) {
			return dflt;
		}
		return value.booleanValue();
	}

	public static <K> Number getNumber(Map<K, ? extends Object> map, K key, Number dflt) {
		Number value = getNumber(map, key);
		return value == null ? dflt : value;
	}

	public static <K> Byte getByte(Map<K, ? extends Object> map, K key, Byte dflt) {
		Byte value = getByte(map, key);
		return value == null ? dflt : value;
	}

	public static <K> byte getByteValue(Map<K, ? extends Object> map, K key, byte dflt) {
		Number value = getNumber(map, key);
		if (value == null) {
			return dflt;
		}
		return value.byteValue();
	}

	public static <K> Integer getInteger(Map<K, ? extends Object> map, K key, Integer dflt) {
		Integer value = getInteger(map, key);
		return value == null ? dflt : value;
	}

	public static <K> int getIntValue(Map<K, ? extends Object> map, K key, int dflt) {
		Number value = getNumber(map, key);
		if (value == null) {
			return dflt;
		}
		return value.intValue();
	}

	public static <K> Short getShort(Map<K, ? extends Object> map, K key, Short dflt) {
		Short value = getShort(map, key);
		return value == null ? dflt : value;
	}

	public static <K> short getShortValue(Map<K, ? extends Object> map, K key, Short dflt) {
		Number value = getNumber(map, key);
		if (value == null) {
			return dflt;
		}
		return value.shortValue();
	}

	public static <K> Long getLong(Map<K, ? extends Object> map, K key, Long dflt) {
		Long value = getLong(map, key);
		return value == null ? dflt : value;
	}

	public static <K> long getLongValue(Map<K, ? extends Object> map, K key, long dflt) {
		Number value = getNumber(map, key);
		if (value == null) {
			return dflt;
		}
		return value.longValue();
	}

	public static <K> Float getFloat(Map<K, ? extends Object> map, K key, Float dflt) {
		Float value = getFloat(map, key);
		return value == null ? dflt : value;
	}

	public static <K> float getFloatValue(Map<K, ? extends Object> map, K key, float dflt) {
		Number value = getNumber(map, key);
		if (value == null) {
			return dflt;
		}
		return value.floatValue();
	}

	public static <K> Double getDouble(Map<K, ? extends Object> map, K key, Double dflt) {
		Double value = getDouble(map, key);
		return value == null ? dflt : value;
	}

	public static <K> double getDoubleValue(Map<K, ? extends Object> map, K key, double dflt) {
		Number value = getNumber(map, key);
		if (value == null) {
			return dflt;
		}
		return value.doubleValue();
	}

	public static <K> BigDecimal getBigDecimal(Map<K, ? extends Object> map, K key, BigDecimal dflt) {
		BigDecimal value = getBigDecimal(map, key);
		return value == null ? dflt : value;
	}

	public static <K> BigInteger getBigInteger(Map<K, ? extends Object> map, K key, BigInteger dflt) {
		BigInteger value = getBigInteger(map, key);
		return value == null ? dflt : value;
	}

	public static <K> Date getDate(Map<K, ? extends Object> map, K key, Date dflt) {
		Date value = getDate(map, key);
		return value == null ? dflt : value;
	}

	public static <K> java.sql.Date getSqlDate(Map<K, ? extends Object> map, K key, java.sql.Date dflt) {
		java.sql.Date value = getSqlDate(map, key);
		return value == null ? dflt : value;
	}

	public static <K> Timestamp getTimestamp(Map<K, ? extends Object> map, K key, Timestamp dflt) {
		Timestamp value = getTimestamp(map, key);
		return value == null ? dflt : value;
	}

}
